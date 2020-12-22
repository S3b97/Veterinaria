package com.Miproyecto.proyectoAA;

import com.Miproyecto.proyectoAA.domain.Usuario;
import com.Miproyecto.proyectoAA.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UsuarioDAO {

    private Connection conexion;

    public void conectar() throws IOException, ClassNotFoundException, SQLException {

        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC", username, password);

    }


    public void desconectar() throws SQLException {
        conexion.close();
    }


    public void RegisterAction(Usuario usuario) throws SQLException {

        String sql = "INSERT INTO usuarios (nombre, contraseña) VALUES (?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuario.getNombre());
        sentencia.setString(2, usuario.getContraseña());
        sentencia.executeUpdate();
    }

}
