package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import stamps.model.Information;

/**
 * la classe permet de manipuler les éléments d'information dans la vue consultation
 *
 * @author baptistedie
 */
public class ControleurInformationConsultation {

    /**
     * label contenant les informations de l'information
     */
    @FXML
    private Label info;

    /**
     * label contenant le titre de l'information
     */
    @FXML
    private Label titre;

    /**
     * information représentée par la classe
     */
    private final Information information;

    /**
     * constructeur de la classe
     *
     * @param information information
     */
    public ControleurInformationConsultation(Information information){
        this.information = information;
    }

    /**
     * méthode permettant d'initialiser les composants de l'information
     */
    @FXML
    void initialize(){
            titre.setText(information.getTitre());
            info.setText(information.getTexte());
    }
}
