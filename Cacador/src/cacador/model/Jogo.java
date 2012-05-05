package cacador.model;

import cacador.controller.Temporizador;
import cacador.controller.DataAccessObject;
import cacador.controller.FabricaDeAves;
import cacador.controller.MP3Player;
import cacador.view.GuiJogo;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javazoom.jl.decoder.JavaLayerException;

public class Jogo {
	private FabricaDeAves fabricaAbsDeAves;
	private int fase=1;
	private EstatisticasETratadorDeTiros estatisticas;
	private DataAccessObject dataAccessObject;
        private ArrayList<Ave> aves = new ArrayList<Ave>();
        private MP3Player mp3pl = new MP3Player();
        private boolean pause = false;
        private GuiJogo gui;
        

     public Jogo(GuiJogo gui){
         this.gui =gui;
        try {
            this.mp3pl.play(MP3Player.getMusicaFundo());
        } catch (JavaLayerException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
        
     public void pause() {
        this.pause = !this.pause;
        if(pause){
            for(Ave ave: aves) //ave.interrupt();
            try {
                ave.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.gui.getTemporizador().pausar();
        }else if(!pause){
            for(Ave ave: aves)
                try {
                    ave.join();ave.notify();
                    //this.gui.getTemporizador().pausar();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
                        }
            //this.gui.getTemporizador().pausar();
        }
    }

  
    
    public void aumentaFase(){
        this.fase++;
    }
    
    public void adicionarAve(int velocidade){
        Ave ave = FabricaDeAves.getAveRandomica(velocidade);
        ave.start();
        aves.add(ave);
    }
    
    public Iterable<Ave> getAves() {
        return aves;
    }

    public void matarAves(ArrayList<Ave> avesAcertadas) {
        for(Ave ave: avesAcertadas){
            ave.morrer(); 
            ave.setRemoverDaqui(aves);
        }
    }

    public int getFase() {
        return this.fase;
    }

    public MP3Player getMP3Player() {
        return this.mp3pl;
    }

    public void finalizar() {
        //this.pause();
        this.gui.getTemporizador().pausar();
        
        try {
            this.mp3pl.play(MP3Player.getAplausos());
        } catch (JavaLayerException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(JOptionPane.showConfirmDialog(gui, "O jogo terminou. Seu SCORE =" + gui.getStats().getScore()+" . Reiniciar?"
                ,"Fim de Jogo", JOptionPane.YES_OPTION)==JOptionPane.YES_OPTION){
            gui = new GuiJogo();
            try {
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else gui.dispose();
//                MessageDialog(gui, "O jogo terminou. Seu SCORE = " + gui.getStats().getScore()
//                        ,"Fim de Jogo",JOptionPane.WARNING_MESSAGE);
        
        
//        if(JOptionPane.showConfirmDialog(this.gui,"O jogo terminou. Seu SCORE = " + gui.getStats().getScore(),
//                    ,"Fim de Jogo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE)
//                    == JOptionPane.OK_OPTION){
//                
//            }
    }

    

    
        
        
	 
}
 
