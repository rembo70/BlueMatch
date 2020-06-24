package BlueMatch;

import BlueMatch.model.Datasource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;


public class Main extends Application {
    static Integer windowWidth;


    @Override
    public void start(Stage primaryStage) throws Exception {
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


