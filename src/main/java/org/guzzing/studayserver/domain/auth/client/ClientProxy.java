package org.guzzing.studayserver.domain.auth.client;

import org.guzzing.studayserver.domain.member.model.Member;

public interface ClientProxy {

    Member getUserData(String accessToken);
}
