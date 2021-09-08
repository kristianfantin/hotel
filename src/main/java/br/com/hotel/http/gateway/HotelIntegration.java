package br.com.hotel.http.gateway;

import br.com.hotel.http.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class HotelIntegration {

    private final RestTemplate restTemplate;

    @Value("${api.url.detail}")
    private String urlDetailHotel;

    public CityDTO[] findByHotel(Long hotelId) {
        final String endpoint = urlDetailHotel.replace("{ID_Do_Hotel}", hotelId.toString());
        return restTemplate.getForObject(endpoint, CityDTO[].class);
    }

}
