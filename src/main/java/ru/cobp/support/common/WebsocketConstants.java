package ru.cobp.support.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebsocketConstants {

    public static final String STOMP_ENDPOINT = "/ws";

    public static final String APP_DESTINATION = "/app";

    public static final String TOPIC_DESTINATION = "/topic";

    public static final String QUEUE_DESTINATION = "/queue";

    public static final String SUPPORT_DESTINATION = "/support";

    public static final String QUEUE_SUPPORT_DESTINATION = QUEUE_DESTINATION + SUPPORT_DESTINATION;

}
