/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.ditta;

/**
 *
 * @author luca
 */
public class Ditta {
    
    private String nome ;
    private String codice ;
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return this.nome;
    }
    
    public void setCodice(String codice){
        this.codice = codice;
    }
    public String getCodice(){
        return this.codice;
    }
}
