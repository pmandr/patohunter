package cacador.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Ave2 extends Ave {
    Image asaFechadaDir = (new ImageIcon("src/cacador/images/ave211.png")).getImage();
    Image asaFechadaEsq = (new ImageIcon("src/cacador/images/ave212.png")).getImage();
    Image asaAbertaDir  = (new ImageIcon("src/cacador/images/ave221.png")).getImage();
    Image asaAbertaEsq  = (new ImageIcon("src/cacador/images/ave222.png")).getImage();
    Image morrendo = (new ImageIcon("src/cacador/images/ave230.png")).getImage();
    AffineTransform af = new AffineTransform(); //movimento o presonagem com uma transformação afim
    Random r = new Random();
    
    public Ave2(){
        this(1);
    }
    public Ave2(int vel){
        if(vel==1)velocidade=10;
        if(vel==2)velocidade=6;
        if(vel==3)velocidade=4;
    }
    
    @Override
    public void desenharVoando(Graphics2D g2, JPanel p) {
        af.setToTranslation((double)xcorrente,(double) ycorrente);
        if(xcorrente%20<10){
            //desenha com asa aberta
            if(sentido==1){
                atual = asaAbertaDir;
                g2.drawImage(asaAbertaDir,af,p);
            }
            else if(sentido==-1){
                atual = asaAbertaEsq;
                g2.drawImage(atual,af,p);
            }
            
        }
        else if(xcorrente%20>=10){
            //desenha com asa fechada
            if(sentido==1){
                atual = asaFechadaDir;
                g2.drawImage(atual,af,p);
            }
            else if(sentido==-1){
                atual = asaFechadaEsq;
                g2.drawImage(atual,af,p);
            }
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
 
