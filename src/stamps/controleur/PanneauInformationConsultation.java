package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import stamps.model.Information;
import stamps.model.Satellite;


public class PanneauInformationConsultation {

    @FXML
    private VBox vbox;

    @FXML
    private Label info;

    @FXML
    private Label titre;

    private Information information;

    /**
     * constructeur de la classe
     *
     * @param information information
     */
    public PanneauInformationConsultation(Information information){
        this.information = information;
    }

    @FXML
    void initialize(){
            titre.setText(information.getTitre());
            info.setText(information.getTexte());
    }
}
