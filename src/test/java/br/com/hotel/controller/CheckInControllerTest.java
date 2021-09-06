package br.com.hotel.controller;

import br.com.hotel.config.FunctionalTest;
import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.utils.MockDataHotel;
import br.com.hotel.utils.MockRequestBuilderUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@FunctionalTest
class CheckInControllerTest {

    private static final String URL_TEMPLATE = "/api/hotels/check-in";

    private final MockMvc mockMvc;
    private final MockDataHotel mockDataHotel;
    private final MockRequestBuilderUtils requestBuilderUtils;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    CheckInControllerTest(MockMvc mockMvc,
                          MockDataHotel mockDataHotel,
                          MockRequestBuilderUtils requestBuilderUtils) {
        this.mockMvc = mockMvc;
        this.mockDataHotel = mockDataHotel;
        this.requestBuilderUtils = requestBuilderUtils;
    }

    @Test
    void shouldFindHotel() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockDataHotel.getDataFromUrl());

        Map<String, Object> params = new HashMap<>();
        params.put("cityId", MockDataHotel.PORTO_SEGURO);
        params.put("checkInDate", LocalDate.of(2021,11,12));
        params.put("checkOutDate", LocalDate.of(2021,11,16));
        params.put("numberOfAdults", 2);
        params.put("numberOfChildren", 1);

        final MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilderGET(params, URL_TEMPLATE);
        final ResultActions resultActions = mockMvc.perform(request);

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        final String contentAsString = getContentAsString(resultActions);
        assertNotNull(contentAsString);
        assertNotEquals("", contentAsString);
        assertTrue(contentAsString.contains("totalPrice\":6428.55"));
    }

    @Test
    void shouldNotFindAnyHotelWhenCityIsZero() throws Exception {
        CityDTO[] empty = {};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(empty);

        Map<String, Object> params = new HashMap<>();
        params.put("cityId", 0);
        params.put("checkInDate", LocalDate.of(2021,11,12));
        params.put("checkOutDate", LocalDate.of(2021,11,16));
        params.put("numberOfAdults", 2);
        params.put("numberOfChildren", 1);

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

    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilderGET(Map<String, Object> params, String url) {
        return requestBuilderUtils.getMockHttpServletRequestBuilderGET(params, url);
    }

}
