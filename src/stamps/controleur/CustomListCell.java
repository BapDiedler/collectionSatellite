package stamps.controleur;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

// Classe d'implémentation de cellule personnalisée
public class CustomListCell extends ListCell<HBox> {
    @Override
    protected void updateItem(HBox item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic(item);
        }
    }
}
