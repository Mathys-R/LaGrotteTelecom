package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * La classe {@code Map} représente une carte dans le jeu, composée de plusieurs salles.
 * Chaque salle peut contenir des éléments, des ennemis, ou des défis pour le joueur.
 *
 * La carte est initialisée avec un certain nombre de salles générées de manière aléatoire.
 * Un logger est utilisé pour enregistrer les événements significatifs dans la génération de la carte.
 */
public class Map {

    /** Liste des salles présentes sur la carte. */
    private List<Salle> salles;

    /** Logger utilisé pour enregistrer les événements relatifs à la carte. */
    private static final Logger logger = LogControler.getLogger();

    /**
     * Constructeur de la classe {@code Map}.
     *
     * Ce constructeur initialise la carte en générant un ensemble de salles à l'aide de la méthode
     * {@link #genererSalles(Player)}. Il prend en paramètre un joueur, qui peut être utilisé pour
     * personnaliser la génération des salles.
     *
     * @param player Le joueur présent sur la carte.
     */
    public Map(Player player) {
        logger.info("Initialisation de la carte...");
        salles = new ArrayList<>();
        genererSalles(player);
        logger.info("Carte générée avec " + salles.size() + " salles.");
    }

    /**
     * Génère un ensemble de salles de manière aléatoire.
     * Le nombre de salles générées varie entre 3 et 5. Chaque salle est choisie parmi une liste de noms possibles.
     *
     * @param player Le joueur qui est présent sur la carte, utilisé pour personnaliser les salles.
     */
    private void genererSalles(Player player) {
        Random random = new Random();
        // Génération entre 3 et 5 salles
        int nombreDeSalles = 3 + random.nextInt(2);
        logger.info("Génération de " + nombreDeSalles + " salles.");

        ArrayList<String> nomsPossibles = new ArrayList<>(Arrays.asList(
                "Forêt Enchantée",
                "Caverne Sombre",
                "Temple Abandonné",
                "Lac Mystique",
                "Forteresse Ancienne",
                "Colline Venteuse",
                "Mine Oubliée",
                "Crypte Perdue",
                "Désert Aride",
                "Île Fantôme",
                "Jardin Suspendu",
                "Passage Souterrain",
                "Marécage Empoisonné",
                "Tour du Dragon",
                "Ruines Maudites"
        ));

        for (int i = 0; i < nombreDeSalles; i++) {
            String nom = nomsPossibles.get(random.nextInt(nomsPossibles.size()));
            logger.fine("Création de la salle : " + nom);
            salles.add(new Salle(nom, player));
            nomsPossibles.remove(nom); // Retire le nom pour éviter les doublons
        }
        logger.info("Toutes les salles ont été générées avec succès.");
    }

    /**
     * Récupère une salle par son identifiant (index).
     * Si l'identifiant est invalide (inférieur à 0 ou supérieur au nombre de salles),
     * une erreur est enregistrée dans les logs et la méthode retourne {@code null}.
     *
     * @param idSalle L'identifiant de la salle à récupérer.
     * @return La salle correspondant à l'identifiant, ou {@code null} si l'identifiant est invalide.
     */
    public Salle getSalle(int idSalle) {
        if (idSalle < 0 || idSalle >= salles.size()) {
            logger.warning("Tentative d'accès à une salle avec un ID invalide : " + idSalle);
            return null;
        }
        logger.info("Accès à la salle avec ID : " + idSalle);
        return salles.get(idSalle);
    }

    /**
     * Récupère le nombre total de salles présentes sur la carte.
     *
     * @return Le nombre de salles générées sur la carte.
     */
    public int getSalleCount() {
        logger.info("La carte a " + salles.size() + " salles.");
        return salles.size();
    }

    /**
     * Affiche toutes les salles présentes sur la carte.
     * Si aucune salle n'est générée, un message d'avertissement est affiché et un message est enregistré dans les logs.
     */
    public void afficherSalles() {
        if (salles.isEmpty()) {
            logger.warning("Aucune salle à afficher.");
            System.out.println("Il n'y a pas de salles générées.");
            return;
        }

        logger.info("Affichage des " + salles.size() + " salles : ");
        for (int i = 0; i < salles.size(); i++) {
            System.out.println("Salle " + (i + 1) + ": " + salles.get(i));
            logger.fine("Salle " + (i + 1) + ": " + salles.get(i).getNom());
        }
    }
}