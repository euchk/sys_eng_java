package my_game;

import java.util.List;
import ui_elements.ScreenPoint;

public class Path {
    private List<ScreenPoint> waypoints;

    public Path(List<ScreenPoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<ScreenPoint> getWaypoints() {
        return waypoints;
    }

    public ScreenPoint getWaypoint(int index) {
        return waypoints.get(index);
    }

    public int getPathLength() {
        return waypoints.size();
    }
}
