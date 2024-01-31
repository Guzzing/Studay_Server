package org.guzzing.studayserver.domain.child.listener;

import org.guzzing.studayserver.domain.member.event.WithdrawEvent;
import org.guzzing.studayserver.global.common.profile.ProfileImageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class WithdrawProfileImageListener {

    private final ProfileImageService profileImageService;

    public WithdrawProfileImageListener(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    @Async
    @TransactionalEventListener
    public void withdrawProfileImage(WithdrawEvent event) {
        event.getChildProfileImageUris()
                .forEach(profileImageService::deleteProfileImage);
    }
}
