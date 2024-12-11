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
        description = "[" + name + "]\nHeals for " + value + " HP.";
        price = 25;
        stackable = true;

        value = 5;

        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogState;
        gp.ui.currentDialog = "You have recovered " + value + " HP!";

        entity.life += value;

        gp.playSE(2);

        return true;
    }
}
