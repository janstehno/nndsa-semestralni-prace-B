package model;

import java.io.Serializable;

public class Node<K extends Comparable<K>, P extends Comparable<P>> implements Serializable {
    private K key;
    private P priority;
    private Node<K, P> left, right;

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

    public Node<K, P> getLeft() {
        return left;
    }

    public Node<K, P> getRight() {
        return right;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setPriority(P priority) {
        this.priority = priority;
    }

    public void setLeft(Node<K, P> left) {
        this.left = left;
    }

    public void setRight(Node<K, P> right) {
        this.right = right;
    }
}
