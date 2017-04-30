/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    Button btnEntra = new Button();
    
    public void btnEntraAction() throws IOException{
        System.out.println("entra premuto");
        
        Stage stage = (Stage) btnEntra.getScene().getWindow();
        Parent node = FXMLLoader.load(getClass().getResource("/meilfx/alberopdc/Albero.fxml"));
        Scene scene = new Scene( node);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        
        
    }
    
}
