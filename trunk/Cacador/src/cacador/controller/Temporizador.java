package cacador.controller;

import cacador.model.Jogo;
import cacador.view.GuiJogo.PainelDeAnimacao;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javazoom.jl.decoder.JavaLayerException;

//a classe Temporizador controla as operações agendadas:
// - mudanca de fase
// - atualizacao do painel de jogo
// -outros
public class Temporizador{
    //private MP3Player mp3pl = new MP3Player();
    private Jogo jogo;
    private PainelDeAnimacao painelDeJogo;
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    
    private final Runnable timerAtualizacaoDeJogo;
    private final Runnable timerAtualizacaoDeFase;
    private final Runnable timerAtualizacaoDeRelogio;
    private final Runnable timerAtualizacaoDeAdicaoDeAves;
    private final Runnable timerAtualizacaoDeSonsRisadas;
    private final Runnable timerFimDeJogo;
    
    private final ScheduledFuture<?> AtualizacaoDeJogoHandle;
    private final ScheduledFuture<?> AtualizacaoDeFaseHandle;
    private final ScheduledFuture<?> AtualizacaoDeRelogioHandle;
    private final ScheduledFuture<?> AtualizacaoDeAdicaoDeAvesHandle;
    private final ScheduledFuture<?> AtualizacaoDeSonsRisadasHandle;
    private final ScheduledFuture<?> FimDeJogoHandle;
            
    public Temporizador(PainelDeAnimacao p, Jogo j){
        this.painelDeJogo = p;
        this.jogo = j;
        
        //define a atualizacao do painel de jogo para a maxima velocidade dos passaros
        //DEFINE TAREFA DE ATUALIZAR O PAINEL DO JOGO
        this.timerAtualizacaoDeJogo = new Runnable() {
                @Override
                public void run() { painelDeJogo.repaint(); }
            };
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        this.AtualizacaoDeJogoHandle = scheduler.scheduleAtFixedRate(timerAtualizacaoDeJogo, 0, 2, TimeUnit.MILLISECONDS);    
        
        
        //define a atualizacao da fase para: 30s a 1a FASE; 1min a 2a FASE; 1min a 3aFASE
        //DEFINE TAREFA DE ATUALIZAR A FASE
        this.timerAtualizacaoDeFase = new Runnable() {
                @Override
                public void run() { 
                    painelDeJogo.aumentaFase(); 
                    jogo.aumentaFase();
                    painelDeJogo.tocarSomDeMudancaDeFase();
                }
            };
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        this.AtualizacaoDeFaseHandle = scheduler.scheduleAtFixedRate(timerAtualizacaoDeFase, 30, 30, TimeUnit.SECONDS); 
        
        //define a adicao de novas aves de acordo com a fase: 1a fase = 1ave/segundo; 2a fase=2aves/segundo; 3a fase = 3aves/segundo
        //DEFINE TAREFA DE ATUALIZAR A ADICAO DE AVES E ADICIONAR
        this.timerAtualizacaoDeAdicaoDeAves = new Runnable() {
                @Override
                public void run() { 
                    jogo.adicionarAve(jogo.getFase());
//                    if(jogo.getFase()==1)jogo.adicionarAve();
//                    else if(jogo.getFase()==2){
//                        jogo.adicionarAve();
//                        jogo.adicionarAve();
//                    }
//                    else if(jogo.getFase()==3){
//                        jogo.adicionarAve();
//                        jogo.adicionarAve();
//                        jogo.adicionarAve();
//                    }
                }
            };
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        this.AtualizacaoDeAdicaoDeAvesHandle = scheduler.scheduleAtFixedRate(timerAtualizacaoDeAdicaoDeAves, 0, 1, TimeUnit.SECONDS);
        
        
        //define a atualizacao do relogio para contar os segundos transcorridos
        //DEFINE TAREFA DE ATUALIZAR O RELOGIO
        this.timerAtualizacaoDeRelogio = new Runnable() {
                @Override
                public void run() { 
                    painelDeJogo.atualizaRelogio();
                }
            };
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        this.AtualizacaoDeRelogioHandle = scheduler.scheduleAtFixedRate(timerAtualizacaoDeRelogio, 1, 1, TimeUnit.SECONDS); 
        
        //define tocar risadas a cada 20 segundos
        //DEFINE TAREFA DE TOCAR AS RISADAS
        this.timerAtualizacaoDeSonsRisadas = new Runnable() {
                @Override
                public void run() { 
                try {
                    painelDeJogo.getJogo().getMP3Player().play(MP3Player.getRisada());
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            };
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        this.AtualizacaoDeSonsRisadasHandle = scheduler.scheduleAtFixedRate(timerAtualizacaoDeSonsRisadas, 0, 15, TimeUnit.SECONDS); 
    
        //define o fim do jogo aos 30min de jogo
        //DEFINE TAREFA DE FINALIZAR JOGO
        this.timerFimDeJogo = new Runnable() {
                @Override
                public void run() { 
                painelDeJogo.getJogo().finalizar();
                }
            };
        //DEFINE FREQUENCIA DA EXECUCAO DA TAREFA ACIMA E A LIGA COM O SCHEDULER
        this.FimDeJogoHandle = scheduler.scheduleAtFixedRate(timerFimDeJogo, 90, 30, TimeUnit.SECONDS);
    }
    
    
    
    
    
    
    public void beepForAnHour() {
        
        final ScheduledFuture<?> MudancaDeFaseHandle = 
            scheduler.scheduleAtFixedRate(timerAtualizacaoDeJogo, 0, 2, TimeUnit.MILLISECONDS);
            scheduler.schedule(new Runnable() {
                public void run() { MudancaDeFaseHandle.cancel(true); }
            }, 60 * 60, TimeUnit.SECONDS);
    }

    public void pausar() {
//        try {
//            //        timerAtualizacaoDeJogo.();
//            //        timerAtualizacaoDeFase.wait();
//            //        timerAtualizacaoDeRelogio.wait();
//            //        timerAtualizacaoDeAdicaoDeAves.wait();
//            //        timerAtualizacaoDeSonsRisadas.wait();
////            //        timerFimDeJogo.wait();
////                    FimDeJogoHandle.wait();
////                    AtualizacaoDeJogoHandle.wait();
////                    AtualizacaoDeFaseHandle.wait();
////                    AtualizacaoDeRelogioHandle.wait();
////                    AtualizacaoDeAdicaoDeAvesHandle.wait();
////                    AtualizacaoDeSonsRisadasHandle.wait();
//                    
//        
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
//        }
        scheduler.shutdown();
    }
	 
}
 
