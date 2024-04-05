package example.data;

import model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataGenerator<K extends Comparable<K>, P extends Comparable<P>> {

    public List<Node<K, P>> generateNodes(Class<K> keyClass, Class<P> priorityClass) {
        return generateTowns(keyClass, priorityClass);
    }

    private List<Node<K, P>> generateTowns(Class<K> keyClass, Class<P> priorityClass) {
        List<Node<K, P>> nodes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1023; i++) {
            int keyConstant = random.nextInt(100);
            int priority = random.nextInt(100);
            nodes.add(new Node<>(keyClass.cast(i * keyConstant), priorityClass.cast(priority)));
        }

        Collections.shuffle(nodes);
        return nodes;
    }
}
