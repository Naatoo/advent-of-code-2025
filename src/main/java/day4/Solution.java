package day4;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        PrintingDepartment SE = new PrintingDepartment();
        System.out.println("Part 1: " + SE.process());
        System.out.println("Part 2: " + SE.process());
    }
}


class PrintingDepartment {

    private final HashMap<String, PaperRoll> data = new HashMap<>();
    private int initialRolls;
    private static final String rollSign = "@";
    private int phase = 0;

    public PrintingDepartment() {
        try {
            this.setData();
            this.initialRolls = this.data.size();
        } catch (IOException e) {
            System.out.println(String.valueOf(e));
        }
    }

    private void setData() throws IOException {
        Scanner scanner = new Scanner(Path.of("src/main/java/day4/input.txt"));
        int row_index = 0;
        int column_index = 0;
        while (scanner.hasNext()) {
            for (String sign : scanner.nextLine().split("")) {
                if (sign.equals(rollSign)) {
                    this.data.put(row_index + "," + column_index, new PaperRoll(row_index, column_index));
                }
                column_index++;
            }
            column_index = 0;
            row_index++;
        }
    }

    public int process() {
        while (true) {
            List<int[]> arrayIndexes = this.data.values().stream().map(PaperRoll::getCoordsArray).toList();
            HashMap<String, PaperRoll> tempData = new HashMap<>(this.data);
            int accessible = 0;
            for (Map.Entry<String, PaperRoll> paperRoll : tempData.entrySet()) {
                if (paperRoll.getValue().getNearbyIndexes().stream().filter(p -> arrayIndexes.stream().anyMatch(a -> Arrays.equals(a, p))).count() < 4) {
                    this.data.remove(paperRoll.getValue().getCoordsString());
                    accessible++;
                }
            }
            this.phase++;
            if (this.phase == 1) {
                return accessible;  // Part 1
            }

            if (accessible == 0) {
                return this.initialRolls - tempData.size();   // Part 2
            }
        }
    }


    class PaperRoll {
        private final int[] coordsArray;
        private final String coordsString;
        private final ArrayList<int[]> nearbyIndexes;

        public PaperRoll(int rowIndex, int columnIndex) {
            this.coordsArray = new int[]{rowIndex, columnIndex};
            this.coordsString = rowIndex + "," + columnIndex;
            this.nearbyIndexes = this.setNearbyIndexes();
        }

        public int[] getCoordsArray() {
            return coordsArray;
        }

        public ArrayList<int[]> getNearbyIndexes() {
            return this.nearbyIndexes;
        }

        public String getCoordsString() {
            return coordsString;
        }

        public ArrayList<int[]> setNearbyIndexes() {
            ArrayList<int[]> nearby = new ArrayList<>();
            for (int i = this.coordsArray[0] - 1; i < this.coordsArray[0] + 2; i++) {
                for (int k = this.coordsArray[1] - 1; k < this.coordsArray[1] + 2; k++) {
                    if (i == this.coordsArray[0] && k == this.coordsArray[1]) {
                        continue;
                    }
                    nearby.add(new int[]{i, k});
                }
            }
            return nearby;
        }
    }
}
