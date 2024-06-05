package com.dorovidal.userservice.repository;

import com.dorovidal.userservice.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    /*
     * Ya que no se trata de una consulta SELECT, se utiliza @Modifying
     * para indicar que este metodo hara una modificacion en la BBDD.
     */
    @Modifying
    @Query(
            "UPDATE users " +
            "SET balance = balance - :amount " +
            "WHERE id = :userId " +
            "AND balance >= :amount"
    )
    Mono<Boolean> updateUserBalance(Long userId, int amount);
}
