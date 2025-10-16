package com.tpi.clientscontainers.service;

import com.tpi.clientscontainers.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client create(Client client);
    Client update(Long id, Client client);
    void delete(Long id);
    List<Client> findAll();
    Optional<Client> findById(Long id);
}
