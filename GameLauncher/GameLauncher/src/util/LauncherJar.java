package util;
import java.io.File;
public class LauncherJar {

    public static void launchJar(String jarPath) throws Exception {
        // Base folder: working directory aplikasi launcher (saat run biasanya dist/)
        File base = new File(System.getProperty("user.dir"));

        File target = new File(jarPath);
        if (!target.isAbsolute()) {
            target = new File(base, jarPath);
        }

        if (!target.exists()) {
            throw new RuntimeException("JAR tidak ditemukan: " + target.getAbsolutePath());
        }

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", target.getAbsolutePath());
        pb.directory(base); // set working directory untuk proses anak [web:372]
        pb.start();
    }
}
