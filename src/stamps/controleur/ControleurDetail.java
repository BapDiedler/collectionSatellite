package stamps.controleur;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;
import stamps.model.Information;
import stamps.model.Satellite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


/**
 * classe qui permet de manipuler et gérer le panneau FXML de la vue détaillée de chaque satellite
 * il va permettre la sauvegarde des éléments de chaque satellite.
 *
 * @author baptistedie
 */
public class ControleurDetail extends Controleur{

    /**
     * scrollPane où se trouve les informations
     */
    public ScrollPane scrollPane;

    /**
     * bouton pour manipuler les tags
     */
    public Button boutonTags;

    /**
     * pane contenant les éléments du bottom
     */
    public Pane paneBottom;

    /**
     * élément du menu qui permet de faire le lien entre le mode consultation et édition
     * et sauvegarde sur le passage de l'édition à consultation
     */
    public MenuItem sauvegarder;

    /**
     * zone de texte où rentrer la date
     */
    public Label date;

    /**
     * zone de texte où l'on rentre le nom du satellite
     */
    public TextField titre;

    /**
     * listView de label affichant les tags du satellites
     */
    public ListView<Label> listeTags;

    /**
     * vbox contenant les informations du satellite
     */
    @FXML
    private VBox vbox;

    /**
     * image du satellite
     */
    @FXML
    private ImageView image;

    /**
     * élément du menu permettant d'ajouter une information
     */
    @FXML
    private MenuItem ajout;

    /**
     * passage à l'élément précédent
     */
    @FXML
    private ImageView precedent;

    /**
     * passage à l'élément suivant
     */
    @FXML
    private ImageView suivant;

    /**
     * position du satellite observé
     */
    private int posSatellite;

    /**
     * arrayListe des informations du satellite
     */
    private final ArrayList<PanneauInformation> informations;

    /**
     * attribut qui permet de savoir
     */
    private boolean estSauvegarde;


    /**
     * constructeur principal de la classe
     *
     * @param posSatellite position du satellite
     * @param collectionSatellites collection manipulée par la classe
     */
    public ControleurDetail(CollectionSatellites collectionSatellites, int posSatellite) {
        super(collectionSatellites);
        this.posSatellite = posSatellite;
        this.informations = new ArrayList<>(10);
        estSauvegarde = true;
    }

    /**
     * initialisation des éléments sur l'affichage
     */
    @FXML
    void initialize(){
        appliquerImageFleche();
        appliquerImage();
        reagir();
    }

    /**
     * méthode qui permet de mettre sur les flèches pour passer au suivant ou au précédent
     */
    private void appliquerImageFleche(){
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/fleche.png")),90,90,true,true);
        precedent.setImage(image1);
        suivant.setImage(image1);
        suivant.setRotate(180);
    }

    /**
     * Passage au satellite suivant avec vérification de la validité du passage. Le passage
     * n'est pas valide s'il n'y a pas de satellite à côté ou si la fenêtre n'a pas été sauvegardée.
     */
    @FXML
    void suivant(){
        if(posSatellite!=collectionSatellites.nbSatellites()-1) { // il n'y a pas de suivant
            if(estSauvegarde) { // le fichier n'a pas été sauvegardé
                posSatellite += 1;
                collectionSatellites.notifierObservateurs();
            }else{
                lancerAlerte("Les données ne sont pas sauvegardées.\nElle risque d'être supprimer");
            }
        }
    }

    /**
     * Passage au satellite suivant avec vérification de la validité du passage. Le passage
     * n'est pas valide s'il n'y a pas de satellite à côté ou si la fenêtre n'a pas été sauvegardée.
     */
    @FXML
    void precedent(){
        if(posSatellite!=0) { // il n'y a pas de suivant
            if(estSauvegarde) { // le fichier n'a pas été sauvegardé
                posSatellite -= 1;
                collectionSatellites.notifierObservateurs();
            }else{
                lancerAlerte("Les données ne sont pas sauvegardées.\nElle risque d'être supprimer");
            }
        }
    }

    /**
     * méthode qui permet de placer les éléments sur la fenêtre en fonction du mode de lecture
     * mise à jour de tous les éléments
     */
    @FXML
    private void appliquerInformation(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        vbox.getChildren().clear(); // on retire tous les éléments de la vbox pour les mettre à jour

        date.setText(String.valueOf(satellite.getDateString()));
        titre.setText(satellite.getNom());
        ajout.setDisable(collectionSatellites.isEstConsulte());

        if(!collectionSatellites.isEstConsulte()) { // les informations changent en fonction du mode de lecture
            appliquerInformationsEdition();
        }else{
            applicationInformationsConsultation();
        }

        ajouterTags();
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }

    /**
     * méthode appliquant les informations dans le mode édition
     */
    private void applicationInformationsConsultation(){
        informations.clear();
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);

        for(Information information : satellite) { // création des vues consultations pour les information
            PanneauInformationConsultation informationConsultation = new PanneauInformationConsultation(information);

            //chargement du FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauInformationConsultation.fxml"));
            loader.setControllerFactory(ic -> informationConsultation);
            try {
                vbox.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * méthode appliquant les informations dans le mode Consultation
     */
    private void appliquerInformationsEdition(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        for (Information information : satellite) {
            PanneauInformation panneauInformation = new PanneauInformation(collectionSatellites, information);
            informations.add(panneauInformation);

            //chargement du FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
            loader.setControllerFactory(ic -> panneauInformation);
            try {
                vbox.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * méthode qui ajout l'image du satellite sur l'affichage
     */
    private void appliquerImage(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        Image im = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                700, 700, true, true) ;
        image.setImage(im);
    }

    /**
     * méthode qui permet d'ajouter des informations au satellite
     */
    @FXML
    private void ajouterInfo(){
        appliquerInformationsEdition();
    }

    /**
     * méthode qui permet de sauvegarder les données
     */
    @FXML
    void sauvegarde(){
        if(!collectionSatellites.isEstConsulte()){
            estSauvegarde = true;
            Satellite satellite = collectionSatellites.getSatellite(posSatellite);
            if(titre.getText().length() != 0)
                satellite.setNom(titre.getText());
            else {
                estSauvegarde = false;
                lancerAlerte("le nom n'a pas été rentré");
            }

            for(PanneauInformation info: informations){ // sauvegarde des informations
                info.sauvegardeInformation();
            }
        }else{
            estSauvegarde = false;
        }
        collectionSatellites.setEstConsulte();
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui permet d'afficher une alerte en cas d'erreur
     *
     * @param message message afficher dans l'alerte
     */
    private void lancerAlerte(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur: ");
        alert.setHeaderText("Une erreur vient de se produire.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * méthode qui permet de changer de vue
     */
    @FXML
    private void changerGlobal(){
        collectionSatellites.clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauGlobal.fxml"));
        Compteur compteur = new Compteur();
        PanneauGlobal global = new PanneauGlobal(collectionSatellites,compteur);
        PanneauMenu menu = new PanneauMenu(collectionSatellites);
        loader.setControllerFactory(ic -> {
            if (ic.equals(stamps.controleur.PanneauMenu.class)) return menu;
            return global;
        });
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Récupérer la référence de la stage actuelle
        StackPane stage = (StackPane) image.getScene().getRoot();

        root.translateYProperty().set(-stage.getScene().getHeight());


        stage.getChildren().clear();

        // Changer la scène de la stage actuelle
        stage.getChildren().add(root);

        //Create a timeline instance
        Timeline timeline = new Timeline();
        //Create a keyValue. We need to slide in -- We gradually decrement Y value to Zero
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        //Create keyframe of 1s with keyvalue kv
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        //Add frame to timeline
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * méthode qui permet de changer d'image
     */
    @FXML
    void chercheImage(){
        if(!collectionSatellites.isEstConsulte()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner une image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
            File initialDirectory = new File("src/ressource/image/utilisateur/");
            fileChooser.setInitialDirectory(initialDirectory);

            Stage stage = (Stage) vbox.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String imagePath = "/image/utilisateur/"+selectedFile.getName();
                collectionSatellites.getSatellite(posSatellite).setUrl(imagePath);
            }
            appliquerImage();
            collectionSatellites.notifierObservateurs();
        }
    }

    /**
     * méthode privée qui permet d'afficher les tags du satellite
     */
    @FXML
    private void afficherTags(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/PanneauListeTags.fxml"));
        Stage nouvelleFenetre = new Stage();
        PanneauListeTags tags = new PanneauListeTags(collectionSatellites,posSatellite,nouvelleFenetre);
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

    /**
     * méthode qui permet d'ajouter les tags du satellite dans l'affichage
     */
    private void ajouterTags(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        Iterator<String> iteratorTags = satellite.iteratorTags();
        listeTags.getItems().clear();

        while (iteratorTags.hasNext()) {
            String tag = iteratorTags.next();
            Label label = new Label(tag);
            label.setStyle("-fx-text-fill: white;" +
                    "-fx-font-size: 30px");
            label.setAlignment(Pos.CENTER);
            listeTags.getItems().add(label);
        }
    }


    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        boolean isConsulted = collectionSatellites.isEstConsulte();
        boutonTags.setVisible(!isConsulted);
        titre.setDisable(isConsulted);
        titre.setStyle("-fx-opacity: 1");
        precedent.setVisible(posSatellite != 0);
        suivant.setVisible(posSatellite != collectionSatellites.nbSatellites() - 1);
        titre.setText(collectionSatellites.getSatellite(posSatellite).getNom());

        sauvegarder.setText(isConsulted?"Édition":"Sauvegarde");

        appliquerInformation();
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }
}
