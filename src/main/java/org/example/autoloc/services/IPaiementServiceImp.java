package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Paiement;
import org.example.autoloc.repositories.PaiementRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class IPaiementServiceImp implements IPaiementService {

    private final PaiementRepository paiementRepository;

    @Override
    public Paiement ajouterPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement modifierPaiement(Paiement paiement) {
        if (paiement.getIdPaiement() == null || !paiementRepository.existsById(paiement.getIdPaiement())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paiement introuvable");
        }
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> consulterPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement consulterPaiementById(Long idPaiement) {
        return paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paiement introuvable"));
    }

    @Override
    public void supprimerPaiementById(Long idPaiement) {
        consulterPaiementById(idPaiement);
        paiementRepository.deleteById(idPaiement);
    }
}
