package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @PostMapping
    public Client ajouter(@RequestBody Client client) {
        return clientService.ajouterClient(client);
    }

    @PutMapping("/{id}")
    public Client modifier(@PathVariable Long id, @RequestBody Client client) {
        client.setIdClient(id);
        return clientService.modifierClient(client);
    }

    @PatchMapping("/{id}")
    public Client patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Client client = PatchUtil.apply(clientService.consulterClientById(id), patch, objectMapper);
        client.setIdClient(id);
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
