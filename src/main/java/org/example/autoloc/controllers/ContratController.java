package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @PostMapping
    public Contrat ajouterContrat(@RequestBody Contrat contrat) {
        return contratService.ajouterContrat(contrat);
    }

    @PutMapping("/{idContrat}")
    public Contrat modifierContrat(@PathVariable Long idContrat, @RequestBody Contrat contrat) {
        contrat.setIdContrat(idContrat);
        return contratService.modifierContrat(contrat);
    }

    @PatchMapping("/{idContrat}")
    public Contrat patch(@PathVariable Long idContrat, @RequestBody JsonNode patch) {
        Contrat contrat = PatchUtil.apply(contratService.consulterContratById(idContrat), patch, objectMapper);
        contrat.setIdContrat(idContrat);
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
