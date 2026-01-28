package pathfindingclean.loaders;

import pathfindingclean.graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OSMGraphLoader {

    /**
     * Load node dari file CSV sederhana: node_id, lat, lon
     * Catatan: class ini hanya membuat node (belum membuat edge).
     */
    public static Graph loadFromOSM(String filePath) {
        Graph graph = new Graph();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip header
                }

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                long id = Long.parseLong(parts[0].trim());
                double lat = Double.parseDouble(parts[1].trim());
                double lon = Double.parseDouble(parts[2].trim());

                // Simple mercator projection
                double x = lon * 20037508.34 / 180.0;
                double y = Math.log(Math.tan((90.0 + lat) * Math.PI / 360.0)) * 20037508.34 / Math.PI;

                graph.addNode(id, x, y);
            }
        } catch (IOException e) {
            System.err.println("Error loading OSM: " + e.getMessage());
        }

        return graph;
    }
}
