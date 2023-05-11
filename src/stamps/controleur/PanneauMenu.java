package stamps.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import stamps.model.CollectionSatellites;

/**
 * classe qui permet de manipuler les composants de menu dans la vue principale
 *
 * @author baptistedie
 */
public class PanneauMenu extends Controleur{

    public MenuItem ajout;
    @FXML
    MenuItem edition;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauMenu(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
    }

    @FXML
    void initialize(){
        reagir();
    }

    @FXML
    void quitter(){
        System.exit(0);
    }

    @FXML
    void trierNom(){
        collectionSatellites.trierNom();
        collectionSatellites.notifierObservateurs();
    }

    @FXML
    void trierDate(){
        collectionSatellites.trierDate();
        collectionSatellites.notifierObservateurs();
    }

    @FXML
    void trierApparition(){
        collectionSatellites.trierApparition();
        collectionSatellites.notifierObservateurs();
    }

    @FXML
    void changeEdition(){
        collectionSatellites.setEstConsulte();
        collectionSatellites.notifierObservateurs();
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
        if(collectionSatellites.isEstConsulte()){
            edition.setText("édition");
            ajout.setDisable(true);
        }else{
            edition.setText("consultation");
            ajout.setDisable(false);
        }
    }
}
