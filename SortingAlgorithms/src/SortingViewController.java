/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Joahn
 */
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
