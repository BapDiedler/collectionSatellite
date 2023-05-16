package stamps.controleur;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;
import stamps.model.Satellite;

import java.util.ArrayList;

/**
 * classe permettant de gérer l'affichage des tags (mots clefs) des sattelites
 *
 * @author baptistedie
 */
public class PanneauListeTags extends Controleur {

    /**
     * liste de labels contenant tous les tags
     */
    public ListView<Label> listView;

    /**
     * liste de string contenant les tags
     */
    private final ArrayList<String> tags;

    /**
     * textField permettant de rentrer une nouvelle donnée
     */
    public TextField nouveauTag;

    /**
     * position du satellite que l'on manipule
     *          (satellites.nbSatellites()+1 si on est dans la vue globale)
     */
    private final int posSatellite;

    /**
     * élément principal pour afficher la fenêtre
     */
    private final Stage stage;
    public Button ajout;
    public Button supprime;

    /**
     * constructeur principal
     *
     * @param satellites collection de satellite à manipuler
     * @param posSatellite position du satellite que l'on manipule
     *                     (satellites.nbSatellites()+1 si on est dans la vue globale)
     * @param stage élément principal pour afficher la fenêtre
     */
    public PanneauListeTags(CollectionSatellites satellites, int posSatellite, Stage stage){
        super(satellites);
        this.tags = satellites.getMotsClefs("");
        this.posSatellite = posSatellite;
        this.stage = stage;
        this.stage.initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * constructeur utilisé dans la vue globale
     *
     * @param satellites collection de satellite que l'on observe
     * @param stage élément principal pour afficher la fenêtre
     */
    public PanneauListeTags(CollectionSatellites satellites, Stage stage){
        this(satellites, satellites.nbSatellites()+1, stage);
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
        Label label = new Label(nouveauTag.getText());
        label.setPrefWidth(listView.getPrefWidth()-35);
        collectionSatellites.setMotsClefs(label.getText());
        if(posSatellite != collectionSatellites.nbSatellites()+1)
            collectionSatellites.getSatellite(posSatellite).setMotsClefs(label.getText());
        listView.getItems().add(label);
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
            if(posSatellite != collectionSatellites.nbSatellites()+1)
                collectionSatellites.getSatellite(posSatellite).removeTag(label.getText());
            collectionSatellites.removeTags(label.getText());
        }
        collectionSatellites.notifierObservateurs();
        stage.close();
    }

    /**
     * méthode qui permet d'ajouter les tags d'un satellite manipulé
     */
    @FXML
    void ajouter(){
        for(Label label: listView.getSelectionModel().getSelectedItems()){
            collectionSatellites.setMotsClefs(label.getText());
            if(posSatellite != collectionSatellites.nbSatellites()+1)
                collectionSatellites.getSatellite(posSatellite).setMotsClefs(label.getText());
        }
        collectionSatellites.notifierObservateurs();
        stage.close();
    }


    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
        if(posSatellite != collectionSatellites.nbSatellites()+1) {
            Satellite satellite = collectionSatellites.getSatellite(posSatellite);
            listView.getItems().clear();
            for (String val : tags) {
                Label label = new Label(val);
                if (satellite.containeTag(val)) label.setStyle("-fx-text-fill: red");
                else label.setStyle("-fx-text-fill: white;" +
                        "-fx-background-color: #323232;");
                label.setAlignment(Pos.CENTER);
                label.setPrefWidth(listView.getPrefWidth() - 35);
                listView.getItems().add(label);
            }
        }else{
            listView.getItems().clear();
            for (String val : tags) {
                Label label = new Label(val);
                label.setStyle("-fx-text-fill: white;" +
                        "-fx-background-color: #323232;");
                label.setAlignment(Pos.CENTER);
                label.setPrefWidth(listView.getPrefWidth() - 35);
                listView.getItems().add(label);
            }
        }
    }
}
