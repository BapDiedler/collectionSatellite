package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import stamps.model.Information;
import stamps.model.Satellite;


/**
 * Classe qui gère le panneau d'information pour chaque satellite.
 * Il permet d'afficher et de modifier les informations d'un satellite.
 *
 * @author baptistedie
 */
public class ControleurInformation{

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
     * satellite contenant l'information
     */
    private final Satellite satellite;

    /**
     * controleur où se trouve l'information
     */
    private final ControleurDetail controleurDetail;



    /**
     * constructeur principal de la classe
     *
     * @param information information manipulée
     */
    public ControleurInformation(ControleurDetail controleur, Satellite satellite, Information information) {
        this.informationSatellite = information;
        this.satellite = satellite;
        this.controleurDetail = controleur;
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
    private void changerTitre(){
        titre.setText(informationSatellite.getTitre());
    }



    /**
     * méthode qui permet de changer l'information
     */
    @FXML
    private void changerInfo(){
        info.setText(informationSatellite.getTexte());
    }



    /**
     * méthode qui permet de supprimer l'information
     */
    @FXML
    private void supprimer(){
        satellite.supprimerInfo(informationSatellite);
        controleurDetail.supprimerInfo(this);
    }



    /**
     * méthode qui permet de changer les informations de satellite
     */
    public void sauvegardeInformation(){
        informationSatellite.setTexte(info.getText());
        informationSatellite.setTitre(titre.getText());
    }
}
