package pathfindingclean.io;

import pathfindingclean.graph.*;

import java.util.*;

public class ProceduralGraphGenerator {

    private static final Random rand = new Random();

    public static Graph generate(int nodeCount, double width, double height) {
        Graph g = new Graph();
        List<Node> nodes = new ArrayList<>(nodeCount);

        // ===== Parameter dinamis =====
        double attractMin, attractMax;
        double repelDist;
        double extraFactor;

        if (nodeCount <= 50) {
            attractMin = 120; attractMax = 350;
            repelDist = 110;
            extraFactor = 0.50;
        } else if (nodeCount <= 200) {
            attractMin = 80; attractMax = 280;
            repelDist = 90;
            extraFactor = 0.35;
        } else {
            attractMin = 60; attractMax = 220;
            repelDist = 70;
            extraFactor = 0.25;
        }

        // 1) Node random
        for (int i = 0; i < nodeCount; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            nodes.add(g.addNode(i, x, y));
        }

        // 2) Relax posisi
        int relaxIter = (nodeCount <= 50) ? 10 : 8;
        for (int it = 0; it < relaxIter; it++) {
            for (Node a : nodes) {
                double moveX = 0, moveY = 0;
                int count = 0;

                for (Node b : nodes) {
                    if (a == b) continue;

                    double dx = b.x - a.x;
                    double dy = b.y - a.y;
                    double d2 = dx * dx + dy * dy;
                    if (d2 < 1) continue;

                    double d = Math.sqrt(d2);

                    if (d < attractMax && d > attractMin) {
                        moveX += dx * 0.02;
                        moveY += dy * 0.02;
                        count++;
                    }

                    if (d < repelDist) {
                        moveX -= dx * 0.05;
                        moveY -= dy * 0.05;
                    }
                }

                if (count > 0) {
                    a.x += moveX / count;
                    a.y += moveY / count;
                }
            }
        }

        // 3) Semua kandidat edge (all-pairs) untuk MST
        class E { Node a, b; double d; }

        List<E> all = new ArrayList<>();
        double maxDist = 0;

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                Node a = nodes.get(i), b = nodes.get(j);
                double d = dist(a, b);
                maxDist = Math.max(maxDist, d);

                E e = new E();
                e.a = a; e.b = b; e.d = d;
                all.add(e);
            }
        }

        all.sort(Comparator.comparingDouble(e -> e.d));

        // 4) Kruskal MST (pasti connect)
        int n = nodes.size();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        List<E> mst = new ArrayList<>();
        for (E e : all) {
            int ia = (int) e.a.id;
            int ib = (int) e.b.id;
            int ra = find(parent, ia), rb = find(parent, ib);
            if (ra != rb) {
                parent[ra] = rb;
                mst.add(e);
            }
        }

        // 5) Tambahkan MST ke graph
        for (E e : mst) {
            g.addEdge(e.a.id, e.b.id, lengthToCost(e.d));
        }

        // 6) Extra edges
        int maxExtraPerNode;
        if (nodeCount <= 50) maxExtraPerNode = 4;
        else if (nodeCount <= 200) maxExtraPerNode = 3;
        else maxExtraPerNode = 2;

        int[] extraDeg = new int[nodeCount];
        double extraLimit = maxDist * extraFactor;

        for (E e : all) {
            if (e.d > extraLimit) continue;

            int ia = (int) e.a.id;
            int ib = (int) e.b.id;
            if (extraDeg[ia] >= maxExtraPerNode) continue;
            if (extraDeg[ib] >= maxExtraPerNode) continue;

            double p;
            if (nodeCount <= 50) p = 0.50;
            else if (nodeCount <= 200) p = 0.25;
            else p = 0.12;

            if (rand.nextDouble() < p) {
                g.addEdge(e.a.id, e.b.id, lengthToCost(e.d));
                extraDeg[ia]++;
                extraDeg[ib]++;
            }
        }

        return g;
    }

    private static int find(int[] p, int x) {
        if (p[x] == x) return x;
        return p[x] = find(p, p[x]);
    }

    private static double dist(Node a, Node b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    private static double lengthToCost(double d) {
        double base = d / 60.0;
        int k = (int) Math.round(base);

        int noise = rand.nextInt(3) - 1; // -1,0,1
        k += noise;

        if (k < 1) k = 1;
        return k * 5.0;
    }
}
