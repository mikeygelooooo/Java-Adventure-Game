package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity{
    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";

        down1 = setup("/objects/key");
    }
}
