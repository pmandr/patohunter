package cacador.view;

import cacador.controller.Temporizador;
import cacador.model.Jogo;
import cacador.model.Ave;
import cacador.model.EstatisticasETratadorDeTiros;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiJogo extends JFrame {
    
        private Temporizador timerJogo;
	private GuiMenu guiMenu;
	private PainelDeAnimacao principal = new PainelDeAnimacao();
	private Jogo jogo;
        private Timer timer = new Timer();
        private EstatisticasETratadorDeTiros stats;
        
        
        public GuiJogo(){
            this.jogo = new Jogo(this);
            setSize(800,600);
            setTitle("Caçador");
            setName("Caçador");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            getContentPane().add(principal);
            
            //ESPACO = pause | ESC = sai do jogo
            this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    //O SISTEMA DE PAUSE AINDA NAO FOI TOTALMENTE IMPLEMENTADO
                    jogo.pause();
                } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
            });
            
            //inicia o temporizador que controla tarefas
            this.timerJogo = new Temporizador(principal,jogo);
            
            //muda cursor
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            
            this.addMouseListener(stats);
            
            setVisible(true);
            
        }

    public Temporizador getTemporizador() {
        return this.timerJogo;
    }
    
    public EstatisticasETratadorDeTiros getStats(){
            return stats;
        }
       
	 
public class PainelDeAnimacao extends JPanel {
    private Image image_BG;
    private String filenameBG;
    private Relogio relogio;
    
    
                
    public PainelDeAnimacao() {
            this.filenameBG = "src/cacador/images/ceu1.png";
            this.image_BG = new ImageIcon(filenameBG).getImage();
           
            //adiciona o relogio
            this.relogio = new Relogio();
            this.add(relogio);
            
            //adiciona o placar, que tambem eh o tratador d e tiros
            stats = new EstatisticasETratadorDeTiros(this);//stats tambem eh MouseListener do JFrame do jogo
            this.add(stats);
            
        }
    public PainelDeAnimacao(int fase){
        if(fase==1)filenameBG = "src/cacador/images/ceu1.png";
        if(fase==2)filenameBG = "src/cacador/images/ceu2.png";
        if(fase==3)filenameBG = "src/cacador/images/ceu3.png";
        this.image_BG = new ImageIcon(filenameBG).getImage();
    }
    
    @Override
    public void paintComponent(Graphics g) {  
        super.paintComponent(g); 
        //desenha plano de fundo
        if (image_BG != null)
            g.drawImage(image_BG, 0, 0, image_BG.getWidth(null), image_BG.getHeight(null), null);
        
        //desenha aves
        Graphics2D g2 = (Graphics2D)g;
        for(Ave ave : jogo.getAves()){
            ave.desenha(g2,this);
        } 
       
    } 
    
    public void aumentaFase(){
            if(filenameBG.equals("src/cacador/images/ceu1.png")) {
                filenameBG = "src/cacador/images/ceu2.png";
                this.image_BG = new ImageIcon(filenameBG).getImage();
            }else if(filenameBG.equals("src/cacador/images/ceu2.png")){
                filenameBG = "src/cacador/images/ceu3.png";
                this.image_BG = new ImageIcon(filenameBG).getImage();
            }
        }

        public void tocarSomDeMudancaDeFase() {
            //throw new UnsupportedOperationException("Not yet implemented");
        }

        public void atualizaRelogio() {
            this.relogio.atualizaRelogio();
        }
        
        public Jogo getJogo(){
            return jogo;
        }

        public void matarAves(ArrayList<Ave> avesAcertadas) {
            jogo.matarAves(avesAcertadas);
        }
        
        
  }
}	 

 
