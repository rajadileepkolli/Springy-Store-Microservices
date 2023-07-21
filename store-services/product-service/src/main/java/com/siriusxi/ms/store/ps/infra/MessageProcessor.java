package com.siriusxi.ms.store.ps.infra;

import com.siriusxi.ms.store.api.core.product.ProductService;
import com.siriusxi.ms.store.api.core.product.dto.Product;
import com.siriusxi.ms.store.api.event.Event;
import com.siriusxi.ms.store.util.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;


@Slf4j
public class MessageProcessor {

    private final ProductService productService;

    public MessageProcessor(@Qualifier("ProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Consumer<Event<Integer, Product>> processStream() {
        return event -> {
            log.info("Process message created at {}...", event.getEventCreatedAt());
            switch (event.getEventType()) {
                case CREATE -> {
                    Product product = event.getData();
                    log.info("Create product with ID: {}", product.getProductId());
                    productService.createProduct(product);
                }
                case DELETE -> {
                    log.info("Delete product with Product Id: {}", event.getKey());
                    productService.deleteProduct(event.getKey());
                }
                default -> {
                    String errorMessage =
                            "Incorrect event type: "
                                    .concat(event.getEventType().toString())
                                    .concat(", expected a CREATE or DELETE event.");
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }
    
            log.info("Message processing done!");
        };
    }

}
