package stamps.controleur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;

import java.io.*;

/**
 * classe qui permet de manipuler les composants de menu dans la vue principale
 *
 * @author baptistedie
 */
public class ControleurMenu extends Controleur{

    /**
     * bouton qui permet d'ajouter un élément
     */
    public MenuItem ajout;

    /**
     * bar contenant les boutons
     */
    public ButtonBar buttonBar;

    /**
     * menu permettant de changer de mode
     */
    public MenuItem edition;

    /**
     * menu permettant de trier les éléments de la collection
     */
    public MenuButton trierMenu;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public ControleurMenu(CollectionSatellites collectionSatellites) {
        super(collectionSatellites);
    }

    /**
     * méthode qui permet l'initialisation des éléments FXML
     */
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
    void sauvegarder() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(collectionSatellites, CollectionSatellites.class);

        try {
            // Utiliser le FileChooser pour obtenir le fichier de sauvegarde
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sauvegarder la collection");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON (*.json)", "*.json"));
            File file = fileChooser.showSaveDialog(buttonBar.getScene().getWindow());

            if (file != null) {

                // Vérifier si l'extension .json est déjà présente
                if (!file.getName().endsWith(".json")) {
                    // Ajouter l'extension .json au nom de fichier
                    String filePath = file.getAbsolutePath() + ".json";
                    file = new File(filePath);
                }

                // Écrire le JSON dans le fichier
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json);
                fileWriter.close();

                System.out.println("Collection sauvegardée avec succès.");
            }

            collectionSatellites.notifierObservateurs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * méthode qui permet de récupérer des données de satellite dans le fichier de sauvegarde
     */
    @FXML
    void chercheDonnee(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une collection");
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
        new ControleurListeTags(collectionSatellites);
    }


    /**
     * méthode qui permet de rechercher des satellites en fonctions des mots clefs
     */
    @FXML
    private void recherche(){
        new ControleurRechercheTags(collectionSatellites);
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
