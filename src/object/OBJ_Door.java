package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Door extends Entity{
    GamePanel gp;

    public static final String ojbName = "Door";


    public OBJ_Door(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_obstacle;
        name = ojbName;

        down1 = setup("/objects/door", gp.tileSize, gp.tileSize);

        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialog();
    }

    public void setDialog() {
        dialogs[0][0] = "You need a key to pass through!";
    }

    public void interact() {
        startDialog(this, 0);
    }
}
