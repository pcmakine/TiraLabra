/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pete
 */
public class MainWindow extends JFrame implements MouseListener, MouseMotionListener {

    JLabel mousePosition;
    JTextField text = new JTextField("hello");
    MyJpanel main = new MyJpanel();
    int cities;

    public MainWindow() {
        init();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mousePosition.setText("Mouse clicked at coordinate : [" + e.getX() + "," + e.getY() + "]");
        JLabel label = new JLabel("" + cities);
        main.add(label);

        Insets insets = main.getInsets();
        Dimension size = label.getPreferredSize();
        label.setBounds(e.getX() + insets.left, e.getY() + insets.top, size.width, size.height);

        cities++;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mousePosition.setText("Current mouse Coordinates : [" + e.getX() + "," + e.getY() + "]");

    }

    @Override
    public void mouseExited(MouseEvent e) {
        mousePosition.setText("Mouse outside access window");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePosition.setText("Mouse pressed at coordinates : [" + e.getX() + "," + e.getY() + "]");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePosition.setText("Current mouse coordinates : [" + e.getX() + "," + e.getY() + "]");

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition.setText("Mouse dragged at coordinates : [" + e.getX() + "," + e.getY() + "]");

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.setText("Mouse moved to coordinates : [" + e.getX() + "," + e.getY() + "]");
        main.getLoc().setText("Mouse moved to coordinates : [" + e.getX() + "," + e.getY() + "]");


    }

    public void init() {
        mousePosition = new JLabel();
        addMouseListener(this);        // listens for own mouse and
        addMouseMotionListener(this);  // mouse-motion events
      //  setLayout(new FlowLayout());
            Insets insets = this.getInsets();
    this.setSize(600 + insets.left + insets.right, 300 + insets.top
        + insets.bottom);
        this.setContentPane(main);
        main.add(mousePosition);
        add(mousePosition);

      //  this.setSize(600, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
