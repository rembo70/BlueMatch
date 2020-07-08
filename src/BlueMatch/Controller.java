package BlueMatch;

import BlueMatch.model.*;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class Controller {
    static String typeofaddaanvraag;
    static String typeofaddaanbod;


    @FXML
    private TableColumn columnBroker;
    @FXML
    private TableColumn columnFunctie;
    @FXML
    private TableColumn columnStatusKlant;
    @FXML
    private TableColumn columnMedewerker;
    @FXML
    private TableColumn columnbrokernaam;
    @FXML
    private TableColumn columnContact;
    @FXML
    private TableView<OverviewRecord> overviewRecordTable;

    @FXML
    private TextField brokerTextField;

    @FXML
    private TextField medewerkerTextField;

    @FXML
    private Button loginstatus;

    @FXML
    private ComboBox<String> statusKlantCombo;

    @FXML
    private Button btnstatushandler;

    @FXML
    private Tooltip tooltiplogin;

    private LoginauthController parentController;
    private Scene ParentScene;

    void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    void setParentController(LoginauthController loginauthController) {
        this.parentController = loginauthController;
        LoginauthController parentcontrol = loginauthController;
    }

    private ObservableList<String> options =
            FXCollections.observableArrayList(

                    "","Vrijblijvend aanbieden",
                    "Aanbieden via broker",
                    "Aanbieden bij eindklant",
                    "(Nog) niet aanbieden",
                    "Anders"

            );


    @FXML
    private ComboBox<String> statusAanbiedingCombo;

    private ObservableList<String> optionsaanb =
            FXCollections.observableArrayList(
                    "Nieuw",
                    "Aangeboden",
                    "Uitgenodigd voor gesprek",
                    "Afronden-Onderhandelen",
                    "Geplaatst",
                    "Afgewezen",
                    "Teruggetrokken",""
            );

    @FXML
    private void initialize() {
        statusKlantCombo.setItems(options);
        statusKlantCombo.setValue("");
        statusAanbiedingCombo.setItems(optionsaanb);
        statusAanbiedingCombo.setValue("");
        loginstatus.setStyle("-fx-Background-color: WHITE");


            switch (LoginauthController.Passwrdstatus){
                case "Not Validated":
                    loginstatus.setText ("Email not validated");
                    loginstatus.setTextFill(Color.GREY);
                    btnstatushandler.setTooltip(tooltiplogin);

                    break;
                case "OK":
                    loginstatus.setText ("Connected");
                    loginstatus.setTextFill(Color.GREEN);
                    btnstatushandler.setTooltip(null);
                    break;
                case "NOK":
                    loginstatus.setText ("Not connected");
                    loginstatus.setTextFill(Color.RED);
                    btnstatushandler.setDisable(false);
                    btnstatushandler.setText("Log in om status te wijzigen");
                    btnstatushandler.setFont(Font.font("Calibri", FontWeight.NORMAL, 10));
                    btnstatushandler.setWrapText(true);
                    btnstatushandler.setTextFill(Color.RED);
                    btnstatushandler.setTooltip(tooltiplogin);
                    btnstatushandler.setStyle("-fx-background-color: WHITE");
                    break;
                default:
                    loginstatus.setText ("Connectiestatus onbekend");
                    loginstatus.setTextFill(Color.GREY);
                    btnstatushandler.setTooltip(null);
            }
        }



    void listOverviewRecord() {
        GetAllOverviewRecordTask task = new GetAllOverviewRecordTask();
        overviewRecordTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
        updateMainView();
    }

    @FXML
    void gotologin (ActionEvent event) throws IOException {
        changescreenloginauth(event);
        //parentController.updateMainView();
        //parentController.refreshscreen();
    }

    private void changescreenloginauth(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Loginauth.fxml"));
        Scene LoginauthScene = new Scene(root);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        window.setWidth(360);
        window.setHeight(500);
    }

    @FXML
    private void refrfilter() {
        listOverviewRecord();
    }

    @FXML
    public void changeSceneAanvraagDetails(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("editaanvrg.fxml"));
        Parent detailViewParent = loader.load();
        Editaanvraag ctrleditaanvraag = loader.getController();

        ctrleditaanvraag.listAanvragen();

        Scene detailViewScene = new Scene(detailViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ctrleditaanvraag.setParentScene(window.getScene());
        ctrleditaanvraag.setParentController(this);
        window.setScene((detailViewScene));
        window.show();
        ctrleditaanvraag.updateView();
    }
    @FXML
    public void statuschange(ActionEvent event) throws IOException, SQLException {

        if (LoginauthController.Passwrdstatus.equals("NOK")){
            changescreenloginauth(event);
        }else
        if (overviewRecordTable.getSelectionModel().getSelectedItem() != null) {

            OverviewRecord overviewrecord = overviewRecordTable.getSelectionModel().getSelectedItem();
            if (overviewrecord.getStatusaanbod() == null){
                Dialog<ButtonType> dialog = new Dialog<ButtonType>();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addaanbieding.fxml"));
                dialog.getDialogPane().setContent(loader.load());
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                Optional<ButtonType> result = dialog.showAndWait();
                {
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        addAanbiedingController addAanbiedingController = loader.getController();
                        Aanbod aanbod = addAanbiedingController.getNewAanbod(String.valueOf(overviewRecordTable.getSelectionModel().getSelectedItem().getIdaanvraag()));
                        Datasource.getInstance().aanbodToevoegen(aanbod);
                    }
                }
//            ObservableList<OverviewRecord> Overviewlist = FXCollections.observableArrayList(Datasource.getInstance().queryMain());
//            overviewRecordTable.itemsProperty().unbind();
//            overviewRecordTable.setItems(Overviewlist);
                refreshscreen();
                updateMainView();
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Statushandler.fxml"));
                Parent statusviewParent = loader.load();
                StatusHandler ctrlstatushandler = loader.getController();
                ctrlstatushandler.editStatus(overviewrecord);

                //ctrlstatushandler.listAanvragen();

                Scene detailViewScene = new Scene(statusviewParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                ctrlstatushandler.setParentScene(window.getScene());
                ctrlstatushandler.setParentController(this);
                window.setScene((detailViewScene));
                window.show();
                ctrlstatushandler.editStatus(overviewrecord);
            }
        }
    }

    @FXML
    public void overzichtMedewerker(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Medewerkeroverzicht.fxml"));
        Parent detailViewParent = loader.load();
        MedewerkerOverzicht ctrlmdwoverzicht = loader.getController();
        ctrlmdwoverzicht.listMedewerkers();
        Scene detailViewScene = new Scene(detailViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ctrlmdwoverzicht.setParentScene(window.getScene());
        ctrlmdwoverzicht.setParentController(this);
        window.setScene((detailViewScene));
        window.show();
        ctrlmdwoverzicht.updateView();
    }

    @FXML
    public void overzichtBroker(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Brokeroverzicht.fxml"));
        Parent detailViewParent = loader.load();
        BrokerOverzicht ctrlbrokeroverzicht = loader.getController();
        ctrlbrokeroverzicht.listBrokers();
        Scene detailViewScene = new Scene(detailViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ctrlbrokeroverzicht.setParentScene(window.getScene());
        ctrlbrokeroverzicht.setParentController(this);
        window.setScene((detailViewScene));
        window.show();
        Main.windowWidth = (int) window.getWidth();
        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            Main.windowWidth = (int) window.getWidth();

            ctrlbrokeroverzicht.updateView();
            ctrlbrokeroverzicht.refreshScreen();

        });
        ctrlbrokeroverzicht.updateView();

}

    @FXML
    public void overzichtKlant(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Klantoverzicht.fxml"));
        Parent detailViewParent = loader.load();
        KlantOverzicht ctrlklantoverzicht = loader.getController();
        ctrlklantoverzicht.listKlanten();
        Scene detailViewScene = new Scene(detailViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ctrlklantoverzicht.setParentScene(window.getScene());
        ctrlklantoverzicht.setParentController(this);
        window.setScene((detailViewScene));
        window.show();
        ctrlklantoverzicht.updateView();
    }

    @FXML
    public void aanbieden(ActionEvent event) throws IOException, SQLException {
        if (overviewRecordTable.getSelectionModel().getSelectedItem() != null) {
            typeofaddaanbod="new";
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addaanbieding.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    addAanbiedingController addAanbiedingController = loader.getController();
                    Aanbod aanbod = addAanbiedingController.getNewAanbod(String.valueOf(overviewRecordTable.getSelectionModel().getSelectedItem().getIdaanvraag()));
                    Datasource.getInstance().aanbodToevoegen(aanbod);

                    Aanvraag aanvraag = Datasource.getInstance().queryAanvraagKlant(overviewRecordTable.getSelectionModel().getSelectedItem().getIdaanvraag());

                    StatusHandler.sendmail(aanbod.getRefmedewerker(),"BM - Er is een aanbieding voor je gedaan","Je bent aangeboden bij \nbroker/klant naam: " + aanvraag.getRefbroker() + " " );
                }
            }
            typeofaddaanbod="update";
            refreshscreen();
            updateMainView();
        }
    }

    @FXML
    public void aanvraagToevoegen(ActionEvent event) throws IOException, SQLException {
        typeofaddaanvraag="new";
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
        refreshscreen();
        updateMainView();
        typeofaddaanvraag="update";
    }


    @FXML
    public void changeSceneAanbodDetails(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("editaanbod.fxml"));
        Parent detailViewParent = loader.load();
        Editaanbod ctrleditaanbod = loader.getController();

        ctrleditaanbod.listAanbiedingen();
        Scene detailViewScene = new Scene(detailViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ctrleditaanbod.setParentScene(window.getScene());
        ctrleditaanbod.setParentController(this);
        window.setScene((detailViewScene));
        window.show();
        ctrleditaanbod.updateView();

    }

    void refreshscreen() {
        ObservableList<OverviewRecord> Overviewlist = FXCollections.observableArrayList(Datasource.getInstance().queryMain());
        overviewRecordTable.itemsProperty().unbind();
        overviewRecordTable.setItems(Overviewlist);
    }

    @FXML
    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        if (event.getClickCount() > 1) {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("overzichtdetails.fxml"));
            Parent detailViewParent = loader.load();
            OverzichtdtlsController ctrldetailsoverzicht = loader.getController();
            ctrldetailsoverzicht.listDetailsRecord(overviewRecordTable.getSelectionModel().getSelectedItem());
            Scene detailViewScene = new Scene(detailViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ctrldetailsoverzicht.setParentScene(window.getScene());
            ctrldetailsoverzicht.setParentController(this);
            window.setScene((detailViewScene));
            window.show();

        }

        updateMainView();
    }

    @FXML
    private Button aanbiedingmaken;

    void updateMainView() {
        if (overviewRecordTable.getSelectionModel().getSelectedItem() == null) {

            aanbiedingmaken.setDisable(true);
            if (LoginauthController.Passwrdstatus.equals("NOK")){btnstatushandler.setDisable(false);}else{
                btnstatushandler.setDisable(true);
            }
        } else {

            aanbiedingmaken.setDisable(false);
            btnstatushandler.setDisable(false);

        }

        Datasource.filterstatus = statusKlantCombo.getValue();
        Datasource.filterstatusaanb = statusAanbiedingCombo.getValue();
        Datasource.filterbroker = brokerTextField.getText();
        Datasource.filtermedewerker = medewerkerTextField.getText();

        changelistener(columnBroker);
        changelistener(columnFunctie);
        changelistener(columnMedewerker);
        changelistener(columnFunctie);

        //changelistenerstatus(columnStatusKlant);
        ObservableList<OverviewRecord> Overviewlist = FXCollections.observableArrayList(Datasource.getInstance().queryMain());
        //Overviewlist.addListener((Change<? extends OverviewRecord> c) -> { });
    }





    private void changelistener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {

                double Kolumnwidthmedewerker = (columnMedewerker.widthProperty().getValue()) / 7;
                double Kolumnwidthbroker = (columnBroker.widthProperty().getValue()) / 7;
                double Kolumnwidthstatus = (columnStatusKlant.widthProperty().getValue()) / 7;
                double Kolumnwidthfunctie = (columnFunctie.widthProperty().getValue()) / 7;

                ObservableList<OverviewRecord> Overviewlist = FXCollections.observableArrayList(Datasource.getInstance().queryMain());

                for (OverviewRecord huidigeoverviewrecord : Overviewlist) {
                    if (!(huidigeoverviewrecord.getRefbroker() == null))
                        huidigeoverviewrecord.setRefbroker(Editaanvraag.lineWrap(huidigeoverviewrecord.getRefbroker(), (int) Kolumnwidthbroker));
                    if (!(huidigeoverviewrecord.getMedewerker() == null)) {
                        huidigeoverviewrecord.setMedewerker(Editaanvraag.lineWrap(huidigeoverviewrecord.getMedewerker(), (int) Kolumnwidthmedewerker));
                    }
                    if (!(huidigeoverviewrecord.getFunctie() == null)) {
                        huidigeoverviewrecord.setFunctie(Editaanvraag.lineWrap(huidigeoverviewrecord.getFunctie(), (int) Kolumnwidthfunctie));
                    }
                    if (!(huidigeoverviewrecord.getStatusklant() == null)) {
                        huidigeoverviewrecord.setStatusklant(Editaanvraag.lineWrap(huidigeoverviewrecord.getStatusklant(), (int) Kolumnwidthstatus));
                    }
                    overviewRecordTable.itemsProperty().unbind();
                    overviewRecordTable.setItems(Overviewlist);
                }
            }
        });
    }
}

class GetAllOverviewRecordTask extends Task {
    @Override
    public ObservableList<OverviewRecord> call() {
        return FXCollections.observableArrayList(Datasource.getInstance().queryMain());
    }
}
