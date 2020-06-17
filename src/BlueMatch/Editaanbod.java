package BlueMatch;

import BlueMatch.model.Aanbod;
import BlueMatch.model.Datasource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Editaanbod {

    private Scene ParentScene;
    @FXML
    private TableColumn columnmedewerker;
    @FXML
    private TableColumn columntariefaanbod;
    @FXML
    private TableColumn columnaanbodstatus;
    @FXML
    private Button btnmodaanbod;


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
        //parentController.refreshscreen();
//        parentController.updateMainView();
        Main.windowWidth = (int) window.getWidth();


    }
    
    @FXML
    public void modAanbod(ActionEvent event) throws IOException, SQLException {
       //System.out.println("bijwerken aanbod");
        Aanbod aanbod = (Aanbod) aanbodTable.getSelectionModel().getSelectedItem();

        if (aanbod != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addaanbieding.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            addAanbiedingController addaanbodcontroller = loader.getController();
            addaanbodcontroller.editAanbod(aanbod,"update");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {

                        addaanbodcontroller.updateAanbod(aanbod);
                        Datasource.getInstance().updateAanbod(aanbod);

                }
            }
            ObservableList<Aanbod> Aanbodlist = FXCollections.observableArrayList(Datasource.getInstance().queryAanbod());
            aanbodTable.itemsProperty().unbind();
            aanbodTable.setItems(Aanbodlist);
            btnmodaanbod.setDisable(true);
        }
        
        
    }

    @FXML
    private TableView<Aanbod> aanbodTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.windowWidth = (int) window.getWidth();
        updateView();
    }

    public void updateView() {
        if (aanbodTable.getSelectionModel().getSelectedItem() == null) {
            System.out.println("brokertable not selected");
            btnmodaanbod.setDisable(true);
        } else {
            System.out.println("brokertable selected");
            btnmodaanbod.setDisable(false);
        }

        changelistener(columnmedewerker);
        changelistener(columntariefaanbod);
        changelistener(columnaanbodstatus);

        //changelistener(columnopmerkingbroker);
    }


    public void changelistener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
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
        }}
        });
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

