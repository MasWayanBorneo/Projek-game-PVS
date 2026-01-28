package ui;

import repository.GameRepository;
import model.GameItem;
import util.LauncherJar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class GameLauncherFrame extends JFrame {

    private final GameRepository repo = new GameRepository();

    private final JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 18, 18));
    private final JScrollPane scroll = new JScrollPane(grid);
    private final JLabel lblStatus = new JLabel(" ");
    private final JLabel lblEmpty = new JLabel("Belum ada game. Klik Tambah untuk menambahkan.");

    private final JTextField tfSearch = new JTextField(20);
    private final JComboBox<String> cbSort = new JComboBox<>(new String[]{"Name: A-Z", "Name: Z-A"});

    private List<GameItem> allGames = new ArrayList<>();

    private static final Color BG_MAIN = new Color(10, 40, 90);
    private static final Color BG_TOP  = new Color(8, 30, 70);
    private static final Color TILE_BG = new Color(20, 70, 150);
    private static final Color TILE_HOVER = new Color(35, 95, 190);

    public GameLauncherFrame() {
        super("Game Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        top.setBackground(BG_TOP);

        JButton btnAdd = new JButton("Tambah");
        JButton btnRefresh = new JButton("Refresh");

        top.add(btnAdd);
        top.add(btnRefresh);

        top.add(Box.createHorizontalStrut(10));
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setForeground(Color.WHITE);
        top.add(lblSearch);
        top.add(tfSearch);

        top.add(cbSort);

        btnAdd.addActionListener(e -> showAddDialog());
        btnRefresh.addActionListener(e -> reload());

        // search realtime (DocumentListener) [web:770]
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            private void apply() { renderFiltered(); }
            @Override public void insertUpdate(DocumentEvent e) { apply(); }
            @Override public void removeUpdate(DocumentEvent e) { apply(); }
            @Override public void changedUpdate(DocumentEvent e) { apply(); }
        });

        cbSort.addActionListener(e -> renderFiltered());

        grid.setBackground(BG_MAIN);
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));

        lblEmpty.setForeground(new Color(220, 230, 255));
        lblEmpty.setFont(lblEmpty.getFont().deriveFont(Font.BOLD, 16f));

        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_MAIN);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(18); // scroll lebih cepat [web:775]

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG_TOP);
        lblStatus.setBorder(new EmptyBorder(6, 10, 6, 10));
        lblStatus.setForeground(new Color(220, 230, 255));
        bottom.add(lblStatus, BorderLayout.WEST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        reload();
    }

    private void reload() {
        try {
            allGames = repo.findAll();
            lblStatus.setText("Total: " + allGames.size());
            renderFiltered();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void renderFiltered() {
        String q = tfSearch.getText().trim().toLowerCase();

        List<GameItem> filtered = new ArrayList<>();
        for (GameItem g : allGames) {
            String t = (g.title == null ? "" : g.title).toLowerCase();
            if (q.isEmpty() || t.contains(q)) filtered.add(g);
        }

        String sort = (String) cbSort.getSelectedItem();
        if ("Name: Z-A".equals(sort)) {
            filtered.sort(Comparator.comparing((GameItem g) -> g.title == null ? "" : g.title).reversed());
        } else {
            filtered.sort(Comparator.comparing(g -> g.title == null ? "" : g.title));
        }

        if (q.isEmpty()) lblStatus.setText("Total: " + allGames.size());
        else lblStatus.setText("Hasil: " + filtered.size() + " (filter: \"" + tfSearch.getText().trim() + "\")");

        grid.removeAll();

        if (filtered.isEmpty()) {
            grid.add(lblEmpty);
        } else {
            for (GameItem g : filtered) {
                grid.add(createGameTile(g));
            }
        }

        grid.revalidate();
        grid.repaint();
    }

    private JButton createGameTile(GameItem g) {
        ImageIcon raw = loadIconOrDefault(g.iconResource);

        Image scaledImg = raw.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);

        JButton b = new JButton(g.title, icon);
        b.setPreferredSize(new Dimension(180, 220));

        b.setBackground(TILE_BG);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 90), 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setIconTextGap(10);

        // Tooltip title + description (multi-line HTML) [web:823][web:808]
        String desc = (g.description == null || g.description.isBlank())
                ? "Tidak ada deskripsi."
                : g.description;
        b.setToolTipText("<html><b>" + escapeHtml(g.title) + "</b><br>"
                + escapeHtml(desc).replace("\n", "<br>") + "</html>");

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                b.setBackground(TILE_HOVER);
                b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
            @Override public void mouseExited(MouseEvent e) {
                b.setBackground(TILE_BG);
                b.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 90), 1));
            }
        });

        b.addActionListener(e -> launchJar(g.jarPath));
        b.setComponentPopupMenu(makePopupMenu(g));
        return b;
    }

    private JPopupMenu makePopupMenu(GameItem g) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem detail = new JMenuItem("Detail");
        detail.addActionListener(e -> showDetail(g));
        menu.add(detail);

        JMenuItem del = new JMenuItem("Hapus");
        del.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Hapus '" + g.title + "'?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );
            if (ok == JOptionPane.YES_OPTION) {
                try {
                    repo.deleteById(g.id);
                    reload();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        menu.add(del);

        return menu;
    }

    private void showDetail(GameItem g) {
        JTextArea ta = new JTextArea(10, 44);
        ta.setText(g.description == null ? "" : g.description);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        JOptionPane.showMessageDialog(this, new JScrollPane(ta), g.title, JOptionPane.INFORMATION_MESSAGE);
    }

    private ImageIcon loadIconOrDefault(String resource) {
        java.net.URL url = getClass().getResource(resource);
        if (url != null) return new ImageIcon(url);

        java.net.URL fallback = getClass().getResource("/icons/default.png");
        if (fallback != null) return new ImageIcon(fallback);

        return new ImageIcon();
    }

    private void launchJar(String jarPath) {
        try {
            LauncherJar.launchJar(jarPath);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "Launch Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddDialog() {
        JTextField tfTitle = new JTextField();
        JTextField tfIcon = new JTextField("/icons/default.png");
        JTextField tfJar  = new JTextField("Games/Masukkan Nama Game.jar");

        JTextArea taDesc = new JTextArea(5, 22);
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);
        JScrollPane spDesc = new JScrollPane(taDesc);

        Object[] fields = {
                "Title:", tfTitle,
                "Icon resource (mis. /icons/x.png):", tfIcon,
                "Jar path (mis. Games/x.jar):", tfJar,
                "Description:", spDesc
        };

        int ok = JOptionPane.showConfirmDialog(this, fields, "Tambah Game", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            repo.insert(
                    tfTitle.getText().trim(),
                    tfIcon.getText().trim(),
                    tfJar.getText().trim(),
                    taDesc.getText().trim()
            );
            reload();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
