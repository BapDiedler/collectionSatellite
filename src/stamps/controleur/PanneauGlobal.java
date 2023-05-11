package stamps.controleur;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;


/**
 * classe qui permet de gérer la vue globale de la collection
 *
 * @author baptistedie
 */
public class PanneauGlobal extends Controleur{

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
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        if(nbSatellites()>compteur.getValue())
            compteur.incrementer();
    }
}
