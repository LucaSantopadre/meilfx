/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.login.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import meilfx.connect.Connessione;
import static meilfx.constants.Constant.DATABASE;

/**
 *
 * @author luca
 */
public class LoginSql {
    
    private String email = new String();
    private String tipoAccesso = new String();
    private int idUtente;
    
    public String getEmail(){
        return this.email;
    }
    
    public String getTipoAccesso(){
        return this.tipoAccesso;
    }
    
    public int getIdUtente(){
        return this.idUtente;
    }
    
    //ritorna 1=amministratore   ,  2=utente   ,  0=errore
    public static int verificaUtente(String email , String password){
        try {
            String query = "SELECT id, passw, email \n"
                    + "FROM " + DATABASE + ".utenti \n" 
                    + "WHERE email = ? ";

            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, email);
            System.out.println(preparedStmt);
            ResultSet rs = preparedStmt.executeQuery();
           
            while (rs.next()) {
                 if(rs.getString(2).equals(password)){
                     if(rs.getInt(1) >= 1000 && rs.getInt(1) < 2000){
                        System.out.println("amministratore");
                        return 1 ;
                     }
                     if(rs.getInt(1) >= 2000 && rs.getInt(1) < 3000){
                        System.out.println("utente");
                        return 2;
                     }
                 }
            }

        } catch (SQLException sqle) {
            return 0;
        }
        return 0;
    }
    
    
    
    //set dei dati campi di accesso in questa classe public
    public void setDatiAccesso(String email) {
        try {
            String query = "SELECT id, email \n"
                    + "FROM " + DATABASE + ".utenti \n"
                    + "WHERE email = ? ";

            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, email);
            System.out.println(preparedStmt);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                //amministratore
                if(rs.getInt(1) >= 1000 && rs.getInt(1) < 2000){
                    this.email = rs.getString(2);
                    this.tipoAccesso = "amministratore";
                    this.idUtente = rs.getInt(1);
                //utente
                }else if (rs.getInt(1) >= 2000 && rs.getInt(1) < 3000){
                    this.email = rs.getString(2);
                    this.tipoAccesso = "utente";
                    this.idUtente = rs.getInt(1);
                }
               

            }

        } catch (SQLException sqle) {
        }

    }
    
    
    
}
