package stamps.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stamps.model.CollectionSatellites;

import java.io.IOException;
import java.util.ArrayList;

public class ControleurRechercheTags extends Controleur {

    @FXML
    private ListView<Label> listView;

    private final ArrayList<String> tags;

    public ControleurRechercheTags(CollectionSatellites collectionSatellites){
        super(collectionSatellites);
        tags = collectionSatellites.getMotsClefs("");
        afficherFenetre();
    }

    /***
     * méthode qui permet d'afficher la fenêtre contenant les tags des satellites
     */
    private void afficherFenetre(){
        Stage nouvelleFenetre = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/PanneauRechercheTags.fxml"));
        loader.setControllerFactory(ic -> this);
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

    public void initialize(){
        reagir();
    }

    @FXML
    private void handleKeywordSelection() {
        Label selectedLabel = listView.getSelectionModel().getSelectedItem();
        if(selectedLabel != null){
            collectionSatellites.getSatellites(selectedLabel.getText());
        }
        collectionSatellites.notifierObservateurs();
    }

    /**
     * méthode réagir qui sera activée à chaque action
     */
    @Override
    public void reagir() {
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
}

