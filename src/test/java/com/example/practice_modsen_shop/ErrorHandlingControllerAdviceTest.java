package com.example.practice_modsen_shop;

import com.example.practice_modsen_shop.advice.ErrorHandlingControllerAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ErrorHandlingControllerAdvice.class)
public class ErrorHandlingControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testArithmeticException() throws Exception {
        mockMvc.perform(get("/triggerArithmeticException"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Arithmetic exception occurred"));
    }

    @Test
    void testMethodArgumentNotValidException() throws Exception {
        String requestBody = "{\"name\": \"\"}"; // Invalid request body

        mockMvc.perform(post("/triggerMethodArgumentNotValidException")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"violations\":[{\"fieldName\":\"name\",\"message\":\"Name must be at least 1 character long\"}]}"));
    }

    @Test
    void testConstraintViolationException() throws Exception {
        String requestBody = "{\"name\": null}"; // Invalid request body

        mockMvc.perform(post("/triggerConstraintViolationException")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"violations\":[{\"fieldName\":\"name\",\"message\":\"Name cannot be null\"}]}"));
    }
}