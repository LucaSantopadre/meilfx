/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.panels;

import java.util.List;
import javafx.scene.control.TreeItem;

/**
 *
 * @author luca
 */
public class Albero {
    
    // metodi pubblici per la costruzione dell'albero
    //gli deve essere passato il nodo su cui "attaccare i figli", e la lista dei figli
    public static void addToAlbero(TreeItem<String> nodo, List<String> listaMasCorrenti) {
        int i;
        for (i = 0; i < listaMasCorrenti.size(); i++) {
            nodo.getChildren().add(eseguiFiglio(listaMasCorrenti.get(i), nodo));
        }
    }
    public static TreeItem<String> eseguiFiglio(String newItemValue, TreeItem<String> parent) {
        TreeItem<String> foglia = new TreeItem<>();
        foglia.setValue(newItemValue);

        return foglia;
    }
    
}
