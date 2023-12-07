package com.hwamok.notice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static fixture.NoticeFixture.createNotice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class NoticeTest {
  @Test
  void 공지사항_생성_성공() {
    // Fixture
    Notice notice = new Notice("제목", "본문");

    //Assertions.assertThat(검증을 원하는 값).isEqualTo(기댓값)
    assertThat(notice.getId()).isNull();
    assertThat(notice.getTitle()).isEqualTo("제목");
    assertThat(notice.getContent()).isEqualTo("본문");
    assertThat(notice.getCreatedAt()).isNotNull();
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 공지사항_생성_실패__제목이_빈값_혹은_null(String title) {
    assertThatIllegalArgumentException().
            isThrownBy(() -> createNotice(title, "본문"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 공지사항_생성_실패__본문이_빈값_혹은_null(String content) {
    assertThatIllegalArgumentException().
            isThrownBy(() -> createNotice("제목", content));
  }

  @Test
  void 공지사항_생성_실패__제목이_11자_이상() {
    assertThatIllegalArgumentException()
            .isThrownBy(() -> createNotice("제목제목제목제목제목제", "본문"));
  }

  @Test
  void 공지사항_수정_성공() {
    Notice notice = createNotice();

    notice.update("수정된 제목", "수정된 본문");

    assertThat(notice.getTitle()).isEqualTo("수정된 제목");
    assertThat(notice.getContent()).isEqualTo("수정된 본문");
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 공지사항_수정_실패__제목이_null_혹은_빈값(String title) {
    Notice notice = createNotice();

    assertThatIllegalArgumentException()
            .isThrownBy(() -> notice.update(title, "수정된 본문"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 공지사항_수정_실패__본문이_null_혹은_빈값(String content) {
    Notice notice = createNotice();

    assertThatIllegalArgumentException()
            .isThrownBy(() -> notice.update("수정된 제목", content));
  }

  @Test
  void 공지사항_수정_실패__제목이_11자_이상() {
    Notice notice = createNotice();

    assertThatIllegalArgumentException()
            .isThrownBy(() -> notice.update("제목제목제목제목제목제", "수정된 본문"));
  }
}