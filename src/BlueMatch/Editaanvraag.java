package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import BlueMatch.model.Klant;
import javafx.beans.value.ChangeListener;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editaanvraag {

    private Scene ParentScene;
    @FXML
    private TableColumn columnidaanvraag;
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
        Controller parentcontrol = controller;
    }

    public void changeSceneMain(ActionEvent event) throws IOException {
               Parent detailViewParent = FXMLLoader.load(getClass().getResource("BlueMatch.fxml"));
              Scene detailViewScene = new Scene(detailViewParent);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        parentController.updateMainView();
        parentController.refreshscreen();
    }


    @FXML
    public void addAanvraag(ActionEvent event) throws IOException, SQLException {
//        System.out.println("add aanvraag");
        Controller.typeofaddaanvraag="new";
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
        Controller.typeofaddaanvraag="update";
        refreshscreen();
        updateView();
    }

    @FXML
    private Button btnmodaanvraag;
    @FXML
    private Button btndelaanvraag;

    @FXML
    public void modAanvraag(ActionEvent event) throws IOException, SQLException {
        Aanvraag aanvraag = (Aanvraag) aanvraagTable.getSelectionModel().getSelectedItem();

        if (aanvraag != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addaanvraag.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            AddAanvraagController addaanvraagcontroller = loader.getController();
            addaanvraagcontroller.editAanvraag(aanvraag,"update");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

  //          System.out.println(aanvraag.getIdaanvraag());
            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    addaanvraagcontroller.updateAanvraag(aanvraag);
                    Datasource.getInstance().updateAanvraag(aanvraag);
                }
            }
        }
        refreshscreen();
        btnmodaanvraag.setDisable(true);
    }

    @FXML
    public void deleteAanvraag(ActionEvent event) throws IOException, SQLException {
        Aanvraag aanvraag = (Aanvraag) aanvraagTable.getSelectionModel().getSelectedItem();
        System.out.println(aanvraag.getIdaanvraag());

        if (aanvraag != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addAanvraag.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            AddAanvraagController addaanvraagcontroller = loader.getController();
            addaanvraagcontroller.editAanvraag(aanvraag,"delete");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (aanvraag.getIdaanvraag()==0) {
                        System.out.println("geen aanvraag met id bekend");
                    } else {
                        //addaanvraagcontroller.deleteAanvraag(aanvraag);
                        Datasource.getInstance().deleteAanvraag(aanvraag);
                    }
                }
            }
            ObservableList<Aanvraag> Aanvraaglist = FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());
            aanvraagTable.itemsProperty().unbind();
            aanvraagTable.setItems(Aanvraaglist);

        } else {
            System.out.println("geen aanvraag selectie");
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
        if (aanvraagTable.getSelectionModel().getSelectedItem() == null) {
            btnmodaanvraag.setDisable(true);
            btndelaanvraag.setDisable(true);
        } else {
            btnmodaanvraag.setDisable(false);
            btndelaanvraag.setDisable(false);
        }

        changelistener(columnopm);
        changelistener(columnfunctie);
        changelistener(columncontact);
        changelistener(columnstatus);
        changelistener(columnlocatie);
        changelistener(columnlink);
    }

    public void changelistener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                double Kolumnwidthopm = (columnopm.widthProperty().getValue()) / 7; //4.9 is amount of pixels/4.9= amount of characters
                double Kolumnwidthfunctie = (columnfunctie.widthProperty().getValue()) / 7;
                double Kolumnwidthcontact = (columncontact.widthProperty().getValue()) / 7;
                // double Kolumnwidthstatus = (columnstatus.widthProperty().getValue()) / 7;
                double Kolumnwidthlocatie = (columnlocatie.widthProperty().getValue()) / 7;
                double Kolumnwidthlink = (columnlink.widthProperty().getValue()) / 7;

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
//                    if (!(huidigeaanvraag.getStatusklant() == null)) {
//                        huidigeaanvraag.setStatusklant(lineWrap(huidigeaanvraag.getStatusklant(), (int) Kolumnwidthstatus));
//                    }
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

        });

    }

    public void refreshscreen() {
        ObservableList<Aanvraag> Aanvraaglist = FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());
        aanvraagTable.itemsProperty().
        unbind();
        aanvraagTable.setItems(Aanvraaglist);
}

    public static String lineWrap(String text, int limit) {
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
        // System.out.println("View list on screen 2");
        new Thread(task).start();
    }
}


class GetAllAanvragenTask extends Task {

    @Override
    public ObservableList<Aanvraag> call() {

        ObservableList<Aanvraag> aanvraaglist = FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());
        //System.out.println(aanvraaglist.size());
        return aanvraaglist;

    }
}
