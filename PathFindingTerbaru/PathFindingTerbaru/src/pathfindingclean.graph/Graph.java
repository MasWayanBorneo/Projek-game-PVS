package pathfindingclean.graph;

import java.util.*;

public class Graph {

    private final Map<Long, Node> nodes = new HashMap<>();

    /* ===================== NODE ===================== */

    public Node addNode(long id, double x, double y) {
        Node n = new Node(id, x, y);
        nodes.put(id, n);
        return n;
    }

    public boolean containsNode(long id) {
        return nodes.containsKey(id);
    }

    public Node getNode(long id) {
        return nodes.get(id);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    /* ===================== EDGE ===================== */

    public void addEdge(long from, long to, double cost) {
        Node a = nodes.get(from);
        Node b = nodes.get(to);
        if (a == null || b == null) return;

        // graph tidak menyimpan edge global → aman
        a.edges.add(new Edge(b, cost));
        b.edges.add(new Edge(a, cost));
    }

    /* ===================== DIJKSTRA RESET ===================== */

    /**
     * WAJIB dipanggil sebelum setiap run Dijkstra.
     * Menghapus SEMUA state lama yang menempel di Node.
     */
    private void resetDijkstraState() {
        for (Node n : nodes.values()) {
            n.distance = Double.POSITIVE_INFINITY;
            n.previous = null;
            n.visited = false;
        }
    }

    /* ===================== DIJKSTRA ===================== */

    public List<Node> dijkstra(long startId, long goalId) {

        Node start = nodes.get(startId);
        Node goal  = nodes.get(goalId);

        if (start == null || goal == null) return List.of();
        if (start == goal) return List.of(start);

        // 🔴 FIX UTAMA: hapus state lama
        resetDijkstraState();

        start.distance = 0;

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        pq.add(start);

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.visited) continue;
            cur.visited = true;

            if (cur == goal) break;

            for (Edge e : cur.edges) {
                Node next = e.to;
                double nd = cur.distance + e.cost;

                if (nd < next.distance) {
                    next.distance = nd;
                    next.previous = cur;
                    pq.add(next);
                }
            }
        }

        if (!goal.visited) return List.of(); // tidak ada jalur

        // reconstruct path
        List<Node> path = new ArrayList<>();
        for (Node n = goal; n != null; n = n.previous) {
            path.add(n);
        }
        Collections.reverse(path);

        return path;
    }
}
