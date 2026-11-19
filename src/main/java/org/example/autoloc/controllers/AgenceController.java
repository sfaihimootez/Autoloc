package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Agence;
import org.example.autoloc.services.IAgenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agence")
@AllArgsConstructor
public class AgenceController {
    private final IAgenceService agenceService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public Agence ajouter(@RequestBody Agence agence) { return agenceService.ajouterAgence(agence); }

    @PostMapping("/avec-vehicules")
    public Agence ajouterAvecVehicules(@RequestBody Agence agence) {
        return agenceService.ajouterAgenceEtAffecterVehicules(agence);
    }

    @GetMapping
    public List<Agence> getAll() { return agenceService.consulterAgences(); }

    @GetMapping("/{id}")
    public Agence getById(@PathVariable Long id) { return agenceService.consulterAgenceById(id); }

    @PutMapping("/{id}")
    public Agence modifier(@PathVariable Long id, @RequestBody Agence agence) {
        agence.setIdAgence(id);
        return agenceService.modifierAgence(agence);
    }

    @PatchMapping("/{id}")
    public Agence patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Agence agence = PatchUtil.apply(agenceService.consulterAgenceById(id), patch, objectMapper);
        agence.setIdAgence(id);
        return agenceService.modifierAgence(agence);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { agenceService.supprimerAgenceById(id); }
}
