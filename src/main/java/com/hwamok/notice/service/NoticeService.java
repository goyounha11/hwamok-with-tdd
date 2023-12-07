package com.hwamok.notice.service;

import com.hwamok.notice.domain.Notice;

public interface NoticeService {
  Notice create(String title, String content);

  Notice getNotice(long id);

  Notice update(Long id, String title, String content);
}
