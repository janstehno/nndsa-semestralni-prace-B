package model;

import java.io.*;

public record State<K extends Comparable<K>, P extends Comparable<P>>(Treap<K, P> treap, Node<K, P> node) {

    public State(Treap<K, P> treap, Node<K, P> node) {
        this.treap = (Treap<K, P>) deepCopy(treap);
        this.node = (Node<K, P>) deepCopy(node);
    }

    private Object deepCopy(Object original) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(original);
            out.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
