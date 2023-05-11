package stamps.controleur;

import javafx.animation.ScaleTransition;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PanneauSatellite extends Controleur {
    public ImageView image;
    public Label nom;
    public HBox hbox;
    private final int posSatellite;
    public ContextMenu menuContext;

    /**
     * constructeur de la classe
     *
     * @param satellites satellites manipulés
     */
    public PanneauSatellite(CollectionSatellites satellites, int pos){
        super(satellites);
        posSatellite = pos;
    }

    @FXML
    void initialize(){
        String url = collectionSatellites.getSatellite(posSatellite).getUrl();
        Image imageUrl = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)),
                50, 50, true, true) ;
        image.setImage(imageUrl);
        nom.setText(collectionSatellites.getSatellite(posSatellite).getNom());
    }

    /**
     * méthode qui permet d'aller sur la vue détaillée d'un satellite
     *
     * @throws IOException exception liée au chargement de fichier
     */
    @FXML
    void changerDetail(MouseEvent event) throws IOException {
        if(event.getButton() == MouseButton.SECONDARY) {
            menuContext.show(nom, event.getScreenX(), event.getScreenY());
        }else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../vue/PanneauDetail.fxml"));
            PanneauDetail detail = new PanneauDetail(collectionSatellites);
            loader.setControllerFactory(ic ->
                    detail
            );
            Scene root = loader.load();
            // Récupérer la référence de la stage actuelle
            Stage stage = (Stage) image.getScene().getWindow();
            // Changer la scène de la stage actuelle
            stage.setScene(root);
            stage.show();
        }
    }

    /**
     * méthode qui permet de supprimer définitivement un satellite
     */
    @FXML
    void supprimer(){
        String filePath = "src/ressource/sauvegarde/sat"+collectionSatellites.getSatellite(posSatellite).getIdentifiant()+".json";
        // Création de l'objet File
        File file = new File(filePath);

        // Affichage de la boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le fichier ?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer le fichier ?");

        // Attente de la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Suppression du fichier
                boolean deleted = file.delete();
                if (deleted) {
                    showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Le fichier a été supprimé avec succès.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "La suppression du fichier a échoué.");
                }
            }
        });
        collectionSatellites.supprimer(collectionSatellites.getSatellite(posSatellite));
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui affiche un message d'alerte
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getNom() {
        return collectionSatellites.getSatellite(posSatellite).getNom();
    }

    @FXML
    void appliquerAnimation(){
        hbox.setStyle("-fx-background-color: #112155");
        nom.setStyle("-fx-background-color: #112155");
    }

    @FXML
    void supprimerAnimation(){
        hbox.setStyle("-fx-background-color: #323232");
        nom.setStyle("-fx-background-color: #323232");
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {

    }
}
