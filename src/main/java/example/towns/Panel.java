package example.towns;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import model.Treap;

import java.util.*;

public class Panel extends Application {
    @Override
    public void start(Stage stage) {
        Map<String, String> towns = new HashMap<>(Map.of("Brno", "", "Praha", "", "Děčín", "", "Hradec_Králové", "", "Liberec", "", "Olomouc", ""));

        Treap<String, Integer, String> treap = new Treap<>(Integer.class, 100);
        TreapPanel<String, Integer, String> treapPanel = new TreapPanel<>(treap, towns);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(treapPanel.getRoot());

        Scene scene = new Scene(scrollPane, treapPanel.getWidth(), treapPanel.getHeight());
        stage.setTitle("Treap");
        stage.setScene(scene);
        stage.show();
    }
}
