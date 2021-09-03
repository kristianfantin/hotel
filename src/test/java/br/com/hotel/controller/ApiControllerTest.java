package br.com.hotel.controller;

import br.com.hotel.config.FunctionalTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@FunctionalTest
class ApiControllerTest {

    private static final String URL_TEMPLATE = "/api";

    private final MockMvc mockMvc;

    @Autowired
    ApiControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void shouldValidateApiOnLine() throws Exception {
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL_TEMPLATE);
        final ResultActions resultActions = mockMvc.perform(request);

        assertEquals("We are online", getContentAsString(resultActions));
    }

    private String getContentAsString(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }
}
