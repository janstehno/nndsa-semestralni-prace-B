package example.towns.visuals;

import example.towns.Towns;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Panel extends Application {
    @Override
    public void start(Stage stage) {
        Towns treap = new Towns();
        TreapPanel<String, Integer> treapPanel = new TreapPanel<>(treap, String.class, Integer.class);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(treapPanel.getRoot());

        Scene scene = new Scene(scrollPane, treapPanel.getWidth(), treapPanel.getHeight());
        stage.setTitle("Treap");
        stage.setScene(scene);
        stage.show();
    }
}
