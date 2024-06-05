package com.dorovidal.userservice.controller;

import com.dorovidal.userservice.dto.UserDto;
import com.dorovidal.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public Flux<UserDto> getAll() {
        return userService.all();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> save(@RequestBody Mono<UserDto> dto) {
        return userService.save(dto);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> update(@PathVariable Long id, @RequestBody Mono<UserDto> dto) {
        return userService.update(id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
