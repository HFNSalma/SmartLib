package dcc.catalogueservice.service;

import dcc.catalogueservice.entities.Livre;
import dcc.catalogueservice.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    public List<Livre> obtenirTousLesLivres() {
        return livreRepository.findAll();
    }

    public Optional<Livre> obtenirLivreParId(Long id) {
        return livreRepository.findById(id);
    }

    public List<Livre> rechercherLivresParTitre(String titre) {
        return livreRepository.findByTitre(titre);
    }

    public List<Livre> rechercherLivresParAuteur(String auteur) {
        return livreRepository.findByAuteur(auteur);
    }

    public List<Livre> rechercherLivresParGenre(String genre) {
        return livreRepository.findByGenre(genre);
    }

    public List<Livre> rechercherLivresParAnneePublication(Integer annee) {
        return livreRepository.findByAnneePublication(annee);
    }

    public boolean verifierExistenceLivreParIsbn(String isbn) {
        return livreRepository.existsByIsbn(isbn);
    }

    public Livre ajouterLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public Livre mettreAJourLivre(Long id, Livre detailsLivre) {
        return livreRepository.findById(id).map(livre -> {
            livre.setTitre(detailsLivre.getTitre());
            livre.setAuteur(detailsLivre.getAuteur());
            livre.setIsbn(detailsLivre.getIsbn());
            livre.setAnneePublication(detailsLivre.getAnneePublication());
            livre.setGenre(detailsLivre.getGenre());
            livre.setCopiesDisponibles(detailsLivre.getCopiesDisponibles());
            livre.setDescription(detailsLivre.getDescription());  // Mise à jour de la description
            return livreRepository.save(livre);
        }).orElse(null);
    }

    public void supprimerLivre(Long id) {
        livreRepository.deleteById(id);
    }
}