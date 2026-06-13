package main;

import object.OBJ_HEART;
import object.SuperObject;

public class Shop {
    GamePanel gp;

    public Shop(GamePanel gp) {
        this.gp = gp;
    }

    public void buyHeart() {
        int price = 500;
        if (gp.player.coins >= price) {
            gp.player.coins -= price;
            SuperObject heart = new OBJ_HEART(gp);
            gp.player.inventory.addItem(heart);
            gp.ui.addMessage("Purchased Heart! -500 Coins");
        } else {
            gp.ui.addMessage("Low in coin! Need 500.");
        }
    }
}
