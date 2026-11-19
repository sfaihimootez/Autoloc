package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.services.IVehiculeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicule")
@AllArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public Vehicule ajouter(@RequestBody Vehicule vehicule) {
        return vehiculeService.ajouterVehicule(vehicule);
    }

    @PutMapping("/{id}")
    public Vehicule modifier(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        vehicule.setIdVehicule(id);
        return vehiculeService.modifierVehicule(vehicule);
    }

    @PatchMapping("/{id}")
    public Vehicule patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Vehicule vehicule = PatchUtil.apply(vehiculeService.consulterVehiculeById(id), patch, objectMapper);
        vehicule.setIdVehicule(id);
        return vehiculeService.modifierVehicule(vehicule);
    }

    @GetMapping
    public List<Vehicule> getAll() {
        return vehiculeService.consulterVehicules();
    }

    @GetMapping("/{id}")
    public Vehicule getById(@PathVariable Long id) {
        return vehiculeService.consulterVehiculeById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehiculeService.supprimerVehiculeById(id);
    }

    @PutMapping("/{id}/equipements/{idEquipement}")
    public Vehicule affecterEquipement(@PathVariable Long id, @PathVariable Long idEquipement) {
        return vehiculeService.affecterEquipement(id, idEquipement);
    }

    @DeleteMapping("/{id}/equipements/{idEquipement}")
    public Vehicule desaffecterEquipement(@PathVariable Long id, @PathVariable Long idEquipement) {
        return vehiculeService.desaffecterEquipement(id, idEquipement);
    }
}
