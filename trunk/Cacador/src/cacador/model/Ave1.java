package cacador.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Ave1 extends Ave {
    Image asaFechada = (new ImageIcon("src/cacador/images/ave11.png")).getImage();
    Image asaAberta  = (new ImageIcon("src/cacador/images/ave12.png")).getImage();
    Image morrendo = (new ImageIcon("src/cacador/images/ave13.png")).getImage();
    AffineTransform af = new AffineTransform(); //movimento o presonagem com uma transformação afim
    Random r = new Random();
    
    public Ave1(){
        this(1);
    }
    public Ave1(int vel){
        if(vel==1)velocidade=10;
        if(vel==2)velocidade=6;
        if(vel==3)velocidade=4;
    }
    
    @Override
    public void desenharVoando(Graphics2D g2, JPanel p) {
        af.setToTranslation((double)xcorrente,(double) ycorrente);
        if(xcorrente%20<10){
            //desenha com asa aberta
            atual = asaAberta;
            g2.drawImage(asaAberta,af,p);
        }
        else if(xcorrente%20>=10){
            //desenha com asa fechada
            atual = asaFechada;
            g2.drawImage(asaFechada,af,p);
        }
        
    }

    @Override
    public void desenharMorrendo(Graphics2D g2,JPanel p) {
        af.setToTranslation((double)xcorrente,(double) ycorrente);
        //desenha morrendo
        atual = morrendo;
        g2.drawImage(morrendo,af,p);
    }
    
    
	 
}
 
