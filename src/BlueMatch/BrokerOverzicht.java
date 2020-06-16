package BlueMatch;

import BlueMatch.model.Broker;
import BlueMatch.model.Datasource;
import BlueMatch.model.Medewerker;
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
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
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

        ObservableList<Broker> Brokerlist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
        brokerTable.itemsProperty().unbind();
        brokerTable.setItems(Brokerlist);
    }
    @FXML
    public void deleteBroker(ActionEvent event) throws IOException, SQLException {
        Broker broker = (Broker) brokerTable.getSelectionModel().getSelectedItem();


        if (broker != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addBroker.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            AddBrokerController addbrokercontroller = loader.getController();
            addbrokercontroller.editBroker(broker,"delete");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (broker.getBrokernaam().isEmpty()) {
                        System.out.println("geen brokernaam ingevuld");
                    } else {
                        //addbrokercontroller.deleteBroker(broker);
                        Datasource.getInstance().deleteBroker(broker);
                    }
                }
            }
            ObservableList<Broker> Brokerlist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
            brokerTable.itemsProperty().unbind();
            brokerTable.setItems(Brokerlist);

        } else {
            System.out.println("geen broker selectie");
        }
    }

    @FXML
    public void updateBroker(ActionEvent event) throws IOException, SQLException {
        Broker broker = (Broker) brokerTable.getSelectionModel().getSelectedItem();

        if (broker != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addBroker.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            AddBrokerController addbrokercontroller = loader.getController();
            addbrokercontroller.editBroker(broker,"update");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            // System.out.println(broker.getIdbroker());
            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (broker.getBrokernaam().isEmpty()) {
                        System.out.println("geen brokernaam ingevuld");
                    } else {
                        addbrokercontroller.updateBroker(broker);
                        Datasource.getInstance().updateBroker(broker);
                    }
                }
            }
            ObservableList<Broker> Brokerlist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
            brokerTable.itemsProperty().unbind();
            brokerTable.setItems(Brokerlist);
            btnmodbroker.setDisable(true);
        }
    }

    public void refreshScreen(){
        ObservableList<Broker> Brokerlist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
        brokerTable.itemsProperty().unbind();
        brokerTable.setItems(Brokerlist);
    }
    
    @FXML
    private TableView<Broker> brokerTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.windowWidth = (int) window.getWidth();
        ObservableList<Broker> brokerslist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());
        updateView();

    }

    @FXML
    private Button btnmodbroker;

    public void updateView() {
        if (brokerTable.getSelectionModel().getSelectedItem() == null) {
            System.out.println("brokertable not selected");
            btnmodbroker.setDisable(true);
        } else {
            System.out.println("brokertable selected");
            btnmodbroker.setDisable(false);
        }

        changelistener(columnbrokernaam);
        changelistener(columncontactpersoon);
        changelistener(columntelbroker);
        changelistener(columnemailbroker);
        //changelistener(columnopmerkingbroker);
    }

    public void changelistener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                double Kolumnwidthbrokernaam = (columnbrokernaam.widthProperty().getValue()) / 4.9;
                double Kolumnwidthcontactpersoon = (columncontactpersoon.widthProperty().getValue()) / 4.9;
                double Kolumnwidthtelbroker = (columntelbroker.widthProperty().getValue()) / 4.9;
                double Kolumnwidthemailbroker = (columnemailbroker.widthProperty().getValue()) / 4.9;
                double Kolumnwidthopmerkingbroker = (columnemailbroker.widthProperty().getValue()) / 4.9;

                ObservableList<Broker> brokerslist = FXCollections.observableArrayList(Datasource.getInstance().queryBroker());

                for (
                        Broker huidigebroker : brokerslist) {
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
        });
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


