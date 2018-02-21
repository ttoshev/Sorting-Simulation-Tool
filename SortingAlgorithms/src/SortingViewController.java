import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class SortingViewController implements Initializable {

    @FXML
    private GridPane sortGridPane;
    @FXML
    private ChoiceBox algorithmChoiceBox;
    @FXML
    private Slider arraySizeSlider;
    @FXML
    private Label arraySizeLabel;
    @FXML
    private Button sortButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label label;

    @FXML
    private SortingStratergy _sortingMethod;

    @FXML
    private Model _model;

    private int size=-1;

    public void sort() throws InterruptedException{
        TaskClass _sortingTask = new TaskClass();
        Thread thread=new Thread(_sortingTask);
        thread.start();
    }

    public void setSortMethod(){
        
    }

    class TaskClass implements Runnable{
        @Override
        public void run(){
            size=(int) arraySizeSlider.getValue();
            setSortMethod();
            if (_sortingMethod==null){label.setText("Please select an algorithm to sort.");}
            else {
                _sortingMethod.Sort(_model.getUnsortedList());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        algorithmChoiceBox.setItems(FXCollections.observableArrayList("Merge Sort", "Selection Sort"));
        arraySizeSlider.valueProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable observable) {

                arraySizeLabel.setText(Double.toString(Math.round(arraySizeSlider.getValue())));

            }



        });

    }

}
