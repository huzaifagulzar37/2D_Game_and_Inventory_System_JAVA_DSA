package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.swing.JPanel;
import entity.Entity;
import entity.Player;
import monster.MON_SKEL;
import object.OBJ_HEART;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    public BufferedImage heart_full, heart_half, heart_blank;

    // Screen Setting
    final int OrignalTileSize = 16;
    final int scale = 3;
    public final int tilesize = OrignalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tilesize * maxScreenCol;
    public final int screenHeight = tilesize * maxScreenRow;

    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWWidth = tilesize * maxWorldCol;
    public final int worldHeight = tilesize * maxWorldRow;

    // Game states
    public final int PLAY = 0;
    public final int PAUSE = 1;
    public final int INVENTORY = 2;
    public final int SHOP = 3;
    public int currentState = PLAY;
    public Stack<Integer> gameStateStack = new Stack<>();

    public HUD hud;
    public boolean showStatus = false;
    Sound music = new Sound();
    public UI ui;

    // --- NEW: Shop ---
    public Shop shop;

    int FPS = 60;
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker;
    public Player player;
    public SuperObject[] obj = new SuperObject[30];
    public AssetSet02 aset = new AssetSet02(this);
    public Entity[] monster = new Entity[20];

    int attackSize = 16;
    boolean attacking = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        player = new Player(this, keyH);
        ui = new UI(this);
        shop = new Shop(this);
    }

    public void setupGame() {
        cChecker = new CollisionChecker(this);
        aset.setObject();
        SuperObject heart = new OBJ_HEART(this);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        aset.setMonster();
        hud = new HUD(this, player);
        playMusic(0);
    }

    public void startgameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextdrawtime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remaintime = nextdrawtime - System.nanoTime();
                remaintime = remaintime / 1000000;
                if (remaintime < 0) remaintime = 0;
                Thread.sleep((long) (remaintime));
                nextdrawtime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void GamePause() {
        if (keyH.pausePressed) {
            if (currentState == PLAY) {
                gameStateStack.push(currentState);
                currentState = PAUSE;
                System.out.println("Game Paused Stack: " + gameStateStack);
            } else if (currentState == PAUSE) {
                if (!gameStateStack.isEmpty()) {
                    currentState = gameStateStack.pop();
                } else {
                    currentState = PLAY;
                }
                System.out.println("Game Resumed  Stack: " + gameStateStack);
            }
            keyH.pausePressed = false;
        }
    }

    public void drawPlayerLife(Graphics2D g2) {
        int x = tilesize / 2;
        int y = tilesize / 2;
        int i = 0;

        while (i < player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += tilesize;
        }

        x = tilesize / 2;
        y = tilesize / 2;
        i = 0;

        while (i < player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += tilesize;
        }
    }

    public void update() {
        GamePause();
        if (currentState == PLAY) {
            player.update();
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }
        if (keyH.statusPressed) {
            showStatus = !showStatus;
            keyH.statusPressed = false;
        }
    }

    public void drawPlayerCoins(Graphics2D g2) {
        g2.setColor(java.awt.Color.YELLOW);
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        g2.drawString("Coins: " + player.coins, tilesize / 2, tilesize * 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        for (int j = 0; j < monster.length; j++) {
            if (monster[j] != null) {
                monster[j].draw(g2);
            }
        }

        player.draw(g2);

       

        drawPlayerLife(g2);
        drawPlayerCoins(g2);

        // --- NEW: Inventory UI Info ---
        g2.setColor(Color.WHITE);
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        g2.drawString("Inventory: " + player.inventory.getSize() + " items", tilesize / 2, tilesize * 3);
        g2.drawString("[B] Buy Heart (500)  [E] Use Heart", tilesize / 2, tilesize * 3 + 25);

        if (showStatus) {
            hud.drawStatusScreen(g2);
        }
        ui.draw(g2);
        g2.dispose();
    }
}
