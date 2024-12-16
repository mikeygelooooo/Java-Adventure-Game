package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 6;
        life = maxLife;
        attack = 2;
        defense = 1;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        if (onPath) {
            // Set Aggro Distance
            checkStopChasingOrNot(gp.player, 15, 100);

            // Player as Goal
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            // Aggro Probability
            checkStartChasingOrNot(gp.player, 5, 100);

            // Random Movement if not Aggro
            getRandomDirection(120);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;

        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        Entity droppedItem;
        int coinAmount = new Random().nextInt(2) + 1;

        // Randomize Monster Drop
        if (i <= 80) {
            droppedItem = new OBJ_Coin_Bronze(gp);
            droppedItem.value = coinAmount;

            dropItem(droppedItem);
        }
        if (i > 80 && i <= 90) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i > 90) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
