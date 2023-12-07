package com.hwamok.notice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;

import static com.hwamok.core.Preconditions.require;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10)
  private String title;

  private String content;

  private Instant createdAt = Instant.now();

  public Notice(String title, String content) {
    // require
    // title 입장에서 긍정문 => 값이 있다
    // 부정문 => 값이 null 이거나 빈 값

    // 도메인 보호 로직 --> DomainDrivenDesign 의 기본 수칙 중 1개
    // 한글 검증
    require(Strings.isNotBlank(title));
    require(Strings.isNotBlank(content));
    require(title.length() < 11);

    this.title = title;
    this.content = content;
  }

  public void update(String title, String content) {
    require(Strings.isNotBlank(title));
    require(Strings.isNotBlank(content));
    require(title.length() < 11);

    this.title = title;
    this.content = content;
  }
}
