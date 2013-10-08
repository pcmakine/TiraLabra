/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import gui.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.SwingUtilities;
import gui.Controller;

/**
 *
 * @author pcmakine
 */
public class Tira {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(2 & -2);
        System.out.println(4 & -4);
        System.out.println(8 & -8);
        System.out.println(16 & -16);
        System.out.println(128 & -128);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window(new Controller());
            }
        });

 
      }
}


