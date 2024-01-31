package org.guzzing.studayserver.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class S3MockConfig {

    private static final int PORT_NUMBER = 8001;

    @Bean
    public S3Mock s3Mock() {
        return new S3Mock.Builder()
                .withPort(PORT_NUMBER)
                .withInMemoryBackend()
                .build();
    }

    @Primary
    @Bean
    public AmazonS3 amazonS3() {
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                "http://127.0.0.1:" + PORT_NUMBER,
                Regions.AP_NORTHEAST_1.name());

        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
    }

}
