package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Client;
import org.example.autoloc.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class IClientServiceImp implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client ajouterClient(Client client) {
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
        return clientRepository.save(client);
    }

    @Override
    public Client modifierClient(Client client) {
        if (client.getIdClient() == null || !clientRepository.existsById(client.getIdClient())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        }
        return clientRepository.save(client);
    }

    @Override
    public List<Client> consulterClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client consulterClientById(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable"));
    }

    @Override
    public void supprimerClientById(Long idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        }
        clientRepository.deleteById(idClient);
    }
}
