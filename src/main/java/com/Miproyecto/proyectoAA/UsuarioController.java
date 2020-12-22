package com.Miproyecto.proyectoAA;

import com.Miproyecto.proyectoAA.domain.Usuario;
import com.Miproyecto.proyectoAA.util.AlertUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UsuarioController {

    public TextField txtUserName;
public PasswordField txtUserPassword;
public Label lbStatusLogin;

public Button btRegister;

    private UsuarioDAO usuarioDAO;
    private Usuario UsuarioRegistrado;

    public UsuarioController(){

        usuarioDAO = new UsuarioDAO();
        try {
           usuarioDAO.conectar();

        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.mostrarError("Error al iniciar la aplicación");
        }catch (IOException ioException){
            AlertUtils.mostrarError("Error al iniciar la aplicacion");
        }
    }

@FXML
private void RegisterAction (Event event) {

    String nombre = txtUserName.getText();
    String contraseña = txtUserPassword.getText();
    if (nombre.equals("")) {
        AlertUtils.mostrarError("El nombre es un campo obligatorio");
        return;
    }  if (contraseña.equals("")) {
        AlertUtils.mostrarError("El nombre es un campo obligatorio");
        return;
    }
    Usuario usuario = new Usuario(nombre, contraseña);
    try {
        usuarioDAO.RegisterAction(usuario);
    } catch (SQLException sqlException) {
        AlertUtils.mostrarError("Error al registrar usuario");
    }

    lbStatusLogin.setText("UsuarioRegistrado");
    AlertUtils.mostrarSucces("Usuario Registrado");
    Stage currentStage = (Stage) btRegister.getScene().getWindow();
    currentStage.close();
}
}




