package com.hwamok.notice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class NoticeRepositoryTest {
  @Autowired
  private NoticeRepository noticeRepository;

  @Test
  void 공지사항_저장_성공() {
    Notice notice =
            noticeRepository.save(new Notice("제목", "본문"));

    assertThat(notice.getId()).isNotNull();
  }
}