package org.guzzing.studayserver.domain.voice.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.guzzing.studayserver.domain.voice.controller.dto.VoiceRegisterRequest;
import org.guzzing.studayserver.domain.voice.controller.dto.VoiceRegisterResponse;
import org.guzzing.studayserver.domain.voice.service.VoiceService;
import org.guzzing.studayserver.global.common.member.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/voices")
public class VoiceRestController {

    private final VoiceService voiceService;

    public VoiceRestController(VoiceService voiceService) {
        this.voiceService = voiceService;
    }

    @PostMapping
    public ResponseEntity<VoiceRegisterResponse> registerVoice(
            @RequestPart(name = "request", required = false) VoiceRegisterRequest request,
            @RequestPart(name = "image", required = false) MultipartFile imageFile,
            @MemberId Long memberId
    ) {
        boolean result = voiceService.sendVoice(VoiceRegisterRequest.to(request, imageFile, memberId));

        return ResponseEntity
                .status(CREATED)
                .body(new VoiceRegisterResponse(result));
    }


}
