package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import stamps.model.CollectionSatellites;

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
        reagir();
    }

    /**
     * méthode qui permet d'ajouter des éléments à la collection
     */
    @FXML
    void ajouter(){
        collectionSatellites.ajouter("satellite");
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        ajout.setVisible(!collectionSatellites.isEstConsulte());
    }
}
