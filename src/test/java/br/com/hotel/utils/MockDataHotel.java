package br.com.hotel.utils;

import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.http.dto.PriceDTO;
import br.com.hotel.http.dto.RoomDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockDataHotel {

    public static final String PORTO_SEGURO = "1032";
    public static final long HOTEL_ID = 1L;
    private static final String CITY_NAME = "Porto Seguro";

    public CityDTO[] getDataFromUrl() {
        final List<CityDTO> list = List.of(getPortoSeguroCity());
        CityDTO[] cities = new CityDTO[list.size()];
        return list.toArray(cities);
    }

    private CityDTO getPortoSeguroCity() {
        return CityDTO.builder()
                .id(HOTEL_ID)
                .cityCode(Long.valueOf(PORTO_SEGURO))
                .cityName(CITY_NAME)
                .name("Hotel Teste 1")
                .rooms(getRooms())
                .build();
    }

    private List<RoomDTO> getRooms() {
        return List.of(getRoomStandard());
    }

    private RoomDTO getRoomStandard() {
        return RoomDTO.builder()
                .roomID(HOTEL_ID)
                .categoryName("Standard")
                .price(PriceDTO.builder()
                        .child(591.06)
                        .adult(854.74)
                        .build())
                .build();
    }

}