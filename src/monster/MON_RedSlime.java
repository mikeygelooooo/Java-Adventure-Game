package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Entity {
    GamePanel gp;

    public MON_RedSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Red Slime";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 7;
        defense = 0;
        exp = 5;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        if (onPath) {
            // Set Aggro Distance
            checkStopChasingOrNot(gp.player, 15, 100);

            // Player as Goal
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            // Shoot Projectile
            checkShootOrNot(200, 30);
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

        // Randomize Monster Drop
        if (i < 60) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 60 && i <= 80) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i > 80 && i <= 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
