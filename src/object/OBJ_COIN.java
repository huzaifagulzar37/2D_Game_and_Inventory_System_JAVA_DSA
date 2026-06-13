package object;


	import java.io.IOException;
	import javax.imageio.ImageIO;
	import main.GamePanel;

	public class OBJ_COIN extends SuperObject {
	    GamePanel gp;

	    public OBJ_COIN(GamePanel gp) {
	    	this.gp=gp;
	        name = "Coin";
	        solidArea = new java.awt.Rectangle(0, 0, gp.tilesize, gp.tilesize); // optional
	        solidAreaDefaultX = solidArea.x;
	        solidAreaDefaultY = solidArea.y;

	        try {
	            image = ImageIO.read(getClass().getResourceAsStream("/object/coin.png")); // your coin image
	            image = uTool.scaleImage(image, gp.tilesize, gp.tilesize);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
