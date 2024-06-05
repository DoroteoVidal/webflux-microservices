package com.dorovidal.productservice.service;

import com.dorovidal.productservice.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> getAll();

    Mono<ProductDto> getById(String id);

    Mono<ProductDto> save(Mono<ProductDto> dto);

    Mono<ProductDto> update(String id, Mono<ProductDto> dto);

    Mono<Void> delete(String id);

    Flux<ProductDto> getByPriceRange(int min, int max);
}
