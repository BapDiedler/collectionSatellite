package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import stamps.model.CollectionSatellites;
import stamps.model.Information;


public class PanneauInformation extends Controleur{

    @FXML
    private VBox vbox;

    @FXML
    private TextArea titre;

    @FXML
    private TextArea info;

    private final Information informationSatellite;

    @FXML
    private VBox informations;


    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauInformation(CollectionSatellites collectionSatellites, Information information) {
        super(collectionSatellites);
        this.informationSatellite = information;
    }

    @FXML
    void initialize(){
        changerTitre();
        changerInfo();
    }

    @FXML
    void changerTitre(){
        titre.setText(informationSatellite.getTitre());
    }

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
        collectionSatellites.notifierObservateurs();
    }

    public void setDisable(boolean dis){
        titre.setDisable(dis);
        info.setDisable(dis);
        if(dis){
            titre.setOpacity(3);
            info.setOpacity(3);
        }
    }

    public boolean getDisable(){
        return titre.isDisable();
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
    }
}
