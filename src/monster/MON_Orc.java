package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 15;
        life = maxLife;
        attack = 5;
        defense = 5;
        exp = 7;
        knockbackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("/monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/orc_right_2", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage() {
        attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
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

        // Check if Orc is Attacking
        if (!attacking) {
            checkAttackOrNot(30, gp.tileSize * 4, gp.tileSize);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;

        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        Entity droppedItem;
        int coinAmount = new Random().nextInt(3) + 3;

        // Randomize Monster Drop
        if (i <= 80) {
            droppedItem = new OBJ_Coin_Bronze(gp);
            droppedItem.value = coinAmount;

            dropItem(droppedItem);
        }
        if (i > 80 && i <= 90) {
            dropItem(new OBJ_Sword_Normal(gp));
        }
        if (i > 90) {
            dropItem(new OBJ_Shield_Wood(gp));
        }
    }
}
