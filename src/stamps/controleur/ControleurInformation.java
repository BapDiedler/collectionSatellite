package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import stamps.model.Information;


/**
 * Classe qui gère le panneau d'information pour chaque satellite.
 * Il permet d'afficher et de modifier les informations d'un satellite.
 */
public class ControleurInformation {

    /**
     * espace où se trouve le titre de l'information
     */
    @FXML
    private TextArea titre;

    /**
     * espace où se trouve les informations en correspondance avec le titre
     */
    @FXML
    private TextArea info;

    /**
     * information représentée
     */
    private final Information informationSatellite;


    /**
     * constructeur principal de la classe
     *
     * @param information information manipulée
     */
    public ControleurInformation(Information information) {
        this.informationSatellite = information;
    }

    /**
     * méthode pour initialiser les éléments de la fenêtre
     */
    @FXML
    void initialize(){
        changerTitre();
        changerInfo();
    }

    /**
     * méthode qui permet de changer le titre de l'information
     */
    @FXML
    void changerTitre(){
        titre.setText(informationSatellite.getTitre());
    }

    /**
     * méthode qui permet de changer l'information
     */
    @FXML
    void changerInfo(){
        info.setText(informationSatellite.getTexte());
    }

    /**
     * méthode qui permet de changer les informations de satellite
     */
    public void sauvegardeInformation(){
        informationSatellite.setTexte(info.getText());
        informationSatellite.setTitre(titre.getText());
    }
}
