package ru.cobp.support.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebsocketConstants {

    public static final String STOMP_ENDPOINT = "/ws";

    public static final String APP_DESTINATION_PREFIX = "/app";

    public static final String BROKER_DESTINATION_PREFIX = "/topic";

    public static final String SUPPORT_SUFFIX = "/support";

    public static final String SUPPORT_DESTINATION = BROKER_DESTINATION_PREFIX + SUPPORT_SUFFIX;

}
