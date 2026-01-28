

import pathfindingclean.graph.Graph;
import pathfindingclean.io.ProceduralGraphGenerator;
import pathfindingclean.ui.GraphPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final int MIN_NODES = 2;
    private static final int MAX_NODES = 20000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Random Pathfinding Map");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);

            JPanel root = new JPanel(new BorderLayout());
            frame.setContentPane(root);

            JPanel top = new JPanel();
            JLabel lblNodes = new JLabel("Nodes:");
            JTextField txtNodes = new JTextField("500", 6);
            JButton btnGenerate = new JButton("Generate");
            JButton btnResetView = new JButton("Reset View");

            top.add(lblNodes);
            top.add(txtNodes);
            top.add(btnGenerate);
            top.add(btnResetView);
            root.add(top, BorderLayout.NORTH);

            final double width = 5000;
            final double height = 5000;

            Graph graph = ProceduralGraphGenerator.generate(500, width, height);
            GraphPanel graphPanel = new GraphPanel(graph);
            root.add(graphPanel, BorderLayout.CENTER);

            btnGenerate.addActionListener(e -> {
                int n;
                try {
                    n = Integer.parseInt(txtNodes.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Masukkan angka valid untuk jumlah node.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (n < MIN_NODES) n = MIN_NODES;
                if (n > MAX_NODES){
                JOptionPane.showMessageDialog(frame, "Maksimal " + MAX_NODES + " node.",
                         "Info", JOptionPane.INFORMATION_MESSAGE);
                n = MAX_NODES;
                }
                // penting: tampilkan nilai final yang benar-benar dipakai
                txtNodes.setText(String.valueOf(n));
                
                btnGenerate.setEnabled(false);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                final int finalN = n;

                SwingWorker<Graph, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Graph doInBackground() {
                        return ProceduralGraphGenerator.generate(finalN, width, height);
                    }

                    @Override
                    protected void done() {
                        try {
                            graphPanel.setGraph(get());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Generate gagal: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            frame.setCursor(Cursor.getDefaultCursor());
                            btnGenerate.setEnabled(true);
                        }
                    }
                };

                worker.execute();
            });

            btnResetView.addActionListener(e -> graphPanel.resetViewPublic());

            frame.setVisible(true);
        });
    }
}
