package org.guzzing.studayserver.domain.member.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class WithdrawEvent {

    private final HttpServletRequest request;

    public WithdrawEvent(HttpServletRequest request) {
        this.request = request;
    }

}
