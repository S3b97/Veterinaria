package com.Miproyecto.proyectoAA;

import com.Miproyecto.proyectoAA.domain.Mascota;
import com.Miproyecto.proyectoAA.util.AlertUtils;
import com.Miproyecto.proyectoAA.util.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AppController{


    public TextField tfNombre;
    public TextField tfEdad;
    public TextField tfPeso;
    public TextField tfRaza;
    public ChoiceBox<String> cbSexo;
    public ChoiceBox<String> cbTipo;
    public Label lbEstado;
    public ListView<Mascota> lvLista;
    public Button btNuevo, btModificar, btGuardar, btEliminar, btEliminarTodo;


    private  enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;

    private VeterinariaDAO veterinariaDAO;
    private Mascota mascotaSeleccionada;



    public AppController() throws IOException{
    veterinariaDAO = new VeterinariaDAO();
    try {
        veterinariaDAO.conectar();
    }catch (SQLException sqle){
        AlertUtils.mostrarError("Error al conectar con la base de datos");
    }catch (ClassNotFoundException cnfe){
        AlertUtils.mostrarError("Error al iniciar la aplicación");
    }
    }

    public void startLogin(Stage stage){

        try {
            UsuarioController startlogin = new UsuarioController();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUI("LoginAA.fxml"));
        loader.setController(startlogin);
            AnchorPane anchorPane = loader.load();

            Scene scene = new Scene(anchorPane);
            Stage nuevoStage = new Stage();
            nuevoStage.initModality(Modality.WINDOW_MODAL);
            nuevoStage.initOwner(stage);
            nuevoStage.setScene(scene);
            nuevoStage.showAndWait();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void cargarDatos() {
        modoEditar(false);
        lvLista.getItems().clear();

            try {
                List<Mascota> mascotas = veterinariaDAO.obtenerMascotas();
                lvLista.setItems(FXCollections.observableArrayList(mascotas));

                String[] sexos = new String[]{"Macho", "Hembra"};
                cbSexo.setItems(FXCollections.observableArrayList(sexos));

                String[] tipos = new String[]{"Gato", "Perro", "Conejo", "Caballo",};
                cbTipo.setItems(FXCollections.observableArrayList(tipos));
            }catch (SQLException sqle){
                AlertUtils.mostrarError("Error cargando los datos");
            }
        }


    @FXML
    public void nuevaMascota(Event event) {
    limpiarCeldas();
    modoEditar(true);
    accion = Accion.NUEVO;

    }

    @FXML
    public void modificarMascota(Event event) {
    modoEditar(true);
    accion = Accion.MODIFICAR;
    }

    @FXML
    public void eliminarMascota(Event event){
        Mascota mascota = lvLista.getSelectionModel().getSelectedItem();
        if (mascota == null){
            lbEstado.setText("No has seleccioando mascota");
            return;
        }

        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Eliminar Mascota");
            confirmacion.setContentText("¿Estas seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;

            veterinariaDAO.eliminarMascota(mascota);
            lbEstado.setText("Mascota eliminada con éxito");

            cargarDatos();
        }catch (SQLException sqle){
           AlertUtils.mostrarError("No se ha podido eliminar la mascota");
        }
    }


    @FXML
    private void eliminarTodo(Event event){

        try {
            veterinariaDAO.eliminarTodo();
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Eliminar Mascotas");
            confirmacion.setContentText("¿Estas seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        lbEstado.setText("Mascotas eliminadas con éxito");
            cargarDatos();
    }

        @FXML
    private void cargarMascota(Mascota mascota) {
        tfNombre.setText(mascota.getNombre());
        tfEdad.setText(String.valueOf(mascota.getEdad()));
        tfPeso.setText(String.valueOf(mascota.getPeso()));
        cbSexo.setValue(mascota.getSexo());
        tfRaza.setText(mascota.getRaza());
        cbTipo.setValue(mascota.getTipo());
    }

    @FXML
    public void seleccionarMascota(Event event) {
        mascotaSeleccionada = lvLista.getSelectionModel().getSelectedItem();
        cargarMascota(mascotaSeleccionada);
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
        String sexo = cbSexo.getSelectionModel().getSelectedItem();
        String raza = tfRaza.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        Mascota mascota = new Mascota(nombre, edad, peso, sexo, raza, tipo);

        try {
            switch (accion) {
                case NUEVO:
                    veterinariaDAO.guardarMascota(mascota);
                    break;
                case MODIFICAR:
                    veterinariaDAO.modificarMascota(mascotaSeleccionada, mascota);
                    break;
            }
        }catch (SQLException sqle){
            AlertUtils.mostrarError("Error al guardar la mascota");
        }

        lbEstado.setText("Mascota Guardada");
        cargarDatos();

        modoEditar(false);
    }

    @FXML
    public void exportar (ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            File fichero = fileChooser.showSaveDialog(null);

            FileWriter fileWriter = new FileWriter(fichero);
            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("id", "nombre", "edad", "peso", "sexo", "raza", "tipo"));

            List<Mascota> mascotas = veterinariaDAO.obtenerMascotas();
            for (Mascota mascota : mascotas)
                printer.printRecord(mascota.getId(), mascota.getNombre(), mascota.getEdad(),
                        mascota.getPeso(), mascota.getSexo(), mascota.getRaza(), mascota.getTipo());
            printer.close();

        } catch (SQLException | IOException sqlException) {
            AlertUtils.mostrarError("Error al exporat los datos");
        }
    }


    private void limpiarCeldas(){

        tfNombre.setText("");
        tfEdad.setText("");
        tfPeso.setText("");
        cbSexo.setValue("<seleccionar sexo>");
        tfRaza.setText("");
        cbTipo.setValue("<seleccionar tipo>");
        tfNombre.requestFocus();
    }

    private void modoEditar(boolean activar){
        btNuevo.setDisable(activar);
        btGuardar.setDisable(!activar);
        btModificar.setDisable(activar);
        btEliminar.setDisable(activar);

        tfNombre.setDisable(!activar);
        tfEdad.setDisable(!activar);
        tfPeso.setDisable(!activar);
        cbSexo.setDisable(!activar);
        tfRaza.setDisable(!activar);
        cbTipo.setDisable(!activar);

        lvLista.setDisable(activar);
    }

}

