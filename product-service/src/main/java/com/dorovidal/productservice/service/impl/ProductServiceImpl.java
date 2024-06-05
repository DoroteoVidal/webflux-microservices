package com.dorovidal.productservice.service.impl;

import com.dorovidal.productservice.dto.ProductDto;
import com.dorovidal.productservice.repository.ProductRepository;
import com.dorovidal.productservice.service.ProductService;
import com.dorovidal.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    @Override
    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ProductDto> getById(String id) {
        return productRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ProductDto> save(Mono<ProductDto> dto) {
        return dto
                .map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(sink::tryEmitNext);
    }

    @Override
    public Mono<ProductDto> update(String id, Mono<ProductDto> dto) {
        return productRepository.findById(id)
                .flatMap(p -> dto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Flux<ProductDto> getByPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
}
