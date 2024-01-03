package org.guzzing.studayserver.global.profile;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.guzzing.studayserver.global.config.S3Config;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProfileImageUriProvider {

    private static final Random random = new Random();
    private static final String PROFILE_IMAGE_URI_FORMAT = "{0}{1}{2}";
    private static final List<String> DEFAULT_IMAGE_URIS = List.of(
            "icon_0_0.png", "icon_0_1.png", "icon_0_2.png",
            "icon_1_0.png", "icon_1_1.png", "icon_1_2.png",
            "icon_2_0.png", "icon_2_1.png", "icon_2_2.png");

    private final S3Config s3Config;

    public ProfileImageUriProvider(
            final S3Config s3Config
    ) {
        this.s3Config = s3Config;
    }

    public String provideDefaultProfileImageURI(final List<String> existsImageUris) {
        while (true) {
            final int index = random.nextInt(DEFAULT_IMAGE_URIS.size());

            final String imageResource = makeProfileImageURI(s3Config.getDefaultUrl(), null, DEFAULT_IMAGE_URIS.get(index));

            if (!existsImageUris.contains(imageResource)) {
                return imageResource;
            }
        }
    }

    public String provideCustomProfileImageURI(final Long memberId, final MultipartFile multipartFile) {
        final String originFileName = Objects.requireNonNull(multipartFile.getName());
        final byte[] encodedFileName = Base64.getEncoder().encode(originFileName.getBytes());

        return makeProfileImageURI(s3Config.getCustomUrl(), memberId, new String(encodedFileName, UTF_8));
    }

    private String makeProfileImageURI(final String folderPath, final Long memberId, final String uri) {
        final String middlePath = makeProfileImageMiddlePath(memberId);

        return MessageFormat.format(PROFILE_IMAGE_URI_FORMAT, folderPath, middlePath, uri);
    }

    private String makeProfileImageMiddlePath(Long memberId) {
        if (memberId == null) {
            return "";
        }

        return memberId + "/";
    }

}
