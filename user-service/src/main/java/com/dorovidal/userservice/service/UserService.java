package com.dorovidal.userservice.service;

import com.dorovidal.userservice.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDto> all();

    Mono<UserDto> getById(Long id);

    Mono<UserDto> save(Mono<UserDto> dto);

    Mono<UserDto> update(Long id, Mono<UserDto> dto);

    Mono<Void> delete(Long id);
}
