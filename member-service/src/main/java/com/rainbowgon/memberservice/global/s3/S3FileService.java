package com.rainbowgon.memberservice.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
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

    @Value("${spring.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.aws.s3.directory}")
    private String directory;

    /**
     * 이미지 저장
     */
    public String saveFile(String path, MultipartFile multipartFile) throws IOException {

        String savedFilename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        uploadFile(path, multipartFile.getSize(), multipartFile.getContentType(), savedFilename,
                   multipartFile.getInputStream());

        return savedFilename;
    }

    /**
     * 이미지 s3에 업로드
     */
    public void fileUpload(MultipartFile image) throws Exception {

        if (amazonS3 != null) {

            String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());
            objectMetadata.setHeader("filename", image.getOriginalFilename());
            amazonS3.putObject(new PutObjectRequest(bucket + "/contact/" + today, fileList.get(i),
                                                    files.get(i).getInputStream(), objectMetadata));
        } else {
            throw new AppException(ErrorType.aws_credentials_fail, null);
        }
    }

    /**
     * 이미지 저장
     */
    public String saveFile(String path, String imageUrl, String originalFilename) throws IOException {

        String extension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        ByteArrayOutputStream byteArrayOutputStream = extractByteArrayOutputStreamFromUrl(imageUrl, extension);

        long size = byteArrayOutputStream.size();
        InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.flush();

        String savedFilename = UUID.randomUUID() + "-" + originalFilename + "." + extension;

        uploadFile(path, size, extension, savedFilename, is);

        return savedFilename;
    }

    /**
     * 이미지 S3 url 가져오기
     */
    public String getS3Url(String imageUrl) {
        return amazonS3.getUrl(bucket, imageUrl).toString();
    }

    /**
     * 이미지 삭제하기
     */
    public void deleteImage(String imageUrl) {
        if (imageUrl != null) {
            amazonS3.deleteObject(bucket, imageUrl);
        }
    }

    private ByteArrayOutputStream extractByteArrayOutputStreamFromUrl(String imageUrl, String extension) throws IOException {

        URL imgURL = new URL(imageUrl);
        BufferedImage image = ImageIO.read(imgURL);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, extension, baos);

        return baos;
    }

    private void uploadFile(String imageUrl, long size, String extension, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        metadata.setContentType(extension);
        amazonS3.putObject(bucket, imageUrl, inputStream, metadata);
    }

}
