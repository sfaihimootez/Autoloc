package org.example.autoloc.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @PostMapping
    public Reservation ajouter(@RequestBody Reservation reservation) {
        return reservationService.ajouterReservation(reservation);
    }

    @PutMapping("/{id}")
    public Reservation modifier(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setIdReservation(id);
        return reservationService.modifierReservation(reservation);
    }

    @PatchMapping("/{id}")
    public Reservation patch(@PathVariable Long id, @RequestBody JsonNode patch) {
        Reservation reservation = PatchUtil.apply(reservationService.consulterReservationById(id), patch, objectMapper);
        reservation.setIdReservation(id);
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
