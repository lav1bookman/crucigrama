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
            {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"I", true}, {"&", false}, {"&", false}, {"&", false},
            {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"&", false}, {"I", false}, {"XVI", false}, {"XVII", false}, {"XVIII", false},
            {"XIX", false}, {"*", false}, {"XXI", false}, {"x", false}, {"XXIII", false}, {"XXIV", false}, {"XXV", false}, {"XXVI", false}, {"XXVII", false},
            {"XXVIII", false}, {"*", false}, {"XXX", false}, {"=", false}, {"XXXII", false}, {"XXXIII", false}, {"XXXIV", false}, {"XXXV", false}, {"XXXVI", false},
            {"XXXVII", false}, {"*", false}, {"XXXIX", false}, {"XX", false}, {"XLI", false}, {"XLII", false}, {"XLIII", false}, {"XLIV", false}, {"XLV", false},
            {"XLVI", false}, {"", false}, {"XLVIII", false}, {"", false}, {"L", false}, {"I", true}, {"LII", false}, {"LIII", false}, {"LIV", false},
            {"LV", false}, {"LVI", false}, {"LVII", false}, {"*", false}, {"LIX", false}, {"LX", false}, {"LXI", false}, {"I", true}, {"LXIII", false},
            {"LXIV", false}, {"LXV", false}, {"LXVI", false}, {"LXVII", false}, {"LXVIII", false}, {"LXIX", false}, {"LXX", false}, {"LXXI", false}, {"I", true},
            {"LXXIII", false}, {"LXXIV", false}, {"LXXV", false}, {"LXXVI", false}, {"LXXVII", false}, {"LXXVIII", false}, {"LXXIX", false}, {"LXXX", false}, {"LXXXI", false}
    };

    private static Object[][] crucigrama2 = {
            {"LXXXII", true}, {"LXXXIII", false}, {"LXXXIV", false}, {"LXXXV", false}, {"LXXXVI", false}, {"LXXXVII", false}, {"LXXXVIII", false}, {"LXXXIX", false}, {"XC", false},
            {"XCI", false}, {"XCII", false}, {"XCIII", true}, {"XCIV", false}, {"XCV", false}, {"XCVI", false}, {"XCVII", false}, {"XCVIII", false}, {"XCIX", false},
            {"C", false}, {"CI", false}, {"CII", false}, {"CIII", false}, {"CIV", false}, {"CV", false}, {"CVI", false}, {"CVII", false}, {"CVIII", false},
            {"CIX", false}, {"CX", false}, {"CXI", false}, {"CXII", false}, {"CXIII", false}, {"CXIV", false}, {"CXV", false}, {"CXVI", false}, {"CXVII", false},
            {"CXVIII", false}, {"CXIX", false}, {"CXX", false}, {"CXXI", false}, {"CXXII", false}, {"CXXIII", false}, {"CXXIV", true}, {"CXXV", false}, {"CXXVI", false},
            {"CXXVII", false}, {"CXXVIII", false}, {"CXXIX", false}, {"CXXX", false}, {"CXXXI", false}, {"CXXXII", false}, {"CXXXIII", false}, {"CXXXIV", false}, {"CXXXV", false},
            {"CXXXVI", false}, {"CXXXVII", false}, {"CXXXVIII", false}, {"CXXXIX", false}, {"CXL", false}, {"CXLI", false}, {"CXLII", false}, {"CXLIII", false}, {"CXLIV", false},
            {"CXLV", false}, {"CXLVI", false}, {"CXLVII", false}, {"CXLVIII", false}, {"CXLIX", false}, {"CL", false}, {"CLI", false}, {"CLII", false}, {"CLIII", false},
            {"CLIV", false}, {"CLV", false}, {"CLVI", false}, {"CLVII", false}, {"CLVIII", false}, {"CLIX", false}, {"CLX", false}, {"CLXI", false}, {"CLXII", false}
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
