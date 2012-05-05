package cacador.model;

import cacador.controller.DataAccessObject;
import cacador.controller.MP3Player;
import cacador.view.GuiJogo.PainelDeAnimacao;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javazoom.jl.decoder.JavaLayerException;

public class EstatisticasETratadorDeTiros extends JPanel implements MouseListener{

    	private Jogo jogo;
	 
	private DataAccessObject dataAccessObject;
	 
	 
        private int ntiros=0;
        private int nacertos=0;
        private JLabel label;
        private int precisao=0;
        private PainelDeAnimacao p;
        private int offsetx =8;//offset do lookandfeel da janela no windows para a área desenhavel
        private int offsety =28;//offset do lookandfeel da janela no windows para a área desenhavel
        
    public EstatisticasETratadorDeTiros(PainelDeAnimacao painel){
        this.setVisible(true);
        this.add(this.getConteudoEstatisticas()); 
        this.setBackground(new Color(255, 255, 255, 0));
        this.p = painel;
    }
    
    private JLabel getConteudoEstatisticas(){
        if (this.label == null) {  
            this.label = new JLabel("");  
            this.label.setText("| Acertos:"+nacertos+"("+precisao+"% de precisão| SCORE:"+(nacertos*precisao));
            this.label.setBounds(10, 10, 100, 22); 
            this.label.setForeground(Color.red);
            this.label.setFont(new Font("Dialog", Font.BOLD, 24));
        }
        return this.label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //toca o som do tiro
        try {
            p.getJogo().getMP3Player().play(MP3Player.getTiro());
        } catch (JavaLayerException ex) {
            Logger.getLogger(EstatisticasETratadorDeTiros.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TESTA SE ACERTOU ALGUMA AVE A QUAIS FORAM
        ArrayList<Ave> avesAcertadas = acertouTiro(e,p);
        ntiros++;
        if(avesAcertadas!=null){
            //toca o som da ave morrendo
            try {
                p.getJogo().getMP3Player().play(MP3Player.getAveMorrendo());
                } catch (JavaLayerException ex) {
                    Logger.getLogger(EstatisticasETratadorDeTiros.class.getName()).log(Level.SEVERE, null, ex);
                    }
            nacertos+=avesAcertadas.size();
            p.matarAves(avesAcertadas);
        }
        precisao = new Double(100*nacertos/ntiros).intValue();
        this.label.setText("| Acertos:"+nacertos+"("+precisao+"% de precisão)|      SCORE:"+(nacertos*precisao));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private ArrayList<Ave> acertouTiro(MouseEvent e, PainelDeAnimacao p) {
        ArrayList<Ave> aves = new ArrayList<Ave>();//LISTA DE AVES ACERTADAS
        Point ptiro = e.getPoint();
        int[] cores= new int[4];
        
        //percorre todas as aves vivas no jogo pq o tiro pode atingit mais de uma ave
        for(Ave ave: p.getJogo().getAves()){
            //TESTA SE O TIRO FOI DENTRO DAS MARGENS DA FIGURA PARA OTIMIZAR O DESEMPENHO
            if(ave.getX()<(ptiro.getX()-offsetx) && ((ave.getImagemAtual().getWidth(null))+ave.getX())>(ptiro.getX()-offsetx)
                    && (ave.getY()<ptiro.getY()-offsety) && ((ave.getImagemAtual().getHeight(null))+ave.getY())>(ptiro.getY()-offsety)
                    && ave.estaViva()){
                
                //testa (retorna true)se o local do clique nao eh TOTALMENTE transparente(ALPHA>0)
                WritableRaster wr = imageToBufferedImage(ave.getImagemAtual()).getRaster();
                wr.getPixel((int)ptiro.getX()-offsetx-ave.getX(),(int) ptiro.getY()-offsety-ave.getY(), cores);
                if(cores[3]!=0)aves.add(ave);
            }
            
        }
        if (!aves.isEmpty()) return aves;
        else return null;
    }
    
    //código ADAPTADO de http://www.rgagnon.com/javadetails/java-0601.html
    public static BufferedImage imageToBufferedImage(Image im) {
     BufferedImage bi = new BufferedImage
        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
     Graphics bg = bi.getGraphics();
     bg.drawImage(im, 0, 0, null);
     bg.dispose();
     return bi;
  }
    
    public int getScore(){
        return (this.nacertos*this.precisao);
    }
	 
}
 
