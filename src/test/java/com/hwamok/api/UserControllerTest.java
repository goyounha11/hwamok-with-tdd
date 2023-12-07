package com.hwamok.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.api.dto.user.UserChangeDto;
import com.hwamok.api.dto.user.UserCreateDto;
import com.hwamok.notice.domain.NoticeRepository;
import com.hwamok.sercurity.UserDetailServiceTest;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Test
  void 유저_생성_성공() throws Exception {
    UserCreateDto.Request request = new UserCreateDto.Request("testId", "1234", "test@gamil.com", "별명", "이름", "1993-01-15");

    mockMvc.perform(post("/user")
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
  void 유저_단건_조회_성공() throws Exception {
    User user = userRepository.save(new User("loginId", "123", "test@gmail.com", "nickname", "name", "1993-01-15"));

    mockMvc.perform(get("/user/{id}", user.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success"),
                    jsonPath("data.id").value(user.getId()),
                    jsonPath("data.password").value("123"),
                    jsonPath("data.email").value("test@gmail.com"),
                    jsonPath("data.nickname").value("nickname"),
                    jsonPath("data.name").value("name"),
                    jsonPath("data.birthDay").value("1993-01-15")
            );
  }

  @Test
  @WithUserDetails
  void 유저_수정_성공() throws Exception {
    User user = userRepository.save(new User("loginId", "123", "test@gmail.com", "nickname", "name", "1993-01-15"));

    UserChangeDto.Request request = new UserChangeDto.Request("123", "test@naver.com", "닉넴요", "이름요", "1993-01-15");
    mockMvc.perform(patch("/user/{id}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success"),
                    jsonPath("data.id").value(user.getId()),
                    jsonPath("data.password").value("123"),
                    jsonPath("data.email").value("test@naver.com"),
                    jsonPath("data.nickname").value("닉넴요"),
                    jsonPath("data.name").value("이름요"),
                    jsonPath("data.birthDay").value("1993-01-15")
            );
  }

  @Test
  @WithUserDetails
  void 유저_탈퇴_성공() throws Exception {
    User user = userRepository.save(new User("loginId", "123", "test@gmail.com", "nickname", "name", "1993-01-15"));

    mockMvc.perform(delete("/user/{id}", user.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success")
            );
  }
}