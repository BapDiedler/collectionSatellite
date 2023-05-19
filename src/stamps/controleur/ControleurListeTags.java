package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.io.IOException;
import java.util.ArrayList;

/**
 * classe permettant de gérer l'affichage des tags (mots clefs) des satellites
 *
 * @author baptistedie
 */
public class ControleurListeTags extends Controleur {

    /**
     * liste de labels contenant tous les tags
     */
    @FXML
    private ListView<Label> listView;

    /**
     * liste de string contenant les tags
     */
    private final ArrayList<String> tags;

    /**
     * textField permettant de rentrer une nouvelle donnée
     */
    @FXML
    private TextField nouveauTag;

    /**
     * position du satellite que l'on manipule
     *          (satellites.nbSatellites()+1 si on est dans la vue globale)
     */
    private final int posSatellite;


    /**
     * constructeur principal
     *
     * @param satellites collection de satellite à manipuler
     * @param posSatellite position du satellite que l'on manipule
     *                     (satellites.nbSatellites()+1 si on est dans la vue globale)
     */
    public ControleurListeTags(CollectionSatellites satellites, int posSatellite){
        super(satellites);
        this.tags = satellites.getMotsClefs("");
        this.posSatellite = posSatellite;

        afficherFenetre();
    }

    /***
     * méthode qui permet d'afficher la fenêtre contenant les tags des satellites
     */
    private void afficherFenetre(){
        Stage nouvelleFenetre = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/PanneauListeTags.fxml"));
        loader.setControllerFactory(ic -> tags);
        Scene root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        nouvelleFenetre.initModality(Modality.APPLICATION_MODAL);

        // Créer une nouvelle fenêtre
        nouvelleFenetre.setTitle("Tags");
        nouvelleFenetre.setScene(root);

        // Afficher la nouvelle fenêtre
        nouvelleFenetre.show();
    }

    /**
     * constructeur utilisé dans la vue globale
     *
     * @param satellites collection de satellite que l'on observe
     */
    public ControleurListeTags(CollectionSatellites satellites){
        this(satellites, satellites.nbSatellites()+1);
    }

    /**
     * initialisation des éléments sur l'affichage
     */
    @FXML
    void initialize(){
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        reagir();
    }

    /**
     * méthode qui permet d'ajouter un tag au satellite manipulé
     */
    @FXML
    void ajoutTag(){
        if(nouveauTag.getText().length() != 0){
            tags.add(nouveauTag.getText());
        }else{
            lancerAlerte("aucun tag ne vient d'être ajouté");
        }
        if(collectionSatellites.nbSatellites()+1 != posSatellite) {
            collectionSatellites.setMotsClefs(1, nouveauTag.getText());
            collectionSatellites.getSatellite(posSatellite).setMotsClefs(nouveauTag.getText());
        }else{
            collectionSatellites.setMotsClefs(-1,nouveauTag.getText());
        }
        nouveauTag.setText("");
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
     * méthode qui permet de supprimer un tag d'un satellite ainsi que dans la vue globale si besoin
     */
    @FXML
    void supprimer(){
        for(Label label: listView.getSelectionModel().getSelectedItems()){
            collectionSatellites.removeTags(label.getText());
            if(collectionSatellites.nbSatellites()+1 != posSatellite)
                collectionSatellites.getSatellite(posSatellite).removeTag(label.getText());
            tags.remove(label.getText());
        }
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui permet d'ajouter les tags d'un satellite manipulé
     */
    @FXML
    void ajouter(){
        for(Label label: listView.getSelectionModel().getSelectedItems()){
            if(posSatellite != collectionSatellites.nbSatellites()+1)
                collectionSatellites.getSatellite(posSatellite).setMotsClefs(label.getText());
        }
        if(nouveauTag.getText().length() != 0) {
            tags.add(nouveauTag.getText());

            if(posSatellite != collectionSatellites.nbSatellites()+1) {
                collectionSatellites.getSatellite(posSatellite).setMotsClefs(nouveauTag.getText());
                collectionSatellites.setMotsClefs(1, nouveauTag.getText());
            }else{
                collectionSatellites.setMotsClefs(-1, nouveauTag.getText());
            }
            nouveauTag.setText("");
        }
        collectionSatellites.notifierObservateurs();
    }


    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        if(posSatellite != collectionSatellites.nbSatellites()+1) {
            reagirDetail();
        }else{
            reagirGlobal();
        }
    }

    /**
     * méthode réagir pour la vue globale
     */
    private void reagirGlobal(){
        listView.getItems().clear();
        for (String val : tags) {
            Label label = new Label(val);
            label.setStyle("-fx-text-fill: white;" +
                    "-fx-font-size: 30px");
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(listView.getPrefWidth() - 35);
            listView.getItems().add(label);
        }
    }


    /**
     * méthode réagir pour la vue détaille
     */
    private void reagirDetail(){
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        listView.getItems().clear();
        for (String val : tags) {
            Label label = new Label(val);
            if (satellite.containeTag(val)) label.setStyle("-fx-text-fill: #5d9cab; -fx-font-size: 30px");
            else label.setStyle("-fx-text-fill: white; -fx-font-size: 30px");
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(listView.getPrefWidth() - 35);
            listView.getItems().add(label);
        }
    }
}
