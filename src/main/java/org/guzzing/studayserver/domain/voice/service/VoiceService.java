package org.guzzing.studayserver.domain.voice.service;

import static com.slack.api.webhook.WebhookPayloads.payload;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import java.io.IOException;
import java.util.List;
import org.guzzing.studayserver.domain.voice.service.dto.VoiceRegisterParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VoiceService {

    @Value("${webhook.slack.url}")
    private String SLACK_WEBHOOK_URL;

    private final Slack slackClient = Slack.getInstance();

    public boolean sendVoice(VoiceRegisterParam param) {
        try {
            slackClient.send(SLACK_WEBHOOK_URL,
                    payload(payloadBuilder -> payloadBuilder
                            .text(param.title())
                            .attachments(List.of(
                                    Attachment.builder()
                                            .color("orange")
                                            .fields(List.of(
                                                    generateFields(param.title(), param.content()),
                                                    generateFields("Member Id", String.valueOf(param.memberId()))
                                            ))
                                            .imageUrl(param.imageFile().getName())
                                            .build()))
                    ));
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("슬랙 메시지 전송에 실패했습니다.");
        }
    }

    private Field generateFields(String title, String content) {
        return Field.builder()
                .title(title)
                .value(content)
                .valueShortEnough(true)
                .build();
    }

}
