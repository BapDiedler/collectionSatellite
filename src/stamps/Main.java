package stamps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stamps.controleur.*;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;

/**
 * classe permettant de lancer la collection
 */
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
        loader.setControllerFactory(ic -> {
            if (ic.equals(stamps.controleur.PanneauMenu.class)) return menu;
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
