package dcc.catalogueservice.repository;


import dcc.catalogueservice.entities.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByTitre(String titre);
    List<Livre> findByAuteur(String auteur);
    List<Livre> findByGenre(String genre);
    List<Livre> findByAnneePublication(Integer annee);
    boolean existsByIsbn(String isbn);
}
