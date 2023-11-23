package org.guzzing.studayserver.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value(value = "${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value(value = "${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value(value = "${cloud.aws.region.static}")
    private String region;

    @Getter
    @Value(value = "${application.bucket.name}")
    private String bucketName;

    @Getter
    @Value(value = "${application.bucket.url}")
    private String url;

    @Getter
    @Value(value = "${application.bucket.default}")
    private String defaultUrl;

    @Getter
    @Value(value = "${application.bucket.custom}")
    private String customUrl;

    @Bean
    public AmazonS3 s3Client() {
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsStaticCredentialsProvider)
                .withRegion(region)
                .build();
    }

}
