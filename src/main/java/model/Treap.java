package model;

import example.towns.TreapPanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Treap<K extends Comparable<K>, P extends Comparable<P>> implements Serializable {
    private Node root;

    public Treap() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insertNode(Node element, TreapPanel<K, P> panel) {
        // render
        if (panel != null) panel.enqueue(this, element);

        root = insert(root, element, panel);
    }

    private Node insert(Node root, Node element, TreapPanel<K, P> panel) {
        if (root == null) return element;

        // render
        if (panel != null) panel.enqueue(this, root);

        if (element.getKey().compareTo(root.getKey()) < 0) {
            root.setLeft(insert(root.getLeft(), element, panel));

            // render
            if (panel != null) panel.enqueue(this, element);

            if (root.getLeft().getPriority().compareTo(root.getPriority()) < 0) {
                root = rotateRight(root);
            }
        } else {
            root.setRight(insert(root.getRight(), element, panel));

            // render
            if (panel != null) panel.enqueue(this, element);

            if (root.getRight().getPriority().compareTo(root.getPriority()) < 0) {
                root = rotateLeft(root);
            }
        }

        // render
        if (panel != null) panel.enqueue(this, root);

        return root;
    }

    private Node rotateRight(Node root) {
        Node left = root.getLeft();
        Node right = left.getRight();

        left.setRight(root);
        root.setLeft(right);

        return left;
    }

    private Node rotateLeft(Node root) {
        Node right = root.getRight();
        Node left = right.getLeft();

        right.setLeft(root);
        root.setRight(left);

        return right;
    }

    public void removeNode(K key, TreapPanel<K, P> panel) {
        root = remove(root, key, panel);

        // render
        panel.enqueue(this, root);
    }

    private Node remove(Node root, K key, TreapPanel<K, P> panel) {
        if (root == null) return null;

        // render
        panel.enqueue(this, root);

        if (key.compareTo(root.getKey()) < 0) {
            root.setLeft(remove(root.getLeft(), key, panel));

            // render
            panel.enqueue(this, root.getLeft());

        } else if (key.compareTo(root.getKey()) > 0) {
            root.setRight(remove(root.getRight(), key, panel));

            // render
            panel.enqueue(this, root.getRight());

        } else {
            // render
            panel.enqueue(this, root);

            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            } else {
                if (root.getLeft().getPriority().compareTo(root.getRight().getPriority()) < 0) {
                    root = rotateRight(root);

                    // render
                    panel.enqueue(this, root);

                    root.setRight(remove(root.getRight(), key, panel));
                } else {
                    root = rotateLeft(root);

                    // render
                    panel.enqueue(this, root);

                    root.setLeft(remove(root.getLeft(), key, panel));
                }
            }
        }

        // render
        panel.enqueue(this, root);

        return root;
    }

    public Node findNode(K key, TreapPanel<K, P> panel) {
        return find(root, key, panel);
    }

    private Node find(Node root, K key, TreapPanel<K, P> panel) {
        // render
        panel.enqueue(this, root);

        if (root == null || root.getKey().equals(key)) return root;

        if (key.compareTo(root.getKey()) < 0) return find(root.getLeft(), key, panel);
        return find(root.getRight(), key, panel);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int numberOfNodes() {
        return countNodes(root);
    }

    private int countNodes(Node root) {
        if (root == null) return 0;
        return 1 + countNodes(root.getLeft()) + countNodes(root.getRight());
    }

    public int height() {
        return heightOf(root);
    }

    private int heightOf(Node node) {
        if (node == null) {
            return -1;
        } else {
            int leftHeight = heightOf(node.getLeft());
            int rightHeight = heightOf(node.getRight());

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public void clear() {
        root = null;
    }

    private Integer randomPriority(Random random) {
        return random.nextInt(100);
    }

    public Node generateNode(K key) {
        final Random random = new Random();
        return new Node(key, (P) randomPriority(random));
    }

    public List<Node> generateNodes(List<K> keys) {
        final Random random = new Random();
        List<Node> nodes = new ArrayList<>();
        for (K key : keys) {
            nodes.add(new Node(key, (P) randomPriority(random)));
        }
        Collections.shuffle(nodes);
        return nodes;
    }

    public class Node implements Serializable {
        private final K key;
        private final P priority;
        private Node left, right;

        public Node(K key, P priority) {
            this.key = key;
            this.priority = priority;
            this.left = null;
            this.right = null;
        }

        public K getKey() {
            return key;
        }

        public P getPriority() {
            return priority;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}