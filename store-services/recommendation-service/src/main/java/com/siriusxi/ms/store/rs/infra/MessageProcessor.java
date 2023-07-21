package com.siriusxi.ms.store.rs.infra;

import com.siriusxi.ms.store.api.core.recommendation.RecommendationService;
import com.siriusxi.ms.store.api.core.recommendation.dto.Recommendation;
import com.siriusxi.ms.store.api.core.review.dto.Review;
import com.siriusxi.ms.store.api.event.Event;
import com.siriusxi.ms.store.util.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

import static java.lang.String.*;

@Slf4j
public class MessageProcessor {

    private final RecommendationService service;

    public MessageProcessor(@Qualifier("RecommendationServiceImpl") RecommendationService service) {
        this.service = service;
    }

    @Bean
    public Consumer<Event<Integer, Recommendation>> processStream() {
        return event -> {

            log.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE -> {
                    Recommendation recommendation = event.getData();
                    log.info("Create recommendation with ID: {}/{}", recommendation.getProductId(),
                            recommendation.getRecommendationId());
                    service.createRecommendation(recommendation);
                }
                case DELETE -> {
                    int productId = event.getKey();
                    log.info("Delete recommendations with ProductID: {}", productId);
                    service.deleteRecommendations(productId);
                }
                default -> {
                    String errorMessage =
                            "Incorrect event type: "
                                    .concat(valueOf(event.getEventType()))
                                    .concat(", expected a CREATE or DELETE event");
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }

            log.info("Message processing done!");
        };
    }

}
