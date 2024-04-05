package example.towns.visuals;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Node;
import model.State;
import model.Treap;
import example.towns.TownGenerator;

import java.util.LinkedList;
import java.util.List;

public class TreapPanel<K extends Comparable<K>, P extends Comparable<P>> {

    public Pane root = new Pane();
    public final int width = 1240;
    public int height = 600;

    private final TownGenerator<K, P> generator = new TownGenerator<>();
    private final TreapRenderer<K, P> renderer;
    private final LinkedList<State<K, P>> renderQueue = new LinkedList<>();

    private final Treap<K, P> treap;
    private List<Node<K, P>> nodes;

    private final Class<K> keyClass;
    private final Class<P> priorityClass;

    private State<K, P> state;

    private TextField removeText;

    public TreapPanel(Treap<K, P> treap, Class<K> keyClass, Class<P> priorityClass) {
        this.treap = treap;
        this.renderer = new TreapRenderer<>();
        this.keyClass = keyClass;
        this.priorityClass = priorityClass;
        this.nodes = generator.generateNodes(keyClass, priorityClass);
        initializeUI();
    }

    public void initializeUI() {
        root.getChildren().add(generateButton());
        root.getChildren().add(allButton());
        root.getChildren().add(nextButton());
        if (state != null) root.getChildren().add(stepText());
        if (state != null) root.getChildren().add(heightText());
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

    public void enqueue(Treap<K, P> treap, Node<K, P> node) {
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
        for (Node<K, P> node : nodes) {
            treap.insertNode(node, this);
        }
        initializeUI();
    }

    private void removeNode(K key) {
        treap.removeNode(key, this);
        initializeUI();
    }

    private void findNode(K key) {
        treap.findNode(key, this);
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
            this.nodes = generator.generateNodes(keyClass, priorityClass);
            this.renderQueue.clear();
            this.treap.clear();
            this.state = null;
            insertNodes();
            draw();
        });
        return generateButton;
    }

    private Button allButton() {
        Button allButton = new Button("All");
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

    private Text heightText() {
        String string = "Height: " + state.treap().height();
        Text heightText = new Text(string);
        heightText.setTranslateY(135);
        return heightText;
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
        root.getChildren().add(removeButton());
        root.getChildren().add(findButton());
    }

    private Button removeButton() {
        Button removeButton = new Button("Remove");
        removeButton.setMinHeight(30);
        removeButton.setMaxHeight(30);
        removeButton.setMinWidth(100);
        removeButton.setMaxWidth(100);
        removeButton.setTranslateY(245);
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
        Button removeButton = new Button("Find");
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
                findNode((K) keyInput);
            }
            draw();
        });
        return removeButton;
    }
}
