package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.*;

/**
 * classe permettant de gérer la collection
 */
public class PanneauCentral extends Controleur{

    @FXML
    private ListView<HBox> listView;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauCentral(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
    }

    @FXML
    void initialize(){
        reagir();
    }

    /**
     * méthode qui permet d'ajouter un satellite à la collection
     */
    @FXML
    void ajouter(){
        PanneauSatellite panneauSat = new PanneauSatellite(collectionSatellites,collectionSatellites.nbSatellites()-1);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauSatellite.fxml"));
        loader.setControllerFactory(ic -> panneauSat);
        try {
            listView.getItems().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void ajouter(int ind){
        PanneauSatellite panneauSat = new PanneauSatellite(collectionSatellites,ind);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauSatellite.fxml"));
        loader.setControllerFactory(ic -> panneauSat);
        try {
            listView.getItems().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        listView.getItems().clear();
        for(int i=0; i<collectionSatellites.nbSatellites(); i++){
            ajouter(i);
        }
    }
}
