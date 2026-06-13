package main;

import java.awt.Color;
import java.awt.Graphics2D;
import entity.Player;

public class HUD {
    GamePanel gp;
    Player player;

    public HUD(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    public void draw(Graphics2D g2) {
        int x = gp.tilesize / 2;
        int y = gp.tilesize / 2;

        // --- Draw health (hearts) ---
        int i = 0;
        while (i < player.life) {
            g2.drawImage(gp.heart_full, x, y, null);
            i++;
            x += gp.tilesize;
        }

        // --- Draw keys ---
        x = gp.tilesize / 2;
        y = gp.tilesize * 2;
        g2.setColor(Color.CYAN);
        g2.setFont(g2.getFont().deriveFont(20f));
        g2.drawString("Keys: " + player.keysCollected, x, y);

        // --- Draw coins ---
        y += gp.tilesize;
        g2.setColor(Color.YELLOW);
        g2.drawString("Coins: " + player.coins, x, y);

        // --- Draw monsters killed ---
        y += gp.tilesize;
        g2.setColor(Color.RED);
        g2.drawString("Monsters killed: " + player.monstersKilled, x, y);
    }

    public void drawStatusScreen(Graphics2D g2) {
        int screenX = gp.screenWidth / 4;
        int screenY = gp.screenHeight / 4;
        int width = gp.screenWidth / 2;
        int height = gp.screenHeight / 2;

        // Semi-transparent background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(screenX, screenY, width, height);

        // White border
        g2.setColor(Color.WHITE);
        g2.drawRect(screenX, screenY, width, height);

        // Draw text
        int textX = screenX + 30;
        int textY = screenY + 50;
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(28f));

        g2.drawString("Character Status", textX, textY);

        textY += 60;
        g2.drawString("Health: " + player.life + " / " + player.maxLife, textX, textY);

        textY += 50;
        g2.drawString("Keys: " + player.keysCollected, textX, textY);

        textY += 50;
        g2.drawString("Coins: " + player.coins, textX, textY);

        textY += 50;
        g2.drawString("Monsters killed: " + player.monstersKilled, textX, textY);
    }
}
