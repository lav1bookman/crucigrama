package org.example;

// Paquete y Imports: Se importan las clases necesarias de JavaFX para construir la interfaz gráfica y los
// elementos de la aplicación.
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox; // Asegúrate de importar ChoiceBox desde javafx.scene.control
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Clase Crucigrama: Extiende Application de JavaFX, lo que permite construir una interfaz gráfica con el método start.
public class Crucigrama extends Application {

    // Definición de crucigramas y matrices de ocultación
    private static char[][] crucigrama1 = {
            {'1', '+', '2', '=', ' ', 'r', 'i', 'l', 'o'},
            {'a', 'r', 'b', 'o', 'l', 'e', 'd', 'a', 'l'},
            {'l', 'a', 'g', 'u', 'n', 'a', 'r', 'i', 'a'},
            {'r', 'e', 'f', 'l', 'e', '-', 'o', 's', 'o'},
            {'a', 'n', 'i', '+', 'l', 'o', 's', 'o', 's'},
            {'n', 'e', 'v', 'a', 'd', 'o', 's', 'o', 'n'},
            {'m', 'a', 'd', 'e', 'r', 'e', 'r', 'a', 'l'},
            {'a', 'd', 'o', 'r', 'n', 'o', 's', 'e', 'r'},
            {'l', 'o', 'c', 'o', 'c', 'e', 'n', 'a', 'd'}
    };

    private static boolean[][] ocultas1 = {
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, true, false, true, false, false, false},
            {false, false, false, false, false, false, false, true, false},
            {false, false, false, false, false, false, false, false, true},
            {false, false, false, false, false, false, false, false, false}
    };

    private static char[][] crucigrama2 = {
            {'X', 'I', '-', 'I', 'I', '=', 'I', 'X', 'r'},
            {'o', 'l', 'i', 'v', 'a', 'r', 'e', 'r', 'o'},
            {'a', 'l', 'f', 'a', 'r', 'e', 'r', 'a', 's'},
            {'n', 'e', 'g', 'o', 'c', 'i', 'o', 's', 'o'},
            {'t', 'r', 'a', 'b', 'a', 'j', 'a', 'd', 'o'},
            {'a', 'r', 'm', 'a', 'd', 'o', 'r', 'o', 's'},
            {'l', 'e', 'g', 'a', 'd', 'o', 's', 'o', 's'},
            {'a', 'b', 's', 't', 'e', 'n', 'e', 'r', 'm'},
            {'m', 'e', 'm', 'o', 'r', 'a', 'n', 'd', 'o'}
    };

    private static boolean[][] ocultas2 = {
            {false, false, false, false, false, false, true, true, false},
            {false, false, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, true, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false}
    };

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
                crearCrucigrama(root, crucigrama1, ocultas1);
            } else if (newValue.equals("Crucigrama 2")) {
                crearCrucigrama(root, crucigrama2, ocultas2);
            }
        });

        // Botón de verificación
        Button verificarButton = new Button("Verificar");
        verificarButton.setOnAction(event -> {
            if (choiceBox.getValue().equals("Crucigrama 1")) {
                verificarRespuestasParaCrucigrama(root, crucigrama1, ocultas1);
            } else if (choiceBox.getValue().equals("Crucigrama 2")) {
                verificarRespuestasParaCrucigrama(root, crucigrama2, ocultas2);
            }
        });

        // Contenedor para el botón y el choiceBox
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(choiceBox, root, verificarButton);

        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crucigrama");
        primaryStage.show();
    }

    // Método auxiliar para crear un crucigrama en el GridPane dado
    // Este método construye dinámicamente el crucigrama seleccionado en el GridPane. Crea TextField para las
    // celdas ocultas y Label para las letras visibles.
    private void crearCrucigrama(GridPane root, char[][] crucigrama, boolean[][] ocultas) {
        for (int i = 0; i < crucigrama.length; i++) {
            for (int j = 0; j < crucigrama[i].length; j++) {
                if (ocultas[i][j]) {
                    TextField textField = new TextField();
                    textField.setPrefWidth(30);
                    textField.setAlignment(Pos.CENTER);
                    root.add(textField, j, i);
                } else {
                    Label label = new Label(String.valueOf(crucigrama[i][j]));
                    label.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
                    label.setPrefSize(30, 30);
                    label.setAlignment(Pos.CENTER);
                    root.add(label, j, i);
                }
            }
        }
    }

    // Método para verificar las respuestas ingresadas por el usuario
    private void verificarRespuestas(GridPane root, char[][] crucigrama1, boolean[][] ocultas1, char[][] crucigrama2, boolean[][] ocultas2) {
        verificarRespuestasParaCrucigrama(root, crucigrama1, ocultas1);
        verificarRespuestasParaCrucigrama(root, crucigrama2, ocultas2);
    }

    // Este método verifica las respuestas ingresadas por el usuario comparando las letras ingresadas en los TextField
    // con las letras correctas del crucigrama. Cambia el color de fondo del TextField según si la respuesta es
    // correcta o incorrecta.
    private void verificarRespuestasParaCrucigrama(GridPane root, char[][] crucigrama, boolean[][] ocultas) {
        for (int i = 0; i < crucigrama.length; i++) {
            for (int j = 0; j < crucigrama[i].length; j++) {
                if (ocultas[i][j]) {
                    TextField textField = (TextField) getNodeFromGridPane(root, j, i);
                    if (textField != null) {
                        String valor = textField.getText().trim();
                        if (!valor.isEmpty()) {
                            char letraAdivinada = valor.charAt(0);
                            char letraCorrecta = crucigrama[i][j];
                            if (letraAdivinada == letraCorrecta) {
                                ocultas[i][j] = false; // Revelar la letra en la matriz
                                textField.setStyle("-fx-background-color: lightgreen;");
                            } else {
                                textField.setStyle("-fx-background-color: salmon;");
                            }
                        }
                    }
                }
            }
        }
    }

    // Método auxiliar para obtener un nodo específico desde un GridPane
    // Este método auxiliar busca y devuelve un nodo específico (en este caso, un TextField o Label) dentro del
    // GridPane dadas las coordenadas de columna y fila.
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
