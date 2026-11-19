package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Maintenance;
import org.example.autoloc.services.IMaintenanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
@AllArgsConstructor
public class MaintenanceController {
    private final IMaintenanceService maintenanceService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public Maintenance ajouter(@RequestBody Maintenance maintenance) { return maintenanceService.ajouterMaintenance(maintenance); }
    @GetMapping
    public List<Maintenance> getAll() { return maintenanceService.consulterMaintenances(); }
    @GetMapping("/{id}")
    public Maintenance getById(@PathVariable Long id) { return maintenanceService.consulterMaintenanceById(id); }
    @PutMapping("/{id}")
    public Maintenance modifier(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        maintenance.setIdMaintenance(id);
        return maintenanceService.modifierMaintenance(maintenance);
    }
    @PatchMapping("/{id}")
    public Maintenance patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Maintenance maintenance = PatchUtil.apply(maintenanceService.consulterMaintenanceById(id), patch, objectMapper);
        maintenance.setIdMaintenance(id);
        return maintenanceService.modifierMaintenance(maintenance);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { maintenanceService.supprimerMaintenanceById(id); }
}
