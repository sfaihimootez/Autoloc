package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Client;
import org.example.autoloc.entities.Reservation;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.entities.enums.StatutReservation;
import org.example.autoloc.entities.enums.StatutVehicule;
import org.example.autoloc.repositories.ClientRepository;
import org.example.autoloc.repositories.ReservationRepository;
import org.example.autoloc.repositories.VehiculeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class IReservationServiceImp implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Reservation ajouterReservation(Reservation reservation) {
        if (reservation.getClient() == null || reservation.getClient().getIdClient() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client.idClient obligatoire");
        }
        if (reservation.getVehicule() == null || reservation.getVehicule().getIdVehicule() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vehicule.idVehicule obligatoire");
        }
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dateDebut et dateFin obligatoires");
        }
        if (reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dateFin doit être après dateDebut");
        }

        Client client = clientRepository.findById(reservation.getClient().getIdClient())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable"));
        Vehicule vehicule = vehiculeRepository.findById(reservation.getVehicule().getIdVehicule())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Véhicule introuvable"));

        if (vehicule.getStatut() != null && vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Véhicule non disponible");
        }

        Reservation nouvelle = new Reservation();
        nouvelle.setClient(client);
        nouvelle.setVehicule(vehicule);
        nouvelle.setDateDebut(reservation.getDateDebut());
        nouvelle.setDateFin(reservation.getDateFin());
        nouvelle.setStatut(StatutReservation.EN_ATTENTE);

        return reservationRepository.save(nouvelle);
    }

    @Override
    @Transactional
    public Reservation modifierReservation(Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idReservation obligatoire");
        }
        Reservation existing = reservationRepository.findById(reservation.getIdReservation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (reservation.getDateDebut() != null) {
            existing.setDateDebut(reservation.getDateDebut());
        }
        if (reservation.getDateFin() != null) {
            existing.setDateFin(reservation.getDateFin());
        }
        if (reservation.getStatut() != null) {
            existing.setStatut(reservation.getStatut());
        }
        return reservationRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> consulterReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation consulterReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));
    }

    @Override
    @Transactional
    public void supprimerReservationById(Long idReservation) {
        if (!reservationRepository.existsById(idReservation)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable");
        }
        reservationRepository.deleteById(idReservation);
    }
}
