package br.com.hotel.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class MockRequestBuilderUtils {

    public MockHttpServletRequestBuilder getMockHttpServletRequestBuilderGET(Map<String, Object> params,
                                                                              String url)  {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON);

        params.forEach((nameParam, valueParam) -> request.param(nameParam, valueParam.toString()));

        return request;
    }

}