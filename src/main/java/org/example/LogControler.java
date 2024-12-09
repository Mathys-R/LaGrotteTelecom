package org.example;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Classe responsable de la gestion des logs dans le projet.
 * Elle configure un logger qui écrit les logs dans un fichier texte.
 *
 * Cette classe est initialisée lors du chargement de la classe (bloc static) et configure le logger
 * pour enregistrer les messages dans un fichier nommé "project.log". Le logger utilise un format
 * simple de texte pour les messages.
 */
public class LogControler {

    // Logger qui sera utilisé pour enregistrer les messages de log
    private static Logger logger = Logger.getLogger("ProjectLogger");

    /**
     * Bloc statique pour configurer le logger au moment du chargement de la classe.
     * Cette méthode configure un fichier handler qui écrit les logs dans un fichier "project.log".
     * Le format des logs est défini par un SimpleFormatter et les logs ne seront pas affichés dans la console.
     *
     * @throws IOException Si un problème survient lors de la création du FileHandler
     */
    static {
        try {
            // Configure un fichier pour enregistrer les logs
            FileHandler fileHandler = new FileHandler("project.log", true); // true pour l'ajout aux logs existants
            fileHandler.setFormatter(new SimpleFormatter()); // Formater les logs en texte simple
            logger.addHandler(fileHandler);

            // Empêche d'afficher les logs dans la console
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'exception si la configuration échoue
        }
    }

    /**
     * Récupère l'instance du logger configuré pour ce projet.
     * Cette méthode retourne le logger configuré pour permettre l'enregistrement des logs dans le fichier.
     *
     * @return Le logger configuré pour ce projet
     */
    public static Logger getLogger() {
        return logger;
    }
}
