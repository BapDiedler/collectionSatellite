package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import stamps.model.CollectionSatellites;


/**
 * classe qui permet de gérer la vue globale de la collection
 *
 * @author baptistedie
 */
public class PanneauGlobal extends Controleur{

    /**
     * bouton des menus de la fenêtre
     */
    public ButtonBar PanneauMenu;
    /**
     * élément permettant d'ajouter des satellites dans la collection
     */
    public ButtonBar PanneauOutils;
    /**
     * élément central de la collection
     */
    public ScrollPane PanneauCentre;


    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauGlobal(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
    }

    @FXML
    int nbSatellites(){
        return collectionSatellites.nbSatellites();
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
