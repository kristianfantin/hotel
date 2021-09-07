package br.com.hotel.http.gateway;

import br.com.hotel.domain.service.CalculateTotalPrice;
import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.utils.ServiceExceptionBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class HotelGateway {

    private static Logger log = Logger.getLogger(HotelGateway.class.getName());

    private final CalculateTotalPrice calculateTotalPrice;
    private final CityIntegration cityIntegration;
    private final HotelIntegration hotelIntegration;

    public List<CityDTO> findByCity(Long cityId) {
        final CityDTO[] cities = callSearchByCity(cityId);
        checkEmpty(cityId, cities);
        return Arrays.stream(Objects.requireNonNull(cities)).collect(Collectors.toList());
    }

    public CityDTO findByHotel(Long hotelId) {
        final CityDTO[] cities = hotelIntegration.findByHotel(hotelId);
        if (cities.length == 0)
            throw ServiceExceptionBuilder.throwNotFoundException(hotelId);

        return cities[0];
    }

    public List<CityDTO> find(Long cityId, LocalDate checkInDate, LocalDate checkOutDate, Integer numberOfAdults, Integer numberOfChildren) {
        final LocalDateTime start = LocalDateTime.now();
        log.log(configLevel(), "Inicio: {0}", start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));

        final CityDTO[] cities = callSearchByCity(cityId);

        Arrays.stream(cities).forEach(dto -> calculateTotalPrice.execute(dto, checkInDate, checkOutDate, numberOfAdults, numberOfChildren));

        final LocalDateTime end = LocalDateTime.now();
        log.log(configLevel(), "Fim: {0}", end.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")));
        log.log(configLevel(),"***** TOTAL: {0}", start.until(end, ChronoUnit.SECONDS));

        checkEmpty(cityId, cities);

        return Arrays.stream(cities).collect(Collectors.toList());
    }

    private Level configLevel() {
        return Level.FINE;
    }

    private CityDTO[] callSearchByCity(Long cityId) {
        return cityIntegration.find(cityId);
    }

    private void checkEmpty(Long cityId, CityDTO[] cities) {
        if (Arrays.stream(Objects.requireNonNull(cities)).findAny().isEmpty()) {
            throw ServiceExceptionBuilder.throwNotFoundException(cityId);
        }
    }

}
