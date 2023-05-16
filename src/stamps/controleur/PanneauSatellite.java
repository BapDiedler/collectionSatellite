package stamps.controleur;

import javafx.animation.ScaleTransition;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PanneauSatellite extends Controleur {
    public ImageView image;
    public Label nom;
    public HBox hbox;
    private final int posSatellite;
    public ContextMenu menuContext;

    /**
     * compteur de satellite
     */
    private final Compteur compteur ;

    /**
     * constructeur de la classe
     *
     * @param satellites satellites manipulés
     */
    public PanneauSatellite(CollectionSatellites satellites, int pos, Compteur compteur){
        super(satellites);
        posSatellite = pos;
        this.compteur = compteur;
    }

    @FXML
    void initialize(){
        String url = collectionSatellites.getSatellite(posSatellite).getUrl();
        Image imageUrl = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)),
                200, 200, true, true) ;
        image.setImage(imageUrl);
        nom.setText(collectionSatellites.getSatellite(posSatellite).getNom());
    }

    /**
     * méthode qui permet d'aller sur la vue détaillée d'un satellite
     *
     * @throws IOException exception liée au chargement de fichier
     */
    @FXML
    void changerDetail(MouseEvent event) throws IOException {
        if(event.getButton() == MouseButton.SECONDARY) {
            menuContext.show(nom, event.getScreenX(), event.getScreenY());
        }else {
            collectionSatellites.clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauDetail.fxml"));
            PanneauDetail detail = new PanneauDetail(collectionSatellites,posSatellite);
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
    }

    /**
     * méthode qui permet de supprimer définitivement un satellite
     */
    @FXML
    void supprimer(){
        collectionSatellites.supprimer(collectionSatellites.getSatellite(posSatellite));
        collectionSatellites.notifierObservateurs();
        compteur.decrementer();
    }

    /**
     * méthode qui permet de tout supprimer
     */
    @FXML
    void supprimerAll(){
        collectionSatellites.supprimerAll();
        collectionSatellites.notifierObservateurs();
        compteur.setValeur(0);
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
