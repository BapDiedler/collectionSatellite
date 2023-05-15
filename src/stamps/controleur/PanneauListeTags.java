package stamps.controleur;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
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

    public ListView<Label> listView;
    private final ArrayList<String> tags;
    public TextField nouveauTag;

    private final int posSatellite;

    private Stage stage;

    public PanneauListeTags(CollectionSatellites satellites, int posSatellite, Stage stage){
        super(satellites);
        this.tags = satellites.getMotsClefs("");
        this.posSatellite = posSatellite;
        this.stage = stage;
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
        if(nouveauTag.getText() != null){
            tags.add(nouveauTag.getText());
        }
        Label label = new Label(nouveauTag.getText());
        label.setPrefWidth(listView.getPrefWidth()-35);
        collectionSatellites.setMotsClefs(label.getText());
        collectionSatellites.getSatellite(posSatellite).setMotsClefs(label.getText());
        listView.getItems().add(label);
        nouveauTag.setText("");
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode qui permet de supprimer un tag d'un satellite ainsi que dans la vue globale si besoin
     */
    @FXML
    void supprimer(){
        for(Label label: listView.getSelectionModel().getSelectedItems()){
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
        Satellite satellite = collectionSatellites.getSatellite(posSatellite);
        listView.getItems().clear();
        for(String val: tags){
            Label label = new Label(val);
            if(satellite.containeTag(val)) label.setStyle("-fx-text-fill: red");
            else label.setStyle("-fx-text-fill: white;" +
                    "-fx-background-color: #323232;");
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(listView.getPrefWidth()-35);
            listView.getItems().add(label);
        }
    }
}
