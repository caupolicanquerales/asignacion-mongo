package com.capo.asignacion_mongo.adapter.configuration;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;
import com.capo.asignacion_mongo.adapter.out.emitEvents.EventInUse;

import reactor.core.publisher.Flux;

@Configuration
public class PublishingCostsAndRoutesFromConfig {
	
	private final EventInUse<CostsAndRoutesFromEvent> eventPublisher;
	
	private static final String DESTINATION_HEADER = "spring.cloud.stream.sendto.destination";
    private static final String EVENTS_CHANNEL = "event-costs-and-routes";
    
	@Autowired
	public PublishingCostsAndRoutesFromConfig(EventInUse<CostsAndRoutesFromEvent> eventPublisher) {
		this.eventPublisher=eventPublisher;
	}
	
	@Bean
    public Supplier<Flux<Message<CostsAndRoutesFromEvent>>> publishingCostsAndRoutesFromEvent() {
        return () -> this.eventPublisher.publish()
                                        .map(this::toMessage);
    }
	
	private Message<CostsAndRoutesFromEvent> toMessage(CostsAndRoutesFromEvent event) {
        return MessageBuilder.withPayload(event)
                             .setHeader(KafkaHeaders.KEY, "14")
                             .setHeader(DESTINATION_HEADER, EVENTS_CHANNEL)
                             .build();
    }
}
