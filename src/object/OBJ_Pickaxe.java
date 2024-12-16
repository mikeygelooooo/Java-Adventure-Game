package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {
    public static final String ojbName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        type = type_pickaxe;
        name = ojbName;
        description = "[" + name + "]\nA pickaxe used for\nbreaking stone.";
        price = 15;

        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 36;
        knockbackPower = 10;
        motion1_duration = 10;
        motion2_duration = 30;

        down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
    }
}
