package model;

import example.towns.visuals.TreapPanel;

import java.io.Serializable;

public abstract class Treap<K extends Comparable<K>, P extends Comparable<P>> implements Serializable {
    private Node<K, P> root;

    public Treap() {
        this.root = null;
    }

    public Node<K, P> getRoot() {
        return root;
    }

    public void insertNode(Node<K, P> element, TreapPanel<K, P> panel) {
        // render
        panel.enqueue(this, element);

        root = insert(root, element, panel);
    }

    public Node<K, P> insert(Node<K, P> root, Node<K, P> element, TreapPanel<K, P> panel) {
        if (root == null) return element;

        // render
        panel.enqueue(this, root);

        if (element.getKey().compareTo(root.getKey()) < 0) {
            root.setLeft(insert(root.getLeft(), element, panel));

            // render
            panel.enqueue(this, element);

            if (root.getLeft().getPriority().compareTo(root.getPriority()) < 0) {
                root = rotateRight(root);
            }
        } else {
            root.setRight(insert(root.getRight(), element, panel));

            // render
            panel.enqueue(this, element);

            if (root.getRight().getPriority().compareTo(root.getPriority()) < 0) {
                root = rotateLeft(root);
            }
        }

        // render
        panel.enqueue(this, root);

        return root;
    }

    private Node<K, P> rotateRight(Node<K, P> root) {
        Node<K, P> left = root.getLeft();
        Node<K, P> right = left.getRight();

        left.setRight(root);
        root.setLeft(right);

        return left;
    }

    private Node<K, P> rotateLeft(Node<K, P> root) {
        Node<K, P> right = root.getRight();
        Node<K, P> left = right.getLeft();

        right.setLeft(root);
        root.setRight(left);

        return right;
    }

    public void removeNode(K key, TreapPanel<K, P> panel) {
        root = remove(root, key, panel);

        // render
        panel.enqueue(this, root);
    }

    private Node<K, P> remove(Node<K, P> root, K key, TreapPanel<K, P> panel) {
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

    public Node<K, P> findNode(K key, TreapPanel<K, P> panel) {
        return find(root, key, panel);
    }

    private Node<K, P> find(Node<K, P> root, K key, TreapPanel<K, P> panel) {
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

    private int countNodes(Node<K, P> root) {
        if (root == null) return 0;
        return 1 + countNodes(root.getLeft()) + countNodes(root.getRight());
    }

    public int height() {
        return heightOf(root);
    }

    private int heightOf(Node<K, P> node) {
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
}