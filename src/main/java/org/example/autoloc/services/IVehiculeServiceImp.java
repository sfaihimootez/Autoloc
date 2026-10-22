package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.entities.enums.StatutVehicule;
import org.example.autoloc.repositories.VehiculeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class IVehiculeServiceImp implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public Vehicule ajouterVehicule(Vehicule vehicule) {
        if (vehicule.getStatut() == null) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule modifierVehicule(Vehicule vehicule) {
        if (vehicule.getIdVehicule() == null || !vehiculeRepository.existsById(vehicule.getIdVehicule())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Véhicule introuvable");
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> consulterVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule consulterVehiculeById(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Véhicule introuvable"));
    }

    @Override
    public void supprimerVehiculeById(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Véhicule introuvable");
        }
        vehiculeRepository.deleteById(idVehicule);
    }
}
