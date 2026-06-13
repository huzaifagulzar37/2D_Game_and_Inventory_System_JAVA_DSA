package main;

import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[10];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/backgroundtheme.wav");

        if (soundURL[0] == null) {
            System.out.println(" Sound file not found: /sound/background.wav");
        } else {
            System.out.println(" Sound file loaded successfully");
        }
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) return;

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
