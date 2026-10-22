package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Contrat;
import org.example.autoloc.entities.Reservation;
import org.example.autoloc.entities.Vehicule;
import org.example.autoloc.entities.enums.StatutReservation;
import org.example.autoloc.entities.enums.StatutVehicule;
import org.example.autoloc.repositories.ContratRepository;
import org.example.autoloc.repositories.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class IContratServiceImp implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public Contrat ajouterContrat(Contrat contrat) {
        if (contrat.getReservation() == null || contrat.getReservation().getIdReservation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une réservation (idReservation) est obligatoire");
        }

        Reservation reservation = reservationRepository.findById(contrat.getReservation().getIdReservation())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (reservation.getStatut() == StatutReservation.ANNULEE || reservation.getStatut() == StatutReservation.TERMINEE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossible de créer un contrat pour une réservation annulée ou terminée");
        }
        if (reservation.getContrat() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un contrat existe déjà pour cette réservation");
        }

        Vehicule vehicule = reservation.getVehicule();
        if (vehicule == null || vehicule.getTarifJournalier() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La réservation doit avoir un véhicule avec un tarif journalier");
        }
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La réservation doit avoir une date de début et de fin");
        }
        if (reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La date de fin doit être après la date de début");
        }

        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        if (jours <= 0) {
            jours = 1;
        }

        Contrat nouveau = new Contrat();
        nouveau.setReservation(reservation);
        nouveau.setDateSignature(contrat.getDateSignature() != null ? contrat.getDateSignature() : LocalDate.now());
        nouveau.setMontantTotal(vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours)));
        nouveau.setValide(true);

        reservation.setStatut(StatutReservation.CONFIRMEE);
        vehicule.setStatut(StatutVehicule.LOUE);

        return contratRepository.save(nouveau);
    }

    @Override
    @Transactional
    public Contrat modifierContrat(Contrat contrat) {
        if (contrat.getIdContrat() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idContrat obligatoire pour la modification");
        }
        Contrat existing = contratRepository.findById(contrat.getIdContrat())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contrat introuvable"));

        if (contrat.getDateSignature() != null) {
            existing.setDateSignature(contrat.getDateSignature());
        }
        if (contrat.getMontantTotal() != null) {
            existing.setMontantTotal(contrat.getMontantTotal());
        }
        existing.setValide(contrat.isValide());

        return contratRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> consulterContrats() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat consulterContratById(Long idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contrat introuvable"));
    }

    @Override
    @Transactional
    public void supprimerContratById(Long idContrat) {
        Contrat contrat = contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contrat introuvable"));

        Reservation reservation = contrat.getReservation();
        if (reservation != null) {
            reservation.setStatut(StatutReservation.ANNULEE);
            reservation.setContrat(null);
            if (reservation.getVehicule() != null) {
                reservation.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
            }
        }

        contratRepository.delete(contrat);
    }
}
