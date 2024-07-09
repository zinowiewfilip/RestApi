package com.java.restapi.controller;

import com.java.restapi.dto.RestApiResponseDTO;
import com.java.restapi.mapper.RestApiResponseMapperImpl;
import com.java.restapi.service.RestApiService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestApiController.class)
@Import(RestApiResponseMapperImpl.class)
class RestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RestApiResponseDTO restApiResponseDTO;

    @MockBean
    private RestApiService restApiService;

    @Test
    public void testApiResponse() throws Exception {
        String testLogin = "testLogin";

        doReturn("1").when(restApiResponseDTO).getId();
        doReturn("test").when(restApiResponseDTO).getName();
        doReturn("test").when(restApiResponseDTO).getLogin();
        doReturn("10").when(restApiResponseDTO).getCalculations();
        doReturn("test").when(restApiResponseDTO).getType();
        doReturn("test").when(restApiResponseDTO).getCreatedAt();
        doReturn(restApiResponseDTO).when(restApiService).getInformationAboutUser(anyString());
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/users/{login}", testLogin)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\"," +
                        "\"login\":\"test\"," +
                        "\"name\":\"test\"," +
                        "\"type\":\"test\"," +
                        "\"avatarUrl\":null," +
                        "\"createdAt\":\"test\"," +
                        "\"calculations\":\"10\"}"));
    }
}