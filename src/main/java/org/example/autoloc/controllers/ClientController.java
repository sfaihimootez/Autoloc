package org.example.autoloc.controllers;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Client;
import org.example.autoloc.services.IClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    public Client ajouter(@RequestBody Client client) {
        return clientService.ajouterClient(client);
    }

    @PutMapping
    public Client modifier(@RequestBody Client client) {
        return clientService.modifierClient(client);
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.consulterClients();
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.consulterClientById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.supprimerClientById(id);
    }
}
