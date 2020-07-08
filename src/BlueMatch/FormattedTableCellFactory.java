package BlueMatch;


import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;


public class FormattedTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public FormattedTableCellFactory() {
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            protected void updateItem(Object item, boolean empty) {

                // CSS Styles
                // System.out.println("in tablecellfactory");
                String formatBiggerSize = "biggerSize";
                String formatevenBiggerSize = "evenbiggerSize";
                String defaultTableStyle = "table-cell";
                String cssStyle = "table-cell";

                //Remove all previously assigned CSS styles from the cell.
                getStyleClass().remove(formatevenBiggerSize);
                getStyleClass().remove(formatBiggerSize);
                getStyleClass().remove(defaultTableStyle);

                super.updateItem((T) item, empty);

                if (Main.windowWidth > 2500) {
                    cssStyle = formatevenBiggerSize;
                } else {
                    if (Main.windowWidth > 1600) {
                        cssStyle = formatBiggerSize;
                    } else {
                        cssStyle = defaultTableStyle;
                    }
                }

                getStyleClass().add(cssStyle);
                if (item != null) {
                    //    System.out.println(cssStyle);
                    setText(item.toString());
                } else {
                    setText("");
                }
            }
        };
        return cell;
    }
}
