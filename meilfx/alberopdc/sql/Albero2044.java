/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.alberopdc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import meilfx.connect.Connessione;
import static meilfx.constants.Constant.DATABASE;

/**
 *
 * @author luca
 */
public class Albero2044 {

    public static ObservableList<String> getContiDitta(String codDitta) {
        ObservableList<String> descrList = null;
        List<String> lista = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT 2044mv.conto \n"
                    + "FROM " + codDitta + ".2044mv \n"
                    + "ORDER BY 2044mv.conto ASC";

            Connection conn = Connessione.getConnection();

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            //preparedStmt.setString(1, codDitta);
            //System.out.println(preparedStmt);

            ResultSet rs = preparedStmt.executeQuery(query);
            while (rs.next()) {
                String cod = rs.getString(1);
                lista.add(cod);
            }

            descrList = FXCollections.observableArrayList(lista);

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static String[] cercaContiMc2(String contoX) {
        String[] record = new String[4];

        try {
            String query = "SELECT mc_02_descr, mc_02_id_ex, Count(mc_02_id_ex) AS quanti, m_02_ID \n" +
                            "FROM "+DATABASE+".mc_02 \n" +
                            "WHERE mc_02_id_ex = ?";

            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, contoX);
            
            ResultSet res = preparedStmt.executeQuery();
            
            while (res.next()) {
                record[0] = res.getString(1);
                record[1] = res.getString(2);
                record[2] = res.getString(3);
                record[3] = res.getString(4);
            }

        } catch (SQLException sqle) {
        }
        return record;

    }
    
    
        public static String[] cercaContiMc3(String contoX) {
        String[] record = new String[4];

        try {
            String query = "SELECT mc_03_descr, mc_03_id_ex, Count(mc_03_id_ex) AS quanti, m_03_ID \n" +
                            "FROM "+DATABASE+".mc_03 \n" +
                            "WHERE mc_03_id_ex = ?";

            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, contoX);
            
            ResultSet res = preparedStmt.executeQuery();
            
            while (res.next()) {
                record[0] = res.getString(1);
                record[1] = res.getString(2);
                record[2] = res.getString(3);
                record[3] = res.getString(4);
            }

        } catch (SQLException sqle) {
        }
        return record;

    }
        
        
        public static String[] cercaContiMc4(String contoX) {
        String[] record = new String[4];

        try {
            String query = "SELECT mc_04_descr, mc_04_id_ex, Count(mc_04_id_ex) AS quanti, m_04_ID \n" +
                            "FROM "+DATABASE+".mc_04 \n" +
                            "WHERE mc_04_id_ex = ?";

            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, contoX);
            
            ResultSet res = preparedStmt.executeQuery();
            
            while (res.next()) {
                record[0] = res.getString(1);
                record[1] = res.getString(2);
                record[2] = res.getString(3);
                record[3] = res.getString(4);
            }

        } catch (SQLException sqle) {
        }
        return record;

    }
    
        
    public static String[] cercaContiMc5(String contoX) {
        String[] record = new String[4];

        try {
            String query = "SELECT mc_05_descr, mc_05_id_ex, Count(mc_05_id_ex) AS quanti, m_05_ID \n" +
                            "FROM "+DATABASE+".mc_05 \n" +
                            "WHERE mc_05_id_ex = ?";

            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, contoX);
            
            ResultSet res = preparedStmt.executeQuery();
            
            while (res.next()) {
                record[0] = res.getString(1);
                record[1] = res.getString(2);
                record[2] = res.getString(3);
                record[3] = res.getString(4);
            }

        } catch (SQLException sqle) {
        }
        return record;

    }
    
    
    public static boolean getContiTabelle(String contoX){
        
        String[] record_mc2 = cercaContiMc2(contoX);
        String[] record_mc3 = cercaContiMc3(contoX);
        String[] record_mc4 = cercaContiMc4(contoX);
        String[] record_mc5 = cercaContiMc5(contoX);
        int quantiMc2 = Integer.parseInt(record_mc2[2]);
        int quantiMc3 = Integer.parseInt(record_mc3[2]);
        int quantiMc4 = Integer.parseInt(record_mc4[2]);
        int quantiMc5 = Integer.parseInt(record_mc5[2]);
        int quanti =0;
        int quanti = quantiMc2+quantiMc3+quantiMc4+quantiMc5;
        
        String[] IDesterni;
        IDesterni[0] = record_mc2[3];
        IDesterni[0] = record_mc3[3];
        IDesterni[0] = record_mc4[3];
        IDesterni[0] = record_mc5[3];

        
        if(quanti ==0){
             res= false;
        }else if(quanti>1){
            res= false;
        }else res= true;
        
        
        for(i=0 ; i<IDesterni.length() ; i++){
            int x=0 ;
            for(x=0 ; x<IDesterni.length() ; x++){
                if(i=x) continue;
                
                if(IDesterni[i] != IDesterni[x]){
                    res = true;
                }else res = false;
            }
        }
        
        return res;
    }


}
