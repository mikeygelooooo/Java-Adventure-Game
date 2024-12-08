package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    int value = 5;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = "Red Potion";
        value = 5;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals for " + value + " HP.";
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogState;
        gp.ui.currentDialog = "You have recovered " + value + " HP!";

        entity.life += value;

        gp.playSE(2);
    }
}
