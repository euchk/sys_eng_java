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
        return random.nextInt(40) - 20;
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
            new ScreenPoint(-80, 99),
            new ScreenPoint(80, 99),
            new ScreenPoint(148, 179),
            new ScreenPoint(230, 238),
            new ScreenPoint(266, 192),
            new ScreenPoint(350, 157),
            new ScreenPoint(391, 106),
            new ScreenPoint(489, 98),
            new ScreenPoint(548, 159),
            new ScreenPoint(612, 191),
            new ScreenPoint(657, 263),
            new ScreenPoint(748, 307),
            new ScreenPoint(831, 259),
            new ScreenPoint(876, 178),
            new ScreenPoint(948, 147),
            new ScreenPoint(1018, 77),
            new ScreenPoint(1105, 126),
            new ScreenPoint(1198, 199),
            new ScreenPoint(1216, 283),
            new ScreenPoint(1341, 362),
            new ScreenPoint(1335, 429),
            new ScreenPoint(1282, 497),
            new ScreenPoint(1182, 542),
            new ScreenPoint(1128, 622),
            new ScreenPoint(1026, 638),
            new ScreenPoint(991, 678),
            new ScreenPoint(892, 734),
            new ScreenPoint(836, 810),
            new ScreenPoint(890, 875),
            new ScreenPoint(950, 925),
            new ScreenPoint(989, 967)
        );

        return new Path(applyRandomOffset(baseWaypoints));
    }

    public static Path levelTwoPath() {
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(1879, 224),
            new ScreenPoint(1813, 187),
            new ScreenPoint(1759, 151),
            new ScreenPoint(1669, 136),
            new ScreenPoint(1581, 124),
            new ScreenPoint(1524, 201),
            new ScreenPoint(1491, 277),
            new ScreenPoint(1444, 341),
            new ScreenPoint(1380, 362),
            new ScreenPoint(1345, 394),
            new ScreenPoint(1280, 410),
            new ScreenPoint(1207, 501),
            new ScreenPoint(1134, 527),
            new ScreenPoint(1067, 619),
            new ScreenPoint(920, 648),
            new ScreenPoint(916, 706),
            new ScreenPoint(800, 762),
            new ScreenPoint(838, 843),
            new ScreenPoint(892, 885),
            new ScreenPoint(947, 949),
            new ScreenPoint(963, 968)
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
