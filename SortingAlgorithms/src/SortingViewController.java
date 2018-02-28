import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SortingViewController implements Initializable {

    @FXML
    private Pane sortPane;
    @FXML
    private ChoiceBox algorithmChoiceBox;
    @FXML
    private Slider arraySizeSlider;
    @FXML
    private Label arraySizeLabel;
    @FXML
    private Button btn;


    private SortingStratergy _sortingMethod;
    public Model _model;

    public void reset() {
        _model.reset(_model.getSize());
        drawArray();
    }

    public boolean setSortMethod() {//choose between merge and selection sort
        if (algorithmChoiceBox.getValue() == "Merge Sort") {
            _sortingMethod = new MergeSort();
            _sortingMethod.setSortingViewController(this);
            return true;
        } else if (algorithmChoiceBox.getValue() == "Selection Sort") {
            _sortingMethod = new SelectionSort();
            _sortingMethod.setSortingViewController(this);
            return true;
        }
        return false;
    }

    public void exitBtn() {
        Stage stage = (Stage) algorithmChoiceBox.getScene().getWindow();
        stage.close();
    }
    
    public void drawArray() {
        sortPane.getChildren().clear();

        int arraySize = _model.getSize();
        int[] array = _model.getUnsortedList();
        double rectangleWidth = sortPane.getPrefWidth() / arraySize;

        for (int i = 0; i < arraySize; i++) {//draw new rectangles

            double rectangleHeight = (sortPane.getPrefHeight() / arraySize) * array[i];
            double xValue = rectangleWidth * i;
            double yValue = sortPane.getPrefHeight() - rectangleHeight;

            Rectangle rectangle = new Rectangle();

            rectangle.setX(xValue);
            rectangle.setY(yValue);
            rectangle.setWidth(rectangleWidth);
            rectangle.setHeight(rectangleHeight);
            rectangle.setStroke(Color.WHITE);
            rectangle.setFill(Color.RED);

            sortPane.getChildren().add(rectangle);

        }//end for

    }
    
    public void sort() throws InterruptedException {

        Thread sortingThread = new Thread() {

            @Override
            public void run() {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (setSortMethod()) {
                            _sortingMethod.Sort(_model.getUnsortedList());
                        }
                    }
                });
            }//end Run
        };//end sortingThread
        sortingThread.start();

    }//end Sort

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        _model = new Model(1);
        algorithmChoiceBox.setItems(FXCollections.observableArrayList("Merge Sort", "Selection Sort"));
        
        arraySizeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {

                double arraySize = arraySizeSlider.getValue();
                int arraySizeIntValue = (int) arraySize;

                arraySizeLabel.setText(Integer.toString(arraySizeIntValue));
                _model.reset(arraySizeIntValue);
                drawArray();

            }

        });

    }

}
