package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        dialogSet = -1;

        getImage();
        setDialog();
    }

    public void getImage() {
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setDialog() {
        dialogs[0][0] = "Greetings, traveler.";
        dialogs[0][1] = "Ah, so you've come to our humble village in search\nof hidden treasures?";
        dialogs[0][2] = "I was an adventurer once, but the years have turned\nmy daring quests into fond memories of the past.";
        dialogs[0][3] = "May fortune smile upon your journey, brave soul.";

        dialogs[1][0] = "The lake water works wonders for your health!";
        dialogs[1][1] = "Although monsters appear whenever someone drinks\nthe lake water.";
        dialogs[1][2] = "In any case, keep safe young traveler!";

        dialogs[2][0] = "I wonder if there is any way to open that door.";
    }

    public void setAction() {
        if (onPath) {
            // Tile as Goal
            // int goalCol = 12;
            // int goalRow = 9;

            // Player as Goal
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();

                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void speak() {
        facePlayer();

        startDialog(this, dialogSet);

        dialogSet++;

        if (dialogs[dialogSet][0] == null) {
            dialogSet--;
        }
    }
}
