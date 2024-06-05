package com.dorovidal.userservice.service.impl;

import com.dorovidal.userservice.dto.UserDto;
import com.dorovidal.userservice.repository.UserRepository;
import com.dorovidal.userservice.service.UserService;
import com.dorovidal.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<UserDto> all() {
        return userRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<UserDto> save(Mono<UserDto> dto) {
        return dto
                .map(EntityDtoUtil::toEntity)
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<UserDto> update(Long id, Mono<UserDto> dto) {
        return userRepository.findById(id)
                .flatMap(u -> dto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return userRepository.deleteById(id);
    }
}
