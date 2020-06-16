package BlueMatch;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    private Button BtnModMedewerker;
    @FXML
    private Button BtnSendMail;
    @FXML
    private Button BtnDelMedewerker;

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
    private PasswordField passwordmail;

    @FXML
    public void sendmail(ActionEvent event) throws IOException, SQLException {
        Medewerker medewerker = (Medewerker) medewerkerTable.getSelectionModel().getSelectedItem();
        String destination = medewerker.getEmailmedewerker();
        System.out.println("send mail");
        new SendEmailOffice365().sendEmail(passwordmail,destination);
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
    public void updateMedewerker(ActionEvent event) throws IOException, SQLException {
        Medewerker medewerker = (Medewerker) medewerkerTable.getSelectionModel().getSelectedItem();


        if (medewerker != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addMedewerker.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            AddMedewerkerController addmedewerkercontroller = loader.getController();
            addmedewerkercontroller.editMedewerker(medewerker,"update");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (medewerker.getVoornaam().isEmpty()) {
                        System.out.println("geen medewerkernaam ingevuld");
                    } else {
                        addmedewerkercontroller.updateMedewerker(medewerker);
                        Datasource.getInstance().updateMedewerker(medewerker);
                    }
                }
            }
            ObservableList<Medewerker> Medewerkerlist = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());
            medewerkerTable.itemsProperty().unbind();
            medewerkerTable.setItems(Medewerkerlist);
            BtnModMedewerker.setDisable(true);
        } else {
            System.out.println("geen medewerker selectie");
        }
    }
    @FXML
    public void deleteMedewerker(ActionEvent event) throws IOException, SQLException {
        Medewerker medewerker = (Medewerker) medewerkerTable.getSelectionModel().getSelectedItem();


        if (medewerker != null) {
            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addMedewerker.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            AddMedewerkerController addmedewerkercontroller = loader.getController();
            addmedewerkercontroller.editMedewerker(medewerker,"delete");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            Optional<ButtonType> result = dialog.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (medewerker.getVoornaam().isEmpty()) {
                        System.out.println("geen medewerkernaam ingevuld");
                    } else {
                        //addmedewerkercontroller.deleteMedewerker(medewerker);
                        Datasource.getInstance().deleteMedewerker(medewerker);
                    }
                }
            }
            ObservableList<Medewerker> Medewerkerlist = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());
            medewerkerTable.itemsProperty().unbind();
            medewerkerTable.setItems(Medewerkerlist);
            BtnModMedewerker.setDisable(true);
        } else {
            System.out.println("geen medewerker selectie");
        }
    }
    @FXML
    private TableView<Medewerker> medewerkerTable;

    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.windowWidth = (int) window.getWidth();
        updateView();
    }

    public void updateView() {
        if (medewerkerTable.getSelectionModel().getSelectedItem() == null) {

            BtnModMedewerker.setDisable(true);
            BtnSendMail.setDisable(true);
            BtnDelMedewerker.setDisable(true);
        } else {

            BtnModMedewerker.setDisable(false);
            if (!passwordmail.getText().isEmpty()) {
                BtnSendMail.setDisable(false);
            }
            BtnDelMedewerker.setDisable(false);
        }

        changelistener(columnvoornaam);
        changelistener(columnachternaam);
        changelistener(columnstatusmdw);
        changelistener(columnurenperweek);
        //changelistener(columnopmerkingbroker);
    }

    public void changelistener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            double Kolumnwidthvoornaam = (columnvoornaam.widthProperty().getValue()) / 4.9;
            double Kolumnwidthachternaam = (columnachternaam.widthProperty().getValue()) / 4.9;
            double Kolumnwidthstatusmdw = (columnstatusmdw.widthProperty().getValue()) / 4.9;
            double Kolumnwidthurenperweek = (columnurenperweek.widthProperty().getValue()) / 4.9;


            ObservableList<Medewerker> medewerkerslist = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());

        for(
            Medewerker huidigemdw :medewerkerslist)

            {
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
            }}
        });
    }





    public void listMedewerkers() {
        Task<ObservableList<Medewerker>> task = new GetAllMedewerkersTask();
        medewerkerTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }}

    class GetAllMedewerkersTask extends Task {

        @Override
        public ObservableList<Medewerker> call() {

            ObservableList<Medewerker> medewerkersList = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());
            return medewerkersList;

        }
    }
