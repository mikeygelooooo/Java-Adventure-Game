package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;

    public static final String ojbName = "Tent";

    public OBJ_Tent(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = ojbName;
        description = "[" + name + "]\nLets you rest until\nmorning.";
        price = 300;
        stackable = true;

        down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.sleepState;

        gp.playSE(14);

        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;

        gp.player.getrSleepImage(down1);

        return true;
    }
}
