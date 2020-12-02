package com.Miproyecto.proyectoAA;

import com.Miproyecto.proyectoAA.domain.Mascota;
import com.Miproyecto.proyectoAA.util.AlertUtils;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private VeterinariaDAO veterinariaDAO;
    public TextField tfNombre;
    public TextField tfEdad;
    public TextField tfPeso;
    public TextField tfRaza;
    public ChoiceBox<String> cbSexo;
    public ChoiceBox<String> cbTipo;
    public Label lbEstado;
    public TableView<Mascota> tvMascotas;

    public AppController() {
    }



public void cargarDatos(){

        tvMascotas.getItems().clear();
        List<Mascota> mascotas = veterinariaDAO.obtenerMascotas();
        tvMascotas.setItems(FXCollections.observableList(mascotas));



        String[] sexos = new String[]{"<Selecciona el sexo>", "Macho", "Hembra"};
        cbSexo.setItems(FXCollections.observableArrayList(sexos));

        String[] tipos = new String[]{"<Selecciona el tipo de animal>", "Gato", "Perro", "Conejo", "Caballo", };
        cbTipo.setItems(FXCollections.observableArrayList(tipos));

}

      private void fijarColumnasTabla(){

        Field[] campos = Mascota.class.getDeclaredFields();

        for (Field field : campos) {
            if (field.getName().equals("id"))
                continue;

            TableColumn<Mascota, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tvMascotas.getColumns().add(column);
        }

        tvMascotas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    @FXML
    public void nuevaMascota(Event event) {

    }

    @FXML
    public void modificarMascota(Event event) {

    }

    @FXML
    public void eliminarMascota(Event event) {

        //Mascota mascota =

    }

    private void cargarMascota(Mascota mascota){
        tfNombre.setText(mascota.getNombre());
        //tfEdad.setText(mascota.getEdad());
    }

    @FXML
    public void seleccionarMascota(Event event){

        Mascota mascota = tvMascotas.getSelectionModel().getSelectedItem();
        cargarMascota(mascota);
    }

    @FXML
    public void guardarMascota(Event event) {
        String nombre = tfNombre.getText();
        if (nombre.equals("")) {
            AlertUtils.mostrarError("El nombre es un campo obligatorio");
            return;


        }
        int edad = Integer.parseInt(tfEdad.getText());
        int peso = Integer.parseInt(tfPeso.getText());
        String raza = tfRaza.getText();
        String sexo = cbSexo.getSelectionModel().getSelectedItem();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();


        Mascota mascota = new Mascota(nombre, edad, peso, sexo, raza, tipo);
        veterinariaDAO.guardarMascota(mascota);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        veterinariaDAO = new VeterinariaDAO();

        try {
            veterinariaDAO.conectar();
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.mostrarError("Error al iniciar la aplicacion");
        } catch (IOException ioe) {
            AlertUtils.mostrarError("Error al cargar la configuracion");
        }

        fijarColumnasTabla();
        cargarDatos();
    }
}

