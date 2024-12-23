package model;

import java.io.*;

public record State<K extends Comparable<K>, P extends Comparable<P>, V>(Treap<K, P, V> treap, Treap<K, P, V>.Node node) {

    public State(Treap<K, P, V> treap, Treap<K, P, V>.Node node) {
        this.treap = (Treap<K, P, V>) deepCopy(treap);
        this.node = (Treap<K, P, V>.Node) deepCopy(node);
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
