package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity{
    GamePanel gp;

    public static final String ojbName = "Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_obstacle;
        name = ojbName;

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

    public void setLoot(Entity loot) {
        this.loot = loot;

        setDialog();
    }

    public void setDialog() {
        dialogs[0][0] = "You obtained a " + loot.name + "!\nUnfortunately, you cannot carry any more items!";
        dialogs[1][0] = "You obtained a " + loot.name + "!\nYou have obtained a " + loot.name + "!";
        dialogs[2][0] = "The loot was already taken!";
    }

    public void interact() {
        if (!opened) {
            gp.playSE(3);

            if (!gp.player.canObtainItem(loot)) {
                startDialog(this, 0);
            } else {
                startDialog(this, 1);

                down1 = image2;

                opened = true;
            }
        } else {
            startDialog(this, 2);
        }
    }
}
