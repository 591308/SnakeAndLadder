import java.util.UUID;

public class Player {

    private String name;
    private String id;
    private boolean fanget;

    public Player(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.fanget = false;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean getFanget() {
        return fanget;
    }

    public void setFanget(boolean fanget) {
        this.fanget = fanget;
    }
}
