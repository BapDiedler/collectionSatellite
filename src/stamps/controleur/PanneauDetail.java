package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.util.Objects;

public class PanneauDetail extends Controleur{

    public Label labelTitre;
    @FXML
    private HBox hbox;
    @FXML
    private Label label;
    private int posSatellite;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauDetail(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
        posSatellite = 0;
    }

    @FXML
    void suivant(){
        if(posSatellite!=collectionSatellites.nbSatellites()-1)
            posSatellite+=1;
        appliquerInformation();
    }

    @FXML
    void precedant(){
        if(posSatellite!=0)
            posSatellite-=1;
        appliquerInformation();
    }

    /**
     * méthode qui permet de placer les éléments sur la fenêtre
     */
    private void appliquerInformation(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                300, 300, true, true) ;
        ImageView imageView = new ImageView(image);
        hbox.getChildren().set(0,imageView);
        hbox.getChildren().set(1,new Label(satellite.getIdentifiant()));
        label.setText(String.valueOf(satellite.getDate()));
        labelTitre.setText(satellite.getNom());
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
