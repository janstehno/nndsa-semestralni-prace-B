package example.towns;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.State;
import model.Treap;

import java.util.*;

public class TreapPanel<K extends Comparable<K>, P extends Comparable<P>> {

    public Pane root = new Pane();
    public final int width = 1240;
    public int height = 600;

    private final TreapRenderer<K, P> renderer;
    private final LinkedList<State<K, P>> renderQueue = new LinkedList<>();

    private final Treap<K, P> treap;
    private List<Treap<K, P>.Node> nodes;
    private final List<K> keys;

    private State<K, P> state;

    private TextField removeText;

    public TreapPanel(Treap<K, P> treap, List<K> keys) {
        this.treap = treap;
        this.renderer = new TreapRenderer<>();
        this.keys = keys;
        this.nodes = treap.generateNodes(keys);
        initializeUI();
    }

    public void initializeUI() {
        root.getChildren().add(generateButton());
        root.getChildren().add(allButton());
        root.getChildren().add(nextButton());
        if (state != null) root.getChildren().add(stepText());
        if (state != null) root.getChildren().add(infoText());
        if (treap.height() != -1) initializeTextField();
    }

    public Pane getRoot() {
        return root;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void enqueue(Treap<K, P> treap, Treap<K, P>.Node node) {
        State<K, P> copy = new State<>(treap, node);
        renderQueue.add(copy);
    }

    public State<K, P> dequeue() {
        return renderQueue.poll();
    }

    public void draw() {
        root.getChildren().clear();
        if (!renderQueue.isEmpty()) {
            state = dequeue();
            renderer.draw(state, this);
        }
        initializeUI();
    }

    private void insertNodes() {
        for (Treap<K, P>.Node node : nodes) {
            treap.insertNode(node, this);
        }
        initializeUI();
    }

    private void insertNode(K key) {
        treap.insertNode(treap.generateNode(key), this);
        initializeUI();
    }

    private void removeNode(K key) {
        treap.removeNode(key, this);
        initializeUI();
    }

    private void findNode(K key) {
        Treap<K, P>.Node found = treap.findNode(key, this);
        initializeUI();
    }

    private Button generateButton() {
        Button generateButton = new Button("Generate");
        generateButton.setMinHeight(30);
        generateButton.setMaxHeight(30);
        generateButton.setMinWidth(100);
        generateButton.setMaxWidth(100);
        generateButton.setOnAction(event -> {
            root.getChildren().clear();
            this.nodes = treap.generateNodes(keys);
            this.renderQueue.clear();
            this.treap.clear();
            this.state = null;
            insertNodes();
            draw();
        });
        return generateButton;
    }

    private Button allButton() {
        Button allButton = new Button("Everything");
        allButton.setMinHeight(30);
        allButton.setMaxHeight(30);
        allButton.setMinWidth(100);
        allButton.setMaxWidth(100);
        allButton.setTranslateY(35);
        allButton.setOnAction(event -> {
            while (!renderQueue.isEmpty()) draw();
        });
        return allButton;
    }

    private Button nextButton() {
        Button nextButton = new Button("Next Step");
        nextButton.setMinHeight(30);
        nextButton.setMaxHeight(30);
        nextButton.setMinWidth(100);
        nextButton.setMaxWidth(100);
        nextButton.setTranslateY(70);
        nextButton.setOnAction(event -> {
            if (!renderQueue.isEmpty()) draw();
        });
        return nextButton;
    }

    private Text stepText() {
        StringBuilder string = new StringBuilder();
        string.append("Step ").append(renderQueue.size()).append(": ");
        if ((state.node() != null && !renderQueue.isEmpty())) {
            string.append(state.node().getKey().toString()).append(" (").append(state.node().getPriority().toString()).append(")");
        } else {
            string.append("-");
        }
        Text stepText = new Text(string.toString());
        stepText.setTranslateY(115);
        return stepText;
    }

    private Text infoText() {
        String string = "Height: " + state.treap().height() + ", Nodes: " + state.treap().numberOfNodes();
        Text infoText = new Text(string);
        infoText.setTranslateY(135);
        return infoText;
    }

    private void initializeTextField() {
        removeText = new TextField();
        removeText.setPromptText("Enter key");
        removeText.setMinHeight(30);
        removeText.setMaxHeight(30);
        removeText.setMinWidth(100);
        removeText.setMaxWidth(100);
        removeText.setTranslateY(210);
        root.getChildren().add(removeText);
        root.getChildren().add(addButton());
        root.getChildren().add(removeButton());
        root.getChildren().add(findButton());
    }

    private Button addButton() {
        Button addButton = new Button("Add");
        addButton.setMinHeight(30);
        addButton.setMaxHeight(30);
        addButton.setMinWidth(100);
        addButton.setMaxWidth(100);
        addButton.setTranslateY(245);
        addButton.setOnAction(event -> {
            root.getChildren().clear();
            this.renderQueue.clear();
            this.state = null;
            String keyInput = removeText.getText();
            if (!keyInput.isEmpty()) {
                insertNode((K) keyInput);
            }
            draw();
        });
        return addButton;
    }

    private Button removeButton() {
        Button removeButton = new Button("Remove");
        removeButton.setMinHeight(30);
        removeButton.setMaxHeight(30);
        removeButton.setMinWidth(100);
        removeButton.setMaxWidth(100);
        removeButton.setTranslateY(280);
        removeButton.setOnAction(event -> {
            root.getChildren().clear();
            this.renderQueue.clear();
            this.state = null;
            String keyInput = removeText.getText();
            if (!keyInput.isEmpty()) {
                removeNode((K) keyInput);
            }
            draw();
        });
        return removeButton;
    }

    private Button findButton() {
        Button findButton = new Button("Find");
        findButton.setMinHeight(30);
        findButton.setMaxHeight(30);
        findButton.setMinWidth(100);
        findButton.setMaxWidth(100);
        findButton.setTranslateY(315);
        findButton.setOnAction(event -> {
            root.getChildren().clear();
            this.renderQueue.clear();
            this.state = null;
            String keyInput = removeText.getText();
            if (!keyInput.isEmpty()) {
                findNode((K) keyInput);
            }
            draw();
        });
        return findButton;
    }
}
