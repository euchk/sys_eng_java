package my_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ui_elements.ScreenPoint;

public class Paths {
    // Static methods for creating paths for invaders
    // Each path is predetermined but a random offset is added to avoid collision between invaders

    private static int generateRandomOffset() {
        Random random = new Random();
        return random.nextInt(121) - 60; // Random value between -60 and 60
    }

    private static List<ScreenPoint> applyRandomOffset(List<ScreenPoint> baseWaypoints) {
        int offsetX = generateRandomOffset();
        int offsetY = generateRandomOffset();

        List<ScreenPoint> randomizedWaypoints = new ArrayList<>();
        for (ScreenPoint point : baseWaypoints) {
            randomizedWaypoints.add(new ScreenPoint(point.x + offsetX, point.y + offsetY));
        }
        return randomizedWaypoints;
    }

    public static Path levelOnePath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(1900, 100),
            new ScreenPoint(1200, 200),
            new ScreenPoint(1100, 100),
            new ScreenPoint(900, 70),
            new ScreenPoint(800, 200),
            new ScreenPoint(500, 300),
            new ScreenPoint(400, 400),
            new ScreenPoint(800, 600),
            new ScreenPoint(1100, 700)
        );

        List<ScreenPoint> randomizedWaypoints = applyRandomOffset(baseWaypoints);
        return new Path(randomizedWaypoints);
    }

    public static Path levelTwoPath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(1800, 250),
            new ScreenPoint(1440, 280),
            new ScreenPoint(1430, 330),
            new ScreenPoint(920, 350),
            new ScreenPoint(900, 280),
            new ScreenPoint(470, 250),
            new ScreenPoint(400, 310),
            new ScreenPoint(50, 350) 
        );

        return new Path(applyRandomOffset(baseWaypoints));
    }

    public static Path levelThreePath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(1900, 540),
            new ScreenPoint(1500, 520),
            new ScreenPoint(1510, 610),
            new ScreenPoint(1110, 640),
            new ScreenPoint(1130, 530),
            new ScreenPoint(610, 500),
            new ScreenPoint(600, 620),
            new ScreenPoint(100, 600)
        );

        return new Path(applyRandomOffset(baseWaypoints));
    }

    public static Path levelFourPath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(1750, 400),
            new ScreenPoint(1350, 400),
            new ScreenPoint(1350, 500),
            new ScreenPoint(850, 500),
            new ScreenPoint(850, 400),
            new ScreenPoint(350, 400),
            new ScreenPoint(350, 500),
            new ScreenPoint(0, 500)
        );

        return new Path(applyRandomOffset(baseWaypoints));
    }

    public static Path levelFivePath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(2100, 350),
            new ScreenPoint(1700, 350),
            new ScreenPoint(1700, 450),
            new ScreenPoint(1200, 450),
            new ScreenPoint(1200, 350),
            new ScreenPoint(700, 350),
            new ScreenPoint(700, 450),
            new ScreenPoint(200, 450)
        );

        return new Path(applyRandomOffset(baseWaypoints));
    }

    public static Path testPath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(1500, 350),
            new ScreenPoint(1000, 200),
            new ScreenPoint(0, 350)
        );

        return new Path(applyRandomOffset(baseWaypoints));
    }
}
