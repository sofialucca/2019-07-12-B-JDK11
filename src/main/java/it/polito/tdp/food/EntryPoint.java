package it.polito.tdp.food;

import javafx.application.Application;

import it.polito.tdp.food.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Food.fxml"));
		BorderPane root = loader.load();
		FoodController controller = loader.getController();
		Model model = new Model();
		controller.setModel(model);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
