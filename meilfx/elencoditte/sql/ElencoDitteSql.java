/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.elencoditte.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import meilfx.connect.Connessione;

/**
 *
 * @author luca
 */
public class ElencoDitteSql {
    
    public static List<String> getDitteUtente(String emailUtente) {
        List<String> listaDitte = new ArrayList<>();

        try {
            String query = "SELECT cod_db, nome, cognome \n" +
                        "FROM idec_fx.ditte \n" +
                        "WHERE "+emailUtente+" = 1 ";
            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            System.out.println(preparedStmt);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String nome = rs.getString(2);
                String cognome = rs.getString(3);
                String descrizione = id + "_" + nome + "." + cognome;
                listaDitte.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return listaDitte;

    }
    
    
    
    public static List<String> getDitteAmmin() {
        List<String> listaDitte = new ArrayList<>();

        try {
            String query = "SELECT cod_db, nome, cognome \n" +
                        "FROM idec_fx.ditte \n" ;
            
            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            System.out.println(preparedStmt);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String nome = rs.getString(2);
                String cognome = rs.getString(3);
                String descrizione = id + "_" + nome + "." + cognome;
                listaDitte.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return listaDitte;

    }

    
    
}
