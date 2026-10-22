package org.example.autoloc.controllers;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Reservation;
import org.example.autoloc.services.IReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public Reservation ajouter(@RequestBody Reservation reservation) {
        return reservationService.ajouterReservation(reservation);
    }

    @PutMapping
    public Reservation modifier(@RequestBody Reservation reservation) {
        return reservationService.modifierReservation(reservation);
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.consulterReservations();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.consulterReservationById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.supprimerReservationById(id);
    }
}
