package model;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * WorldBoxQuestFrame
 * Versi custom tanpa NetBeans GUI Builder
 * Tanpa JSON, tanpa Gson
 */
public class WorldBoxQuestFrame extends JFrame {

    // ===== GAME STATE =====
    private int posisiJawaban = 0;
    private int lives = 3;
    private int score = 0;

    private final int WAKTU_PER_SOAL = 20;
    private int waktuSisa;
    private Timer timer;

    // ===== GAME DATA =====
    private QuestionManager questionManager;
    private Question currentQuestion;

    // ===== UI COMPONENT =====
    private JLabel lblClue;
    private JLabel lblTimer;
    private JLabel lblScore;
    private JLabel lblLives;

    private JPanel panelJawaban;
    private JPanel panelHuruf;

    private JLabel[] kotakJawaban;
    private List<JButton> tombolHuruf;

    private JButton btnHapus;

    public WorldBoxQuestFrame() {
        setTitle("WorldBoxQuest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        questionManager = new QuestionManager();
        initUI();
        initTimer();
        loadQuestion();
    }

    // ================= UI =================
    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // ===== HEADER =====
        JLabel lblTitle = new JLabel("WorldBoxQuest", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Monospaced", Font.BOLD, 32));

        lblClue = new JLabel("CLUE", SwingConstants.CENTER);
        lblClue.setFont(new Font("Monospaced", Font.PLAIN, 18));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(lblTitle);
        header.add(lblClue);

        add(header, BorderLayout.NORTH);

        // ===== CENTER =====
        panelJawaban = new JPanel();
        panelHuruf = new JPanel();

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(panelJawaban, BorderLayout.NORTH);
        center.add(panelHuruf, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // ===== FOOTER =====
        lblTimer = new JLabel("⏱ 20 detik");
        lblScore = new JLabel("💎 Skor: 0");
        lblLives = new JLabel("❤️❤️❤️");

        lblTimer.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblScore.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblLives.setFont(new Font("Monospaced", Font.BOLD, 18));

        btnHapus = new JButton("HAPUS");
        btnHapus.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnHapus.setBackground(Color.RED);
        btnHapus.setForeground(Color.WHITE);
        btnHapus.addActionListener(e -> hapusHuruf());

        JPanel footer = new JPanel(new GridLayout(1, 4, 10, 10));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footer.add(lblTimer);
        footer.add(lblScore);
        footer.add(lblLives);
        footer.add(btnHapus);

        add(footer, BorderLayout.SOUTH);
    }

    private void initTimer() {
        timer = new Timer(1000, e -> {
            waktuSisa--;
            lblTimer.setText("⏱ " + waktuSisa + " detik");

            if (waktuSisa < 0) {
                timer.stop();
                salahJawaban();
            }
        });
    }

    // ================= GAME LOGIC =================
    private void loadQuestion() {
        posisiJawaban = 0;
        currentQuestion = questionManager.getRandomQuestion();

        if (currentQuestion == null) {
            JOptionPane.showMessageDialog(this, "Tidak ada soal!");
            System.exit(0);
        }

        lblClue.setText("CLUE: " + currentQuestion.getClue());
        buildKotakJawaban();
        buildTombolHuruf();

        waktuSisa = WAKTU_PER_SOAL;
        lblTimer.setText("⏱ " + waktuSisa + " detik");
        timer.start();
    }

    private void buildKotakJawaban() {
        panelJawaban.removeAll();

        int len = currentQuestion.getAnswer().length();
        kotakJawaban = new JLabel[len];

        panelJawaban.setLayout(new GridLayout(1, len, 10, 10));
        panelJawaban.setPreferredSize(new Dimension(400, 80));

        for (int i = 0; i < len; i++) {
            JLabel lbl = new JLabel("_", SwingConstants.CENTER);
            lbl.setFont(new Font("Monospaced", Font.BOLD, 28));
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            kotakJawaban[i] = lbl;
            panelJawaban.add(lbl);
        }

        panelJawaban.revalidate();
        panelJawaban.repaint();
    }

    private void buildTombolHuruf() {
        panelHuruf.removeAll();
        tombolHuruf = new ArrayList<>();

        List<String> huruf = new ArrayList<>(currentQuestion.getLetters());
        Collections.shuffle(huruf);

        int cols = Math.max(4, huruf.size() / 2);
        panelHuruf.setLayout(new GridLayout(2, cols, 10, 10));

        for (String h : huruf) {
            JButton btn = new JButton(h);
            btn.setFont(new Font("Monospaced", Font.BOLD, 18));
            btn.addActionListener(e -> isiHuruf(btn));
            tombolHuruf.add(btn);
            panelHuruf.add(btn);
        }

        panelHuruf.revalidate();
        panelHuruf.repaint();
    }

    private void isiHuruf(JButton btn) {
        if (posisiJawaban < kotakJawaban.length) {
            kotakJawaban[posisiJawaban].setText(btn.getText());
            btn.setEnabled(false);
            posisiJawaban++;

            if (posisiJawaban == kotakJawaban.length) {
                cekJawaban();
            }
        }
    }

    private void hapusHuruf() {
        if (posisiJawaban > 0) {
            posisiJawaban--;
            String huruf = kotakJawaban[posisiJawaban].getText();
            kotakJawaban[posisiJawaban].setText("_");

            for (JButton btn : tombolHuruf) {
                if (btn.getText().equals(huruf) && !btn.isEnabled()) {
                    btn.setEnabled(true);
                    break;
                }
            }
        }
    }

    private void cekJawaban() {
        timer.stop();

        StringBuilder sb = new StringBuilder();
        for (JLabel lbl : kotakJawaban) {
            sb.append(lbl.getText());
        }

        if (sb.toString().equalsIgnoreCase(currentQuestion.getAnswer())) {
            score += 10;
            JOptionPane.showMessageDialog(this, "BENAR!\nSkor: " + score);
        } else {
            salahJawaban();
            return;
        }

        updateStatus();
        loadQuestion();
    }

    private void salahJawaban() {
        lives--;
        updateStatus();

        if (lives <= 0) {
            JOptionPane.showMessageDialog(this, "GAME OVER\nSkor: " + score);
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(this, "SALAH! Nyawa tersisa: " + lives);
            loadQuestion();
        }
    }

    private void updateStatus() {
        lblScore.setText("💎 Skor: " + score);
        lblLives.setText("❤️".repeat(lives));
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WorldBoxQuestFrame().setVisible(true);
        });
    }
}
