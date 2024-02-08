package org.guzzing.studayserver.domain.voice.service.dto;

import org.springframework.web.multipart.MultipartFile;

public record VoiceRegisterParam(
        String title,
        String content,
        MultipartFile imageFile,
        Long memberId
) {

}
