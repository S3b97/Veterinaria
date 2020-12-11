package com.Miproyecto.proyectoAA;

import com.Miproyecto.proyectoAA.domain.Mascota;
import com.Miproyecto.proyectoAA.util.R;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VeterinariaDAO {

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

    public void guardarMascota(Mascota mascota) throws SQLException {

        String sql = "INSERT INTO mascotas (nombre, edad, peso, sexo, raza, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, mascota.getNombre());
        sentencia.setInt(2, mascota.getEdad());
        sentencia.setInt(3, mascota.getPeso());
        sentencia.setString(4, mascota.getSexo());
        sentencia.setString(5, mascota.getRaza());
        sentencia.setString(6, mascota.getTipo());
        sentencia.executeUpdate();
    }

    public void eliminarTodo() throws SQLException{
        String sql = "DELETE * FROM mascotas WHERE nombre = not null";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.executeUpdate();

    }

    public void eliminarMascota(Mascota mascota) throws SQLException {
        String sql = "DELETE FROM mascotas WHERE id = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, mascota.getId());
        sentencia.executeUpdate();
    }

    public void modificarMascota(Mascota mascotaVieja, Mascota mascotaNueva) throws SQLException {
        String sql = " UPDATE mascotas SET nombre = ? , edad = ? , peso = ? , sexo = ? , raza = ? , tipo = ? WHERE id = ? ";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, mascotaNueva.getNombre());
        sentencia.setInt(2, mascotaNueva.getEdad());
        sentencia.setInt(3, mascotaNueva.getPeso());
        sentencia.setString(4, mascotaNueva.getSexo());
        sentencia.setString(5, mascotaNueva.getRaza());
        sentencia.setString(6, mascotaNueva.getTipo());
        sentencia.setInt(7, mascotaVieja.getId());
        sentencia.executeUpdate();

    }

    public List<Mascota> obtenerMascotas() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Mascota mascota = new Mascota();
            mascota.setId(resultado.getInt(1));
            mascota.setNombre(resultado.getString(2));
            mascota.setEdad(resultado.getInt(3));
            mascota.setPeso(resultado.getInt(4));
            mascota.setSexo(resultado.getString(5));
            mascota.setRaza(resultado.getString(6));
            mascota.setTipo(resultado.getString(7));

            mascotas.add(mascota);
        }

        return mascotas;
    }
}