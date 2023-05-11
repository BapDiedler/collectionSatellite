package stamps;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stamps.controleur.*;
import stamps.model.CollectionSatellites;
import stamps.model.Information;
import stamps.model.Satellite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private CollectionSatellites recuperationDonnees(){
        CollectionSatellites collectionSatellites = new CollectionSatellites();
        Gson gson = new Gson();
        File directory = new File("src/ressource/sauvegarde");
        File[] jsonFiles = directory.listFiles();
        for (File jsonFile : jsonFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
                Satellite satellite = gson.fromJson(reader, Satellite.class);
                collectionSatellites.ajouter(satellite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return collectionSatellites;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Collection de satellites");

        CollectionSatellites collectionSatellites = recuperationDonnees();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("vue/PanneauGlobal.fxml"));
        PanneauGlobal global = new PanneauGlobal(collectionSatellites);
        PanneauMenu menu = new PanneauMenu(collectionSatellites);
        PanneauCentral central = new PanneauCentral(collectionSatellites);
        PanneauOutils outils = new PanneauOutils(collectionSatellites,central);
        loader.setControllerFactory(ic -> {
            if (ic.equals(stamps.controleur.PanneauMenu.class)) return menu;
            else if (ic.equals(stamps.controleur.PanneauOutils.class)) return outils;
            else if (ic.equals(stamps.controleur.PanneauCentral.class)) return central;
            return global;
        });
        Scene root = loader.load();
        primaryStage.setScene(root);
        primaryStage.show();

        /*CollectionSatellites collectionSatellites = new CollectionSatellites();
        collectionSatellites.ajouter("A");
        //collectionSatellites.ajouter("B");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("vue/PanneauDetail.fxml"));
        PanneauDetail detail = new PanneauDetail(collectionSatellites,0);
        loader.setControllerFactory(ic -> detail);
        Scene root = loader.load();
        primaryStage.setScene(root);
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
