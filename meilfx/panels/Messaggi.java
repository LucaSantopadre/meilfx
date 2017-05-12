/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.panels;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author luca
 */
public class Messaggi {
    
    public static void messaggioChiudi(Stage primaryStage){
        		        // ************* metodo messaggio chiudi
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            event.consume();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma chiusura");
            alert.setHeaderText("Sei sicuro di voler uscire dal programma?");
            alert.initOwner(primaryStage);
            
            ButtonType buttonTypeNo = new ButtonType("NO");
            ButtonType buttonTypeSi = new ButtonType("SI");
            
            
            alert.getButtonTypes().setAll(buttonTypeNo,buttonTypeSi);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeSi){
                Platform.exit();
            }
        });
		        // *************fine metodo messaggio chiudi
    }
    
}
