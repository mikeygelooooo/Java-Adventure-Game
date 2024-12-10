package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity{
    public OBJ_Key(GamePanel gp) {
        super(gp);

        type = type_consumable;
        name = "Key";
        description = "[" + name + "]\nKey to a locked door.";
        price = 100;

        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
    }
}
