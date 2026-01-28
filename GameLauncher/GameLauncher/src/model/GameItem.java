package model;
public class GameItem {
    public final int id;
    public final String title;
    public final String iconResource;
    public final String jarPath;
    public final String description;

    public GameItem(int id, String title, String iconResource, String jarPath, String description) {
        this.id = id;
        this.title = title;
        this.iconResource = iconResource;
        this.jarPath = jarPath;
        this.description = description;
    }
}
