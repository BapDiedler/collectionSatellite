package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

/**
 * classe qui permet de gérer les actions des boutons
 */
public class PanneauOutils extends Controleur{

    /**
     * panneau où se trouve la collection
     */
    private final PanneauCentral centre;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauOutils(CollectionSatellites collectionSatellites, PanneauCentral centre) {
        super(collectionSatellites);
        this.centre = centre;
    }

    /**
     * méthode qui permet d'ajouter des éléments à la collection
     */
    @FXML
    void ajouter(){
        collectionSatellites.ajouter("satellite");
        int placeSatellite = collectionSatellites.nbSatellites()-1;
        Satellite lastSatellite = collectionSatellites.getSatellite(placeSatellite);
        centre.ajouter(lastSatellite);
        System.out.println(collectionSatellites.nbSatellites());
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}