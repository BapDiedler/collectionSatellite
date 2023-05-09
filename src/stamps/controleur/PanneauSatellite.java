package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.util.Objects;

public class PanneauSatellite extends Controleur{
    public ImageView image;
    public Label nom;
    private int posSatellite;

    /**
     * constructeur de la classe
     *
     * @param satellites satellites manipulés
     */
    public PanneauSatellite(CollectionSatellites satellites, int pos){
        super(satellites);
        posSatellite = pos;
    }

    @FXML
    void initialize(){
        String url = collectionSatellites.getSatellite(posSatellite).getUrl();
        Image imageUrl = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)),
                300, 300, true, true) ;
        image.setImage(imageUrl);
        nom.setText(collectionSatellites.getSatellite(posSatellite).getNom());
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
