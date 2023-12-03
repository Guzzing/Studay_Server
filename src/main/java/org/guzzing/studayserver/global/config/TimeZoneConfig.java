package org.guzzing.studayserver.global.config;

import java.util.TimeZone;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeZoneConfig implements CommandLineRunner {

    @Override
    public void run(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
