package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public static final String ojbName = "Woodcutter's Axe";

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = ojbName;
        description = "[" + name + "]\nAn axe used for\nchopping wood.";
        price = 75;

        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 36;
        knockbackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;

        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
    }
}
