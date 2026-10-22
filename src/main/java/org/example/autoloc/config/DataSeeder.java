package org.example.autoloc.config;

import lombok.RequiredArgsConstructor;
import org.example.autoloc.entities.Agence;
import org.example.autoloc.entities.Client;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.entities.enums.CategorieVehicule;
import org.example.autoloc.entities.enums.StatutVehicule;
import org.example.autoloc.repositories.AgenceRepository;
import org.example.autoloc.repositories.ClientRepository;
import org.example.autoloc.repositories.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AgenceRepository agenceRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public void run(String... args) {
        if (clientRepository.count() > 0) {
            return;
        }

        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("71000000");
        agence = agenceRepository.save(agence);

        Client client = new Client();
        client.setNom("Ben Ali");
        client.setPrenom("Sami");
        client.setEmail("sami@example.com");
        client.setTelephone("20000000");
        client.setNumPermis("TN123456");
        client.setDateInscription(LocalDate.now());
        clientRepository.save(client);

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("123 TUN 456");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Corolla");
        vehicule.setCategorie(CategorieVehicule.BERLINE);
        vehicule.setTarifJournalier(new BigDecimal("120.000"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setAgence(agence);
        vehiculeRepository.save(vehicule);
    }
}
