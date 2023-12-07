package com.hwamok.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.api.dto.notice.NoticeUpdateDto;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.security.HwamokUser;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static fixture.NoticeFixture.createNotice;
import static fixture.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//시나리오 테스트
// API제공 - Endpoint --> localhost:8080/notice
// 생성 시나리오 request -> c -> s -> r -> reponse
// 가짜 요청 -> MockMvc(가짜 요청을 해주는 라이브러리) - Spring의 기본(공식?) 라이브러리

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class NoticeControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private NoticeRepository noticeRepository;

  private Notice notice;


  @BeforeEach
  void setUp() {
    notice = noticeRepository.save(createNotice());
  }

  @Test
  @WithUserDetails
  void 공지사항_생성_성공() throws Exception {
    NoticeCreateDto.Request request = new NoticeCreateDto.Request("제목", "본문");

    mockMvc.perform(post("/notice")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success")
            );
  }

  @Test
  @WithUserDetails
  void 공지사항_수정_성공() throws Exception {

    NoticeUpdateDto request = new NoticeUpdateDto("수정된 제목", "수정된 본문");

    mockMvc.perform(patch("/notice/{id}", notice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success")
            );
  }

  @ParameterizedTest
  @NullAndEmptySource
  @WithUserDetails
  void 공지사항_수정_실패__제목이_빈값_혹은_null(String title) throws Exception {
    NoticeUpdateDto request = new NoticeUpdateDto(title, "수정된 본문");

    mockMvc.perform(patch("/notice/{id}", notice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("E001"),
                    jsonPath("message").value("필수 값이 누락 되었습니다.")
            );
  }

  @ParameterizedTest
  @NullAndEmptySource
  @WithUserDetails
  void 공지사항_수정_실패__본문이_빈값_혹은_null(String content) throws Exception {
    NoticeUpdateDto request = new NoticeUpdateDto("수정된 제목", content);

    mockMvc.perform(patch("/notice/{id}", notice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("E001"),
                    jsonPath("message").value("필수 값이 누락 되었습니다.")
            );
  }

  @Test
  @WithUserDetails
  void 공지사항_수정_실패__존재하지_않는_공지사항() throws Exception {
    NoticeUpdateDto request = new NoticeUpdateDto("수정된 제목", "수정된 본문");

    mockMvc.perform(patch("/notice/{id}", -1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("E002"),
                    jsonPath("message").value("공지사항을 찾을 수 없습니다.")
            );
  }
}