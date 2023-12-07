package com.hwamok.api;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goyounha11.docs.DocsUtil;
import com.hwamok.api.dto.auth.LoginDto;
import com.hwamok.user.domain.UserRepository;
import fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class AuthControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void 로그인_성공() throws Exception {
    // 3가지 방법의 문서 자동화
    // 1. Swagger
    // 장점
    // -> Annotation을 기반으로한 문서화 방법 -> 사용이 간편
    // -> 이쁨
    // -> API문서에서 실제 요청을 보내고 응답을 받아볼 수 있음
    // -> 사용자가 사용하기 좋음
    // -> 빠른 문서화가 가능하다.

    // 단점
    // -> 테스트코드가 없어도 문서화가 가능
    // -> 어노테이션들이 Application Code에 작성됨


    // 2. RestDocs
    // 장점
    // -> 테스트가 통과하지 않으면 문서도 안 만들어진다.
    // -> Application Code를 침범하지 않는다.
    // -> Snippet(조각)라는 것을 기반으로 하기 때문에 만들어진 문서는 실제 요청과 응답의 값을 문서화 한다.

    // 단점
    // -> 못생겼다
    // -> 테스트를 바로 해볼 수 없고 curl 복사가 필요하다. 불친절함
    // -> 문서화 하는데 시간이 오래걸림


    // 3. openApi3 + SwaggerUI (epages) --> 제일 좋아함
    // Swagger의 장점 + RestDocs의 장점 모아다가 문서화 함
    // 장점
    // -> 테스트가 통과하지 않으면 문서도 안 만들어진다.
    // -> Application Code를 침범하지 않는다.
    // -> 이쁨
    // -> API문서에서 실제 요청을 보내고 응답을 받아볼 수 있음
    // -> 사용자가 사용하기 좋음
    // -> Snippet(조각)라는 것을 기반으로 하기 때문에 만들어진 문서는 실제 요청과 응답의 값을 문서화 한다.


    userRepository.save(UserFixture.createUser("testId", passwordEncoder.encode("1234")));

    LoginDto.Request request = new LoginDto.Request("testId", "1234");

    mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success"),
                    jsonPath("data.accessToken").isNotEmpty(),
                    jsonPath("data.refreshToken").isNotEmpty()
            )
            .andDo(document("auth/login",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    ResourceDocumentation.resource(
                            new ResourceSnippetParametersBuilder()
                                    .tag("Auth")
                                    .description("로그인 API")
                                    .requestFields(
                                            List.of(
                                                    PayloadDocumentation.fieldWithPath("loginId").type(JsonFieldType.STRING).description("testId"),
                                                    PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("1234")
                                                    )
                                    )
                                    .responseFields(
                                            List.of(
                                                    PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING).description("S000"),
                                                    PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("success"),
                                                    PayloadDocumentation.fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("access Token"),
                                                    PayloadDocumentation.fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("refresh Token")
                                            )
                                    )
                                    .requestSchema(Schema.schema("LoginDto.Request"))
                                    .responseSchema(Schema.schema("LoginDto.Response"))
                                    .build()
                    )
            ));

  }

  @Test
  void 문서_자동화_테스트() throws Exception {
    userRepository.save(UserFixture.createUser("testId", passwordEncoder.encode("1234")));

    LoginDto.Request request = new LoginDto.Request("testId", "1234");

    ResultActions resultActions = mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)));


    resultActions.andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("code").value("S000"),
                    jsonPath("message").value("success"),
                    jsonPath("data.accessToken").isNotEmpty(),
                    jsonPath("data.refreshToken").isNotEmpty()
            );

    resultActions.andDo(
                    DocsUtil.createDocs(
                            "Auth2",
                            "auth/login2",
                            "로그인 자동화 API",
                            resultActions
                            )
            );
  }
}