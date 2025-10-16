package com.tpi.clientscontainers.service.impl;

import com.tpi.clientscontainers.entity.Client;
import com.tpi.clientscontainers.repository.ClientRepository;
import com.tpi.clientscontainers.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {
        LOGGER.info("Creating client {}", client.getEmail());
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        return clientRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(client.getNombre());
                    existing.setEmail(client.getEmail());
                    existing.setTelefono(client.getTelefono());
                    return clientRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
}
