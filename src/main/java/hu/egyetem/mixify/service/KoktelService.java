package hu.egyetem.mixify.service;

import hu.egyetem.mixify.model.Koktel;
import hu.egyetem.mixify.repository.KoktelRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class KoktelService {

    private final KoktelRepository repository;

    public KoktelService(KoktelRepository repository) {
        this.repository = repository;
    }

    public List<Koktel> findAll() {
        return repository.findAll();
    }

    public Koktel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen azonosítójú koktél!"));
    }

    public Koktel save(Koktel koktel) {
        // 1. Üzleti szabály: Ár ellenőrzése
        if (koktel.getAr() < 500) {
            throw new IllegalArgumentException("A koktél ára nem lehet kevesebb 500 Ft-nál!");
        }

        // 2. Üzleti szabály: Alkoholmentes koktél védelme
        if (koktel.getKategoria() != null && koktel.getKategoria().getNev().toLowerCase().contains("mentes")) {
            boolean vanBenneAlkohol = koktel.getHozzavalok().stream()
                    .anyMatch(h -> h.getNev().toLowerCase().contains("rum") ||
                            h.getNev().toLowerCase().contains("vodka") ||
                            h.getNev().toLowerCase().contains("gin"));

            if (vanBenneAlkohol) {
                throw new IllegalArgumentException("Alkoholmentes koktél nem tartalmazhat alkoholt!");
            }
        }

        // JPA kétirányú kapcsolat miatt a hozzávalóknak is tudniuk kell, melyik koktélhoz tartoznak
        if (koktel.getHozzavalok() != null) {
            koktel.getHozzavalok().forEach(h -> h.setKoktel(koktel));
        }

        return repository.save(koktel);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}