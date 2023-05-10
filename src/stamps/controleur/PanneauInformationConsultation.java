package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import stamps.model.Information;
import stamps.model.Satellite;


public class PanneauInformationConsultation {

    @FXML
    private VBox vbox;

    private Satellite satellite;

    private Information information;

    public PanneauInformationConsultation(Satellite satellite, Information information){
        this.satellite = satellite;
        this.information = information;
    }

    @FXML
    void initialize(){
            Label titre = new Label(information.getTitre());
            Label info = new Label(information.getTexte());
            vbox.getChildren().addAll(titre,info);
    }
}
