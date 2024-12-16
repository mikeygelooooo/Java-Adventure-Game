package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public static final String ojbName = "Wood Shield";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = ojbName;
        description = "[" + name + "]\nAn old shield.";
        price = 10;

        defenseValue = 2;

        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
    }
}
