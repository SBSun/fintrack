package com.sun.fintrack.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sun.fintrack.common.exception.ValidationException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * S3 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * 파일 업로드
   */
  public String upload(Long memberSeq, String feature, MultipartFile file) {
    final String fileName = createFileName(memberSeq, FilenameUtils.getExtension(file.getOriginalFilename()));
    final String filePath = createFilePath(feature);
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    metadata.setContentType(file.getContentType());

    try {
      // 원본 파일 업로드
      amazonS3.putObject(new PutObjectRequest(bucket + filePath, fileName, file.getInputStream(), metadata));

      // 결과 반환
      return filePath + "/" + fileName;
    } catch (Exception e) {
      log.info("S3 파일 업로드 중 에러 발생 - " + e.getMessage(), e);
    }
    throw new ValidationException("system.upload");
  }

  private String createFileName(long memberSeq, String extension) {
    return memberSeq + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")) + "." +
        StringUtils.lowerCase(extension);
  }

  private String createFilePath(String feature) {
    final LocalDate now = LocalDate.now();
    return "/" + feature + "/" + now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
}
