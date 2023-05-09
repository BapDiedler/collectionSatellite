package stamps.controleur;

import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.Objects;

public class PanneauSatellite extends Controleur {
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
     * méthode qui permet d'aller sur la vue détaillée d'un satellite
     *
     * @throws IOException
     */
    @FXML
    void changerDetail() throws IOException {
        FXMLLoader loader = new FXMLLoader();


        loader.setLocation(getClass().getResource("../vue/PanneauDetail.fxml"));
        PanneauDetail detail = new PanneauDetail(collectionSatellites);
        loader.setControllerFactory(ic ->
                detail
        );

        Scene root = loader.load();
        // Récupérer la référence de la stage actuelle
        Stage stage = (Stage) image.getScene().getWindow();

        // Changer la scène de la stage actuelle
        stage.setScene(root);
        stage.show();
    }

    public String getNom() {
        return collectionSatellites.getSatellite(posSatellite).getNom();
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
