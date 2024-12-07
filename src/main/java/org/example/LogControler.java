package org.example;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogControler {
    private static Logger logger = Logger.getLogger("ProjectLogger");

    static {
        try {
            // Configure un fichier pour enregistrer les logs
            FileHandler fileHandler = new FileHandler("project.log", true); // Fichier des logs
            fileHandler.setFormatter(new SimpleFormatter()); // Formater les logs en texte simple
            logger.addHandler(fileHandler);

            logger.setUseParentHandlers(false); // Évite d'afficher les logs dans la console
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer le logger
    public static Logger getLogger() {
        return logger;
    }
}
