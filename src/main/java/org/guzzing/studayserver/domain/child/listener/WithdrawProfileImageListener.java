package org.guzzing.studayserver.domain.child.listener;

import org.guzzing.studayserver.domain.member.event.WithdrawEvent;
import org.guzzing.studayserver.global.common.profile.service.S3ClientService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class WithdrawProfileImageListener {

    private final S3ClientService s3ClientService;

    public WithdrawProfileImageListener(S3ClientService s3ClientService) {
        this.s3ClientService = s3ClientService;
    }

    @Async
    @TransactionalEventListener
    public void withdrawProfileImage(WithdrawEvent event) {
        event.getChildProfileImageUris()
                .forEach(s3ClientService::deleteProfileImage);
    }
}
