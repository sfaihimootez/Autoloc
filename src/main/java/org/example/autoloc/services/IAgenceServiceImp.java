package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Agence;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.repositories.AgenceRepository;
import org.example.autoloc.repositories.VehiculeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IAgenceServiceImp implements IAgenceService {

    private final AgenceRepository agenceRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public Agence ajouterAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    @Transactional
    public Agence ajouterAgenceEtAffecterVehicules(Agence agence) {
        List<Vehicule> vehicules = agence.getVehicules();
        agence.setVehicules(null);
        Agence agenceEnregistree = agenceRepository.save(agence);

        if (vehicules != null && !vehicules.isEmpty()) {
            for (Vehicule vehicule : vehicules) {
                vehicule.setAgence(agenceEnregistree);
            }
            vehiculeRepository.saveAll(vehicules);
            agenceEnregistree.setVehicules(vehicules);
        }
        return agenceEnregistree;
    }

    @Override
    public Agence modifierAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public List<Agence> consulterAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence consulterAgenceById(Long idAgence) {
        return agenceRepository.findById(idAgence).orElse(null);
    }

    @Override
    public void supprimerAgenceById(Long idAgence) {
        agenceRepository.deleteById(idAgence);
    }
}
