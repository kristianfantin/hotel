package br.com.hotel.http.gateway;

import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.utils.ServiceExceptionBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class HotelGateway {

    private final RestTemplate restTemplate;

    @Value("${api.url.description}")
    private String urlHotelSearch;

    @Value("${api.url.detail}")
    private String urlDetailHotel;

    public List<CityDTO> findByCity(Long cityId) {
        final String endpoint = urlHotelSearch.replace("{ID_da_Cidade}", cityId.toString());
        final CityDTO[] cities = restTemplate.getForObject(endpoint, CityDTO[].class);

        if (Arrays.stream(Objects.requireNonNull(cities)).findAny().isEmpty()) {
            throw ServiceExceptionBuilder.throwNotFoundException(cityId);
        }

        return Arrays.stream(Objects.requireNonNull(cities)).collect(Collectors.toList());
    }

    public CityDTO findByHotel(Long hotelId) {
        final String endpoint = urlDetailHotel.replace("{ID_Do_Hotel}", hotelId.toString());
        final CityDTO[] cities = restTemplate.getForObject(endpoint, CityDTO[].class);

        final Optional<CityDTO> first = Arrays.stream(Objects.requireNonNull(cities)).findFirst();

        if (first.isPresent())
            return first.get();

        throw ServiceExceptionBuilder.throwNotFoundException(hotelId);
    }
}
