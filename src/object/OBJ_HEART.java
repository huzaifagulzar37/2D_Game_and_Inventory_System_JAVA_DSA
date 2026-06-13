package object;

import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;

public class OBJ_HEART extends SuperObject {
    GamePanel gp;
    public int healAmount = 2; // Number of life points restored

    public OBJ_HEART(GamePanel gp) {
        name = "Heart";
        this.gp = gp;
        loadImage();
    }

    public void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
            image = uTool.scaleImage(image, gp.tilesize, gp.tilesize);
            image2 = uTool.scaleImage(image2, gp.tilesize, gp.tilesize);
            image3 = uTool.scaleImage(image3, gp.tilesize, gp.tilesize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Called when player touches the heart
    public void recoverHealth(Player player) {
        player.life += healAmount;
        if (player.life > player.maxLife) player.life = player.maxLife;
        gp.ui.addMessage("❤️ Health restored! HP: " + player.life);
    }
}
