package org.guzzing.studayserver.domain.voice.controller.dto;

import org.guzzing.studayserver.domain.voice.service.dto.VoiceRegisterParam;
import org.springframework.web.multipart.MultipartFile;

public record VoiceRegisterRequest(
        String title,
        String content
) {

    public static VoiceRegisterParam to(VoiceRegisterRequest request, MultipartFile imageFile, Long memberId) {
        return new VoiceRegisterParam(request.title(), request.content(), imageFile, memberId);
    }
}
