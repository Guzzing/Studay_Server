package org.guzzing.studayserver.domain.child.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.text.MessageFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class ProfileImageUri {

    @Transient
    private static final String urlPath = "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/";

    @Column(name = "image_uri", nullable = false)
    private String imageUri;

    public ProfileImageUri(final String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageURLPath() {
        return MessageFormat.format("{0}{1}", urlPath, imageUri);
    }

}
