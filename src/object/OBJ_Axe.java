package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Woodcutter's Axe";
        description = "[" + name + "]\nAn axe used for chopping\nwood.";
        price = 75;

        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 36;

        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
    }
}
