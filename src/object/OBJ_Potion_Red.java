package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    int value = 5;
    public static final String ojbName = "Red Potion";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = ojbName;
        description = "[" + name + "]\nRestores " + value + " HP.";
        price = 3;
        stackable = true;

        value = 10;

        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);

        setDialog();
    }

    public void setDialog() {
        dialogs[0][0] = "You have recovered " + value + " HP!";
    }

    public boolean use(Entity entity) {
        startDialog(this, 0);

        entity.life += value;

        gp.playSE(2);

        return true;
    }
}
