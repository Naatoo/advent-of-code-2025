package day2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        GiftShop SE = new GiftShop();
        long[] results = SE.process();
        System.out.println("Part 1: " + results[0]);
        System.out.println("Part 2 " + results[1]);
    }
}


class IdRange {
    long firstId;
    long lastId;

    public IdRange(long firstId, long lastId) {
        this.firstId = firstId;
        this.lastId = lastId;
    }
}

class GiftShop {

    private final ArrayList<IdRange> data = new ArrayList<>();

    public GiftShop() {
        try {
            this.setData();

        } catch (IOException e) {
            System.out.println(String.valueOf(e));
        }
    }

    private void setData() throws IOException {
        Scanner scanner = new Scanner(Path.of("src/main/java/day2/input.txt"));
        String rawData = scanner.nextLine();
        for (String range : rawData.split(",")) {
            String[] rangePart = range.split("-");
            this.data.add(new IdRange(Long.parseLong(rangePart[0]), Long.parseLong(rangePart[1])));
        }
    }

    public long[] process() {
        long totalCount = 0;
        long totalCountPart2 = 0;
        for (IdRange range : this.data) {
            totalCount += this.countInvalid(range);
            totalCountPart2 += this.countInvalidPart2(range);
        }
        return new long[]{totalCount, totalCountPart2};
    }

    public long countInvalid(IdRange range) {
        long count = 0;
        for (long index = range.firstId; index <= range.lastId; index++) {
            String stringIndex = String.valueOf(index);
            long halfLength = stringIndex.length() / 2;
            if (stringIndex.length() % 2 == 0) {
                if (index % (Math.pow(10, halfLength)) == Math.floor(index / Math.pow(10, halfLength))) {
                    count += index;
                }
            }
        }
        return count;
    }

    public long countInvalidPart2(IdRange range) {
        long count = 0;
        for (long index = range.firstId; index <= range.lastId; index++) {
            String stringIndex = String.valueOf(index);
            if (stringIndex.length() == 1) {
                continue;
            }
            if (stringIndex.chars().distinct().count() == 1) {
                count += index;
                if (stringIndex.length() % 2 == 0) {
                    continue;
                }
            }

            if (stringIndex.length() == 2 || (stringIndex.length() % 2 != 0 && stringIndex.length() != 9)) {
                continue;
            }

            long halfLength = stringIndex.length() / 2;
            for (int number : new int[]{2, 3, 4, 5}) {
                if (halfLength >= number && stringIndex.length() % number == 0) {
                    long partial_count = this.countNested(stringIndex, number);
                    if (partial_count != 0) {
                        count += partial_count;
                        break;
                    }
                }
            }
        }
        return count;
    }


    private long countNested(String index, int number) {
        String firstOcc = index.substring(0, number);
        for (int i = 1; i < index.length() / number; i++) {
            String nextOcc = index.substring(i * number, i * number + number);
            if (!firstOcc.equals(nextOcc)) {
                return 0;
            }
        }
        return Long.parseLong(index);
    }
}
