/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.pub;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author luca
 */
public class Bottone extends Button{
    
    //non passare lo stesso bottone su cui si fa l'action
    public void btnEsciAction(Button btnEsci) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma chiusura");
        alert.setHeaderText("Sei sicuro di voler uscire dal programma?");

        ButtonType buttonTypeNo = new ButtonType("NO");
        ButtonType buttonTypeSi = new ButtonType("SI");

        alert.getButtonTypes().setAll(buttonTypeNo, buttonTypeSi);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeSi) {
            Stage stage = (Stage) btnEsci.getScene().getWindow();
            Parent node = FXMLLoader.load(getClass().getResource("/meilfx/login/Login.fxml"));
            Scene scene = new Scene(node);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }
    } 
}
