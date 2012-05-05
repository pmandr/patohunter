/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cacador.controller;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

/**
 *
 * @author paulovich
 */
public class MP3Player {

   

    public void play(final String filename) throws JavaLayerException {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    //FileInputStream fin = new FileInputStream();
                    InputStream input = ClassLoader.getSystemResourceAsStream(filename);
                    BufferedInputStream bin = new BufferedInputStream(input);
                    AudioDevice dev = FactoryRegistry.systemRegistry().createAudioDevice();
                    player = new Player(bin, dev);
                    player.play();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        t.start();
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
    }
    
    public static String getTiro(){
        return ("cacador/sounds/tiro.mp3");
    }
    
    public static void main(String[] args) throws IOException, JavaLayerException, URISyntaxException {
        MP3Player pl = new MP3Player();
        pl.play("cacador/sounds/risada1.mp3");
    }
    
     public static String getAplausos() {
        return ("cacador/sounds/aplauso.mp3");
    }
     
    public static String getRisada(){
        return ("cacador/sounds/risada"+(new Random().nextInt(3)+1)+".mp3");
        }
    public static String getAveMorrendo() {
        return ("cacador/sounds/avemorrendo.mp3");
    }
    
    public static String getMusicaFundo() {
        return ("cacador/sounds/Full.mp3");
    }
    
    private Player player;
}
