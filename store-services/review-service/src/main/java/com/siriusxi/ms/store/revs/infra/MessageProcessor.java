package com.siriusxi.ms.store.revs.infra;

import com.siriusxi.ms.store.api.core.review.ReviewService;
import com.siriusxi.ms.store.api.core.review.dto.Review;
import com.siriusxi.ms.store.api.event.Event;
import com.siriusxi.ms.store.util.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

import static java.lang.String.valueOf;

@Slf4j
public class MessageProcessor {

    private final ReviewService service;

    public MessageProcessor(@Qualifier("ReviewServiceImpl") ReviewService service) {
        this.service = service;
    }

    @Bean
    public Consumer<Event<Integer, Review>> processStream() {
        return event -> {

            log.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE -> {
                    Review review = event.getData();
                    log.info("Create review with ID: {}/{}", review.getProductId(),
                            review.getReviewId());
                    service.createReview(review);
                }
                case DELETE -> {
                    int productId = event.getKey();
                    log.info("Delete review with Product Id: {}", productId);
                    service.deleteReviews(productId);
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
