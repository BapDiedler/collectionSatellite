package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Information;
import stamps.model.Satellite;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * classe permettant de gérer la collection
 */
public class PanneauCentral extends Controleur{

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vbox;

    @FXML
    private ArrayList<PanneauSatellite> satellites;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauCentral(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
        satellites = new ArrayList<>(10);
    }

    @FXML
    void initialize(){
        scrollPane.setContent(vbox);
        vbox.setPrefHeight(scrollPane.getPrefHeight());
        for(int i=0; i<collectionSatellites.nbSatellites(); i++){
            ajouter();
        }
    }

    /**
     * méthode qui permet d'ajouter un satellite à la collection
     */
    @FXML
    void ajouter(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauSatellite.fxml"));
        PanneauSatellite panneauSat = new PanneauSatellite(collectionSatellites,collectionSatellites.nbSatellites()-1);
        satellites.add(panneauSat);
        loader.setControllerFactory(ic -> panneauSat);
        try {
            vbox.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * méthode qui permet d'aller sur la vue détaillée d'un satellite
     *
     * @throws IOException
     */
    @FXML
    void changerDetail() throws IOException {
        FXMLLoader loader = new FXMLLoader();


        loader.setLocation(getClass().getResource("../vue/PanneauDetail.fxml"));
        PanneauDetail detail = new PanneauDetail(collectionSatellites);
        loader.setControllerFactory(ic ->
            detail
        );

        Scene root = loader.load();
        // Récupérer la référence de la stage actuelle
        Stage stage = (Stage) vbox.getScene().getWindow();

        // Changer la scène de la stage actuelle
        stage.setScene(root);
        stage.show();
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
