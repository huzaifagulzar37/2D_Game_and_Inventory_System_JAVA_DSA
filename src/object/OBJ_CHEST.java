package object;

import javax.imageio.ImageIO;

import entity.Player;

public class OBJ_CHEST extends SuperObject {

    public boolean opened = false;

    public OBJ_CHEST() {
        name = "Chest";
        collision = true; // player can "collide" to open
        // load image
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/chest.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void open(Player player) {
        if (!opened && player.hasKey > 0) {
            player.coins += 500; // give coins
            player.hasKey--;      // use one key
            opened = true;        // mark chest as opened
            System.out.println("Chest opened! Coins: " + player.coins);
        }
    }
}
