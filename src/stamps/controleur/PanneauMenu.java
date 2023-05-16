package stamps.controleur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * classe qui permet de manipuler les composants de menu dans la vue principale
 *
 * @author baptistedie
 */
public class PanneauMenu extends Controleur{

    /**
     * bouton qui permet d'ajouter un élément
     */
    public MenuItem ajout;

    /**
     * bar contenant les boutons
     */
    public ButtonBar buttonBar;

    /**
     *
     */
    public MenuItem edition;
    public MenuButton trierMenu;

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

    /**
     * méthode qui ferme la fenêtre
     */
    @FXML
    void quitter(){
        System.exit(0);
    }

    /**
     * méthode qui permet de trier les satellites par leur nom
     */
    @FXML
    void trierNom(){
        collectionSatellites.trierNom();
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui permet de trier les satellites par leur date
     */
    @FXML
    void trierDate(){
        collectionSatellites.trierDate();
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui permet de trier par apparition dans la collection
     */
    @FXML
    void trierApparition(){
        collectionSatellites.trierApparition();
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui change le mode de consultation
     */
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
        collectionSatellites.ajouter("Satellite");
    }


    /**
     * méthode qui permet de sauvegarder la collection de satellites en format Json
     */
    @FXML
    void sauvegarder(){
        File selectedDirectory = new File("src/ressource/sauvegarde/");
        Path directoryPath = selectedDirectory.toPath();
        long nombreElements = 0;
        try {
            nombreElements = Files.list(directoryPath).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(collectionSatellites,CollectionSatellites.class);
        try (FileWriter writer = new FileWriter("src/ressource/sauvegarde/collection"+nombreElements+".json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collectionSatellites.notifierObservateurs();
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(fichier);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedReader reader = new BufferedReader(fileReader);
            collectionSatellites.copieCollectionSatellites(gson.fromJson(reader,CollectionSatellites.class));
        }
        collectionSatellites.notifierObservateurs();
    }


    /**
     * méthode qui permet d'afficher les tags se trouvant dans la collection de satellites
     */
    @FXML
    private void afficherTags(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/PanneauListeTags.fxml"));
        Stage nouvelleFenetre = new Stage();
        PanneauListeTags tags = new PanneauListeTags(collectionSatellites,nouvelleFenetre);
        loader.setControllerFactory(ic -> tags);
        Scene root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Créer une nouvelle fenêtre
        nouvelleFenetre.setTitle("Tags");
        nouvelleFenetre.setScene(root);

        // Afficher la nouvelle fenêtre
        nouvelleFenetre.show();
    }

    @FXML
    private void recherche(){

    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        if(collectionSatellites.isEstConsulte()){
            edition.setText("édition");
            ajout.setDisable(true);
            trierMenu.setVisible(false);
        }else{
            edition.setText("consultation");
            ajout.setDisable(false);
            trierMenu.setVisible(true);
        }
    }
}
