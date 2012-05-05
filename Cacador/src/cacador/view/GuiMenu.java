package cacador.view;

import cacador.model.EstatisticasETratadorDeTiros;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultTreeCellEditor.DefaultTextField;

public class GuiMenu extends JFrame{
    	 
	 
	 
	private GuiJogo guiJogo;
	 
        
        public GuiMenu(){
        this.setName("Menu Principal");                
        this.setTitle("Menu Principal");
        
        //painel principal tem 8 linhas = 7 botoes + estatisticas
        JPanel painelprincipal = new JPanel(new GridLayout(8, 1, 50, 50));
        this.setContentPane(painelprincipal);
        
        
        //estatisticas
        JTextArea stats = new JTextArea(3,50);
        //Estatisticas.carregarPainelMenu(stats);
        painelprincipal.add(stats);
        
        this.setVisible(true);
        //this.pack();
        }
        
        class OuvinteMenu implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
            
            
        }
        
        
	 
}
 
