package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Equipement;
import org.example.autoloc.services.IEquipementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipement")
@AllArgsConstructor
public class EquipementController {
    private final IEquipementService equipementService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public Equipement ajouter(@RequestBody Equipement equipement) { return equipementService.ajouterEquipement(equipement); }
    @GetMapping
    public List<Equipement> getAll() { return equipementService.consulterEquipements(); }
    @GetMapping("/{id}")
    public Equipement getById(@PathVariable Long id) { return equipementService.consulterEquipementById(id); }
    @PutMapping("/{id}")
    public Equipement modifier(@PathVariable Long id, @RequestBody Equipement equipement) {
        equipement.setIdEquipement(id);
        return equipementService.modifierEquipement(equipement);
    }
    @PatchMapping("/{id}")
    public Equipement patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Equipement equipement = PatchUtil.apply(equipementService.consulterEquipementById(id), patch, objectMapper);
        equipement.setIdEquipement(id);
        return equipementService.modifierEquipement(equipement);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { equipementService.supprimerEquipementById(id); }
}
