package example.dates;

import misc.Printer;
import model.Treap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExperimentApp {
    final static int EXPERIMENT_COUNT = 10000;

    public static void main(String[] args) {
        Treap<Date, Integer> treap = new Treap<>();

        int sumHeight = 0;
        int maxHeight = Integer.MIN_VALUE;
        int minHeight = Integer.MAX_VALUE;
        int[] heights = new int[EXPERIMENT_COUNT];
        int[] heightCounts = new int[EXPERIMENT_COUNT];
        int mode = -1;
        int maxCount = Integer.MIN_VALUE;

        for (int i = 0; i < EXPERIMENT_COUNT; i++) {
            List<Date> data = readDataFromFile();
            List<Treap<Date, Integer>.Node> nodes = treap.generateNodes(data);
            for (Treap<Date, Integer>.Node node : nodes) {
                treap.insertNode(node, null);
            }

            int height = treap.height();
            heights[i] = height;
            sumHeight += height;
            maxHeight = Math.max(maxHeight, height);
            minHeight = Math.min(minHeight, height);
            heightCounts[height]++;
            if (heightCounts[height] > maxCount) {
                maxCount = heightCounts[height];
                mode = height;
            }

            treap.clear();
        }

        double averageHeight = (double) sumHeight / EXPERIMENT_COUNT;

        System.out.println("Cumulative averages:");
        double cumulativeSum = 0;
        for (int i = 0; i < EXPERIMENT_COUNT; i++) {
            cumulativeSum += heights[i];
            System.out.println("Experiment " + (i + 1) + ": " + cumulativeSum / (i + 1));
        }
        System.out.println("Average: " + Printer.formatGreen(String.valueOf(averageHeight)));
        System.out.println("Maximum: " + Printer.formatRed(String.valueOf(maxHeight)));
        System.out.println("Minimum: " + Printer.formatRed(String.valueOf(minHeight)));
        System.out.println("Mode: " + Printer.formatBlue(String.valueOf(mode)));
    }

    private static List<Date> readDataFromFile() {
        List<Date> data = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/random_dates.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Date date = sdf.parse(line);
                    data.add(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
