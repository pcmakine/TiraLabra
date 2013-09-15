/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tira;

import gui.DrawingBoardWithMatrix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.SwingUtilities;
import gui.MainWindow;

/**
 *
 * @author pcmakine
 */
public class Tira {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DrawingBoardWithMatrix();
            }
        });
  


 
      }
}


