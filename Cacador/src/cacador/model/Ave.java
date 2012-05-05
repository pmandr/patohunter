/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cacador.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Paulo Mário
 */
public abstract class Ave extends Thread{
    private boolean vivo = true;
    private int TAM_JANELAx = 800;
    private int TAM_JANELAy = 640;
    protected Image atual; //guarda a imagens atual
    
    //VARIAVEIS PARA CALCULO DA TRAJETORIA RANDOMICA
    private Random r = new Random();
    protected Integer xcorrente = new Integer(0);
    protected int ycorrente =0;
    //escolhe taxas iniciais de curvas para o grafico
    private int taxavert = r.nextInt(10)+1;//proporcional a diferenca da hmax e hmin: maior = mais dificil
    private int taxahor = r.nextInt(100)+100; //proporcional a suavidade da curva, inversamente ao nro periodos: maior = mais facil
    protected int sentido; //sera inicializada em iniciarVoo()
    //int yinicial = r.nextInt(TAM_JANELAy); //proporcional a altura de inicio na tela
    private int yinicial =200;
    protected int velocidade; //existem 3 velocidades(lento=10;media=5;rapida=2, quanto menor mais rapido
    private int tempoTranscorridoDeQueda=0;//este tempo nao eh real
    private ArrayList<Ave> removerDaqui;

    
    @Override
    public void run() {
        iniciarVoo();
        while(vivo) voar();
        while((ycorrente<TAM_JANELAy+70)) cair();
    }
    
    private void iniciarVoo() {
        //escolhe se começa do lado esquerdo ou direito; e ajusta a direcao
        if(r.nextInt(2)==0){
            xcorrente = 0;
            sentido = 1;
        }else{
            xcorrente=TAM_JANELAx-1;
            sentido=-1;
        }
    }
    
    //funcao que traca o voo das aves
    private void voar() {
            //checa se atingiu os limites laterais da tela e calcula o sentido da movimentacao da ave
            if (xcorrente == TAM_JANELAx-1){
                sentido = -1;
                reiniciaVariaveisDeVoo();
                //Ave.inverteDesenho
            }
                else if(xcorrente == -70){
                    sentido = 1;
                    reiniciaVariaveisDeVoo();
                    //Ave.inverteDesenho
                }

            //formula de calculo da trajetoria
            ycorrente = (int) Math.abs(xcorrente/taxavert * Math.sin(xcorrente.doubleValue()/taxahor)+yinicial);

//            //evita que a ave saia da tela e volte
//            if (ycorrente >= TAM_JANELAy-70)ycorrente = TAM_JANELAy-1 - (ycorrente - TAM_JANELAy-1);
//            else if (ycorrente<-100) ycorrente = -70 + (-70 - ycorrente);
//            
            try{
                this.sleep(velocidade);
            }catch(InterruptedException ie){}
             
            //incrementa na direcao atual
            xcorrente += sentido;
    }
    
    public void desenha(Graphics2D g2, JPanel p) {
        if(vivo)desenharVoando(g2,p);
        else desenharMorrendo(g2,p);
    }
    public abstract void desenharVoando(Graphics2D g2,JPanel p);
    public abstract void desenharMorrendo(Graphics2D g2,JPanel p);
    
    public int getX(){
        return this.xcorrente;
    }
    
    public int getY(){
        return this.ycorrente;
    }    
    
    public Image getImagemAtual(){
        return this.atual;
    }
    
    public void setVelocidade(int vel){
        
    }

    //para nao criar novas aves e otimizar o desemppenho, quando a ave alcanca as laterais da tela ela so retraca sua trajetoria
    private void reiniciaVariaveisDeVoo() {
        taxavert = r.nextInt(5)+1;//proporcional a diferenca da hmax e hmin: maior = mais dificil
        taxahor = r.nextInt(100)+100; //proporcional a suavidade da curva, inversamente ao nro periodos: maior = mais facil
        yinicial = r.nextInt(TAM_JANELAy/2)+100;
    }

    void morrer() {
        vivo = false;
    }

    private void cair() {
        //int x = xcorrente-xiniqueda;
        //caso ja tiver atingido abaixo do chao da tela termina a thread
        if(ycorrente==TAM_JANELAy+70) {
            this.removerDaqui.remove(this);
        }
        
        //implementacao da formula do Movimento Uniformemente Variad (MUV)
        //ycorrente = (int) Math.abs(yiniqueda-100+ (1/5)*Math.pow((x-23),2) + (1/5)*x);
        ycorrente += Math.pow(tempoTranscorridoDeQueda,2);
        xcorrente++;
        if(xcorrente%10==0)tempoTranscorridoDeQueda++;
        try{
            this.sleep(20);
        }catch(InterruptedException e){}
        
    }
    
    

    void setRemoverDaqui(ArrayList<Ave> aves) {
        this.removerDaqui = aves;
    }

    public boolean estaViva() {
        return this.vivo;
    }
   }
    

