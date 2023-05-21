package stamps.controleur;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
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
import java.util.Optional;


/**
 * Classe qui permet de manipuler et gérer le panneau FXML de la vue détaillée de chaque satellite.
 * Elle permet la sauvegarde des éléments de chaque satellite.
 *
 * @author baptistedie
 */
public class ControleurDetail extends Controleur {

    /**
     * ScrollPane contenant les informations en mode édition et consultation.
     */
    @FXML
    private ScrollPane scrollPane;

    /**
     * Bouton pour manipuler les tags d'un satellite.
     */
    @FXML
    private Button boutonTags;

    /**
     * Élément du menu permettant de faire le lien entre le mode consultation et édition
     * et sauvegarde lors du passage de l'édition à la consultation.
     */
    @FXML
    private MenuItem sauvegarder;

    /**
     * Zone se trouvant dans le bas du BorderPane et affichant la date de création.
     */
    @FXML
    private Label date;

    /**
     * Zone de texte où l'on rentre le nom du satellite. Il peut être modifié en mode édition,
     * contrairement au mode consultation.
     */
    @FXML
    private TextField titre;

    /**
     * ListView de labels affichant les tags du satellite.
     */
    @FXML
    private ListView<Label> listeTags;

    /**
     * VBox contenant les informations du satellite.
     */
    @FXML
    private VBox vbox;

    /**
     * Image du satellite sur le côté droit
     */
    @FXML
    private ImageView image;

    /**
     * Élément du menu permettant d'ajouter une information en mode édition.
     */
    @FXML
    private MenuItem ajout;

    /**
     * Permet de passer à l'élément précédent s'il existe.
     */
    @FXML
    private ImageView precedent;

    /**
     * Permet de passer à l'élément suivant s'il existe.
     */
    @FXML
    private ImageView suivant;

    /**
     * Position du satellite observé (correspondant à sa position dans la ArrayList de la collection).
     */
    private int posSatellite;

    /**
     * ArrayList contenant les informations du satellite, utilisée pour la sauvegarde des éléments.
     */
    private final ArrayList<ControleurInformation> informations;

    /**
     * Attribut permettant de savoir si le satellite a été sauvegardé lors du changement de satellite.
     */
    private boolean estSauvegarde;

    /**
     * url de l'image pour faire la sauvegarde
     */
    private String urlImage;



    /**
     * Constructeur principal de la classe.
     *
     * @param posSatellite Position du satellite dans la collection.
     * @param collectionSatellites Collection manipulée par la classe.
     */
    public ControleurDetail(CollectionSatellites collectionSatellites, int posSatellite) {
        super(collectionSatellites);
        this.posSatellite = posSatellite;
        this.informations = new ArrayList<>(10);
        estSauvegarde = collectionSatellites.isEstConsulte();
        urlImage = collectionSatellites.getSatellite(posSatellite).getUrl();
    }



    /**
     * Initialise les éléments de l'affichage.
     */
    @FXML
    void initialize() {
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        date.setText(String.valueOf(satellite.getDateString()));

        appliquerImageFleche();
        appliquerImageSatellite();
        reagir();
    }



    /**
     * Applique l'image de la flèche pour passer au satellite suivant ou précédent.
     */
    private void appliquerImageFleche() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/developpeur/fleche.png")),
                90, 90, true, true);
        precedent.setImage(image);
        suivant.setImage(image);
        suivant.setRotate(180);
    }



    /**
     * Applique l'image du satellite sur l'affichage.
     */
    private void appliquerImageSatellite() {
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);

        Image im = new Image(Objects.requireNonNull(getClass().getResourceAsStream(satellite.getUrl())),
                700, 700, true, true);
        image.setImage(im);
    }



    /**
     * Passe au satellite suivant avec vérification de la validité du passage.
     * Le passage n'est valide que s'il y a un satellite disponible et si la fenêtre a été sauvegardée.
     */
    @FXML
    void suivant() {
        if (posSatellite != collectionSatellites.nbSatellites() - 1) {
            if (estSauvegarde) {
                posSatellite += 1;
                collectionSatellites.notifierObservateurs();
            } else {
                if(lancerAlerte("Les données ne sont pas sauvegardées.\nElles risquent d'être supprimées.")) {
                    sauvegarde();
                }
                estSauvegarde = true;
                suivant();
            }
        }
    }



    /**
     * Passe au satellite précédent avec vérification de la validité du passage.
     * Le passage n'est valide que s'il y a un satellite disponible et si la fenêtre a été sauvegardée.
     */
    @FXML
    void precedent() {
        if (posSatellite != 0) {
            if (estSauvegarde) {
                posSatellite -= 1;
                collectionSatellites.notifierObservateurs();
            } else {
                if(lancerAlerte("Les données ne sont pas sauvegardées.\nElles risquent d'être supprimées.")) {
                    sauvegarde();
                    precedent();
                }
                estSauvegarde = true;
                precedent();
            }
        }
    }



    /**
     * Applique les éléments sur la fenêtre en fonction du mode de lecture et met à jour tous les éléments.
     * Il y a aussi une mise à jour pour la dimension des éléments
     */
    @FXML
    private void appliquerInformation() {
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        vbox.getChildren().clear();

        titre.setText(satellite.getNom());
        ajout.setDisable(collectionSatellites.isEstConsulte());

        if (!collectionSatellites.isEstConsulte()) { // choix du type des informations
            appliquerInformationsEdition();
        } else {
            applicationInformationsConsultation();
        }

        ajouterTags();
        vbox.setPrefHeight(scrollPane.getPrefHeight());
    }



    /**
     * Applique les informations dans le mode Consultation.
     */
    private void applicationInformationsConsultation() {
        informations.clear();
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);

        for (Information information : satellite) { // un chargement par information
            ControleurInformationConsultation info = new ControleurInformationConsultation(information);

            // chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauInformationConsultation.fxml"));
            loader.setControllerFactory(ic -> info);
            try {
                vbox.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    /**
     * Applique les informations dans le mode Édition.
     */
    private void appliquerInformationsEdition() {
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);

        for (Information information : satellite) { // un chargement  par information
            ControleurInformation info = new ControleurInformation(this,satellite,information);
            informations.add(info);

            // chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
            loader.setControllerFactory(ic -> info);
            try {
                vbox.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    /**
     * Ajoute des informations au satellite.
     */
    @FXML
    private void ajouterInfo() {
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        satellite.ajoutInfo();
        int posInfo = satellite.nbInformations()-1;
        Information newInfo = satellite.getInformations(posInfo);

        ControleurInformation info = new ControleurInformation(this,satellite,newInfo);
        informations.add(info);

        // chargement du fichier FXML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vue/PanneauInformation.fxml"));
        loader.setControllerFactory(ic -> info);
        try {
            vbox.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * méthode qui permet de supprimer une information
     *
     * @param obj information supprimé
     */
    public void supprimerInfo(ControleurInformation obj) {
        int index = informations.indexOf(obj);
        informations.remove(obj);
        vbox.getChildren().remove(index);
    }



    /**
     * Sauvegarde les données du satellite. Ou passage en mode consultation
     */
    @FXML
    void sauvegarde() {
        if (!collectionSatellites.isEstConsulte())
            sauvegardeEdition();
        else
            sauvegardeConsultation();
    }



    /**
     * méthode qui fait la sauvegarde des données en mode édition
     */
    private void sauvegardeEdition(){
        if (titre.getText().length() != 0) { // le nom doit posséder au moins un caractère
            Satellite satellite = collectionSatellites.getSatellite(posSatellite);

            estSauvegarde = true;
            satellite.setNom(titre.getText());
            satellite.setUrl(urlImage);

            for (ControleurInformation info : informations) // sauvegarde de chaque information
                info.sauvegardeInformation();

            collectionSatellites.setEstConsulte(); // changement du mode
            collectionSatellites.notifierObservateurs();
        } else {
            if(lancerAlerte("Le nom n'a pas été saisi.")) { // si l'utilisateur veut quand même sauvegarder
                estSauvegarde = true;
                sauvegardeEdition();
            }
        }
    }



    /**
     * méthode qui fait la sauvegarde des données en mode consultation
     */
    private void sauvegardeConsultation(){
        estSauvegarde = false;
        collectionSatellites.setEstConsulte();
        collectionSatellites.notifierObservateurs();
    }



    /**
     * Change de vue vers le panneau global.
     */
    @FXML
    private void changerGlobal() {
        if(estSauvegarde) {
            changerVueSiSauvegarder();
        } else {
            if(lancerAlerte("la sauvegarde n'a pas été effectué.")) {
                sauvegarde();
                changerGlobal();
            }
            estSauvegarde = true;
            changerGlobal();
        }
    }


    /**
     * méthode qui permet de changer de vue si le fichier a été sauvegardé
     */
    private void changerVueSiSauvegarder(){
        collectionSatellites.clear();

        FXMLLoader loader = new FXMLLoader(); //chargement du Panneau FXML
        loader.setLocation(getClass().getResource("../vue/PanneauGlobal.fxml"));
        Compteur compteur = new Compteur();
        ControleurGlobal global = new ControleurGlobal(collectionSatellites, compteur);
        ControleurMenu menu = new ControleurMenu(collectionSatellites);
        loader.setControllerFactory(ic -> {
            if (ic.equals(ControleurMenu.class)) return menu;
            return global;
        });
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        transition(root);
    }



    /**
     * méthode qui permet de faire une transition lors du changement de vue
     *
     * @param root élément  principal de la nouvelle fenêtre
     */
    private void transition(Parent root){
        StackPane stage = (StackPane) image.getScene().getRoot();
        root.translateYProperty().set(-stage.getScene().getHeight());
        stage.getChildren().clear();
        stage.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
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

            if (selectedFile != null) { // mise à jour du path de l'image
                urlImage = "/image/utilisateur/"+selectedFile.getName();

                Image im = new Image(Objects.requireNonNull(getClass().getResourceAsStream(urlImage)),
                        700, 700, true, true);
                image.setImage(im);
            }
        }
    }



    /**
     * méthode privée qui permet d'afficher les tags du satellite
     */
    @FXML
    private void afficherTags(){
        new ControleurListeTags(collectionSatellites,posSatellite);
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
            label.setStyle("-fx-text-fill: white; -fx-font-size: 30px");
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



    /**
     * Affiche une alerte avec deux boutons.
     *
     * @param message le message à afficher dans l'alerte
     * @return true si le bouton "Sauvegarder" est cliqué, false si le bouton "Annuler" est cliqué
     */
    private boolean lancerAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Veuillez confirmer");
        alert.setContentText(message);

        // Création des boutons personnalisés
        ButtonType boutonSauvegarder = new ButtonType("Sauvegarder");
        ButtonType boutonAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Ajout des boutons à l'alerte
        alert.getButtonTypes().setAll(boutonSauvegarder, boutonAnnuler);

        // Affichage de l'alerte et attente de la réponse de l'utilisateur
        Optional<ButtonType> resultat = alert.showAndWait();

        // Bouton "Annuler" cliqué ou alerte fermée
        return resultat.isPresent() && resultat.get() == boutonSauvegarder; // Bouton "Sauvegarder" cliqué
    }
}
