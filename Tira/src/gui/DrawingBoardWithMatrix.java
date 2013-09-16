/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Pete
 */
//http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Mousedraganddraw.htm
public class DrawingBoardWithMatrix extends JFrame {

    public DrawingBoardWithMatrix() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private class PaintSurface extends JComponent {

        ArrayList<Shape> shapes = new ArrayList<Shape>();
        Point startDrag, endDrag;
        ArrayList cityXCoords = new ArrayList();
        ArrayList cityYCoords = new ArrayList();

        public PaintSurface() {
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (!cityXCoords.contains(e.getX()) && !cityYCoords.contains(e.getY())) {


                        int radius = 50;
                        Shape ellipse = new Ellipse2D.Double(e.getX() - radius, e.getY() - radius, 2.0 * radius, 2.0 * radius);

                        shapes.add(ellipse);
                        startDrag = new Point(e.getX(), e.getY());
                        endDrag = startDrag;
                    }
                    repaint();

                }

                public void mouseReleased(MouseEvent e) {
                    //Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                     int radius = 50;
                    Shape line = new Line2D.Float(startDrag.x, startDrag.y, e.getX(), e.getY());
                    Shape ellipse = new Ellipse2D.Double(e.getX() - radius, e.getY() - radius, 1.0 * radius, 1.0 * radius);
                    shapes.add(line);
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    endDrag = new Point(e.getX(), e.getY());
                    repaint();
                }
            });
        }

        private void paintBackground(Graphics2D g2) {
            g2.setPaint(Color.LIGHT_GRAY);
            for (int i = 0; i < getSize().width; i += 10) {
                //   Shape line = new Line2D.Float(i, 0, i, getSize().height);
                //g2.draw(line);
            }

            for (int i = 0; i < getSize().height; i += 10) {
                // Shape line = new Line2D.Float(0, i, getSize().width, i);
                //  g2.draw(line);
            }


        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintBackground(g2);
            Color[] colors = {Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK};
            int colorIndex = 0;

            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes) {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(colors[(colorIndex++) % 6]);
                g2.fill(s);
            }

            if (startDrag != null && endDrag != null) {
                g2.setPaint(Color.LIGHT_GRAY);
                Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                Shape line = new Line2D.Float(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                g2.draw(line);
            }
        }

        private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
            return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }
    }
}
