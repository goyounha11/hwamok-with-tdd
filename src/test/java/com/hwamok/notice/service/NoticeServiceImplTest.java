package com.hwamok.notice.service;

import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.hwamok.core.exception.ExceptionCode.*;
import static com.hwamok.core.exception.HwamokExceptionTest.assertThatHwamokException;
import static fixture.NoticeFixture.createNotice;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
class NoticeServiceImplTest {

  @Autowired
  private NoticeService noticeService;

  @Autowired
  private NoticeRepository noticeRepository;

  @Test
  void 공지사항_생성_성공() {
    Notice notice = noticeService.create("1234", "12345");

    assertThat(notice.getId()).isNotNull();
  }


  @Test
  void 공지사항_단건_조회_성공() {
    Notice notice = noticeRepository.save(createNotice());

    Notice foundNotice = noticeService.getNotice(notice.getId());

    assertThat(foundNotice.getId()).isEqualTo(notice.getId());
  }

  @Test
  void 공지사항_단건_조회_실패__공지사항이_존재하지않음() {
    assertThatHwamokException(NOT_FOUND_NOTICE)
            .isThrownBy(() -> noticeService.getNotice(-1L));
  }

  @Test
  void 공지사항_수정_성공() {
    Notice notice = noticeRepository.save(createNotice());

    Notice foundNotice = noticeRepository.findById(notice.getId()).orElseThrow();

    foundNotice = noticeService.update(foundNotice.getId(), "수정된 제목", "수정된 본문");

    assertThat(foundNotice.getTitle()).isEqualTo("수정된 제목");
    assertThat(foundNotice.getContent()).isEqualTo("수정된 본문");
  }
}