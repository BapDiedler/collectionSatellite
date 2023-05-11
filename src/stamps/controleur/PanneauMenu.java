package stamps.controleur;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.*;

/**
 * classe qui permet de manipuler les composants de menu dans la vue principale
 *
 * @author baptistedie
 */
public class PanneauMenu extends Controleur{

    public MenuItem ajout;
    public ButtonBar buttonBar;
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

    @FXML
    void sauvegarder(){
        for(Satellite satellite: collectionSatellites){
            Gson gson = new Gson();
            String json = gson.toJson(satellite);
            try (FileWriter writer = new FileWriter("src/ressource/sauvegarde/sat"+satellite.getIdentifiant()+".json")) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * méthode qui permet de récupérer des données de satellite dans le fichier de sauvegarde
     */
    @FXML
    void chercheDonnee(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("fichier", "*.json"));

        File initialDirectory = new File("src/ressource/sauvegarde/");
        fileChooser.setInitialDirectory(initialDirectory);

        Stage stage = (Stage) buttonBar.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            File fichier = selectedFile.getAbsoluteFile();
            Gson gson = new Gson();
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(fichier);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedReader reader = new BufferedReader(fileReader);
            Satellite sat = gson.fromJson(reader,Satellite.class);
            collectionSatellites.ajouter(sat);
        }
        collectionSatellites.notifierObservateurs();
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
