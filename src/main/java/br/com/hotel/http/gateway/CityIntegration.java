package br.com.hotel.http.gateway;

import br.com.hotel.http.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
class CityIntegration {

    private final RestTemplate restTemplate;

    @Value("${api.url.description}")
    private String urlHotelSearch;

    CityDTO[] find(Long cityId) {
        final String endpoint = urlHotelSearch.replace("{ID_da_Cidade}", cityId.toString());
        return restTemplate.getForObject(endpoint, CityDTO[].class);
    }

}
