package com.hwamok.api;

import com.hwamok.api.dto.notice.NoticeCreateDto;
import com.hwamok.api.dto.notice.NoticeUpdateDto;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.notice.domain.Notice;
import com.hwamok.notice.service.NoticeService;
import com.hwamok.notice.service.NoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hwamok.core.exception.ExceptionCode.NOT_FOUND_NOTICE;

@RestController
@RequestMapping("/notice")
public class NoticeController {
  private final NoticeService noticeService;

  public NoticeController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  @PostMapping
  public ResponseEntity<ApiResult<?>> createNotice(@RequestBody NoticeCreateDto.Request request) {
    noticeService.create(request.getTitle(), request.getContent());
    return Result.created();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResult<?>> updateNotice(@PathVariable Long id, @RequestBody NoticeUpdateDto dto) {
    noticeService.update(id, dto.getTitle(), dto.getContent());
    return Result.ok();
  }
}
