package hu.egyetem.mixify.repository;

import hu.egyetem.mixify.model.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {
}