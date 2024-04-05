package example.towns;

import model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TownGenerator<K extends Comparable<K>, P extends Comparable<P>> {
    private final Random random = new Random();

    private final String[] towns15 = {"Adamov",
                                      "Brno",
                                      "Praha",
                                      "Děčín",
                                      "Frýdek_Místek",
                                      "Hradec_Králové",
                                      "Chomutov",
                                      "Jičín",
                                      "Kladno",
                                      "Liberec",
                                      "Olomouc",
                                      "Plzeň",
                                      "Ostrava",
                                      "Pardubice",
                                      "Vysoké_Mýto"
    };

    private int randomPriority() {
        return random.nextInt(100);
    }

    public Node<K, P> generateNode(K key, Class<K> keyClass, Class<P> priorityClass) {
        return new Node<>(keyClass.cast(key), priorityClass.cast(randomPriority()));
    }

    public List<Node<K, P>> generateNodes(Class<K> keyClass, Class<P> priorityClass) {
        return generateTowns(keyClass, priorityClass);
    }

    private List<Node<K, P>> generateTowns(Class<K> keyClass, Class<P> priorityClass) {
        List<Node<K, P>> nodes = new ArrayList<>();
        for (String townName : towns15) {
            nodes.add(new Node<>(keyClass.cast(townName), priorityClass.cast(randomPriority())));
        }
        Collections.shuffle(nodes);
        return nodes;
    }
}
