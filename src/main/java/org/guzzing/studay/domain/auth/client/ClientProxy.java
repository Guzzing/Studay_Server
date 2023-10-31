package org.guzzing.studay.domain.auth.client;

import org.guzzing.studay.domain.member.model.Member;

public interface ClientProxy {

    Member getUserData(String accessToken);
}
