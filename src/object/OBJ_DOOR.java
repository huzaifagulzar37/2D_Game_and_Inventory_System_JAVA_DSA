package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_DOOR extends SuperObject {

	public OBJ_DOOR() {
		name="Door";
        collision = true;
        loadImage();
    }

    public void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision  = true;
    }
}
