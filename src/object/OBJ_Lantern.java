package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {
    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lantern";
        description = "[" + name + "]\nIlluminates the surroundings.";
        price = 200;
        lightRadius = 250;

        down1 = setup("/objects/lantern", gp.tileSize, gp.tileSize);
    }
}
