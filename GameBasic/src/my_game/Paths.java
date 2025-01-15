package my_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ui_elements.ScreenPoint;

public class Paths {

    // Generate random offset within +/-60 for both axes
    private static int generateRandomOffset() {
        Random random = new Random();
        return random.nextInt(121) - 60; // Random value between -60 and 60
    }

    // Apply random offset to all waypoints in a path
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
        // Define the base waypoints for the path
        List<ScreenPoint> baseWaypoints = List.of(
            new ScreenPoint(2000, 300),
            new ScreenPoint(1600, 300), // Move left
            new ScreenPoint(1600, 400), // Move down
            new ScreenPoint(1000, 400), // Move left
            new ScreenPoint(1000, 300), // Move up
            new ScreenPoint(500, 300),  // Move left
            new ScreenPoint(500, 400),  // Move down
            new ScreenPoint(0, 400)    // Move left
        );

        // Apply random offset to the waypoints
        List<ScreenPoint> randomizedWaypoints = applyRandomOffset(baseWaypoints);
        return new Path(randomizedWaypoints);
    }

    public static List<Path> additionalPaths() {
        List<Path> paths = new ArrayList<>();

        // Define base waypoints for additional paths
        List<ScreenPoint> basePath2 = List.of(
            new ScreenPoint(1800, 250),
            new ScreenPoint(1400, 250), // Move left
            new ScreenPoint(1400, 350), // Move down
            new ScreenPoint(900, 350),  // Move left
            new ScreenPoint(900, 250),  // Move up
            new ScreenPoint(400, 250),  // Move left
            new ScreenPoint(400, 350),  // Move down
            new ScreenPoint(50, 350)    // Move left
        );

        List<ScreenPoint> basePath3 = List.of(
            new ScreenPoint(1900, 500),
            new ScreenPoint(1500, 500), // Move left
            new ScreenPoint(1500, 600), // Move down
            new ScreenPoint(1100, 600), // Move left
            new ScreenPoint(1100, 500), // Move up
            new ScreenPoint(600, 500),  // Move left
            new ScreenPoint(600, 600),  // Move down
            new ScreenPoint(100, 600)   // Move left
        );

        List<ScreenPoint> basePath4 = List.of(
            new ScreenPoint(1750, 400),
            new ScreenPoint(1350, 400), // Move left
            new ScreenPoint(1350, 500), // Move down
            new ScreenPoint(850, 500),  // Move left
            new ScreenPoint(850, 400),  // Move up
            new ScreenPoint(350, 400),  // Move left
            new ScreenPoint(350, 500),  // Move down
            new ScreenPoint(0, 500)     // Move left
        );

        List<ScreenPoint> basePath5 = List.of(
            new ScreenPoint(2100, 350),
            new ScreenPoint(1700, 350), // Move left
            new ScreenPoint(1700, 450), // Move down
            new ScreenPoint(1200, 450), // Move left
            new ScreenPoint(1200, 350), // Move up
            new ScreenPoint(700, 350),  // Move left
            new ScreenPoint(700, 450),  // Move down
            new ScreenPoint(200, 450)   // Move left
        );

        // Apply random offsets to each path
        paths.add(new Path(applyRandomOffset(basePath2)));
        paths.add(new Path(applyRandomOffset(basePath3)));
        paths.add(new Path(applyRandomOffset(basePath4)));
        paths.add(new Path(applyRandomOffset(basePath5)));

        return paths;
    }
}
