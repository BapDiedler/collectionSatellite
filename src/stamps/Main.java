package stamps;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stamps.controleur.*;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Collection de satellites");

        CollectionSatellites collectionSatellites = new CollectionSatellites();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("vue/PanneauGlobal.fxml"));
        Compteur compteur = new Compteur();
        PanneauGlobal global = new PanneauGlobal(collectionSatellites,compteur);
        PanneauMenu menu = new PanneauMenu(collectionSatellites);
        PanneauCentral central = new PanneauCentral(collectionSatellites);
        PanneauOutils outils = new PanneauOutils(collectionSatellites);
        loader.setControllerFactory(ic -> {
            if (ic.equals(stamps.controleur.PanneauMenu.class)) return menu;
            else if (ic.equals(stamps.controleur.PanneauOutils.class)) return outils;
            else if (ic.equals(stamps.controleur.PanneauCentral.class)) return central;
            return global;
        });
        Scene root = loader.load();
        primaryStage.setScene(root);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
