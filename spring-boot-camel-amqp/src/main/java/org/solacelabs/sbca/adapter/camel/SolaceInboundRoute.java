package org.solacelabs.sbca.adapter.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SolaceInboundRoute extends RouteBuilder {

    private static final String EVENT_DLQ_ENDPOINT = "{{main.inbound.error-dlq}}";

    public void configure() {

        // @formatter:off
        from("{{main.inbound.from-endpoint}}")
            .routeId("solace-inbound-route")
            .autoStartup("{{main.inbound.autostart-enabled}}")
            .to("{{main.inbound.destinations.default-endpoint}}")
        ;
        // @formatter:on
    }

}
