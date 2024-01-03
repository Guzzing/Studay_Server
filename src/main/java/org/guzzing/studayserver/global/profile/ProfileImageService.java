package org.guzzing.studayserver.global.profile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.global.config.S3Config;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ProfileImageService {

    private final S3Config s3Config;
    private final AmazonS3 s3Client;

    public ProfileImageService(S3Config s3Config, AmazonS3 s3Client) {
        this.s3Config = s3Config;
        this.s3Client = s3Client;
    }

    public void uploadProfileImage(MultipartFile multipartFile, String profileImageURI) {
        try {
            final PutObjectRequest putObjectRequest = getPutObjectRequest(multipartFile, profileImageURI);

            s3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            log.warn("해당하는 파일이 없습니다.");
            throw new UncheckedIOException(e);
        } catch (NullPointerException e) {
            log.warn("이미지 파일 이름이 없습니다.");
            throw e;
        }
    }

    private PutObjectRequest getPutObjectRequest(MultipartFile multipartFile, String profileImageURI)
            throws IOException {
        final ObjectMetadata objectMetadata = getObjectMetadata(multipartFile);
        final InputStream inputStream = multipartFile.getInputStream();

        return new PutObjectRequest(
                s3Config.getBucketName(),
                profileImageURI,
                inputStream,
                objectMetadata);
    }

    private ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }

}
