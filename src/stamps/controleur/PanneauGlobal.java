package stamps.controleur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.Objects;


/**
 * classe qui permet de gérer la vue globale de la collection
 *
 * @author baptistedie
 */
public class PanneauGlobal extends Controleur{

    @FXML
    private ListView<HBox> listView;

    @FXML
    private Button ajout;

    /**
     * bouton des menus de la fenêtre
     */
    public ButtonBar PanneauMenu;
    /**
     * élément permettant d'ajouter des satellites dans la collection
     */
    public ButtonBar PanneauOutils;
    /**
     * élément central de la collection
     */
    public ListView<PanneauSatellite> PanneauCentre;

    /**
     * compteur de satellite
     */
    private final Compteur compteur ;
    public Label compte;


    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauGlobal(CollectionSatellites collectionSatellites, Compteur compteur) {
        super(collectionSatellites);
        this.compteur = compteur;
        this.compteur.setValeur(collectionSatellites.nbSatellites());
    }

    @FXML
    void initialize(){
        listView.setCellFactory(listView-> new CustomListCell());
        compte.textProperty().bind(compteur.getPropertyValue().asString());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/ajouter.png")),90,90,true,true);
        ajout.setGraphic(new ImageView(image));
        reagir();
    }

    @FXML
    int nbSatellites(){
        return collectionSatellites.nbSatellites();
    }

    @FXML
    void ajouter(){
        collectionSatellites.ajouter(new Satellite());
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui ajout un satellite
     *
     * @param ind position du satellite
     */
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
        if(nbSatellites()>compteur.getValue())
            compteur.incrementer();
        if(nbSatellites()<compteur.getValue())
            compteur.decrementer();
        if(collectionSatellites.isEstConsulte())
            ajout.setVisible(false);
        else
            ajout.setVisible(true);
    }
}
