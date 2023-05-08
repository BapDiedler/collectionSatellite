package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Information;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PanneauDetail extends Controleur{

    public TextArea labelTitre;
    public VBox PanneauInformation;
    public ScrollPane scrollPane;
    @FXML
    private VBox vbox;
    @FXML
    private Label date;
    @FXML
    private ImageView image;
    private int posSatellite;

    @FXML
    private final ArrayList<PanneauInformation> informations;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauDetail(CollectionSatellites collectionSatellites) {
        this(collectionSatellites,0);
    }

    /**
     * constructeur principal de la classe
     *
     * @param posSatellite position du satellite
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauDetail(CollectionSatellites collectionSatellites, int posSatellite) {
        super(collectionSatellites);
        this.posSatellite = posSatellite;
        this.informations = new ArrayList<>(10);
    }

    /**
     * initialisation des éléments sur l'affichage
     */
    @FXML
    void initialize(){
        appliquerInformation();
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }

    /**
     * passage au satellite suivant
     */
    @FXML
    void suivant(){
        if(posSatellite!=collectionSatellites.nbSatellites()-1) {
            posSatellite += 1;
            appliquerInformation();
        }
    }

    /**
     * passage au satellite precedent
     */
    @FXML
    void precedent(){
        if(posSatellite!=0) {
            posSatellite -= 1;
            appliquerInformation();
        }
    }

    /**
     * méthode qui permet de placer les éléments sur la fenêtre
     */
    @FXML
    private void appliquerInformation(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        appliquerImage();
        vbox.getChildren().clear();
        date.setText(String.valueOf(satellite.getDate()));
        labelTitre.setText(satellite.getNom());
        for(Information information: satellite){
            PanneauInformation panneauInformation = new PanneauInformation(collectionSatellites,information);
            informations.add(panneauInformation);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
            loader.setControllerFactory(ic ->panneauInformation);
            try {
                vbox.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * méthode qui ajout l'image sur l'affichage
     */
    private void appliquerImage(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        Image im = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                300, 300, true, true) ;
        image = new ImageView(im);
    }


    @FXML
    private void ajouterInfo(){
        Information info = new Information();
        collectionSatellites.getSatellite(posSatellite).setInformations(info);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
        PanneauInformation panneauInfo = new PanneauInformation(collectionSatellites,info);
        //PanneauDetail detail = new PanneauDetail(collectionSatellites,posSatellite);
        informations.add(panneauInfo);
        loader.setControllerFactory(ic -> panneauInfo);
        try {
            vbox.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void sauvegarde(){
        labelTitre.setDisable(!labelTitre.isDisable());
        scrollPane.setDisable(!scrollPane.isDisabled());
        for(PanneauInformation information: informations){
            information.sauvegardeInformation();
            information.setDisable(!information.getDisable());
        }
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
