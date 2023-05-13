package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stamps.model.CollectionSatellites;

import java.util.Objects;

/**
 * classe qui permet de gérer les actions des boutons
 */
public class PanneauOutils extends Controleur{

    public Button ajout;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauOutils(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
    }

    @FXML
    void initialize(){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/ajouter.png")),90,90,true,true);
        ajout.setGraphic(new ImageView(image));
        reagir();
    }

    /**
     * méthode qui permet d'ajouter des éléments à la collection
     */
    @FXML
    void ajouter(){
        collectionSatellites.ajouter("Satellite");
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        ajout.setVisible(!collectionSatellites.isEstConsulte());
    }
}
