package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PanneauDetail extends Controleur{

    public Label labelTitre;
    @FXML
    private HBox hbox;
    @FXML
    private Label date;
    private int posSatellite;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauDetail(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
        posSatellite = 0;
    }

    /**
     * passage au satellite suivant
     */
    @FXML
    void suivant(){
        if(posSatellite!=collectionSatellites.nbSatellites()-1)
            posSatellite+=1;
        appliquerInformation();
    }

    /**
     * passage au satellite precedent
     */
    @FXML
    void precedent(){
        if(posSatellite!=0)
            posSatellite-=1;
        appliquerInformation();
    }

    /**
     * méthode qui permet de placer les éléments sur la fenêtre
     */
    private void appliquerInformation(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                300, 300, true, true) ;
        ImageView imageView = new ImageView(image);
        hbox.getChildren().set(0,imageView);
        Label label1 = new Label(satellite.getInformations(0).getTexte());
        hbox.getChildren().set(1,label1);
        date.setText(String.valueOf(satellite.getDate()));
        labelTitre.setText(satellite.getNom());
    }

    @FXML
    void sauvegarde(){

    }

    @FXML
    void changerGlobal() throws IOException {
        CollectionSatellites collectionSatellites = new CollectionSatellites() ;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauGlobal.fxml"));
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
        // Récupérer la référence de la stage actuelle
        Stage stage = (Stage) hbox.getScene().getWindow();

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
