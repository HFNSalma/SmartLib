package dcc.catalogueservice.web;

import dcc.catalogueservice.entities.Livre;
import dcc.catalogueservice.service.LivreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/livres")
public class API {

    @Autowired
    private LivreService livreService;

    @Operation(summary = "Obtenir tous les livres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de tous les livres",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class)))
    })
    @GetMapping
    public List<Livre> obtenirTousLesLivres() {
        return livreService.obtenirTousLesLivres();
    }

    @Operation(summary = "Obtenir un livre par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Détails du livre",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class))),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Livre> obtenirLivreParId(@PathVariable Long id) {
        Optional<Livre> livre = livreService.obtenirLivreParId(id);
        return livre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Rechercher des livres par titre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres correspondant au titre recherché",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class)))
    })
    @GetMapping("/recherche/titre")
    public ResponseEntity<List<Livre>> rechercherLivresParTitre(@RequestParam String titre) {
        List<Livre> livres = livreService.rechercherLivresParTitre(titre);
        return ResponseEntity.ok(livres);
    }

    @Operation(summary = "Rechercher des livres par auteur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres de l'auteur recherché",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class)))
    })
    @GetMapping("/recherche/auteur")
    public ResponseEntity<List<Livre>> rechercherLivresParAuteur(@RequestParam String auteur) {
        List<Livre> livres = livreService.rechercherLivresParAuteur(auteur);
        return ResponseEntity.ok(livres);
    }

    @Operation(summary = "Rechercher des livres par genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres du genre recherché",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class)))
    })
    @GetMapping("/recherche/genre")
    public ResponseEntity<List<Livre>> rechercherLivresParGenre(@RequestParam String genre) {
        List<Livre> livres = livreService.rechercherLivresParGenre(genre);
        return ResponseEntity.ok(livres);
    }

    @Operation(summary = "Rechercher des livres par année de publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres publiés l'année recherchée",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class)))
    })
    @GetMapping("/recherche/annee")
    public ResponseEntity<List<Livre>> rechercherLivresParAnneePublication(@RequestParam Integer annee) {
        List<Livre> livres = livreService.rechercherLivresParAnneePublication(annee);
        return ResponseEntity.ok(livres);
    }

    @Operation(summary = "Vérifier l'existence d'un livre par ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne vrai si le livre existe, sinon faux")
    })
    @GetMapping("/existe")
    public ResponseEntity<Boolean> verifierExistenceLivreParIsbn(@RequestParam String isbn) {
        boolean existe = livreService.verifierExistenceLivreParIsbn(isbn);
        return ResponseEntity.ok(existe);
    }

    @Operation(summary = "Ajouter un nouveau livre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livre créé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class)))
    })
    @PostMapping
    public ResponseEntity<Livre> ajouterLivre(@RequestBody Livre livre) {
        Livre livreCree = livreService.ajouterLivre(livre);
        return ResponseEntity.status(HttpStatus.CREATED).body(livreCree);
    }

    @Operation(summary = "Mettre à jour un livre existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre mis à jour avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livre.class))),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Livre> mettreAJourLivre(@PathVariable Long id, @RequestBody Livre detailsLivre) {
        Livre livreMisAJour = livreService.mettreAJourLivre(id, detailsLivre);
        return livreMisAJour != null ? ResponseEntity.ok(livreMisAJour) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Supprimer un livre par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livre supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLivre(@PathVariable Long id) {
        livreService.supprimerLivre(id);
        return ResponseEntity.noContent().build();
    }
}
