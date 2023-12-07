package com.hwamok.notice.service;

import com.hwamok.core.exception.HwamokException;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import static com.hwamok.core.exception.ExceptionCode.NOT_FOUND_NOTICE;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
  private final NoticeRepository noticeRepository;
  @Override
  public Notice create(String title, String content) {
    // some logic..
    Notice notice = noticeRepository.save(new Notice(title, content));

    return notice;
  }

  @Override
  public Notice getNotice(long id) {
    return noticeRepository.findById(id)
            .orElseThrow(() -> new HwamokException(NOT_FOUND_NOTICE));
  }

  @Override
  public Notice update(Long id, String title, String content) {
    Notice notice = noticeRepository.findById(id).orElseThrow(() -> new HwamokException(NOT_FOUND_NOTICE));

    notice.update(title, content);

    return notice;
  }
}
