package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Wood Shield";
        description = "[" + name + "]\nAn old shield.";
        price = 35;

        defenseValue = 1;

        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
    }
}
