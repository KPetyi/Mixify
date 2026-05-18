package hu.egyetem.mixify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Koktel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nev;
    private int ar;
    private String poharTipus;

    @ManyToOne
    private Kategoria kategoria;

    @JsonIgnore
    @OneToMany(mappedBy = "koktel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hozzavalo> hozzavalok = new ArrayList<>();
}