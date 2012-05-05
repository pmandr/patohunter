package cacador.controller;

import cacador.model.Ave;
import cacador.model.Ave1;
import cacador.model.Ave2;
import cacador.model.Ave3;
import cacador.model.Ave4;
import cacador.model.Jogo;
import java.util.Random;

public class FabricaDeAves {
    private static Random r = new Random();
    private static int num_aves = 4;
    private static boolean pause = false;
    private Jogo jogo;
    
    public static Ave getAveRandomica(int velocidade) {
        if(!pause){
            switch(r.nextInt(num_aves)){
            case(0): return new Ave1(velocidade);
            case(1): return new Ave2(velocidade);
            case(2): return new Ave3(velocidade);
            case(3): return new Ave4(velocidade);
        }
        }
        
        return null;
    }
    
    public void pause(){
        this.pause = !this.pause;
    }
    

 
	
	 
}
 
