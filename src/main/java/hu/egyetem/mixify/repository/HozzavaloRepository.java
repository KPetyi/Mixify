package hu.egyetem.mixify.repository;

import hu.egyetem.mixify.model.Hozzavalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HozzavaloRepository extends JpaRepository<Hozzavalo, Long> {
}