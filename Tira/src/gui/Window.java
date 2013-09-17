/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tira.Node;

/**
 *
 * @author Pete
 */
//http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Mousedraganddraw.htm
public class Window extends JFrame {

    private Controller controller;
    private JButton bfs;
    private JTextField origin;
    private JTextField target;
    private JPanel controlArea;
    private PaintSurface drawer;
    private ArrayList<Node> results;

    public Window(Controller controller) {
        this.controller = controller;
        createComponents();
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawer = new PaintSurface();
        this.add(drawer, BorderLayout.CENTER);
        this.add(controlArea, BorderLayout.SOUTH);
        results = new ArrayList();
//        this.add(origin, BorderLayout.SOUTH);
//        this.add(target, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void addListener(JButton button, String funktio) {
        if (funktio.equals("bfs")) {
            bfs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //Execute when button is pressed
                    Node[] result = controller.getBfsResult(Integer.valueOf(origin.getText()), Integer.valueOf(target.getText()));
                    results.addAll(Arrays.asList(result));
                    drawer.showResult(result);
                }
            });
        }
    }

    private void createComponents() {
        this.controlArea = new JPanel();
        this.bfs = new JButton("bfs");
        addListener(bfs, "bfs");
        this.origin = new JTextField("", 3);
        this.target = new JTextField("", 3);
        controlArea.add(bfs, BorderLayout.WEST);
        controlArea.add(origin, BorderLayout.CENTER);
        controlArea.add(target, BorderLayout.EAST);
    }

    private class PaintSurface extends JComponent {

        ArrayList<Shape> shapes = new ArrayList<Shape>();
        HashMap<Node, Point> centers = new HashMap();
        HashMap<Shape, Node> nodeCircles = new HashMap();
        int nodes;
        Point startDrag, endDrag;
        ArrayList cityXCoords = new ArrayList();
        ArrayList cityYCoords = new ArrayList();

        public PaintSurface() {
            this.nodes = 0;
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (!cityXCoords.contains(e.getX()) && !cityYCoords.contains(e.getY())) {

                        int radius = 50;
                        Shape ellipse = new Ellipse2D.Double(e.getX() - radius, e.getY() - radius, 2.0 * radius, 2.0 * radius);

                        //  shapes.add(ellipse);
                        startDrag = new Point(e.getX(), e.getY());
                        endDrag = startDrag;
                    }
                    repaint();

                }

                public void mouseReleased(MouseEvent e) {
                    //Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());

                    if (Math.abs(lineLength(startDrag.x, startDrag.y, endDrag.x, endDrag.y)) < 20) {
                        int radius = 50;
                        //Shape ellipse = new Ellipse2D.Double(e.getX() - radius, e.getY() - radius, 1.0 * radius, 1.0 * radius);
                        Shape ellipse = getEllipseFromCenter(e.getX(), e.getY(), 25, 25);
                        shapes.add(ellipse);
                        nodes++;
                        Ellipse2D el = (Ellipse2D) ellipse;
                        Node node = controller.createNode(nodes, (int) el.getCenterX(), (int) el.getCenterY());
                        centers.put(node, endDrag);
                        nodeCircles.put(ellipse, node);

                    } else {
                        Node[] targets = targetNodes(startDrag, endDrag);
                        if (targets[0] != null && targets[1] != null) {
                            Shape line = new Line2D.Float(targets[0].getX(), targets[0].getY(), (float) targets[1].getX(), (float) targets[1].getY());
                            shapes.add(line);
                            controller.setNeighbours(targets);
                        }
                    }

                    startDrag = null;
                    endDrag = null;
                    repaint();
                }

                private double lineLength(int startX, int startY, int targetX, int targetY) {
                    double a = Math.abs(startX - targetX);
                    double b = Math.abs(startY - targetY);

                    double distance = Math.sqrt((a * a) + (b * b));
                    return distance;
                }

                private Ellipse2D getEllipseFromCenter(double x, double y, double width, double height) {
                    double newX = x - width / 2.0;
                    double newY = y - height / 2.0;

                    Ellipse2D ellipse = new Ellipse2D.Double(newX, newY, width, height);

                    return ellipse;
                }

                //checks whether the user is trying to draw the line in an existing node. Returns the center of the node or null if there's no node
                private Node[] targetNodes(Point start, Point end) {
                    boolean startin;
                    boolean endin;
                    Node[] targets = new Node[2];            //point[0] is the start node's center and point[1] the end node's center
                    Ellipse2D ellipse = null;
                    for (Shape s : shapes) {
                        if (s instanceof Ellipse2D && s.contains(start)) {
                            ellipse = (Ellipse2D) s;
                            int x = (int) ellipse.getCenterX();
                            int y = (int) ellipse.getCenterY();
                            targets[0] = nodeCircles.get(s);

                        } else if (s instanceof Ellipse2D && s.contains(end) && s != ellipse) {
                            ellipse = (Ellipse2D) s;
                            int x = (int) ellipse.getCenterX();
                            int y = (int) ellipse.getCenterY();
                            targets[1] = nodeCircles.get(s);
                        }
                    }
                    return targets;
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

        public void showResult(Node[] result) {
            invalidate();
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
            Color[] colors = {Color.YELLOW, Color.BLUE,};
            //Color[] colors = {Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK};
            int colorIndex = 0;

            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes) {

                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(colors[(colorIndex++) % colors.length]);
                g2.fill(s);

            }

            for (Map.Entry<Node, Point> entry : centers.entrySet()) {
                g2.setPaint(Color.BLACK);
                Point point = entry.getValue();
                Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 12);

                g2.drawString((entry.getKey().getId() + ""), point.x, point.y);
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
