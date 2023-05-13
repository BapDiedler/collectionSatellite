package stamps.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import stamps.exception.CollectionExceptionDate;
import stamps.model.CollectionSatellites;
import stamps.model.Compteur;
import stamps.model.Information;
import stamps.model.Satellite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PanneauDetail extends Controleur{

    public Label labelTitre;
    public ScrollPane scrollPane;
    public BorderPane borderPane;
    public Pane paneBottom;
    public MenuItem sauvegarder;
    public TextArea zoneDate;
    public TextArea titre;
    public Pane paneImage;

    @FXML
    private VBox vbox;
    @FXML
    private Label date;
    @FXML
    private ImageView image;

    @FXML
    private MenuItem ajout;

    @FXML
    private ImageView precedent;

    @FXML
    private ImageView suivant;
    private int posSatellite;

    private final ArrayList<PanneauInformation> informations;

    /**
     * constructeur principal de la classe
     *
     * @param posSatellite position du satellite
     * @param collectionSatellites collection manipulée par la classe
     */
    public PanneauDetail(CollectionSatellites collectionSatellites, int posSatellite) {
        super(collectionSatellites);
        this.posSatellite = posSatellite;
        this.informations = new ArrayList<>(10);
    }

    /**
     * initialisation des éléments sur l'affichage
     */
    @FXML
    void initialize(){
        appliquerImageFleche();
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
     * passage au satellite suivant
     */
    @FXML
    void suivant(){
        if(posSatellite!=collectionSatellites.nbSatellites()-1) {
            posSatellite += 1;
            collectionSatellites.notifierObservateurs();
        }
    }

    /**
     * passage au satellite precedent
     */
    @FXML
    void precedent(){
        if(posSatellite!=0) {
            posSatellite -= 1;
            appliquerInformation();
            collectionSatellites.notifierObservateurs();
        }
    }

    /**
     * méthode qui permet de placer les éléments sur la fenêtre
     */
    @FXML
    private void appliquerInformation(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        appliquerImage();
        vbox.getChildren().clear();
        date.setText(String.valueOf(satellite.getDate()));
        labelTitre.setText(satellite.getNom());
        if(!collectionSatellites.isEstConsulte()) {
            ajout.setDisable(false);
            for (Information information : satellite) {
                PanneauInformation panneauInformation = new PanneauInformation(collectionSatellites, information);
                informations.add(panneauInformation);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
                loader.setControllerFactory(ic -> panneauInformation);
                try {
                    vbox.getChildren().add(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            vbox.setPrefHeight(scrollPane.getPrefHeight());
        }else{
            ajout.setDisable(true);
            informations.clear();
            for(Information information : satellite) {
                PanneauInformationConsultation informationConsultation = new PanneauInformationConsultation(information);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../vue/PanneauInformationConsultation.fxml"));
                loader.setControllerFactory(ic -> informationConsultation);
                try {
                    vbox.getChildren().add(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            vbox.setPrefHeight(scrollPane.getPrefHeight());
        }
    }

    /**
     * méthode qui ajout l'image sur l'affichage
     */
    private void appliquerImage(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        Image im = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                700, 700, true, true) ;
        image.setImage(im);
        image.setLayoutY(150);
    }


    /**
     * méthode qui permet d'ajouter des informations au satellite
     */
    @FXML
    private void ajouterInfo(){
        if(!collectionSatellites.isEstConsulte()) {
            Information info = new Information();
            collectionSatellites.getSatellite(posSatellite).setInformations(info);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
            PanneauInformation panneauInfo = new PanneauInformation(collectionSatellites, info);
            informations.add(panneauInfo);
            loader.setControllerFactory(ic -> panneauInfo);
            try {
                vbox.getChildren().add(loader.load());
                vbox.setPrefHeight(scrollPane.getPrefHeight());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * méthode qui permet de sauvegarder les données
     */
    @FXML
    void sauvegarde(){
        if(!collectionSatellites.isEstConsulte()){
            Satellite satellite = collectionSatellites.getSatellite(posSatellite);
            satellite.setNom(titre.getText());
            try {
                satellite.setDateString(zoneDate.getText());
            } catch (CollectionExceptionDate e) {
                lancerAlerte(e.getMessage());
            }
            for(PanneauInformation information: informations){
                information.sauvegardeInformation();
            }
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
     * méthode qui permet de faire la transition entre la vue détaillé et la vue global
     */
    @FXML
    void changerGlobal() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(vbox.getPrefWidth());
        progressBar.setLayoutX(paneBottom.getPrefWidth()/2-progressBar.getPrefWidth()/2);

        // Ajouter la barre de chargement à la première scène
        paneBottom.getChildren().add(progressBar);

        // Créer l'animation de la barre de chargement
        Timeline timeline = new Timeline();
        KeyFrame startFrame = new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(2), new KeyValue(progressBar.progressProperty(), 1));
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.setOnFinished(e -> {
            changerVue();
            // Supprimer la barre de chargement de la première scène
            borderPane.getChildren().remove(progressBar);
        });
        timeline.play();
    }


    /**
     * méthode qui permet de changer de vue
     */
    private void changerVue(){

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
        Scene root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        // Récupérer la référence de la stage actuelle
        Stage stage = (Stage) vbox.getScene().getWindow();

        // Changer la scène de la stage actuelle
        stage.setScene(root);
        stage.show();
        collectionSatellites.notifierObservateurs();
    }

    @FXML
    void chercheImage(){
        if(!collectionSatellites.isEstConsulte()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner une image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

            File initialDirectory = new File("src/ressource/image/");
            fileChooser.setInitialDirectory(initialDirectory);

            Stage stage = (Stage) vbox.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String imagePath = "/image/"+selectedFile.getName();
                collectionSatellites.getSatellite(posSatellite).setUrl(imagePath);
            }
            collectionSatellites.notifierObservateurs();
        }
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        if(collectionSatellites.isEstConsulte()){
            sauvegarder.setText("édition");
            zoneDate.setVisible(false);
            titre.setVisible(false);
        }else{
            sauvegarder.setText("sauvegarde");
            zoneDate.setVisible(true);
            titre.setVisible(true);
            titre.setText(collectionSatellites.getSatellite(posSatellite).getNom());
            zoneDate.setText(collectionSatellites.getSatellite(posSatellite).getDateString());
        }
        precedent.setVisible(posSatellite != 0);
        suivant.setVisible(posSatellite != collectionSatellites.nbSatellites() - 1);
        appliquerInformation();
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }
}
