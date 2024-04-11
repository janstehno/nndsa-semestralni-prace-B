package model;

import example.towns.TreapPanel;
import generator.PriorityGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Treap<K extends Comparable<K>, P extends Comparable<P>, V> implements Serializable {
    private Node root;
    private final PriorityGenerator<P> generator;

    public Treap(Class<P> priorityClass, P priorityBound) {
        this.root = null;
        generator = new PriorityGenerator<>(priorityClass, priorityBound);
    }

    public Node getRoot() {
        return root;
    }

    public void insertNode(Node node, TreapPanel<K, P, V> panel) {
        // Krok insert pro grafické zobrazení
        if (panel != null) panel.enqueue(this, node);

        // Ošetření null prvku a vložení duplicitního klíče
        if (node != null && findNode(node.getKey(), null) != null) return;

        // Metoda insert, která po vložení prvku vrací kořen stromu
        root = insert(root, node, panel);
    }

    private Node insert(Node root, Node node, TreapPanel<K, P, V> panel) {
        // Pokud je strom nebo potomek prázdný, vrátí se prvek jako kořen nebo potomek
        if (root == null) return node;

        // Krok insert pro grafické zobrazení
        if (panel != null) panel.enqueue(this, root);

        // Porovnání klíčů vkládaného prvku a rodiče (vlastnost binárního stromu)
        if (node.getKey().compareTo(root.getKey()) < 0) {
            // Pokud je klíč vkládaného prvku menší, provede se rekurzivně insert na levého potomka
            root.setLeft(insert(root.getLeft(), node, panel));

            // Krok insert pro grafické zobrazení
            if (panel != null) panel.enqueue(this, node);

            // Po vložení prvku na správné místo
            // Porovnání priorit mezi vloženým prvkem a jeho rodičem (vlastnost haldy) pro každý rekurzivně volaný insert
            if (root.getLeft().getPriority().compareTo(root.getPriority()) < 0) {
                // Rotace vpravo, vrácení prvku jako rodiče
                root = rotateRight(root);
            }
        } else {
            // Pokud je klíč vkládaného prvku menší, provede se rekurzivně insert na pravého potomka
            root.setRight(insert(root.getRight(), node, panel));

            // Krok insert pro grafické zobrazení
            if (panel != null) panel.enqueue(this, node);

            // Po vložení prvku na správné místo
            // Porovnání priorit mezi vloženým prvkem a jeho rodičem (vlastnost haldy) pro každý rekurzivně volaný insert
            if (root.getRight().getPriority().compareTo(root.getPriority()) < 0) {
                // Rotace vlevo, vrácení prvku jako rodiče
                root = rotateLeft(root);
            }
        }

        // Krok insert pro grafické zobrazení
        if (panel != null) panel.enqueue(this, root);

        // Vrácení aktuálního rodiče pro rekurzivní volání
        return root;
    }

    public void removeNode(K key, TreapPanel<K, P, V> panel) {
        // Krok remove pro grafické zobrazení
        if (panel != null) panel.enqueue(this, root);

        // Metoda remove, která po smazání prvku vrací kořen stromu
        root = remove(root, key, panel);
    }

    private Node remove(Node root, K key, TreapPanel<K, P, V> panel) {
        // Pokud je strom nebo potomek prázdný, vrátí se null
        if (root == null) return null;

        // Krok remove pro grafické zobrazení
        if (panel != null) panel.enqueue(this, root);

        // Porovnání klíčů pro nalezení odstraňovaného prvku
        if (key.compareTo(root.getKey()) < 0) {
            // Pokud je klíč odstraňovaného prvku menší než rodiče, provede se rekurzivně remove na levého potomka
            root.setLeft(remove(root.getLeft(), key, panel));

            // Krok remove pro grafické zobrazení
            if (panel != null) panel.enqueue(this, root.getLeft());

        } else if (key.compareTo(root.getKey()) > 0) {
            // Pokud je klíč odstraňovaného prvku větší než rodiče, provede se rekurzivně remove na pravého potomka
            root.setRight(remove(root.getRight(), key, panel));

            // Krok remove pro grafické zobrazení
            if (panel != null) panel.enqueue(this, root.getRight());

        } else {
            // Pokud je klíč odstraňovaného prvku stejný jako rodiče, provede se remove rodiče

            // Krok remove pro grafické zobrazení
            if (panel != null) panel.enqueue(this, root);

            if (root.getLeft() == null) {
                // Pokud nemá odstraňovaný prvek levého potomka, vrátí se jeho pravý potomek, ten pak bude potomkem rodiče odstraňovaného prvku
                return root.getRight();
            } else if (root.getRight() == null) {
                // Pokud nemá odstraňovaný prvek pravého potomka, vrátí se jeho levý potomek, ten pak bude potomkem rodiče odstraňovaného prvku
                return root.getLeft();
            } else {
                // Pokud má odstraňovaný prvek pravého i levého potomka, provede se kontrola priorit mezi nimi
                if (root.getLeft().getPriority().compareTo(root.getRight().getPriority()) < 0) {
                    // Pokud je priorita levého potomka menší než pravého, provede se rotace vpravo
                    root = rotateRight(root);

                    // Krok remove pro grafické zobrazení
                    if (panel != null) panel.enqueue(this, root);

                    // Rekurzivní provedení remove na pravého potomka
                    root.setRight(remove(root.getRight(), key, panel));
                } else {
                    // Pokud je priorita pravého potomka menší než levého, provede se rotace levo
                    root = rotateLeft(root);

                    // Krok remove pro grafické zobrazení
                    if (panel != null) panel.enqueue(this, root);

                    // Rekurzivní provedení remove na levého potomka
                    root.setLeft(remove(root.getLeft(), key, panel));
                }
            }
        }

        // Krok remove pro grafické zobrazení
        if (panel != null) panel.enqueue(this, root);

        // Vrácení aktuálního rodiče pro rekurzivní volání
        return root;
    }

    private Node rotateRight(Node root) {
        // Levý potomek rodiče
        Node left = root.getLeft();
        // Pravý potomek rotovaného prvku
        Node right = left.getRight();

        // Pravý potomek rotovaného prvku bude rodič
        left.setRight(root);
        // Levý potomek rodiče bude pravý potomek rotovaného prvku
        root.setLeft(right);

        // Vrácení rotovaného prvku jako nového rodiče
        return left;
    }

    private Node rotateLeft(Node root) {
        // Pravý potomek rodiče
        Node right = root.getRight();
        // Levý potomek rotovaného prvku
        Node left = right.getLeft();

        // Levý potomek rotovaného prvku bude rodič
        right.setLeft(root);
        // Pravý potomek rodiče bude levý potomek rotovaného prvku
        root.setRight(left);

        // Vrácení rotovaného prvku jako nového rodiče
        return right;
    }

    public Node findNode(K key, TreapPanel<K, P, V> panel) {
        return find(root, key, panel);
    }

    private Node find(Node root, K key, TreapPanel<K, P, V> panel) {
        // Krok find pro grafické zobrazení
        if (panel != null) panel.enqueue(this, root);

        // Vrácení null (nenalezeno) nebo nalezeného klíče
        if (root == null || root.getKey().equals(key)) return root;

        if (key.compareTo(root.getKey()) < 0) return find(root.getLeft(), key, panel);
        return find(root.getRight(), key, panel);
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

    public Node generateNode(K key, V value) {
        return new Node(key, generator.randomPriority(), value);
    }

    public List<Node> generateNodes(Map<K, V> elements) {
        List<Node> nodes = new ArrayList<>();
        for (K key : elements.keySet()) {
            nodes.add(new Node(key, generator.randomPriority(), elements.get(key)));
        }
        Collections.shuffle(nodes);
        return nodes;
    }

    public class Node implements Serializable {
        private final K key;
        private final P priority;
        private V value;
        private Node left, right;

        public Node(K key, P priority, V value) {
            this.key = key;
            this.priority = priority;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public K getKey() {
            return key;
        }

        public P getPriority() {
            return priority;
        }

        public V getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}