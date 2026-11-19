package org.example.autoloc.services;

import lombok.AllArgsConstructor;
import org.example.autoloc.entities.Agence;
import org.example.autoloc.entities.Employe;
import org.example.autoloc.repositories.AgenceRepository;
import org.example.autoloc.repositories.EmployeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IEmployeServiceImp implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;

    @Override
    public Employe ajouterEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe modifierEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> consulterEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Employe consulterEmployeById(Long idEmploye) {
        return employeRepository.findById(idEmploye).orElse(null);
    }

    @Override
    public void supprimerEmployeById(Long idEmploye) {
        employeRepository.deleteById(idEmploye);
    }

    @Override
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new IllegalArgumentException("Employé introuvable : " + idEmploye));
        employe.setAgence(null);
        return employeRepository.save(employe);
    }

    @Override
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new IllegalArgumentException("Employé introuvable : " + idEmploye));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new IllegalArgumentException("Agence introuvable : " + idAgence));
        employe.setAgence(agence);
        return employeRepository.save(employe);
    }
}
