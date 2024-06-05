package com.dorovidal.productservice.controller;


import com.dorovidal.productservice.dto.ProductDto;
import com.dorovidal.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> all() {
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable String id) {
        this.simulateRandomException();
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> save(@RequestBody Mono<ProductDto> dto) {
        return productService.save(dto);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> update(@PathVariable String id, @RequestBody Mono<ProductDto> dto) {
        return productService.update(id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return productService.delete(id);
    }

    @GetMapping("price-range")
    public Flux<ProductDto> getProductsByPriceRange(@RequestParam("min") int min,
                                                    @RequestParam("max") int max) {
        return productService.getByPriceRange(min, max);
    }

    private void simulateRandomException() {
        int nextInt = ThreadLocalRandom.current().nextInt(1, 10);
        if(nextInt > 5) {
            throw new RuntimeException("something is wrong");
        }
    }
}
