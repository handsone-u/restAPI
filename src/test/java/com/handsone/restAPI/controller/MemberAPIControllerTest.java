package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(value = MemberAPIController.class)
class MemberAPIControllerTest {

    private MockMvc mvc;
    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new MemberAPIController(memberService, new ModelMapper()))
                .build();
    }

    @Test
    void joinTest() {
        MemberDto dto = new MemberDto(0l, "id1", "pass1", "nick1", null);
        BDDMockito.given(memberService.signUp(any()))
                .willReturn(new Member(1L, dto.getUserId(), dto.getPassword(), dto.getNickName(), dto.getAddress()));

    }
}