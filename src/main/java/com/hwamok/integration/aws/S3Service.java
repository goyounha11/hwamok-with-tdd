package com.hwamok.integration.aws;

import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
  List<Pair<String, String>> upload(List<MultipartFile> multipartFiles);

  boolean delete(String savedFileName);
}
