package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Employe;
import org.example.autoloc.services.IEmployeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employe")
@AllArgsConstructor
public class EmployeController {
    private final IEmployeService employeService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public Employe ajouter(@RequestBody Employe employe) { return employeService.ajouterEmploye(employe); }

    @GetMapping
    public List<Employe> getAll() { return employeService.consulterEmployes(); }

    @GetMapping("/{id}")
    public Employe getById(@PathVariable Long id) { return employeService.consulterEmployeById(id); }

    @PutMapping("/{id}")
    public Employe modifier(@PathVariable Long id, @RequestBody Employe employe) {
        employe.setIdEmploye(id);
        return employeService.modifierEmploye(employe);
    }

    @PatchMapping("/{id}")
    public Employe patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Employe employe = PatchUtil.apply(employeService.consulterEmployeById(id), patch, objectMapper);
        employe.setIdEmploye(id);
        return employeService.modifierEmploye(employe);
    }

    @PatchMapping("/{id}/agence")
    public Employe affecterAgence(@PathVariable Long id, @RequestBody JsonNode body) {
        return employeService.affecterEmploye(id, body.path("idAgence").asLong());
    }

    @DeleteMapping("/{id}/agence")
    public Employe desaffecterAgence(@PathVariable Long id) { return employeService.desaffecterEmploye(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { employeService.supprimerEmployeById(id); }
}
