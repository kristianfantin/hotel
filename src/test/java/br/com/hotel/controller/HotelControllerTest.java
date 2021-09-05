package br.com.hotel.controller;

import br.com.hotel.config.FunctionalTest;
import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.http.dto.PriceDTO;
import br.com.hotel.http.dto.RoomDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@FunctionalTest
class HotelControllerTest {

    private static final String URL_TEMPLATE = "/api/hotels/avail";
    private static final String PORTO_SEGURO = "1032";
    private static final String CITY_NAME = "Porto Seguro";

    private final MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    HotelControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void shouldFindHotel() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(getDescriptionFromAvail());

        Map<String, Object> params = new HashMap<>();
        params.put("cityId", PORTO_SEGURO);
        final MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilderGET(params, URL_TEMPLATE);
        final ResultActions resultActions = mockMvc.perform(request);

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertNotNull(getContentAsString(resultActions));
        assertNotEquals("", getContentAsString(resultActions));
    }

    @Test
    void shouldNotFindAnyHotelWhenCityIsZero() throws Exception {
        CityDTO[] empty = {};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(empty);

        Map<String, Object> params = new HashMap<>();
        params.put("cityId", 0);
        final MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilderGET(params, URL_TEMPLATE);
        final ResultActions resultActions = mockMvc.perform(request);

        assertEquals(HttpStatus.NOT_FOUND.value(), resultActions.andReturn().getResponse().getStatus());
        assertNotNull(getContentAsString(resultActions));
        assertNotEquals("", getContentAsString(resultActions));
        Assertions.assertTrue(getContentAsString(resultActions).contains("No record found for id 0"));
    }

    private String getContentAsString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }

    private CityDTO[] getDescriptionFromAvail() {
        final List<CityDTO> list = List.of(getPortoSeguroCity());
        CityDTO[] cities = new CityDTO[list.size()];
        return list.toArray(cities);
    }

    private CityDTO getPortoSeguroCity() {
        return CityDTO.builder()
                .id(1L)
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
                .roomID(1L)
                .categoryName("Standard")
                .price(PriceDTO.builder()
                        .child(BigDecimal.valueOf(591.06))
                        .adult(BigDecimal.valueOf(854.74))
                        .build())
                .build();
    }

    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilderGET(Map<String, Object> params,
                                                                             String urlTemplate)  {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON);

        params.forEach((nameParam, valueParam) -> request.param(nameParam, valueParam.toString()));

        return request;
    }

}
