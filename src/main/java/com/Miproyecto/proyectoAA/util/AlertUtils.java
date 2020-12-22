package com.Miproyecto.proyectoAA.util;

import javafx.scene.control.Alert;

public class AlertUtils {

public static void mostrarError(String mensaje){

    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setContentText(mensaje);
    alerta.show();
}
public static void mostrarSucces(String mensaje){
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setContentText(mensaje);
    alerta.show();
}
}
