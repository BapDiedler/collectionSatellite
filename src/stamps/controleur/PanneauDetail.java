package stamps.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    /**
     * label contenant le nom du satellite
     */
    public Label labelTitre;

    /**
     * scrollPane où se trouve les informations
     */
    public ScrollPane scrollPane;

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
    public TextArea zoneDate;

    /**
     * zone de texte où l'on rentre le nom du satellite
     */
    public TextArea titre;

    /**
     * listeView de mots clefs afficher quand l'utilisateur choisit de rentrer les mots clefs
     */
    public ListView<Label> listMotsClefs;

    /**
     * label contenant tous les mots clefs du satellite
     */
    public Label labelMotClef;

    /**
     * vbox contenant les informations du satellite
     */
    @FXML
    private VBox vbox;

    /**
     * label contenant la date du satellite
     */
    @FXML
    private Label date;

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
     * méthode qui permet de placer les éléments sur la fenêtre en fonction du mode de lecture
     */
    @FXML
    private void appliquerInformation(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        appliquerImage();
        vbox.getChildren().clear();
        date.setText(String.valueOf(satellite.getDate()));
        labelTitre.setText(satellite.getNom());
        ajout.setDisable(collectionSatellites.isEstConsulte());
        if(!collectionSatellites.isEstConsulte()) {
            appliquerInformationsEdition();
        }else{
            applicationInformationsConsultation();
        }
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }


    /**
     * méthode appliquant les informations dans le mode édition
     */
    private void applicationInformationsConsultation(){
        informations.clear();
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
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
    }

    /**
     * méthode appliquant les informations dans le mode Consultation
     */
    private void appliquerInformationsEdition(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
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
        appliquerInformationsEdition();
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
            paneBottom.getChildren().remove(progressBar);
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
     * méthode qui permet d'ajouter des mots clefs dans au satellite
     */
    private void appliquerMotsClefs(){
        for(String mot: collectionSatellites.getMotsClefs("b")) {
            listMotsClefs.getItems().add(new Label(mot));
        }
    }

    /**
     * méthode qui permet d'ajouter des éléments dans la liste de mots clefs du satellite
     *
     * @return liste de mots clefs sous forme de string
     */
    private String ajouterMotsClefs(){
        StringBuilder builder = new StringBuilder(10);
        for(Label label: listMotsClefs.getItems()){
            builder.append(label.getText());
        }
        return builder.toString();
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        listMotsClefs.getItems().clear();
        boolean change = collectionSatellites.isEstConsulte();
        appliquerMotsClefs();
        zoneDate.setVisible(!change);
        titre.setVisible(!change);
        labelMotClef.setVisible(change);
        listMotsClefs.setVisible(!change);
        precedent.setVisible(posSatellite != 0);
        suivant.setVisible(posSatellite != collectionSatellites.nbSatellites() - 1);
        if(change){
            labelMotClef.setText(ajouterMotsClefs());
            sauvegarder.setText("édition");
        }else{
            sauvegarder.setText("sauvegarde");
            titre.setText(collectionSatellites.getSatellite(posSatellite).getNom());
            zoneDate.setText(collectionSatellites.getSatellite(posSatellite).getDateString());
        }
        appliquerInformation();
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }
}
