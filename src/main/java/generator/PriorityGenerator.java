package generator;

import java.io.Serializable;
import java.util.Random;

public class PriorityGenerator<T> implements Serializable {

    private final Random random = new Random();
    private final Class<T> c;
    private Integer bound;

    public PriorityGenerator(Class<T> c, Integer bound) {
        this.c = c;
        this.bound = bound;
    }

    private Integer randomInteger() {
        if (bound != null) return random.nextInt(bound);
        return random.nextInt();
    }

    private Double randomDouble() {
        if (bound != null) return random.nextDouble(bound);
        return random.nextDouble();
    }

    private String randomString() {
        if(bound == null) bound = 5;
        StringBuilder stringBuilder = new StringBuilder(bound);
        for (int i = 0; i < bound; i++) {
            int asciiValue = random.nextInt(128);
            stringBuilder.append((char) asciiValue);
        }
        return stringBuilder.toString();
    }

    public T randomPriority() {
        if (c.equals(Integer.class)) {
            return (T) randomInteger();
        } else if (c.equals(Double.class)) {
            return (T) randomDouble();
        } else if (c.equals(String.class)) {
            return (T) randomString();
        } else {
            throw new IllegalArgumentException("Unsupported type for priority generation: " + c);
        }
    }
}
