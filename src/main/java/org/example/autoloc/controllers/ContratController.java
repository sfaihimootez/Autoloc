package org.example.autoloc.controllers;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Contrat;
import org.example.autoloc.services.IContratService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrat")
@AllArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @PostMapping
    public Contrat ajouterContrat(@RequestBody Contrat contrat) {
        return contratService.ajouterContrat(contrat);
    }

    @PutMapping
    public Contrat modifierContrat(@RequestBody Contrat contrat) {
        return contratService.modifierContrat(contrat);
    }

    @GetMapping
    public List<Contrat> getAll() {
        return contratService.consulterContrats();
    }

    @GetMapping("/{idContrat}")
    public Contrat getById(@PathVariable Long idContrat) {
        return contratService.consulterContratById(idContrat);
    }

    @DeleteMapping("/{idContrat}")
    public void deleteById(@PathVariable Long idContrat) {
        contratService.supprimerContratById(idContrat);
    }
}
