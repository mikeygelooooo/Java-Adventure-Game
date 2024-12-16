package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public static final String ojbName = "Iron Sword";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = ojbName;
        description = "[" + name + "]\nAn iron sword.";
        price = 10;

        attackValue = 2;
        attackArea.width = 36;
        attackArea.height = 36;
        knockbackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;

        down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
    }
}
