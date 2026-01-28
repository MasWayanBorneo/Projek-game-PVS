package pathfindingclean.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public final long id;
    public double x, y;

    public final List<Edge> edges = new ArrayList<>();

    // Dijkstra state
    public double distance = Double.POSITIVE_INFINITY;
    public Node previous = null;
    public boolean visited = false;

    public Node(long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
