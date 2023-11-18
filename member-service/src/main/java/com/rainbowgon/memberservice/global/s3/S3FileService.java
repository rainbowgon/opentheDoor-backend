package com.rainbowgon.memberservice.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.rainbowgon.memberservice.global.error.exception.AwsS3ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.directory}")
    private String directory;

    /**
     * 이미지 저장 (from MultipartFile)
     * DB에 저장되는 파일명(directory + path + fileName) 반환
     */
    public String saveFile(String path, MultipartFile multipartFile) throws IOException {

        // S3에 저장되는 파일명
        String S3FileName = generateS3FileName(path, multipartFile.getOriginalFilename());

        // S3 업로드
        uploadFile(multipartFile.getSize(), multipartFile.getContentType(), S3FileName, multipartFile.getInputStream());

        return S3FileName;
    }

    /**
     * 이미지 저장 (from ImageUrl)
     * DB에 저장되는 파일명(directory + path + fileName) 반환
     */
    public String saveFile(String path, String imageUrl, String originalFilename) throws IOException {

        String extension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        ByteArrayOutputStream byteArrayOutputStream = extractByteArrayOutputStreamFromUrl(imageUrl, extension);

        long size = byteArrayOutputStream.size();
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.flush();

        // S3에 저장되는 파일명
        String S3FileName = generateS3FileName(path, originalFilename + "." + extension);

        // S3 업로드
        uploadFile(size, extension, S3FileName, inputStream);

        return S3FileName;
    }

    /**
     * S3 파일명으로 S3 전체 경로 가져오기
     * S3 전체 url 반환
     */
    public String getS3Url(String S3FileName) {
        return amazonS3.getUrl(bucket, S3FileName).toString();
    }


    /**
     * 이미지 파일 s3에 업로드
     */
    private void uploadFile(long size, String extension, String S3FileName, InputStream inputStream) {

        if (amazonS3 != null) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(extension);
            objectMetadata.setContentLength(size);
            amazonS3.putObject(bucket, S3FileName, inputStream, objectMetadata);
        } else {
            throw AwsS3ErrorException.EXCEPTION;
        }
    }

    /**
     * s3 file name 생성
     */
    private String generateS3FileName(String path, String originalFileName) {
        StringBuilder sb = new StringBuilder();
        return sb.append(directory)
                .append("/")
                .append(path)
                .append("/")
                .append(UUID.randomUUID())
                .append("-")
                .append(originalFileName).toString();
    }

    /**
     * 이미지 삭제하기
     */
    public void deleteFile(String S3FileName) {
        if (S3FileName != null) {
            amazonS3.deleteObject(bucket, S3FileName);
        }
    }

    private ByteArrayOutputStream extractByteArrayOutputStreamFromUrl(String imageUrl, String extension) throws IOException {

        URL imgURL = new URL(imageUrl);
        BufferedImage image = ImageIO.read(imgURL);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, extension, baos);

        return baos;
    }

}
