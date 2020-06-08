package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import BlueMatch.model.Medewerker;
import BlueMatch.model.OverviewRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MedewerkerOverzicht {

    private Scene ParentScene;
    @FXML
    private TableColumn columnvoornaam;
    @FXML
    private TableColumn columnachternaam;
    @FXML
    private TableColumn columnurenperweek;
    @FXML
    private TableColumn columnstatusmdw;


    private Controller parentController;

    public void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    public void changeSceneMain(ActionEvent event) throws IOException {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        parentController.updateMainView();
        Main.windowWidth = (int) window.getWidth();

    }


    @FXML
    public void addMedewerker(ActionEvent event) throws IOException, SQLException {
        //System.out.println("add medewerker");

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addMedewerker.fxml"));
        dialog.getDialogPane().setContent(loader.load());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        {
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AddMedewerkerController addMedewerkerController = loader.getController();
                Medewerker medewerker = addMedewerkerController.getNewMedewerker();
                Datasource.getInstance().medewerkerToevoegen(medewerker);
            }
        }
        //updateMainView();
        ObservableList<Medewerker> Medewerkerlist = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());
        medewerkerTable.itemsProperty().unbind();
        medewerkerTable.setItems(Medewerkerlist);
        updateView();


    }

    @FXML
    private TableView<Medewerker> medewerkerTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.windowWidth = (int) window.getWidth();
        updateView();
    }

    public void updateView() {
        double Kolumnwidthvoornaam = (columnvoornaam.widthProperty().getValue()) / 4.9;
        double Kolumnwidthachternaam = (columnachternaam.widthProperty().getValue()) / 4.9;
        double Kolumnwidthstatusmdw = (columnstatusmdw.widthProperty().getValue()) / 4.9;
        double Kolumnwidthurenperweek = (columnurenperweek.widthProperty().getValue()) / 4.9;


        ObservableList<Medewerker> medewerkerslist = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());

        for (Medewerker huidigemdw : medewerkerslist) {
            if (!(huidigemdw.getVoornaam() == null))
                huidigemdw.setVoornaam(Editaanvraag.lineWrap(huidigemdw.getVoornaam(), (int) Kolumnwidthvoornaam));
            if (!(huidigemdw.getAchternaam() == null)) {
                huidigemdw.setAchternaam(Editaanvraag.lineWrap(huidigemdw.getAchternaam(), (int) Kolumnwidthachternaam));
            }
            if (!(huidigemdw.getUrenperweek() == null)) {
                huidigemdw.setUren(Editaanvraag.lineWrap(huidigemdw.getUrenperweek(), (int) Kolumnwidthurenperweek));
            }
            if (!(huidigemdw.getStatusmdw() == null)) {
                huidigemdw.setStatusmdw(Editaanvraag.lineWrap(huidigemdw.getStatusmdw(), (int) Kolumnwidthstatusmdw));
            }

            medewerkerTable.itemsProperty().unbind();
            medewerkerTable.setItems(medewerkerslist);
        }
    }


    public void listMedewerkers() {
        Task<ObservableList<Medewerker>> task = new GetAllMedewerkersTask();
        medewerkerTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    class GetAllMedewerkersTask extends Task {

        @Override
        public ObservableList<Medewerker> call() {

            ObservableList<Medewerker> medewerkersList = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());
            return medewerkersList;

        }
    }
}

