/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.panels;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author luca
 */
public class Schermo {
    
    // get screensize of monitor
    public static Rectangle2D SCREEN_SIZE = Screen.getPrimary().getVisualBounds();
    
    public static Stage STAGE;
    public static Scene SCENE;
    
   
    
}
