/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package patohunter.coisas;

/**
 *
 * @author Paulo Mário
 */
public class Ambiente {

    public void inicio(){


    }



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import patohunter.personagens.Ave;
import patohunter.personagens.FabricaDeAves;
import patohunter.personagens.FabricaDeAves.TiposDeAves;
import patohunter.ferramentas.Listener;
import patohunter.ferramentas.TimerListener;

/**
 *
 * @author Paulo Mário
 */
public class Ambiente extends JPanel implements TimerListener {

    public Ambiente() {
        this.setBackground(Color.BLACK);
        this.timer = new Timer(this, 100);
        this.pers = new Personagem[3];
    }

    public void inicio() {
        timer.start();
    }
       
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
    }

    public void update() {
        
    }

    private Timer timer;

    }

}
