package main;

import entity.Entity;
import object.*;

public class EntityGenerator {
    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case OBJ_Axe.ojbName:
                obj = new OBJ_Axe(gp);

                break;
            case OBJ_Boots.ojbName:
                obj = new OBJ_Boots(gp);

                break;
            case OBJ_Chest.ojbName:
                obj = new OBJ_Chest(gp);

                break;
            case OBJ_Coin_Bronze.ojbName:
                obj = new OBJ_Coin_Bronze(gp);

                break;
            case OBJ_Door.ojbName:
                obj = new OBJ_Door(gp);

                break;
            case OBJ_Door_Iron.ojbName:
                obj = new OBJ_Door_Iron(gp);

                break;
            case OBJ_Fireball.ojbName:
                obj = new OBJ_Fireball(gp);

                break;
            case OBJ_Heart.ojbName:
                obj = new OBJ_Heart(gp);

                break;
            case OBJ_Key.ojbName:
                obj = new OBJ_Key(gp);

                break;
            case OBJ_Lantern.ojbName:
                obj = new OBJ_Lantern(gp);

                break;
            case OBJ_ManaCrystal.ojbName:
                obj = new OBJ_ManaCrystal(gp);

                break;
            case OBJ_Pickaxe.ojbName:
                obj = new OBJ_Pickaxe(gp);

                break;
            case OBJ_Potion_Red.ojbName:
                obj = new OBJ_Potion_Red(gp);

                break;
            case OBJ_Rock.ojbName:
                obj = new OBJ_Rock(gp);

                break;
            case OBJ_Shield_Blue.ojbName:
                obj = new OBJ_Shield_Blue(gp);

                break;
            case OBJ_Shield_Wood.ojbName:
                obj = new OBJ_Shield_Wood(gp);

                break;
            case OBJ_Sword_Normal.ojbName:
                obj = new OBJ_Sword_Normal(gp);

                break;
            case OBJ_Tent.ojbName:
                obj = new OBJ_Tent(gp);

                break;
        }

        return obj;
    }

}
