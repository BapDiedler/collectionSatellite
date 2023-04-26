package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import stamps.model.CollectionSatellites;
import stamps.model.Information;
import stamps.model.Satellite;

public class PanneauInformation extends Controleur{

    @FXML
    private VBox vbox;

    private final Satellite satellite;


    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauInformation(CollectionSatellites collectionSatellites, Satellite satellite) {
        super(collectionSatellites);
        this.satellite = satellite;
    }

    @FXML
    void initialize(){
        for (Information info: satellite){
            TextArea titre = new TextArea(info.getTitre());
            vbox.getChildren().add(titre);
            TextArea texte = new TextArea(info.getTexte());
            vbox.getChildren().add(texte);
        }
    }

    @FXML
    void ajouterInfo(){
        Information info = satellite.getInformations(satellite.nbInformations());
        TextArea titre = new TextArea(info.getTitre());
        vbox.getChildren().add(titre);
        TextArea texte = new TextArea(info.getTexte());
        vbox.getChildren().add(texte);
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {


    }
}
