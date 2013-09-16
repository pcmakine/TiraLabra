/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pete
 */
public class MyJpanel extends JPanel {

    JTextField location = new JTextField();

    
    public MyJpanel(){
        super();
        setLayout(null);
        initLocation();
    }
    
    private void initLocation() {
        location.setText("hello world");
        //location.setEditable(false);
      //  this.add(location, BorderLayout.NORTH);
    }

    public JTextField getLoc() {
        return location;
    }
    
    

}
