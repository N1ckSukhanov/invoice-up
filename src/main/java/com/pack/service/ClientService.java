package com.pack.service;

import com.pack.entity.Client;
import com.pack.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Page<Client> getClientsWithPagination(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getClientById(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
    }

    public Client updateClientStatus(Integer clientId, String status) {
        Client existingClient = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
        existingClient.setClientStatus(status);

        clientRepository.save(existingClient);
        return existingClient;
    }

    public Client updateClientData(Integer clientId, Client updatedClient) {
        Client existingClient = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Клиент не найден"));

        existingClient.setClient1CName(updatedClient.getClient1CName());
        existingClient.setClientShortName(updatedClient.getClientShortName());
        existingClient.setClientShortName(updatedClient.getClientShortName());
        existingClient.setClientInvoiceName(updatedClient.getClientInvoiceName());
        existingClient.setClientReportName(updatedClient.getClientReportName());
        existingClient.setClientDocTransfer(updatedClient.getClientDocTransfer());
        existingClient.setClientSwiftBic(updatedClient.getClientSwiftBic());
        existingClient.setClientBic(updatedClient.getClientBic());
        existingClient.setClientPrServiceBureau(updatedClient.getClientPrServiceBureau());
        existingClient.setClientPrSanctions(updatedClient.getClientPrSanctions());
        existingClient.setClientStatus(updatedClient.getClientStatus());
        existingClient.setUser(updatedClient.getUser());
        existingClient.setChangeDate(LocalDateTime.now());

        clientRepository.save(existingClient);
        return existingClient;
    }
}