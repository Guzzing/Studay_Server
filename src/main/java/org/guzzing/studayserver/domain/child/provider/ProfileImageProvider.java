package org.guzzing.studayserver.domain.child.provider;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.global.config.S3Config;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class ProfileImageProvider {

    private static final List<String> DEFAULT_IMAGE_URIS = List.of(
            "icon_0_0.png", "icon_0_1.png", "icon_0_2.png",
            "icon_1_0.png", "icon_1_1.png", "icon_1_2.png",
            "icon_2_0.png", "icon_2_1.png", "icon_2_2.png");

    private final S3Config s3Config;
    private final AmazonS3 s3Client;

    public ProfileImageProvider(
            final S3Config s3Config,
            final AmazonS3 s3Client
    ) {
        this.s3Config = s3Config;
        this.s3Client = s3Client;
    }

    public String provideDefaultProfileImageURI(final List<String> existsImageUris) {
        while (true) {
            Random random = new Random();
            int index = random.nextInt(DEFAULT_IMAGE_URIS.size());

            String imageResource = makeProfileImageURI(s3Config.getDefaultUrl(), DEFAULT_IMAGE_URIS.get(index));

            if (!existsImageUris.contains(imageResource)) {
                return imageResource;
            }
        }
    }

    public String uploadProfileImage(final MultipartFile multipartFile) {
        try {
            final String originFileName = Objects.requireNonNull(multipartFile.getOriginalFilename());
            final byte[] encodedFileName = Base64.getEncoder().encode(originFileName.getBytes());
            final String profileImageURI = makeProfileImageURI(s3Config.getCustomUrl(),
                    new String(encodedFileName, UTF_8));

            final PutObjectRequest putObjectRequest = getPutObjectRequest(multipartFile, profileImageURI);

            s3Client.putObject(putObjectRequest);

            return profileImageURI;
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

    private String makeProfileImageURI(final String folderPath, final String uri) {
        return MessageFormat.format("{0}{1}", folderPath, uri);
    }

}
