import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Sorting extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SortingView.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/WesternLogo.png"));
        stage.setTitle("Sorting Engine");
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
