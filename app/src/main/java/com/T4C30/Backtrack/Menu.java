/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.T4C30.Backtrack;


import com.T4C30.Backtrack.Control.SQLite;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

/**
 *
 * @author santiago
 */
public class Menu {
    @FXML
    private ListView<String> menu;

    @FXML
    private MenuItem editarJuego;

    @FXML
    private MenuItem crear;

    @FXML
    private MenuItem refrescar;

    @FXML
    public void initialize() throws SQLException{
        editarJuego.setOnAction(_ ->{
            try{
                Editor.editor(menu.getSelectionModel().getSelectedItem());
                App app = new App();
                app.editor();
        } catch (Exception e) {System.err.println("Problema con la edicion: " + e);}
        });

        refrescar.setOnAction(_->{
            try (Connection con = DriverManager.getConnection(SQLite.getUrl()); Statement dec = con.createStatement(); ResultSet res= dec.executeQuery("SELECT * FROM Reseña");){
                menu.getItems().clear();
                while (res.next()) {
                    menu.getItems().add(res.getString("Juego"));
                }
        } catch (Exception e) {System.err.println("Problema con refrescar: " + e);}
        });

        crear.setOnAction(_->{
            try {
                App app = new App();
                app.editor();
            } catch (IOException e) {
                System.err.println("El problema es este: " + e);
                e.printStackTrace();
            }
        });
        
        try (Connection con = DriverManager.getConnection(SQLite.getUrl()); Statement dec = con.createStatement(); ResultSet res= dec.executeQuery("SELECT * FROM Reseña");){
            while (res.next()) {menu.getItems().add(res.getString("Juego"));}
        } catch (Exception e) {System.err.println("Problema con la Primera consulta: " + e);}
    }
}
