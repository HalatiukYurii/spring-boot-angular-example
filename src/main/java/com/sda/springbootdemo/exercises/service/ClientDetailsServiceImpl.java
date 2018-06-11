package com.sda.springbootdemo.exercises.service;

import com.sda.springbootdemo.exercises.model.Client;
import com.sda.springbootdemo.exercises.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientRepository.findOneByClientId(clientId).orElseThrow(() -> new NoSuchClientException(String.format("Client with clientId: %s not found", clientId)));

        return new com.sda.springbootdemo.exercises.model.ClientDetails(client);
    }
}
