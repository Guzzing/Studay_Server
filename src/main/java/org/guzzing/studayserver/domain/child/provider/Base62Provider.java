package org.guzzing.studayserver.domain.child.provider;

import org.springframework.stereotype.Component;

@Component
public class Base62Provider {

    private static final int ENCODE_LENGTH = 8;
    private static final int BASE_62_COUNT = 62;
    private static final char DEFAULT_ENCODE_CHARACTER = 'A';
    private static final String BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String encode(final long originValue) {
        long value = originValue;
        StringBuilder stringBuilder = new StringBuilder();

        while (value > 0) {
            final int index = (int) (value % BASE_62_COUNT);
            stringBuilder.append(BASE_62_CHARS.charAt(index));
            value /= BASE_62_COUNT;
        }

        fillInDefaultCharacter(stringBuilder);

        return stringBuilder.toString();
    }

    public long decode(final String value) {
        long originValue = 0;
        long factor = 1;

        for (int i = 0; i < value.length(); i++) {
            final char character = value.charAt(i);
            final int index = BASE_62_CHARS.indexOf(character);

            originValue += index * factor;

            factor *= BASE_62_COUNT;
        }

        return originValue;
    }

    private void fillInDefaultCharacter(StringBuilder stringBuilder) {
        while (stringBuilder.length() < ENCODE_LENGTH) {
            stringBuilder.append(DEFAULT_ENCODE_CHARACTER);
        }
    }

}
