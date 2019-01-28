package ch.fhnw.swc.mrs;

import ch.fhnw.swc.mrs.api.MRSServices;
import ch.fhnw.swc.mrs.data.SimpleMRSServices;
import ch.fhnw.swc.mrs.view.MRSController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Main class of the Movie Rental System App.
 */
public class MovieRentalSystem extends Application {

    private MRSServices backend = new SimpleMRSServices();

    @Override
    public void start(Stage primaryStage) {
        backend.init();
        primaryStage.setTitle("Software Construction Lab");

        try {
            // Load gui content
            FXMLLoader loader = new FXMLLoader(MRSController.class.getResource("MRS.fxml"));

            // Show the scene containing the root layout.
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();

            MRSController controller = loader.getController();
            controller.initTabs(backend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method to start the app.
     *
     * @param args currently ignored.
     * @throws Exception whenever something goes wrong.
     */
    public static void main(String[] args) throws Exception {
        loadPriceCategories();
        launch(args);
    }

    private static void loadPriceCategories() throws Exception {
        URI uri = MovieRentalSystem.class.getClassLoader().getResource("data/pricecategories.config").toURI();

        if ("jar".equals(uri.getScheme())) {
            for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
                if (provider.getScheme().equalsIgnoreCase("jar")) {
                    try {
                        provider.getFileSystem(uri);
                    } catch (FileSystemNotFoundException e) {
                        // intialize first as empty
                        provider.newFileSystem(uri, Collections.emptyMap());
                    }
                }
            }
        }

        try (Stream<String> stream = Files.lines(Paths.get(uri))) {
            stream.forEach(x -> {
                try {
                    Class.forName(x);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
