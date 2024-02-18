package io.codeforall.javatars_filhosdamain;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Background implements Runnable {

    private Clip backgroundMusic;
    private AudioInputStream audioInputStream;

    public Background() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream resourceStream = getClass().getResourceAsStream("/data/sound/futebol.wav");
        if (resourceStream == null) {
            throw new IOException("Resource not found: /data/sound/futebol.wav");
        }
        BufferedInputStream bufferedIn = new BufferedInputStream(resourceStream);
        this.audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

        this.backgroundMusic = AudioSystem.getClip();
        this.backgroundMusic.open(audioInputStream);

        FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
        // The music is now ready to be played but not played here directly
    }

    @Override
    public void run() {
        playMusic(); // Call this to start playing music
    }

    public void playMusic() {
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // Start playing music
    }

    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop(); // Stop the music
        }
    }

    public void resumeMusic() {
        if (backgroundMusic != null && !backgroundMusic.isRunning()) {
            backgroundMusic.start(); // Resume music from where it was stopped
        }
    }
}