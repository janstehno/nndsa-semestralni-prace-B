package example.towns;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import model.Treap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Panel extends Application {
    @Override
    public void start(Stage stage) {
        Treap<String, Integer> treap = new Treap<>();
        List<String> towns = new ArrayList<>(Arrays.asList("Brno", "Praha", "Děčín", "Hradec_Králové", "Liberec", "Olomouc"));
        TreapPanel<String, Integer> treapPanel = new TreapPanel<>(treap, towns);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(treapPanel.getRoot());

        Scene scene = new Scene(scrollPane, treapPanel.getWidth(), treapPanel.getHeight());
        stage.setTitle("Treap");
        stage.setScene(scene);
        stage.show();
    }
}
