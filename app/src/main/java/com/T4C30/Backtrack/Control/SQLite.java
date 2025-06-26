/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.T4C30.Backtrack.Control;

import com.T4C30.Backtrack.Editor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author santiago
 */
public class SQLite {   

    private static final String URL = "jdbc:sqlite:BDJ.db";

    public static String getUrl() {
        return URL;
    }

    public static void comprobacion() throws IOException{
        Path ruta = Paths.get("./BDJ.db");
        if (Files.notExists(ruta)) {Files.createFile(ruta);}

        try (Connection con = DriverManager.getConnection(URL);){
            Statement dec1 = con.createStatement();
            dec1.execute("CREATE TABLE IF NOT EXISTS Reseña (Juego TEXT, Pulgar NUMERIC, Estrella INTEGER, Nota REAL, Título TEXT, Comentario TEXT)");
        } catch (Exception e) {
            System.err.println("Tabla no creada: " + e);
        }
    }

    public static void guardar(String titulo, boolean pulgar, int estrella, double nota, String juego, String comentario){
        try (Connection con = DriverManager.getConnection(URL); PreparedStatement dec = con.prepareStatement("INSERT INTO Reseña (Juego, Pulgar, Estrella, Nota, Título, Comentario) VALUES (?,?,?,?,?,?)")) {
            dec.setString(1, juego);
            dec.setBoolean(2, pulgar);
            dec.setInt(3, estrella);
            dec.setDouble(4, nota);
            dec.setString(5, titulo);
            dec.setString(6, comentario);

            
            dec.executeUpdate();
        } catch (Exception e) {
            System.err.println("Insercion no realizada: " + e);
        }
    }

    public static void actualizar(String titulo, boolean pulgar, int estrella, double nota, String juego, String comentario){
            try (Connection con = DriverManager.getConnection(URL); PreparedStatement dec = con.prepareStatement("UPDATE Reseña SET Juego = ?, Pulgar = ?, Estrella = ?, Nota = ?, Título = ?, Comentario = ? WHERE Juego = ?")) {
            dec.setString(1, juego);
            dec.setBoolean(2, pulgar);
            dec.setInt(3, estrella);
            dec.setDouble(4, nota);
            dec.setString(5, titulo);
            dec.setString(6, comentario);
            dec.setString(7, Editor.getNombre());
            
            dec.executeUpdate();
        } catch (Exception e) {
            System.err.println("Actualizacion no realizada: " + e);
        }
    }

    public static void eliminar(String nombre){
        try (Connection con = DriverManager.getConnection(URL); PreparedStatement dec = con.prepareStatement("DELETE FROM Reseña WHERE Juego = ?")){
            dec.setString(1, nombre);

            dec.executeUpdate();
        } catch (Exception e) {
            System.err.println("Fallo la eliminacion: " + e);
        }

    }




/*     public static String consulta(){
        try (Connection con = DriverManager.getConnection(SQLite.getUrl()); Statement dec = con.createStatement(); ResultSet res= dec.executeQuery("SELECT * FROM Reseña");){
            while (res.next()) {
                 res.getString("Juego");
                
            }
        } catch (Exception e) {
            System.err.println("Problema con la consulta: " + e);
        }
        return null;
    } */
}
