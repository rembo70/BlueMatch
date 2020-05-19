package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editaanvraag {

    private Scene ParentScene;
    @FXML
    private TableColumn columnopm;
    @FXML
    private TableColumn columnfunctie;
    @FXML
    private TableColumn columnlocatie;
    @FXML
    private TableColumn columnlink;
    @FXML
    private TableColumn columnstatus;
    @FXML
    private TableColumn columncontact;

    private Controller parentController;

    public void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    public void changeSceneMain(ActionEvent event) throws IOException {
        //       Parent detailViewParent = FXMLLoader.load(getClass().getResource("BlueMatch.fxml"));
        //      Scene detailViewScene = new Scene(detailViewParent);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        parentController.updateMainView();
    }


    @FXML
    public void addAanvraag(ActionEvent event) throws IOException, SQLException {
        System.out.println("add aanvraag");
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addaanvraag.fxml"));
        dialog.getDialogPane().setContent(loader.load());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        {
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AddAanvraagController addaanvraagController = loader.getController();
                Aanvraag aanvraag = addaanvraagController.getNewAanvraag();
                Datasource.getInstance().aanvraagToevoegen(aanvraag);
            }
        }
        updateView();
    }

    @FXML
    private TableView<Aanvraag> aanvraagTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        // resize table on mouse clicked
        updateView();
    }

    public void updateView() {
        double Kolumnwidthopm = (columnopm.widthProperty().getValue()) / 5.4; //4.9 is amount of pixels/4.9= amount of characters
        double Kolumnwidthfunctie = (columnfunctie.widthProperty().getValue()) / 4.9;
        double Kolumnwidthcontact = (columncontact.widthProperty().getValue()) / 4.9;
        double Kolumnwidthstatus = (columnstatus.widthProperty().getValue()) / 4.9;
        double Kolumnwidthlocatie = (columnlocatie.widthProperty().getValue()) / 4.9;
        double Kolumnwidthlink = (columnlink.widthProperty().getValue()) / 4.9;

        ObservableList<Aanvraag> aanvraaglist = FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());

        for (Aanvraag huidigeaanvraag : aanvraaglist) {
            if (!(huidigeaanvraag.getOpmerking() == null)) {
                huidigeaanvraag.setOpmerking(lineWrap(huidigeaanvraag.getOpmerking(), (int) Kolumnwidthopm));
            }
            if (!(huidigeaanvraag.getFunctie() == null)) {
                huidigeaanvraag.setFunctie(lineWrap(huidigeaanvraag.getFunctie(), (int) Kolumnwidthfunctie));
            }
            if (!(huidigeaanvraag.getRefcontact() == null)) {
                huidigeaanvraag.setRefcontact(lineWrap(huidigeaanvraag.getRefcontact(), (int) Kolumnwidthcontact));
            }
            if (!(huidigeaanvraag.getStatusklant() == null)) {
                huidigeaanvraag.setStatusklant(lineWrap(huidigeaanvraag.getStatusklant(), (int) Kolumnwidthstatus));
            }
            if (!(huidigeaanvraag.getLocatie() == null)) {
                huidigeaanvraag.setLocatie(lineWrap(huidigeaanvraag.getLocatie(), (int) Kolumnwidthlocatie));
            }
            if (!(huidigeaanvraag.getLinkaanvraag() == null)) {
                huidigeaanvraag.setLinkaanvraag(lineWrap(huidigeaanvraag.getLinkaanvraag(), (int) Kolumnwidthlink));
            }
            aanvraagTable.itemsProperty().unbind();
            aanvraagTable.setItems(aanvraaglist);
        }
    }


    public String lineWrap(String text, int limit) {
        int offset = 0;
        Pattern pattern = Pattern.compile(" ");
        StringBuilder result = new StringBuilder(text.length());
        while (offset < text.length()) {
            int wrapPosition = -1;
            Matcher matcher = pattern.matcher(text.substring(offset, Math.min((int) Math.min(Integer.MAX_VALUE, offset + limit + 1L), text.length())));
            if (matcher.find()) {
                // Skip unnecessary space at the beginning of each line
                if (matcher.start() == 0) {
                    offset += matcher.end();
                    continue;
                }
                wrapPosition = matcher.start() + offset;
            }
            // Stop wrapping if the remaining words are within boundary
            if (text.length() - offset <= limit) {
                break;
            }
            // Find the position to insert line break
            while (matcher.find()) {
                wrapPosition = matcher.start() + offset;
            }
            if (wrapPosition >= offset) {
                // Normal case
                result.append(text, offset, wrapPosition).append(System.lineSeparator());
                offset = wrapPosition + 1;
            } else {
                // Word too long
                result.append(text, offset, offset + limit).append(System.lineSeparator());
                offset += limit;
            }
        }
        // Append the remaining words
        result.append(text, offset, text.length());
        return result.toString();
    }


    public void listAanvragen() {
        Task<ObservableList<Aanvraag>> task = new GetAllAanvragenTask();
        aanvraagTable.itemsProperty().bind(task.valueProperty());
        System.out.println("View list on screen 2");
        new Thread(task).start();
    }

    class GetAllAanvragenTask extends Task {

        @Override
        public ObservableList<Aanvraag> call() {

            ObservableList<Aanvraag> aanvraaglist = FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());
            System.out.println(aanvraaglist.size());
            return aanvraaglist;

        }
    }

}

