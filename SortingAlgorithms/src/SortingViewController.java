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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Button sortButton;
    @FXML
    private Button resetButton;

    private Model _model;

    @FXML
    private SortingStratergy _sortingMethod;

    public  void sort() throws InterruptedException {
        Thread th = new Thread(){
            @Override
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run()
                    {
                        if (setSortMethod()){
                            try {
                                _sortingMethod.Sort(_model.getUnsortedList());
                                drawArray();
                            } catch (InterruptedException ex){}
                            drawArray();
                            }
                    }
                });
            }
        };
        
        Thread dr = new Thread(){
            @Override
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run()
                    {
                        while (th.isAlive())
                        {drawArray();}
                        
                    }
                });
            }
    
        };
        
        th.start();
        dr.start();
     
   
    }
    public void reset(){
        _model.reset(_model.getSize());
        drawArray();
    }

    public boolean setSortMethod() {
        if (algorithmChoiceBox.getValue()=="Merge Sort"){
            _sortingMethod = new MergeSort();
            return true;
        }
        else if (algorithmChoiceBox.getValue()=="Selection Sort"){
            _sortingMethod = new SelectionSort();
            return true;
        }
        return false;
    }
    
    public void drawArray() {
        
        sortPane.getChildren().clear();

        int arraySize = _model.getSize();
        int[] array = _model.getUnsortedList();

        double rectangleWidth = sortPane.getPrefWidth() / arraySize;

        for (int i = 0; i < arraySize; i++) {

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

        }

    }

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
