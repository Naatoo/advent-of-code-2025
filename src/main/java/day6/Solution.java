package day6;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        ThrashCompactor TC = new ThrashCompactor();
        System.out.println("Part 1: " + TC.part1());
        System.out.println("Part 2: " + TC.part2());
    }
}


class ThrashCompactor {
    private final Path filePath = Path.of("src/main/java/day6/input.txt");
    private final static int dataLines = 4;
    private List<String> signs;
    private ArrayList<Long> results;
    private long resultsRightToLeft = 0;


    public ThrashCompactor() {
        try {
            this.setDataPart1();
            this.setDataPart2();
        } catch (IOException e) {
            System.out.println(String.valueOf(e));
        }
    }

    private void setDataPart1() throws IOException {
        Scanner scanner = new Scanner(filePath);
        String lastLine = "";
        String first_line = scanner.nextLine().strip();
        while (scanner.hasNext()) {
            lastLine = scanner.nextLine();
        }
        this.signs = Arrays.stream(lastLine.split("\\s+")).toList();
        this.results = new ArrayList<>(Arrays.stream(first_line.split("\\s+")).map(Long::parseLong).toList());

        scanner = new Scanner(filePath);
        scanner.nextLine();
        for (int line = 0; line < dataLines - 2; line++) {
            String[] numbers = scanner.nextLine().strip().split(" +");
            for (int index = 0; index < numbers.length; index++) {
                long firstNumber = this.results.get(index);
                int secondNumber = Integer.parseInt(numbers[index]);
                this.results.set(index, this.signs.get(index).equals("+") ? Math.addExact(firstNumber, secondNumber) : Math.multiplyExact(firstNumber, secondNumber));
            }

        }

    }


    private void updateRes(ArrayList<Long> tempRes, int counter) {
        if (this.signs.get(counter).equals("+")) {
            this.resultsRightToLeft += tempRes.stream().reduce(0L, (x, y) -> x + y);
        } else {
            this.resultsRightToLeft += tempRes.stream().reduce(1L, (x, y) -> x * y);
        }
    }

    public void setDataPart2() throws IOException {
        Scanner scanner = new Scanner(filePath);
        ArrayList<String> lines = new ArrayList<>(IntStream.range(0, dataLines).mapToObj(i -> scanner.nextLine()).toList());

        ArrayList<Long> tempRes = new ArrayList<>();
        int counter = 0;
        for (int column = 0; column < lines.stream().map(String::length).max(Integer::compare).get(); column++) {
            StringBuilder columnString = new StringBuilder();
            for (int row = 0; row < lines.size(); row++) {
                try {
                    columnString.append(lines.get(row).charAt(column));
                } catch (StringIndexOutOfBoundsException e) {
                    columnString.append(" ");
                }
            }
            if (columnString.toString().isBlank()) {
                this.updateRes(tempRes, counter);
                counter++;
                tempRes = new ArrayList<>();
            } else {
                long columnValue = Integer.parseInt(columnString.toString().strip());
                tempRes.add(columnValue);
            }
        }
        this.updateRes(tempRes, counter);
    }

    public long part1() {
        return this.results.stream().mapToLong(r -> r).sum();
    }

    public long part2() {
        return this.resultsRightToLeft;
    }


}


