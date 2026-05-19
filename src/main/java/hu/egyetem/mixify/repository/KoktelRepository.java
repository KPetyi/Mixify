package hu.egyetem.mixify.repository;

import hu.egyetem.mixify.model.Koktel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KoktelRepository extends JpaRepository<Koktel, Long> {
}