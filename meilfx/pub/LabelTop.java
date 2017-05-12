/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.pub;

import javafx.scene.control.Label;
import meilfx.login.LoginController;

/**
 *
 * @author luca
 */
public class LabelTop extends Label{
    
    //*** dati di accesso publici ( vengono settati dopo il login
    int     idUtente    = LoginController.Login.getIdUtente();
    String  emailUtente = LoginController.Login.getEmail();
    String  tipoAccesso = LoginController.Login.getTipoAccesso();
    //***
    
    LabelTop lblTop = new LabelTop();
    
    public void setLblTopsx(){
        this.lblTop.setText(emailUtente);       
    }    
    public LabelTop getlblTopSx(){
        return this.lblTop;
    }
    
    
    public void setLblTopCenter(){
        this.lblTop.setText(tipoAccesso);       
    }    
    public LabelTop getlblTopCenter(){
        return this.lblTop;
    }
    
    
    public void setLblTopDx(){      
        this.lblTop.setText(Integer.toString(idUtente));       
    }    
    public LabelTop getlblTopDx(){
        return this.lblTop;
    }
    
}
