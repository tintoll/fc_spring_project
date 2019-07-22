package com.fc.project3.mycontact.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;


// 스프링 부트 테스트를 나타내는 어노테이션
@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    // juni4에서는 public을 강제하고 있지만 5에서는 디폴트를 사용해되 상관없다.
    // junit5에서는 Test 어노테이션이 다른것을 사용한다.
    @Test
    void helloWorld() {
        System.out.println(helloWorldController.helloWorld());
        assertThat(helloWorldController.helloWorld()).isEqualTo("HelloWorld");
    }

    @Test
    void mockMvcTest() throws Exception {
        // mockMvc 사용설정
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/helloWorld"))
                // 결과를 상세하기 보게 하기
                .andDo(MockMvcResultHandlers.print())
                // 상태코드가 200인지 체크
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 결과값이 HelloWorld인지 체크
                .andExpect(MockMvcResultMatchers.content().string("HelloWorld"));
    }

}