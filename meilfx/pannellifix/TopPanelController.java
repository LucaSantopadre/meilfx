/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.pannellifix;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class TopPanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public Label lblCentro = new Label();
    @FXML
    public Label lblDestra = new Label();
    @FXML
    public AnchorPane pane = new AnchorPane();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
