package hu.egyetem.mixify.controller;

import hu.egyetem.mixify.model.Koktel;
import hu.egyetem.mixify.service.KoktelService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/koktelek")
public class KoktelController {

    private final KoktelService service;

    public KoktelController(KoktelService service) {
        this.service = service;
    }

    // CREATE (Létrehozás)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Koktel koktel) {
        try {
            Koktel mentett = service.save(koktel);
            return ResponseEntity.status(HttpStatus.CREATED).body(mentett);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // READ ALL (Összes lekérdezése)
    @GetMapping
    public List<Koktel> getAll() {
        return service.findAll();
    }

    // READ ONE (Egy lekérdezése ID alapján)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // UPDATE (Módosítás)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Koktel koktel) {
        try {
            koktel.setId(id);
            return ResponseEntity.ok(service.save(koktel));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE (Törlés)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}