package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Blue Shield";
        description = "[" + name + "]\nA shiny blue shield.";
        price = 250;

        defenseValue = 2;

        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
    }
}
