package org.guzzing.studayserver.domain.auth.client;

import org.guzzing.studayserver.domain.member.model.Member;

public interface ClientStrategy {

    Member getUserData(String accessToken);

}
