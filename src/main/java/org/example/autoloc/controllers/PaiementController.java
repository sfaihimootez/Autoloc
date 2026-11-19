package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Paiement;
import org.example.autoloc.services.IPaiementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiement")
@AllArgsConstructor
public class PaiementController {
    private final IPaiementService paiementService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public Paiement ajouter(@RequestBody Paiement paiement) { return paiementService.ajouterPaiement(paiement); }
    @GetMapping
    public List<Paiement> getAll() { return paiementService.consulterPaiements(); }
    @GetMapping("/{id}")
    public Paiement getById(@PathVariable Long id) { return paiementService.consulterPaiementById(id); }
    @PutMapping("/{id}")
    public Paiement modifier(@PathVariable Long id, @RequestBody Paiement paiement) {
        paiement.setIdPaiement(id);
        return paiementService.modifierPaiement(paiement);
    }
    @PatchMapping("/{id}")
    public Paiement patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Paiement paiement = PatchUtil.apply(paiementService.consulterPaiementById(id), patch, objectMapper);
        paiement.setIdPaiement(id);
        return paiementService.modifierPaiement(paiement);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { paiementService.supprimerPaiementById(id); }
}
