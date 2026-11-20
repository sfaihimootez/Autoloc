package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.entities.Equipement;
import org.example.autoloc.entities.enums.StatutVehicule;
import org.example.autoloc.repositories.EquipementRepository;
import org.example.autoloc.repositories.VehiculeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class IVehiculeServiceImp implements IVehiculeService {

    private static final Logger logger = LoggerFactory.getLogger(IVehiculeServiceImp.class);
    private static final ZoneId TUNIS_TIME_ZONE = ZoneId.of("Africa/Tunis");

    private final VehiculeRepository vehiculeRepository;
    private final EquipementRepository equipementRepository;

    @Scheduled(initialDelay = 5_000, fixedRate = 60_000)
    public void actualiserEtatVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        LocalDateTime dateHeure = LocalDateTime.now(TUNIS_TIME_ZONE);
        logger.info("Actualisation des véhicules : {} véhicule(s) enregistré(s) à {}", vehicules.size(), dateHeure);
    }

    @Scheduled(cron = "0 0 9 * * MON", zone = "Africa/Tunis")
    public void genererRapportVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        LocalDateTime dateHeure = LocalDateTime.now(TUNIS_TIME_ZONE);
        logger.info("Rapport périodique des véhicules : {} véhicule(s) enregistré(s), généré le {}",
                vehicules.size(), dateHeure);
    }

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

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = consulterVehiculeById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Équipement introuvable"));
        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new ArrayList<>());
        }
        if (vehicule.getEquipements().stream().noneMatch(e -> e.getIdEquipement().equals(idEquipement))) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = consulterVehiculeById(idVehicule);
        if (vehicule.getEquipements() != null) {
            vehicule.getEquipements().removeIf(e -> e.getIdEquipement().equals(idEquipement));
        }
        return vehiculeRepository.save(vehicule);
    }
}
