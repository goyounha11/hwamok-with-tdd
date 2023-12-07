package com.hwamok.integration.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
  private final AmazonS3 amazonS3;

  private static final String BUCKET_NAME = "hwamok1";

  @Override
  public List<Pair<String, String>> upload(List<MultipartFile> multipartFiles) {
    List<Pair<String, String>> pairs = new ArrayList<>();

    multipartFiles.forEach(file -> {
      String savedFileName = createFileName(file.getOriginalFilename());
      String fileName = file.getOriginalFilename();

      ObjectMetadata objectMetadata = new ObjectMetadata();

      objectMetadata.setContentType(file.getContentType());
      objectMetadata.setContentLength(file.getSize());

      try(InputStream inputStream = file.getInputStream()) {
        amazonS3.putObject(BUCKET_NAME, savedFileName, inputStream, objectMetadata);
      }catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 업로드에 실패 하였습니다.");
      }

      pairs.add(Pair.of(fileName, savedFileName));
    });

    return pairs;
  }

  @Override
  public boolean delete(String savedFileName) {
    if(amazonS3.doesObjectExist(BUCKET_NAME, savedFileName)) {
      amazonS3.deleteObject(new DeleteObjectRequest(BUCKET_NAME, savedFileName));

      return true;
    }

    return false;
  }

  private String createFileName(String name) {
    return "F_"  + System.currentTimeMillis() + getExtension(name);
  }

  private String getExtension(String name) {
   try {
     return name.substring(name.lastIndexOf("."));
   }catch (StringIndexOutOfBoundsException e) {
     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 입니다.");
   }
  }
}
