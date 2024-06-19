package org.example;

// Paquete y Imports: Se importan las clases necesarias de JavaFX para construir la interfaz gráfica y los
// elementos de la aplicación.
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

// Clase Crucigrama: Extiende Application de JavaFX, lo que permite construir una interfaz gráfica con el método start.
public class Crucigrama extends Application {

    private Map<TextField, int[]> textFieldPositions = new HashMap<>();
    // Definición de crucigramas y matrices de ocultación

    private static Object[][] crucigrama1 = {
            {"&", false}, {"XIV", false}, {"-", false}, {"VI", true}, {"=", false}, {"VIII", false}, {"&", false}, {"&", false}, {"&", false},
            {"VIII", false}, {"&", false}, {"VII", false}, {"&", false}, {"V", false}, {"*", true}, {"IV", false}, {"=", false}, {"XX", false},
            {"+", false}, {"&", false}, {"+", false}, {"&", false}, {"&", false}, {"-", false}, {"&", false}, {"&", false}, {"-", true},
            {"XV", true}, {"&", false}, {"X", true}, {"&", false}, {"&", false}, {"-", false}, {"&", false}, {"&", false}, {"IV", false},
            {"-", false}, {"&", false}, {"=", false}, {"VI", false}, {"&", false}, {"VI", false}, {"&", false}, {"&", false}, {"-", true},
            {"IX", false}, {"-", false}, {"III", true}, {"+", false}, {"V", false}, {"=", false}, {"XI", false}, {"&", false}, {"VII", false},
            {"=", false}, {"&", false}, {"&", false}, {"VI", true}, {"&", false}, {"X", true}, {"&", false}, {"&", false}, {"=", false},
            {"XIV", false}, {"&", false}, {"&", false}, {"=", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"IX", false},
            {"&", false}, {"&", false}, {"&", false}, {"VII", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}
    };

    private static Object[][] crucigrama2 = {
            {"VII", false}, {"&", false}, {"&", false}, {"&", false}, {"v", false}, {"*", false}, {"III", false}, {"=", false}, {"XV", true},
            {"*", false}, {"&", false}, {"&", false}, {"&", false}, {"+", true}, {"&", false}, {"*", false}, {"&", false}, {"*", false},
            {"IV", true}, {"+", false}, {"III", false}, {"=", false}, {"VII", false}, {"&", false}, {"IV", false}, {"&", false}, {"II", false},
            {"=", false}, {"&", false}, {"*", false}, {"&", false}, {"-", false}, {"&", false}, {"=", false}, {"&", false}, {"-", false},
            {"XXVIII", false}, {"&", false}, {"III", false}, {"&", false}, {"IV", false}, {"&", false}, {"XII", true}, {"&", false}, {"XIII", false},
            {"&", false}, {"XIV", false}, {"-", true}, {"IV", false}, {"=", false}, {"X", false}, {"&", false}, {"&", false}, {"-", true},
            {"&", false}, {"&", false}, {"II", false}, {"&", false}, {"VIII", false}, {"&", false}, {"&", false}, {"&", false}, {"VIII", false},
            {"&", false}, {"&", false}, {"=", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"=", true},
            {"II", true}, {"+", false}, {"VII", true}, {"=", false}, {"IX", false}, {"&", false}, {"&", false}, {"&", false}, {"IX", false}
    };
    private Object[][] currentCrucigrama;

    // Inicia la aplicación JavaFX llamando al método launch
    public static void main(String[] args) {
        launch(args);
    }

    // Este método se llama automáticamente al iniciar la aplicación. Configura la interfaz gráfica, incluyendo un
    // ChoiceBox para seleccionar entre los dos crucigramas disponibles, un GridPane para mostrar las celdas del
    // crucigrama seleccionado y un botón "Verificar" para comprobar las respuestas
    @Override
    public void start(Stage primaryStage) {
        // Crear un ChoiceBox para seleccionar el crucigrama
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Escoge crucigrama", "Crucigrama 1", "Crucigrama 2");
        choiceBox.setValue("Escoge crucigrama"); // Valor inicial

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);

        // Crear y configurar las etiquetas y campos de texto para el crucigrama seleccionado
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            root.getChildren().clear(); // Limpiar el GridPane anterior
            if (newValue.equals("Crucigrama 1")) {
                currentCrucigrama = crucigrama1;
            } else if (newValue.equals("Crucigrama 2")) {
                currentCrucigrama = crucigrama2;
            }
            crearCrucigrama(root, currentCrucigrama);
        });

        // Botón de verificación
        Button verificarButton = new Button("Verificar");
        verificarButton.setOnAction(e -> verificarRespuestas());

        // Contenedor para el botón y el choiceBox
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(choiceBox, root, verificarButton);

        Scene scene = new Scene(vbox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crucigrama");
        primaryStage.show();
    }

    // Método auxiliar para crear un crucigrama en el GridPane dado
    // Este método construye dinámicamente el crucigrama seleccionado en el GridPane. Crea TextField para las
    // celdas ocultas y Label para las letras visibles.
    private void crearCrucigrama(GridPane root, Object[][] crucigrama) {
        textFieldPositions.clear();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String valor = (String) crucigrama[i * 9 + j][0];
                boolean oculto = (boolean) crucigrama[i * 9 + j][1];
                if (oculto) {
                    TextField textField = new TextField();
                    textField.setPrefWidth(50);
                    textField.setAlignment(Pos.CENTER);
                    root.add(textField, j, i);
                    textFieldPositions.put(textField, new int[]{i, j});
                } else {
                    Label label = new Label(valor);
                    label.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
                    label.setPrefSize(50, 50);
                    label.setAlignment(Pos.CENTER);
                    if (!valor.equals("&")) root.add(label, j, i);


                }
            }
        }
    }


    // Este método verifica las respuestas ingresadas por el usuario comparando las letras ingresadas en los TextField
    // con las letras correctas del crucigrama. Cambia el color de fondo del TextField según si la respuesta es
    // correcta o incorrecta.
    private void verificarRespuestas() {
        boolean allCorrect = true;
        for (Map.Entry<TextField, int[]> entry : textFieldPositions.entrySet()) {
            TextField textField = entry.getKey();
            int[] position = entry.getValue();
            String expectedValue = (String) currentCrucigrama[position[0] * 9 + position[1]][0];
            String userInput = textField.getText();

            if (!expectedValue.equals(userInput)) {
                textField.setStyle("-fx-background-color: red;");
                allCorrect = false;
            } else {
                textField.setStyle("-fx-background-color: lightgreen;");
            }
        }
        if (allCorrect) {
            showAlert("¡FELICITACIONES Todas las respuestas son correctas!");
        } else {
            showAlert("Algunas respuestas son incorrectas. Inténtalo de nuevo.");
        }
    }

    // Método auxiliar para mostrar un cuadro de diálogo emergente con un mensaje
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
