package day7;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        Laboratories L = new Laboratories();
        System.out.println("Part 1: " + L.part1());
        System.out.println("Part 2: " + L.part2());
    }
}


class Laboratories {
    private final Path filePath = Path.of(Objects.requireNonNull(getClass().getResource("input.txt")).getPath());
    public final String START_SIGN = "S";
    public final String SPLITTER_SIGN = "^";
    private int split = 0;
    private long possibilities = 0;

    private final HashMap<Integer, Long> splitPossibilities = new HashMap<>();
    private HashMap<Integer, Long> newPossibilities = new HashMap<>();

    public Laboratories() {
        try {
            this.setDataPart1();
            this.setDataPart2();
        } catch (IOException e) {
            System.out.println(String.valueOf(e));
        }
    }

    private void setDataPart1() throws IOException {
        Scanner scanner = new Scanner(filePath);
        HashSet<Integer> splittersIndexes = new HashSet<>(List.of(scanner.nextLine().indexOf(START_SIGN)));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            ArrayList<Integer> newIndexes = new ArrayList<>();
            IntStream.range(0, line.length()).filter(splittersIndexes::contains)
                    .forEach(i -> {
                        if (String.valueOf(line.charAt(i)).equals(SPLITTER_SIGN)) {
                            newIndexes.addAll(List.of(i - 1, i + 1));
                            split++;
                        } else {
                            newIndexes.add(i);
                        }
                    });
            splittersIndexes = new HashSet<>(newIndexes);
        }
    }

    private void setDataPart2() throws IOException {
        Scanner scanner = new Scanner(filePath);
        this.splitPossibilities.put(scanner.nextLine().indexOf(START_SIGN), 1L);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            this.newPossibilities.clear();
            IntStream.range(0, line.length()).filter(this.splitPossibilities.keySet()::contains)
                    .forEach(i -> {
                        if (String.valueOf(line.charAt(i)).equals(SPLITTER_SIGN)) {
                            updateNewPossibilities(i, i - 1);
                            updateNewPossibilities(i, i + 1);
                        } else {
                            updateNewPossibilities(i, i);
                        }
                    });
            this.splitPossibilities.clear();
            this.splitPossibilities.putAll(newPossibilities);
        }
        this.possibilities = splitPossibilities.values().stream().mapToLong(Long::longValue).sum();
    }

    private void updateNewPossibilities(int sourceIndex, int targetIndex) {
        this.newPossibilities.put(targetIndex, this.newPossibilities.getOrDefault(targetIndex, 0L) + this.splitPossibilities.get(sourceIndex));

    }

    public int part1() {
        return this.split;
    }

    public long part2() {
        return this.possibilities;
    }
}
