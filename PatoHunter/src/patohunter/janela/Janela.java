
package patohunter.janela;

import javax.swing.JFrame;
import patohunter.coisas.Ambiente;

/**
 *
 * @author Paulo Mário
 */
public class Janela extends JFrame {

    private Ambiente amb;

    public Janela() {
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.amb = new Ambiente();
        this.amb.inicio();
        this.getContentPane().add(amb);
    }

    
}
