/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tira.Graph;
import tira.Node;

/**
 *
 * @author Pete
 */
//http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Mousedraganddraw.htm
public class Window extends JFrame {

    private Controller controller;
    private JButton graphMaker;
    private JButton bfs;
    private JTextField graphSide;
    private JTextField textfield;
    private JPanel controlArea;
    private PaintSurface drawer;
    private HashMap<Integer, Node> nodeMap;
    private HashMap<Shape, Color> shapeColors;
    private ArrayList<Node> results;

    public Window(Controller controller) {
        this.controller = controller;
        this.shapeColors = new HashMap();
        createComponents();
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawer = new PaintSurface();
        this.add(drawer, BorderLayout.CENTER);
        this.add(controlArea, BorderLayout.SOUTH);
        results = new ArrayList();
        this.setVisible(true);
    }

    private void addListener(JButton button, String funktio) {
        if (funktio.equals("make graph")) {
            graphMaker.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nodeMap = new HashMap();
                    drawer.nodeSquares = new HashMap();
                    drawer.shapes = new ArrayList();
                    drawer.target = null;
                    drawer.origin = null;
                    int graphSize = getNumericValue(graphSide.getText());
                    if (graphSize != -1) {
                        controller.createGraph(graphSize);
                        nodeMap = controller.makeNodes(graphSize * graphSize);
                        drawer.nodes = graphSize * graphSize;
                        drawer.addRectangleNodes();
                        repaint();
                    }
                }
            });
        }
    }

    private void addBfsListener() {
        bfs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawer.target != null && drawer.origin != null) {
                    Node[] result = controller.getBfsResult(drawer.origin.getId(), drawer.target.getId());
                    drawer.showResult(result);
                }

            }
        });
    }

    private int getNumericValue(String text) {
        int value;
        try {
            value = Integer.valueOf(text);
        } catch (NumberFormatException e) {
            return -1;
        }
        return value;
    }

    private void createComponents() {
        this.controlArea = new JPanel();
        controlArea.setLayout(new FlowLayout());
        this.graphMaker = new JButton("make graph");
        addListener(graphMaker, "make graph");
        this.bfs = new JButton("Search with bfs");
        addBfsListener();
        this.graphSide = new JTextField("10", 10);
        this.textfield = new JTextField("", 6);
        controlArea.add(bfs);
        controlArea.add(graphMaker);
        controlArea.add(graphSide);
        controlArea.add(textfield);
    }

    private class PaintSurface extends JComponent {

        ArrayList<Shape> shapes = new ArrayList<Shape>();
        HashMap<Node, Point> centers = new HashMap();
        HashMap<Shape, Node> nodeSquares = new HashMap();
        int nodes;
        Point startDrag, endDrag;
        ArrayList cityXCoords = new ArrayList();
        ArrayList cityYCoords = new ArrayList();
        Node origin;
        Node target;

        public PaintSurface() {
            this.nodes = 0;
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (!cityXCoords.contains(e.getX()) && !cityYCoords.contains(e.getY())) {

                        int radius = 15;
                        Shape ellipse = new Ellipse2D.Double(e.getX() - radius, e.getY() - radius, 2.0 * radius, 2.0 * radius);

                        //  shapes.add(ellipse);
                        startDrag = new Point(e.getX(), e.getY());
                        endDrag = startDrag;
                    }
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {

                    Shape clicked = new Ellipse2D.Double(e.getX() - 0, e.getY() - 0, 1.0 * 0, 1.0 * 0);
                    for (Shape s : shapes) {
                        if (s.contains(e.getX(), e.getY())) {
                            clicked = s;
                        }
                    }
                    if (Math.abs(lineLength(startDrag.x, startDrag.y, endDrag.x, endDrag.y)) > 10 && !(clicked instanceof Ellipse2D)) {
                        Node node = nodeSquares.get(clicked);
                        controller.removeNodesAllNeighbours(node);
                        nodeSquares.remove(clicked);
                        nodeMap.remove(node.getId());
                        shapes.remove(clicked);
                        shapeColors.remove(clicked);

                    } else {
                        if (origin == null) {
                            origin = nodeSquares.get(clicked);
                            shapeColors.put(clicked, Color.green);
                        } else if (target == null) {
                            target = nodeSquares.get(clicked);
                            shapeColors.put(clicked, Color.red);
                        }
                    }
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }

//                @Override
//                public void mouseDragged(MouseEvent e) {
//
//                    endDrag = new Point(e.getX(), e.getY());
//                    startDrag = new Point(e.getX(), e.getY());
//                    Shape clicked = new Ellipse2D.Double(e.getX() - 0, e.getY() - 0, 1.0 * 0, 1.0 * 0);
//                    for (Shape s : shapes) {
//                        if (s.contains(e.getX(), e.getY())) {
//                            clicked = s;
//                        }
//                    }
//                    if (Math.abs(lineLength(startDrag.x, startDrag.y, endDrag.x, endDrag.y)) > 10 && !(clicked instanceof Ellipse2D)) {
//                        Node node = nodeSquares.get(clicked);
//                        controller.removeNodesAllNeighbours(node);
//                        nodeSquares.remove(clicked);
//                        nodeMap.remove(node.getId());
//                        shapes.remove(clicked);
//                        shapeColors.remove(clicked);
//                    }
////                    startDrag = null;
////                    endDrag = null;
//                    repaint();
//
//                }
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
                private ArrayList<Node> targetNodes(Point start, Point end) {
                    boolean startin;
                    boolean endin;
                    ArrayList targets = new ArrayList();
                    Ellipse2D ellipse = null;
                    for (Shape s : shapes) {
                        if (s.contains(start)) {
                            ellipse = (Ellipse2D) s;
                            int x = (int) ellipse.getCenterX();
                            int y = (int) ellipse.getCenterY();
                            targets.add(nodeSquares.get(s));

                        } else if (s instanceof Ellipse2D && s.contains(end) && s != ellipse) {
                            ellipse = (Ellipse2D) s;
                            int x = (int) ellipse.getCenterX();
                            int y = (int) ellipse.getCenterY();
                            targets.add(nodeSquares.get(s));
                        }
                    }
                    return targets;
                }
            });

            this.addMouseMotionListener(
                    new MouseMotionAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            endDrag = new Point(e.getX(), e.getY());
                            repaint();
                        }
                    });
        }

        public void showResult(Node[] result) {
            if (target != null && origin != null) {
                for (int i = 0; i < result.length; i++) {

                    for (Shape s : shapes) {
                        if (nodeSquares.get(s) == result[i]) {
                            shapeColors.remove(s);
                            shapeColors.put(s, Color.yellow);
                        }
                    }
                }
            }
            repaint();
        }

        private void paintBackground(Graphics2D g2) {
            g2.setPaint(Color.BLACK);
            for (int i = 0; i < getSize().width; i += 20) {
                Shape line = new Line2D.Float(i, 0, i, getSize().height);
                g2.draw(line);
            }

            for (int i = 0; i < getSize().height; i += 20) {
                Shape line = new Line2D.Float(0, i, getSize().width, i);
                g2.draw(line);
            }
        }

        public void addRectangleNodes() {
            if (nodes > 0) {
                for (int i = 0; i < nodes; i++) {

                    Node node = nodeMap.get(i + 1);
                    int x = node.getX();
                    int y = node.getY();

                    Shape rect = makeRectangle(x, y, x + Node.getWidth(), y + Node.getHeight());

                    if (!nodeSquares.containsKey(rect)) {
                        shapes.add(rect);
                        nodeSquares.put(rect, node);
                        shapeColors.put(rect, Color.BLUE);
                    }

                }
            }
        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color[] colors = {Color.YELLOW, Color.BLUE,};
            //Color[] colors = {Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK};
            int colorIndex = 0;
            //drawGraph();
//
//            Shape re = makeRectangle(60, 100, 60 + Node.getWidth(), 100 + Node.getHeight());
//            shapes.add(re);

            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes) {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(shapeColors.get(s));
//                g2.setPaint(colors[(colorIndex++) % colors.length]);
                g2.fill(s);
            }

//            for (Map.Entry<Node, Point> entry : centers.entrySet()) {
//                g2.setPaint(Color.BLACK);
//                Point point = entry.getValue();
//                Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 12);
//
//                g2.drawString((entry.getKey().getId() + ""), point.x, point.y);
//            }

            paintBackground(g2);

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
