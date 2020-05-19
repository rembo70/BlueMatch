package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import BlueMatch.model.OverviewRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private TableView<OverviewRecord> overviewRecordTable;


//    public void listAanvragen() {
    //       Task<ObservableList<Aanvraag>> task = new GetAllAanvragenTask();
//        aanvraagTable.itemsProperty().bind(task.valueProperty());
//
//       new Thread(task).start();
//       updateMainView();
//    }

    public void listOverviewRecord() {
        Task<ObservableList<OverviewRecord>> task = new GetAllOverviewRecordTask();
        overviewRecordTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
        updateMainView();
    }


    @FXML
    public void changeSceneAanvraagDetails(ActionEvent event) throws IOException {
        //
        //Parent detailViewParent = FXMLLoader.load(getClass().getResource("editaanvrg.fxml"));
        //System.out.println("Hallo");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editaanvrg.fxml"));
        Parent detailViewParent = loader.load();
        Editaanvraag ctrleditaanvraag = loader.getController();

        ctrleditaanvraag.listAanvragen();
        // System.out.println("completed listaanvragen controller2");
        Scene detailViewScene = new Scene(detailViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();


        ctrleditaanvraag.setParentScene(window.getScene());
        ctrleditaanvraag.setParentController(this);
        window.setScene((detailViewScene));
        window.show();
        ctrleditaanvraag.updateView();
    }

    @FXML
    public void tableViewMouseClicked(MouseEvent event) throws IOException {
        // resize table on mouse clicked
        updateMainView();
    }

    //    public void updateMainView() {
//        ObservableList<Aanvraag> aanvraaglist = FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());
//        aanvraagTable.itemsProperty().unbind();
//        aanvraagTable.setItems(aanvraaglist);
//        System.out.println("main view updated");
//    }
    public void updateMainView() {
        ObservableList<OverviewRecord> overviewrecordlist = FXCollections.observableArrayList(Datasource.getInstance().queryMain());
        overviewRecordTable.itemsProperty().unbind();
        overviewRecordTable.setItems(overviewrecordlist);
        System.out.println("main view updated");
    }

}


//class GetAllAanvragenTask extends Task {
//    @Override
//    public ObservableList<Aanvraag> call() {
//        return FXCollections.observableArrayList(Datasource.getInstance().queryAanvraag());
//    }
//}
class GetAllOverviewRecordTask extends Task {
    @Override
    public ObservableList<OverviewRecord> call() {
        return FXCollections.observableArrayList(Datasource.getInstance().queryMain());
    }
}