package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Boots extends Entity {
    GamePanel gp;

    int value = 5;
    public static final String ojbName = "Speed Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = ojbName;
        description = "[" + name + "]\nAdds " + value + " speed.";
        price = 15;
        stackable = true;

        value = 2;

        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);

        setDialog();
    }

    public void setDialog() {
        dialogs[0][0] = "You have added " + value + " speed!";
    }

    public boolean use(Entity entity) {
        startDialog(this, 0);

        entity.defaultSpeed += value;
        entity.speed = entity.defaultSpeed;

        gp.playSE(2);

        return true;
    }
}
