package com.siriusxi.ms.store.api.composite;

import com.siriusxi.ms.store.api.composite.dto.ProductAggregate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Interface <code>StoreEndpoint</code> is a higher level Interface
 * to define <strong>Store Service</strong> endpoint APIs that follow <code>StoreService</code>
 * interface. And to be implemented by service controllers.
 *
 *
 * @author mohamed.taman
 * @version v5.8
 * @since v3.0 codename Storm
 */
@Tag(name = "StoreAPI", description = "REST API for Springy Store products information.")
@RequestMapping("store/api/v1")
public interface StoreEndpoint extends StoreService {

  /**
   * Sample usage:
   *
   * <p><code>curl $HOST:$PORT/store/api/v1/products/1</code></p>
   *
   * @param id is the product that you are looking for.
   * @return the composite product info, if found, else null.
   * @since v3.0 codename Storm.
   */
  @Operation(
      summary = "${api.product-composite.get-composite-product.description}",
      description = "${api.product-composite.get-composite-product.notes}")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "400",
            description = """
                    Bad Request, invalid format of the request.
                    See response message for more information.
                    """),
        @ApiResponse(responseCode = "404", description = "Not found, the specified id does not exist."),
        @ApiResponse(
            responseCode = "422",
            description = """
                    Unprocessable entity, input parameters caused the processing to fails.
                    See response message for more information.
                    """)
      })
  @GetMapping(value = "products/{id}",
          produces = APPLICATION_JSON_VALUE)
  @Override
  Mono<ProductAggregate> getProduct(
      @PathVariable int id,
          @RequestParam(required = false, defaultValue = "0") int delay,
          @RequestParam(required = false, defaultValue = "0") int faultPercent);
  /**
   * Sample usage:
   *
   * <p><code>curl -X POST $HOST:$PORT/store/api/v1/products \
   *    -H "Content-Type: application/json" --data \
   *    '{"productId":123,"name":"product 123", "weight":123}'</code></p>
   *
   * @param body of composite product elements definition.
   * @since v3.0 codename Storm.
   * @return Nothing.
   */
  @Operation(
      summary = "${api.product-composite.create-composite-product.description}",
      description = "${api.product-composite.create-composite-product.notes}")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "400",
            description = """
                Bad Request, invalid format of the request. 
                See response message for more information.
                """),
        @ApiResponse(
            responseCode = "422",
            description = """
                Unprocessable entity, input parameters caused the processing to fail. 
                See response message for more information.
                """)
      })
  @PostMapping(
          value = "products",
          consumes = APPLICATION_JSON_VALUE)
  @Override
  Mono<Void> createProduct(@RequestBody ProductAggregate body);

  /**
   * Sample usage:
   *
   * <p><code>curl -X DELETE $HOST:$PORT/store/api/v1/products/1</code></p>
   *
   * @param id is the product id to delete it.
   * @since v3.0 codename Storm.
   * @return Nothing.
   */
  @Operation(
      summary = "${api.product-composite.delete-composite-product.description}",
      description = "${api.product-composite.delete-composite-product.notes}")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "400",
            description ="""
                Bad Request, invalid format of the request. 
                See response message for more information.
                """),
        @ApiResponse(
            responseCode = "422",
            description ="""
                Unprocessable entity, input parameters caused the processing to fail. 
                See response message for more information.
                """)
      })
  @DeleteMapping("products/{id}")
  @Override
  Mono<Void> deleteProduct(@PathVariable int id);
}
