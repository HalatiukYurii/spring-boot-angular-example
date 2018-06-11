package com.sda.springbootdemo.exercises.repository;

import com.sda.springbootdemo.exercises.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, String> {

    Optional<Client> findOneByClientId(String clientId);
}
