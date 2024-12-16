package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity{
    GamePanel gp;

    public static final String ojbName = "Key";

    public OBJ_Key(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = ojbName;
        description = "[" + name + "]\na key to a locked door.";
        price = 20;
        stackable = true;

        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);

        setDialog();
    }

    public void setDialog() {
        dialogs[0][0] = "You have opened the door!";

        dialogs[1][0] = "There is no door here!";
    }

    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != 999) {
            startDialog(this, 0);

            gp.playSE(3);

            gp.obj[gp.currentMap][objIndex] = null;

            return true;
        } else {
            startDialog(this, 1);

            return false;
        }
    }
}
