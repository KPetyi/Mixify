import hu.egyetem.mixify.model.Hozzavalo;
import hu.egyetem.mixify.model.Kategoria;
import hu.egyetem.mixify.model.Koktel;
import hu.egyetem.mixify.repository.KoktelRepository;
import hu.egyetem.mixify.service.KoktelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KoktelServiceTest {

    @Mock
    private KoktelRepository repository;

    @InjectMocks
    private KoktelService service;

    @Test
    void testSave_SikeresMentes() {
        // Arrange (Előkészítés)
        Kategoria kategoria = new Kategoria();
        kategoria.setNev("Klasszikus");

        Koktel koktel = new Koktel();
        koktel.setNev("Mojito");
        koktel.setAr(2500);
        koktel.setKategoria(kategoria);

        when(repository.save(any(Koktel.class))).thenReturn(koktel);

        // Act (Cselekvés)
        Koktel mentett = service.save(koktel);

        // Assert (Ellenőrzés)
        assertNotNull(mentett);
        assertEquals("Mojito", mentett.getNev());
        verify(repository, times(1)).save(koktel);
    }

    @Test
    void testSave_TulOlcsokitelKiveteltDob() {
        // Arrange
        Koktel koktel = new Koktel();
        koktel.setNev("Olcsó Víz");
        koktel.setAr(300); // 500 alatt van!

        // Act & Assert
        IllegalArgumentException kivetel = assertThrows(IllegalArgumentException.class, () -> {
            service.save(koktel);
        });

        assertEquals("A koktél ára nem lehet kevesebb 500 Ft-nál!", kivetel.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void testSave_AlkoholmentesbeAlkoholtTeszKiveteltDob() {
        // Arrange
        Kategoria mentesKategoria = new Kategoria();
        mentesKategoria.setNev("Alkoholmentes");

        Hozzavalo rum = new Hozzavalo();
        rum.setNev("Fehér Rum");

        List<Hozzavalo> hozzavalok = new ArrayList<>();
        hozzavalok.add(rum);

        Koktel koktel = new Koktel();
        koktel.setNev("Virgin Mojito... rummal?");
        koktel.setAr(1500);
        koktel.setKategoria(mentesKategoria);
        koktel.setHozzavalok(hozzavalok);

        // Act & Assert
        IllegalArgumentException kivetel = assertThrows(IllegalArgumentException.class, () -> {
            service.save(koktel);
        });

        assertEquals("Alkoholmentes koktél nem tartalmazhat alkoholt!", kivetel.getMessage());
        verify(repository, never()).save(any());
    }
}