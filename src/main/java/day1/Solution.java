package day1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        SecretEntrance SE = new SecretEntrance();
        System.out.println("Part 1: " + SE.part1());
        System.out.println("Part 2 " + SE.part2());
    }

}


class SecretEntrance {

    private final List<String> data = new ArrayList<>();
    final int totalDistance = 50;
    int distanceMin = 0;
    int distanceMax = 99;


    public SecretEntrance() {
        try {
            this.setData();

        } catch (IOException e) {
            System.out.println(String.valueOf(e));
        }
    }

    private void setData() throws IOException {
        Scanner scanner = new Scanner(Path.of(Objects.requireNonNull(getClass().getResource("input.txt")).getPath()));
        while (scanner.hasNext()) {
            this.data.add(scanner.nextLine());
        }
    }

    public int part1() {
        int rotateDistance = this.totalDistance;
        int pointingAtZero = 0;
        for (String elem : data) {
            char rotation = elem.charAt(0);
            int distance = Integer.parseInt(elem.substring(1));
            distance = distance > 99 ? distance % 100 : distance;
            if (rotation == 'R') {
                int tempDistance = rotateDistance + distance;
                rotateDistance = tempDistance > this.distanceMax ? tempDistance % 100 : tempDistance;
            } else if (rotation == 'L') {
                int tempDistance = rotateDistance - distance;
                rotateDistance = tempDistance < this.distanceMin ? 100 - Math.abs(tempDistance) : tempDistance;
            }
            if (rotateDistance == 0) {
                pointingAtZero++;
            }
        }
        return pointingAtZero;
    }

    public int part2() {
        int rotateDistance = this.totalDistance;
        int pointingAtZero = 0;
        for (String elem : data) {
            char rotation = elem.charAt(0);
            int distance = Integer.parseInt(elem.substring(1));
            if (distance > this.distanceMax) {
                pointingAtZero += Math.round(distance / 100);
                distance = distance % 100;
            }
            int tempDistance = 0;
            if (rotation == 'R') {
                tempDistance = rotateDistance + distance;
                if (tempDistance > this.distanceMax) {
                    rotateDistance = tempDistance % 100;
                    pointingAtZero++;
                } else {
                    rotateDistance = tempDistance;
                }
            } else if (rotation == 'L') {
                tempDistance = rotateDistance - distance;
                if (tempDistance < this.distanceMin) {
                    if (rotateDistance != 0) {
                        pointingAtZero++;
                    }
                    rotateDistance = 100 - Math.abs(tempDistance);

                } else {
                    rotateDistance = tempDistance;
                }
            }
            if (rotateDistance == 0 && (tempDistance % 100 != 0 || tempDistance == 0)) {
                pointingAtZero++;
            }
        }
        return pointingAtZero;
    }
}
