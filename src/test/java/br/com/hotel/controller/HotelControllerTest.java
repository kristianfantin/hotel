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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@FunctionalTest
class HotelControllerTest {

    private static final String URL_TEMPLATE = "/api/hotels";

    private final MockMvc mockMvc;
    private final MockDataHotel mockDataHotel;
    private final MockRequestBuilderUtils requestBuilderUtils;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    HotelControllerTest(MockMvc mockMvc,
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
        final MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilderGET(params, URL_TEMPLATE + "/avail");
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
        final MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilderGET(params, URL_TEMPLATE + "/avail");
        final ResultActions resultActions = mockMvc.perform(request);

        assertEquals(HttpStatus.NOT_FOUND.value(), resultActions.andReturn().getResponse().getStatus());
        assertNotNull(getContentAsString(resultActions));
        assertNotEquals("", getContentAsString(resultActions));
        Assertions.assertTrue(getContentAsString(resultActions).contains("No record found for id 0"));
    }

    @Test
    void shouldFindHotelByHotelId() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockDataHotel.getDataFromUrl());

        Map<String, Object> params = new HashMap<>();
        params.put("hotelId", MockDataHotel.HOTEL_ID);
        final MockHttpServletRequestBuilder request = getMockHttpServletRequestBuilderGET(params, URL_TEMPLATE);
        final ResultActions resultActions = mockMvc.perform(request);

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertNotNull(getContentAsString(resultActions));
        assertNotEquals("", getContentAsString(resultActions));
    }

    @Test
    void shouldNotFindAnyHotelWhenHotelIdIsZero() throws Exception {
        CityDTO[] empty = {};
        when(restTemplate.getForObject(anyString(), any())).thenReturn(empty);

        Map<String, Object> params = new HashMap<>();
        params.put("hotelId", 0);
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
