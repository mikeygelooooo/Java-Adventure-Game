package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    public static final String ojbName = "Normal Sword";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = ojbName;
        description = "[" + name + "]\nAn old sword.";
        price = 20;

        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        knockbackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;

        down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
    }
}
