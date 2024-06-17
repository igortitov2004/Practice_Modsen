package com.example.practice_modsen_shop;

import com.example.practice_modsen_shop.advice.ErrorHandlingControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TestController.class)
@Import(ErrorHandlingControllerAdvice.class)
public class ErrorHandlingControllerAdviceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHandleRuntimeException() throws Exception {
        mockMvc.perform(get("/test/runtime-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("A runtime exception occurred: runtime exception"));
    }

    @Test
    public void testHandleConstraintViolationException() throws Exception {
        mockMvc.perform(get("/test/constraint-violation-exception")
                .param("field", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"violations\":[{\"fieldName\":\"field\",\"message\":\"error\"}]}"));
    }

    @Test
    public void testHandleMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(get("/test/method-argument-not-valid-exception"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"violations\":[{\"fieldName\":\"field\",\"message\":\"error\"}]}"));
    }
}