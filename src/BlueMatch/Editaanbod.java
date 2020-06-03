package BlueMatch;

import BlueMatch.model.Aanbod;
import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editaanbod {

    private Scene ParentScene;
    @FXML
    private TableColumn columnmedewerker;
    @FXML
    private TableColumn columntariefaanbod;
    @FXML
    private TableColumn columnaanbodstatus;


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
    public void modAanbod(ActionEvent event) throws IOException, SQLException {
        System.out.println("bijwerken aanbod");
//        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("addaanvraag.fxml"));
//        dialog.getDialogPane().setContent(loader.load());
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//        Optional<ButtonType> result = dialog.showAndWait();
//        {
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                AddaanvraagController addaanvraagController = loader.getController();
//                Aanvraag aanvraag = addaanvraagController.getNewAanvraag();
//                Datasource.getInstance().aanvraagToevoegen(aanvraag);
//            }
//        }
//        updateView();
    }

    @FXML
    private TableView<Aanbod> aanbodTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.windowWidth = (int) window.getWidth();
        updateView();
    }

    public void updateView() {
        double Kolumnwidthmedewerker = (columnmedewerker.widthProperty().getValue()) / 4.9;
        double Kolumnwidthtarief = (columntariefaanbod.widthProperty().getValue()) / 4.9;
        double Kolumnwidthstatus = (columnaanbodstatus.widthProperty().getValue()) / 4.9;
        //double Kolumnwidthlocatie = (columnlocatie.widthProperty().getValue()) / 4.9;


        ObservableList<Aanbod> aanbodlist = FXCollections.observableArrayList(Datasource.getInstance().queryAanbod());

        for (Aanbod huidigeaanbod : aanbodlist) {
            if (!(huidigeaanbod.getTariefaanbod() == null))
                huidigeaanbod.setTariefaanbod(Editaanvraag.lineWrap(huidigeaanbod.getTariefaanbod(), (int) Kolumnwidthtarief));
            if (!(huidigeaanbod.getRefmedewerker() == null)) {
                huidigeaanbod.setRefmedewerker(Editaanvraag.lineWrap(huidigeaanbod.getRefmedewerker(), (int) Kolumnwidthmedewerker));
            }
            if (!(huidigeaanbod.getStatusaanbod() == null)) {
                huidigeaanbod.setStatusaanbod(Editaanvraag.lineWrap(huidigeaanbod.getStatusaanbod(), (int) Kolumnwidthstatus));
            }

            aanbodTable.itemsProperty().unbind();
            aanbodTable.setItems(aanbodlist);
        }
    }


    public void listAanbiedingen() {
        Task<ObservableList<Aanbod>> task = new GetAllAanbiedingenTask();
        aanbodTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    class GetAllAanbiedingenTask extends Task {

        @Override
        public ObservableList<Aanbod> call() {

            ObservableList<Aanbod> aanbodlist = FXCollections.observableArrayList(Datasource.getInstance().queryAanbod());
            return aanbodlist;

        }
    }
}

