package stamps.controleur;

import javafx.fxml.FXML;
import stamps.model.CollectionSatellites;

/**
 * classe qui permet de manipuler les composants de menu dans la vue principale
 *
 * @author baptistedie
 */
public class PanneauMenu extends Controleur{

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauMenu(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
    }

    @FXML
    void quitter(){
        System.exit(0);
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
