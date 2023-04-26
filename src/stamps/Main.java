package stamps;

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

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Collection de satellites");

        /**CollectionSatellites collectionSatellites = new CollectionSatellites() ;
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
        primaryStage.show();*/

        CollectionSatellites collectionSatellites = new CollectionSatellites();
        collectionSatellites.ajouter("A");
        collectionSatellites.ajouter("B");
        Information info = new Information("info1");
        info.setTexte("azertyhjnbvfds");
        collectionSatellites.getSatellite(0).setInformations(info);
        collectionSatellites.getSatellite(1).setInformations(info);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("vue/PanneauDetail.fxml"));
        PanneauInformation information = new PanneauInformation(collectionSatellites,collectionSatellites.getSatellite(0));
        PanneauDetail detail = new PanneauDetail(collectionSatellites, information);
        loader.setControllerFactory(ic -> {
            if (ic.equals(stamps.controleur.PanneauInformation.class)) return information;
            return detail;
        });
        Scene root = loader.load();
        primaryStage.setScene(root);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
