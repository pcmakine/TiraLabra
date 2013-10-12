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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logicwithmycollections.Node;

/**
 *
 * @author Pete
 */
//http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Mousedraganddraw.htm
public class Window extends JFrame {

    private Controller controller;
    private JButton graphMaker;
    private JButton bfs;
    private JButton astar;
    private JButton randomGraph;
    private JTextField graphSide;
    private JPanel controlArea;
    private PaintSurface drawer;
    private HashMap<Shape, Color> shapeColors;
    private ArrayList<Node> results;
    private Color noNode = new Color(255, 100, 255);
    private int portionofNodesRemoved = 3;

    public Window(Controller controller) {
        this.controller = controller;
        this.shapeColors = new HashMap();
        createComponents();
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawer = new PaintSurface();
        this.add(drawer, BorderLayout.CENTER);
        this.add(controlArea, BorderLayout.SOUTH);
        results = new ArrayList();
        this.setVisible(true);
    }

    private void addMakeGraphListener() {
        graphMaker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initdrawerValues();
                int graphSize = getNumericValue(graphSide.getText());
                if (graphSize != -1) {
                    controller.createGraph(graphSize);
                    drawer.nodes = graphSize * graphSize;
                    drawer.addRectangleNodes();
                    repaint();
                }
            }
        });

    }

    private void addRouteSearchListener(final String function, JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (drawer.target != null && drawer.origin != null) {
                    results = new ArrayList();
                    Node[] result;
                    if (function.equals("bfs")) {
                        result = controller.getBfsResult(drawer.origin.getId(), drawer.target.getId());
                    } else {
                        result = controller.getAstarResult(drawer.origin.getId(), drawer.target.getId());
                    }
                    if (result != null) {
                        drawer.showResult(result);
                    } else {
                        // TODO: let the user know there was no solution
                    }

                }

            }
        });
    }

    private void addRandomGraphListener() {
        randomGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initdrawerValues();

                int graphSize = getNumericValue(graphSide.getText());
                if (graphSize != -1) {
                    controller.makeRandomGraph(graphSize, (graphSize * graphSize) / 3);
                    drawer.nodes = graphSize * graphSize;
                    drawer.addRectangleNodes();
                   // removeManyNodes();
                    repaint();
                }
            }
        });
    }

    private void initdrawerValues() {
        drawer.nodeSquares = new HashMap();
        drawer.shapes = new ArrayList();
        drawer.target = null;
        drawer.origin = null;
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
        addMakeGraphListener();

        this.bfs = new JButton("Search with bfs");
        addRouteSearchListener("bfs", bfs);

        this.astar = new JButton("Search with astar");
        addRouteSearchListener("astar", astar);

        this.randomGraph = new JButton("Random Graph");
        addRandomGraphListener();


        this.graphSide = new JTextField("10", 10);
        controlArea.add(bfs);
        controlArea.add(astar);
        controlArea.add(randomGraph);
        controlArea.add(graphMaker);
        controlArea.add(graphSide);
    }

    private class PaintSurface extends JComponent {

        ArrayList<Shape> shapes = new ArrayList<Shape>();
        HashMap<Shape, Node> nodeSquares = new HashMap();
        HashMap<Node, Shape> shapeNodes = new HashMap();
        int nodes;
        Point startDrag, endDrag;
        Node origin;
        Node target;

        public PaintSurface() {
            this.nodes = 0;
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                }

                public void mouseReleased(MouseEvent e) {
                    Shape clicked = searchForClicked(e);
                    if (Math.abs(lineLength(startDrag.x, startDrag.y, endDrag.x, endDrag.y)) > 10 && !(clicked instanceof Ellipse2D)) {
                        removeNode(clicked);

                    } else {
                        if (origin == null) {
                            origin = nodeSquares.get(clicked);
                            shapeColors.put(clicked, Color.green);
                        } else if (target == null && nodeSquares.get(clicked) != origin) {
                            target = nodeSquares.get(clicked);
                            shapeColors.put(clicked, Color.yellow);
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

        public Shape searchForClicked(MouseEvent e) {
            Shape clicked = new Ellipse2D.Double(e.getX() - 0, e.getY() - 0, 1.0 * 0, 1.0 * 0);
            for (Shape s : shapes) {
                if (s.contains(e.getX(), e.getY())) {
                    clicked = s;
                    return s;
                }
            }
            return clicked;
        }

        private void removeNode(Shape clicked) {
            Node node = nodeSquares.get(clicked);
            int id = node.getId();
            controller.removeNode(node);
            nodeSquares.remove(clicked);
            int row = controller.getGraph().idToRow(id);
            int column = controller.getGraph().idToColumn(id);
            shapeColors.remove(clicked);
            shapeColors.put(clicked, noNode);
            shapeNodes.remove(node);
            nodes--;
        }

        public void showResult(Node[] result) {
            for (int i = 1; i < result.length-1; i++) {
                Shape s = (shapeNodes.get(result[i]));
                shapeColors.remove(s);
                shapeColors.put(s, Color.cyan);
                repaint();
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
            Node[][] n = controller.getGraph().getNodes();
            
            for (int i = 0; i < n.length; i++) {
                for (int j = 0; j < n[0].length; j++) {
                    Node node = n[i][j];

                    if (node != null) {

                        int x = node.getX();
                        int y = node.getY();

                        Shape rect = makeRectangle(x, y, x + Node.getWidth(), y + Node.getHeight());

                        if (!nodeSquares.containsKey(rect)) {
                            shapes.add(rect);
                            nodeSquares.put(rect, node);
                            shapeColors.put(rect, Color.BLUE);
                            shapeNodes.put(node, rect);
                        }
                    } else {
                        int x = j * Node.getWidth() + (Node.getWidth() * controller.getGraph().getPos());
                        int y = i * Node.getWidth() + (Node.getHeight() * controller.getGraph().getPos());
                        Shape r = makeRectangle(x, y, x + Node.getWidth(), y + Node.getHeight());
                        shapes.add(r);
                        shapeColors.put(r, noNode);
                    }
                }
            }
        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color[] colors = {Color.YELLOW, Color.BLUE,};
            int colorIndex = 0;

            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes) {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(shapeColors.get(s));
                g2.fill(s);
            }

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
