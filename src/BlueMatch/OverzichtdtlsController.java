package BlueMatch;

import BlueMatch.model.Aanbod;
import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import BlueMatch.model.OverviewRecord;
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

public class OverzichtdtlsController {

    @FXML
    private TextField refbrokerfield;
    @FXML
    private TextField refmedewerkerfield;
    @FXML
    private TextField refcontactfield;
    @FXML
    private TextField functiefield;
    @FXML
    private TextField locatiefield;
    @FXML
    private TextField refurenperweekaanbodfield;
    @FXML
    private TextField vraagurenfield;
    @FXML
    private TextField startdatumfield;
    @FXML
    private TextField aanvraagdatumfield;
    @FXML
    private TextField tariefaanvraagfield;
    @FXML
    private TextField tariefaanbodfield;
    @FXML
    private TextArea opmerkingfield;
    @FXML
    private TextArea opmerkingaanbodfield;
    @FXML
    private TextField linkaanvraagfield;


//    @FXML
//    private ComboBox<String> statusKlantCombo;
//
//    ObservableList<String> options =
//            FXCollections.observableArrayList(
//                    "", "Nieuw",
//                    "Vrijblijvend aangeboden",
//                    "Aangeboden Broker",
//                    "Aangeboden Eindklant",
//                    "Aangeboden"
//
//
//            );
//    @FXML
//    private ComboBox<String> statusAanbiedingCombo;
//
//    ObservableList<String> optionsaanb =
//            FXCollections.observableArrayList(
//                    "",
//                    "Nieuw",
//                    "Uitgenodigd voor gesprek",
//                    "Plaatsing",
//                    "Afgewezen",
//                    "Teruggetrokken",
//                    "Overig"
//            );
//
//    @FXML
//    private void initialize() {
//        statusKlantCombo.setItems(options);
//        statusKlantCombo.setValue("");
//        statusAanbiedingCombo.setItems(optionsaanb);
//        statusAanbiedingCombo.setValue("");
//    }

    private Controller parentController;
    private Scene ParentScene;

    public void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
        Controller parentcontrol;
        parentcontrol = this.parentController;
    }
    
    public void listDetailsRecord(OverviewRecord overviewrecord) {

        refbrokerfield.setText(overviewrecord.getRefbroker());
        refmedewerkerfield.setText(overviewrecord.getMedewerker());
        refcontactfield.setText(overviewrecord.getRefcontact());
        functiefield.setText(overviewrecord.getFunctie());
        locatiefield.setText(overviewrecord.getLocatie());
        vraagurenfield.setText(overviewrecord.getVraagurenweek());
        refurenperweekaanbodfield.setText(overviewrecord.getUrenperweekaanbod());
        startdatumfield.setText(overviewrecord.getStartdatum());
        aanvraagdatumfield.setText(overviewrecord.getDatumaanvraag());
        tariefaanvraagfield.setText(overviewrecord.getTariefaanvraag());
        tariefaanbodfield.setText(overviewrecord.getTariefaanbod());
        opmerkingfield.setText(overviewrecord.getOpmerking());
        opmerkingaanbodfield.setText(overviewrecord.getOpmerkingaanbod());
        linkaanvraagfield.setText(overviewrecord.getLinkaanvraag());
    }

    public void changeSceneMain(ActionEvent event) throws IOException {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        parentController.updateMainView();
        Main.windowWidth = (int) window.getWidth();
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
        System.out.println(parentController);
        ctrleditaanvraag.setParentController(this.parentController);
        window.setScene((detailViewScene));
        window.show();
        ctrleditaanvraag.updateView();
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
        ctrlmdwoverzicht.setParentController(this.parentController);
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
        ctrlbrokeroverzicht.setParentController(this.parentController);
        window.setScene((detailViewScene));
        window.show();
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
       ctrlklantoverzicht.setParentController(this.parentController);
        window.setScene((detailViewScene));
        window.show();
        ctrlklantoverzicht.updateView();
    }

    @FXML
    public void aanvraagToevoegen(ActionEvent event) throws IOException, SQLException {
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
        ctrleditaanbod.setParentController(this.parentController);
        window.setScene((detailViewScene));
        window.show();
        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            Main.windowWidth = (int) window.getWidth();

            ctrleditaanbod.updateView();
        });
    }
    public void updateMainView(){
         System.out.println("update view in detailscreen");
    };
}