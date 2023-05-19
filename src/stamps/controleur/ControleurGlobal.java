package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.Objects;


/**
 * Classe qui permet de gérer la vue globale de la collection de satellites.
 * Cette classe étend la classe Controleur.
 * Elle contient les éléments de la vue globale et les méthodes associées.
 * Elle utilise également un compteur de satellites.
 *
 * @author  baptistedie
 */
public class ControleurGlobal extends Controleur {

    /**
     * ListView contenant les HBox représentant les satellites
     */
    @FXML
    private ListView<HBox> listView;

    /**
     * Bouton permettant l'ajout d'un satellite
     */
    @FXML
    private Button ajout;

    /**
     * Compteur de satellites
     */
    private final Compteur compteur;

    /**
     * Label permettant d'afficher le compteur de satellites
     */
    @FXML
    private Label compte;


    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public ControleurGlobal(CollectionSatellites collectionSatellites, Compteur compteur) {
        super(collectionSatellites);
        this.compteur = compteur;
        this.compteur.setValeur(collectionSatellites.nbSatellites());
    }

    /**
     * méthode qui permet d'initialiser les éléments de la fenêtre
     */
    @FXML
    void initialize(){
        listView.setCellFactory(listView-> new CustomListCell());
        compte.textProperty().bind(compteur.getPropertyValue().asString());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/developpeur/ajouter.png")),
                90,90,true,true);
        ajout.setGraphic(new ImageView(image));
        reagir();
    }

    /**
     * méthode qui permet d'ajouter un nouveau satellite
     */
    @FXML
    void ajouter(){
        collectionSatellites.ajouter(new Satellite());
        collectionSatellites.notifierObservateurs();
        compteur.incrementer();
    }

    /**
     * méthode qui ajout un satellite
     *
     * @param ind position du satellite
     */
    void ajouter(int ind){
        PanneauSatellite panneauSat = new PanneauSatellite(collectionSatellites,ind,compteur);

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
        ajout.setVisible(!collectionSatellites.isEstConsulte());
    }
}
