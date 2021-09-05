package br.com.hotel.http.gateway;

import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.utils.ServiceExceptionBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class HotelGateway {

    private final RestTemplate restTemplate;

    @Value("${api.url.description}")
    private String urlHotelSearch;

    public List<CityDTO> find(HttpServletRequest request, Long cityId) {
        final String endpoint = urlHotelSearch.replace("{ID_da_Cidade}", cityId.toString());
        final CityDTO[] cities = restTemplate.getForObject(endpoint, CityDTO[].class);

        if (Arrays.stream(Objects.requireNonNull(cities)).findAny().isEmpty()) {
            throw ServiceExceptionBuilder.throwNotFoundException(cityId);
        }

        return Arrays.stream(Objects.requireNonNull(cities)).collect(Collectors.toList());
    }

}
