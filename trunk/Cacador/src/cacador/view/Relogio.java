/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cacador.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Paulo Mário
 */
public class Relogio extends JPanel {  
  
    private JLabel label;  
      
    private static int number = 0;  
      
    public Relogio() {
        this.setVisible(true);
        this.add(this.getConteudoRelogio()); 
        this.setBackground(new Color(255, 255, 255, 0));
    }  
         
    private JLabel getConteudoRelogio() {  
        if (this.label == null) {  
            this.label = new JLabel("");  
            this.label.setText(number+"");
            this.label.setBounds(10, 10, 100, 22); 
            this.label.setFont(new Font("Dialog", Font.BOLD, 24));
            this.label.setForeground(Color.YELLOW);
            //this.label.setForeground(new Color(255, 200, 200));
            //this.label.setBackground(new Color(0,0,0,128));//seta o fundo semi transparente
            //this.label.setFont(null);
        }  
        return this.label;  
    }  
      
    public void atualizaRelogio() {  
        Relogio.number++;
        this.label.setText(number+"");
    }  
}  
