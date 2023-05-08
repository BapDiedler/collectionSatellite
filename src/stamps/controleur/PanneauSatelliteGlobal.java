package stamps.controleur;

import javafx.fxml.FXML;
import stamps.model.Satellite;

/**
 * controleur qui va gérer les éléments de la vue globale comme le changement d'image
 *
 * @author baptistedie
 */
public class PanneauSatelliteGlobal {

    private Satellite satellite;

    /**
     * constructeur de la classe
     *
     * @param satellite satellite qui est représenté par le panneau
     */
    public PanneauSatelliteGlobal(Satellite satellite){
        this.satellite = satellite;
    }

    @FXML
    void changerUrl(){

    }

    @FXML
    void changerNom(){

    }
}
