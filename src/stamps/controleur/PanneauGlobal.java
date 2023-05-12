package stamps.controleur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;

import java.io.IOException;


/**
 * classe qui permet de gérer la vue globale de la collection
 *
 * @author baptistedie
 */
public class PanneauGlobal extends Controleur{

    @FXML
    private ListView<HBox> listView;

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
        compte.textProperty()
                .bind(compteur.getPropertyValue().asString());
    }

    @FXML
    int nbSatellites(){
        return collectionSatellites.nbSatellites();
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

        //listView.setCellFactory(listView -> new ListCell<>());
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
    }
}
