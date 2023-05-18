package stamps.controleur;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;

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
            ControleurDetail detail = new ControleurDetail(collectionSatellites,posSatellite);
            loader.setControllerFactory(ic ->
                    detail
            );
            Parent root = loader.load();
            // Récupérer la référence de la stackPane actuelle
            StackPane stackPane = (StackPane) image.getScene().getRoot();

            root.translateYProperty().set(image.getScene().getHeight());

            stackPane.getChildren().clear();

            // Changer la scène de la stackPane actuelle
            stackPane.getChildren().add(root);

//Create a timeline instance
            Timeline timeline = new Timeline();
//Create a keyValue. We need to slide in -- We gradually decrement Y value to Zero
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
//Create keyframe of 1s with keyvalue kv
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
//Add frame to timeline
            timeline.getKeyFrames().add(kf);

//Start animation
            timeline.play();
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
