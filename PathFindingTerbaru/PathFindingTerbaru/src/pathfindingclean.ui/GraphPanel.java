package pathfindingclean.ui;

import pathfindingclean.graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GraphPanel extends JPanel {

    private List<Node> path;
    private Graph graph;

    private Node startNode = null;
    private Node goalNode = null;

    // ===== Bounds (world) =====
    private double minX, maxX, minY, maxY;

    // ===== Camera =====
    private double scale = 1.0;
    private double offsetX = 0;
    private double offsetY = 0;

    private static final double MIN_SCALE = 0.3;
    private static final double MAX_SCALE = 8.0;

    private int lastX, lastY;
    private boolean dragging = false;

    public GraphPanel(Graph graph) {
        this.graph = graph;
        setBackground(Color.WHITE);
        computeBounds();

        // Click: pilih start/goal
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Node clicked = findNode(e.getX(), e.getY());
                if (clicked == null) return;

                Node current = GraphPanel.this.graph.getNode(clicked.id);
                if (current == null) return;

                if (startNode == null) {
                    startNode = current;
                    path = null;
                    repaint();
                    return;
                }
                if (goalNode == null) {
                    goalNode = current;
                    path = GraphPanel.this.graph.dijkstra(startNode.id, goalNode.id);
                    repaint();
                    return;
                }

                startNode = current;
                goalNode = null;
                path = null;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
                dragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });

        // Drag: pan
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragging) return;

                offsetX += e.getX() - lastX;
                offsetY += e.getY() - lastY;

                lastX = e.getX();
                lastY = e.getY();

                repaint();
            }
        });

        // Wheel: zoom
        addMouseWheelListener(e -> {
            double oldScale = scale;
            double zoomFactor = (e.getWheelRotation() < 0) ? 1.1 : 0.9;

            scale *= zoomFactor;
            scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale));

            double mx = e.getX();
            double my = e.getY();

            offsetX = mx - (mx - offsetX) * (scale / oldScale);
            offsetY = my - (my - offsetY) * (scale / oldScale);

            repaint();
        });
    }

    // ===== API =====
    public void setGraph(Graph graph) {
        this.graph = graph;
        this.path = null;
        this.startNode = null;
        this.goalNode = null;
        computeBounds();
        resetView();
        repaint();
    }

    public void resetViewPublic() {
        resetView();
        repaint();
    }

    // ===== Camera helpers =====
    private void resetView() {
        scale = 1.0;
        offsetX = 0;
        offsetY = 0;
    }

    private void computeBounds() {
        minX = minY = Double.MAX_VALUE;
        maxX = maxY = -Double.MAX_VALUE;

        for (Node n : graph.getNodes()) {
            minX = Math.min(minX, n.x);
            maxX = Math.max(maxX, n.x);
            minY = Math.min(minY, n.y);
            maxY = Math.max(maxY, n.y);
        }

        if (maxX - minX < 1e-9) maxX = minX + 1;
        if (maxY - minY < 1e-9) maxY = minY + 1;
    }

    private int mapX(double x) {
        double nx = (x - minX) / (maxX - minX);
        return (int) (nx * getWidth() * scale + offsetX);
    }

    private int mapY(double y) {
        double ny = (maxY - y) / (maxY - minY);
        return (int) (ny * getHeight() * scale + offsetY);
    }

    private Node findNode(int mx, int my) {
        Node closest = null;
        double closestDist = Double.MAX_VALUE;
        final double THRESHOLD = 30.0;

        for (Node n : graph.getNodes()) {
            int dx = mapX(n.x) - mx;
            int dy = mapY(n.y) - my;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < closestDist && dist <= THRESHOLD) {
                closestDist = dist;
                closest = n;
            }
        }
        return closest;
    }

    // ===== Rendering =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Edges
        g2.setStroke(new BasicStroke(1));
        for (Node n : graph.getNodes()) {
            for (Edge e : n.edges) {
                if (n.id > e.to.id) continue;

                int x1 = mapX(n.x), y1 = mapY(n.y);
                int x2 = mapX(e.to.x), y2 = mapY(e.to.y);

                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(x1, y1, x2, y2);

                int tx = (x1 + x2) / 2;
                int ty = (y1 + y2) / 2;
                g2.setColor(new Color(0, 150, 0));
                g2.drawString(String.valueOf((int) e.cost), tx, ty);
            }
        }

        // 🔴 PATH (FIXED)
        drawPathUsingEdges(g2);

        // Nodes
        for (Node n : graph.getNodes()) {
            if (n == startNode) g2.setColor(Color.GREEN);
            else if (n == goalNode) g2.setColor(Color.BLUE);
            else g2.setColor(Color.BLACK);

            int x = mapX(n.x);
            int y = mapY(n.y);
            g2.fillOval(x - 2, y - 2, 4, 4);
        }

        // Debug
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("Scale: " + String.format("%.2f", scale), 10, 20);
        if (startNode != null) g2.drawString("Start: Node " + startNode.id, 10, 40);
        if (goalNode != null) g2.drawString("Goal: Node " + goalNode.id, 10, 60);
        if (path != null) g2.drawString("Path: " + path.size() + " nodes", 10, 80);
    }

    /**
     * ✅ PATH DIGAMBAR BERDASARKAN EDGE
     */
    private void drawPathUsingEdges(Graphics2D g2) {
        if (path == null || path.size() < 2) return;

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));

        for (int i = 0; i < path.size() - 1; i++) {
            Node a = path.get(i);
            Node b = path.get(i + 1);

            for (Edge e : a.edges) {
                if (e.to == b) {
                    g2.drawLine(
                            mapX(a.x), mapY(a.y),
                            mapX(b.x), mapY(b.y)
                    );
                    break;
                }
            }
        }
    }
}
