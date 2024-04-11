package generator;

import java.io.Serializable;
import java.util.Random;

public class PriorityGenerator<T> implements Serializable {

    private final Random random = new Random();
    private final Class<T> c;
    private final T bound;

    public PriorityGenerator(Class<T> c, T bound) {
        this.c = c;
        this.bound = bound;
    }

    private Integer randomInteger() {
        return random.nextInt((Integer) bound);
    }

    private Double randomDouble() {
        return random.nextDouble((Double) bound);
    }

    public T randomPriority() {
        if (c.equals(Integer.class)) {
            return (T) randomInteger();
        } else if (c.equals(Double.class)) {
            return (T) randomDouble();
        } else {
            throw new IllegalArgumentException("Unsupported type for priority generation: " + c);
        }
    }
}
