package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    static Integer windowWidth;
    public static String userEmail;
    public static PasswordField userpassword;



    @Override
    public void start(Stage primaryStage) throws Exception {

            Dialog<DialogPane> dialog = new Dialog<DialogPane>();
            FXMLLoader loaderdg = new FXMLLoader(getClass().getResource("loginauth.fxml"));
            dialog.getDialogPane().setContent(loaderdg.load());
            dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("SKIP", ButtonBar.ButtonData.CANCEL_CLOSE),ButtonType.OK);
            Window window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> System.exit(0));
            Optional<DialogPane> result = dialog.showAndWait();
            {
                if (result.isPresent()) {
                    System.out.println("Ga verder, authentication done");
                }
            }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("BlueMatch.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        // System.out.println("start listaanvragen controller");
        controller.listOverviewRecord();
        // System.out.println("completed listaanvragen controller");
        primaryStage.setTitle("BlueMatch");
        primaryStage.setScene(new Scene(root, 1024, 700));
        primaryStage.show();

        //controller.updateMainView();
        windowWidth = 1024;
    }


    @Override
    public void init() throws Exception {
        super.init();
        if (!Datasource.getInstance().open()) {
            TimeUnit.SECONDS.sleep(8);
            System.out.println("Could not connect to database");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().close();
    }

    public static void main(String[] args) {

        System.out.println("start program");
        launch(args);
    }
}


