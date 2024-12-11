package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity{
    GamePanel gp;

    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);

        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";

        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;

        collision = true;
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        gp.gameState = gp.dialogState;

        if (!opened) {
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("You obtained a " + loot.name + "!");

            if (gp.player.inventory.size() == gp.player.maxInventorySize) {
                sb.append("\nUnfoetunately, you cannot carry any more items!");
            } else {
                sb.append("\nYou have found the hidden treasure!");

                gp.player.inventory.add(loot);

                down1 = image2;

                opened = true;
            }

            gp.ui.currentDialog = sb.toString();
        } else {
            gp.ui.currentDialog = "The loot was already taken!";
        }
    }
}
