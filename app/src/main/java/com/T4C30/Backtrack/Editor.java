/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.T4C30.Backtrack;

import com.T4C30.Backtrack.Control.SQLite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author santiago
 */
public class Editor { 

    @FXML
    private ChoiceBox<String> gustar;

    @FXML
    private Button guardar;

    @FXML
    private RadioButton r1, r2, r3, r4, r5;

    @FXML
    private TextArea comentario;

    @FXML
    private TextField juego;
    
    @FXML
    private TextField titulo;

    @FXML
    private TextField nota;

    @FXML
    private Button eliminar;

    private static String nombre = "No";

    

    public static String getNombre() {
        return nombre;
    }

    public static boolean gusto(String texto){
        if(texto.equals("Me Gusta")) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public Integer estrella(){
        if (r1.isSelected()){return 1;} 
        if (r2.isSelected()){return 2;} 
        if (r3.isSelected()){return 3;} 
        if (r4.isSelected()){return 4;}
        if (r5.isSelected()){return 5;}
        return null;
    }

    @FXML
    public void initialize(){
        gustar.getItems().addAll("Me Gusta", "No Me Gusta");

        try (Connection con = DriverManager.getConnection(SQLite.getUrl()); PreparedStatement dec = con.prepareStatement("SELECT * FROM Reseña Where Juego = ? ")){
            dec.setString(1, nombre);
            ResultSet res = dec.executeQuery();
            juego.setText(res.getString("Juego"));
             res.getInt("Pulgar");
             res.getInt("Estrella");
            nota.setText(String.valueOf(res.getDouble("Nota")));
            titulo.setText(res.getString("Título"));
            comentario.setText(res.getString("Comentario"));
        } catch (Exception e) {System.err.println("Problema con la Obtencion: " + e);}
        
        eliminar.setOnAction(_->{
            SQLite.eliminar(juego.getText());
        });
        
        guardar.setOnAction(_ -> {
            String notaReal = nota.getText();
            if (nombre.equals("No")) {
                SQLite.guardar(titulo.getText(),gusto(gustar.getValue().toString()),estrella(),Double.valueOf(notaReal),juego.getText(),comentario.getText());
            }else{
                SQLite.actualizar(titulo.getText(),gusto(gustar.getValue().toString()),estrella(),Double.valueOf(notaReal),juego.getText(),comentario.getText());
                nombre = "No";
            }
        });
    }

    public static void editor(String nombreString){
        nombre = nombreString;
    }

}