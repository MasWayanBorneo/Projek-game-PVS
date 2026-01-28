package app;

import ui.GameLauncherFrame;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GameLauncherFrame().setVisible(true);
        });
    }
}
