package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity{
    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = "Key";
        description = "[" + name + "]\nKey to a locked door.";
        price = 100;

        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogState;

        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != 999) {
            gp.ui.currentDialog = "You have opened the door!";

            gp.playSE(3);

            gp.obj[gp.currentMap][objIndex] = null;

            return true;
        } else {
            gp.ui.currentDialog = "There is no door here!";

            return false;
        }
    }
}
