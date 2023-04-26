package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.Objects;

public class PanneauDetail extends Controleur{

    public Label labelTitre;
    public VBox PanneauInformation;
    @FXML
    private HBox hbox;
    @FXML
    private Label date;
    @FXML
    private ImageView image;
    private int posSatellite;

    @FXML
    private Button ajoutInfo;

    private PanneauInformation informations;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauDetail(CollectionSatellites collectionSatellites, PanneauInformation informations) {
        super(collectionSatellites);
        posSatellite = 0;
        this.informations = informations;
    }

    @FXML
    void initialize(){
        appliquerInformation();
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
        Image im = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                300, 300, true, true) ;
        image = new ImageView(im);
        date.setText(String.valueOf(satellite.getDate()));
        labelTitre.setText(satellite.getNom());
    }

    @FXML
    private void ajouterInfo(){
        informations.ajouterInfo();
    }

    @FXML
    void sauvegarde(){

    }

    @FXML
    void changerGlobal() throws IOException {
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
