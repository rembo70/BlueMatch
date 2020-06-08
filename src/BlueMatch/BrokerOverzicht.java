package BlueMatch;

import BlueMatch.model.Broker;
import BlueMatch.model.Datasource;
import BlueMatch.model.Medewerker;
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

public class BrokerOverzicht {

    private Scene ParentScene;
    @FXML
    private TableColumn columnbrokernaam;
    @FXML
    private TableColumn columncontactpersoon;
    @FXML
    private TableColumn columntelbroker;
    @FXML
    private TableColumn columnemailbroker;
    @FXML
    private TableColumn columnopmerkingbroker;


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
    public void addBroker(ActionEvent event) throws IOException, SQLException {

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addBroker.fxml"));
        dialog.getDialogPane().setContent(loader.load());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        {
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AddBrokerController addBrokerController = loader.getController();
                Broker broker = addBrokerController.getNewBroker();
                Datasource.getInstance().brokerToevoegen(broker);
            }
        }
        //updateMainView();
        ObservableList<Broker> Brokerlist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
        brokerTable.itemsProperty().unbind();
        brokerTable.setItems(Brokerlist);


    }

    @FXML
    private TableView<Broker> brokerTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.windowWidth = (int) window.getWidth();
        // updateView();
    }

    public void updateView() {
        double Kolumnwidthbrokernaam = (columnbrokernaam.widthProperty().getValue()) / 4.9;
        double Kolumnwidthcontactpersoon = (columncontactpersoon.widthProperty().getValue()) / 4.9;
        double Kolumnwidthtelbroker = (columntelbroker.widthProperty().getValue()) / 4.9;
        double Kolumnwidthemailbroker = (columnemailbroker.widthProperty().getValue()) / 4.9;
        double Kolumnwidthopmerkingbroker = (columnemailbroker.widthProperty().getValue()) / 4.9;

        ObservableList<Broker> brokerslist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());

        for (Broker huidigebroker : brokerslist) {
            if (!(huidigebroker.getBrokernaam() == null))
                huidigebroker.setBrokernaam(Editaanvraag.lineWrap(huidigebroker.getBrokernaam(), (int) Kolumnwidthbrokernaam));
            if (!(huidigebroker.getContactpersoon() == null)) {
                huidigebroker.setContactpersoon(Editaanvraag.lineWrap(huidigebroker.getContactpersoon(), (int) Kolumnwidthcontactpersoon));
            }
            if (!(huidigebroker.getTelbroker() == null)) {
                huidigebroker.setTelbroker(Editaanvraag.lineWrap(huidigebroker.getTelbroker(), (int) Kolumnwidthtelbroker));
            }
            if (!(huidigebroker.getEmailbroker() == null)) {
                huidigebroker.setEmailbroker(Editaanvraag.lineWrap(huidigebroker.getEmailbroker(), (int) Kolumnwidthemailbroker));
            }
            if (!(huidigebroker.getOpmerkingbroker() == null)) {
                huidigebroker.setOpmerkingbroker(Editaanvraag.lineWrap(huidigebroker.getOpmerkingbroker(), (int) Kolumnwidthopmerkingbroker));
            }

            brokerTable.itemsProperty().unbind();
            brokerTable.setItems(brokerslist);
        }
    }


        public void listBrokers() {
            Task<ObservableList<Broker>> task = new GetAllBrokersTask();
            brokerTable.itemsProperty().bind(task.valueProperty());
            new Thread(task).start();
        }
    }

    class GetAllBrokersTask extends Task {

        @Override
        public ObservableList<Broker> call() {

            ObservableList<Broker> BrokersList = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
            return BrokersList;

        }
    }


