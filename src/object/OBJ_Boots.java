package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Boots extends Entity {
    public static final String ojbName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = ojbName;

        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
    }
}
