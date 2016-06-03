/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Control;
import Logic.Node;
import Logic.Polygon;
import Logic.Straight;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author daniel
 */
public class ComponentPlot extends JComponent implements MouseListener {

    private Control control;
    Graphics g;

    public ComponentPlot(Control control) {
        this.control = control;
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        this.g = g;
        Graphics2D draw = (Graphics2D) g;

        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        plotNodes(g);
        g.setColor(Color.RED);
        plotRelations(g);
        g.setColor(Color.GREEN);
        plotIntersections(g);
        if (control.getIntersecciones().size() == 0) {
            plotPlanes(g);
        }
        /*
        g.setColor(Color.BLACK);
        dibujarNodos(g);
        g.setColor(Color.RED);
        dibujarRelaciones(g);*/
    }

    public void Clear() {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void plotNodes(Graphics g) {
        ArrayList<Node> nodos = control.getNode();
        for (int i = 0; i < nodos.size(); i++) {
            Node aux = nodos.get(i);
            if (aux.getId() == -1) {
                g.setColor(Color.BLUE);
                // g.drawString(Integer.toString(aux.getId()) + "(" + aux.getPosX() + " " + aux.getPosyY() + ")", (int) aux.getPosX(), (int) aux.getPosyY());
                g.drawString(Integer.toString(aux.getId() + 1), (int) aux.getPosX(), (int) aux.getPosyY());
                g.setColor(Color.BLACK);
            } else {
                // g.drawString(Integer.toString(aux.getId()) + "(" + aux.getPosX() + " " + aux.getPosyY() + ")", (int) aux.getPosX(), (int) aux.getPosyY());
                g.drawString(Integer.toString(aux.getId() + 1), (int) aux.getPosX(), (int) aux.getPosyY());
            }
        }
    }

    public void plotRelations(Graphics g) {
        ArrayList<Straight> rectas = control.getRectas();
        for (int i = 0; i < rectas.size(); i++) {
            Node origen = rectas.get(i).getOrigin();
            Node destino = rectas.get(i).getDestination();
            g.drawLine((int) origen.getPosX(), (int) origen.getPosyY(), (int) destino.getPosX(), (int) destino.getPosyY());
        }
    }

    public void plotIntersections(Graphics g) {
        ArrayList<Node> inter = control.getIntersecciones();
        for (int i = 0; i < inter.size(); i++) {
            Node aux = inter.get(i);
            g.drawString(Integer.toString(aux.getId()) + "(" + aux.getPosX() + " " + aux.getPosyY() + ")", (int) aux.getPosX(), (int) aux.getPosyY());
        }
    }

    public void plotPlanes(Graphics g) {
        int o = 8;
        ArrayList<Polygon> inter = control.getSidesPolinomio();
        Color arry[] = {Color.PINK, Color.CYAN, Color.YELLOW, Color.ORANGE, Color.green
        , Color.MAGENTA,Color.cyan};
        for (int i = 0; i < inter.size(); i++) {
            Random r = new Random();
            int n = r.nextInt((arry.length) );
            g.setColor(arry[n]);
            Integer[] nodes = inter.get(i).getPoligono();
            int tam = nodes.length;
            int x[] = new int[tam];
            int y[] = new int[tam];
            for (int j = 0; j < tam; j++) {
                Node a = control.getNodo(nodes[j]);
                if (a != null) {
                    x[j] = (int) a.getPosX();
                    y[j] = (int) a.getPosyY();
                }
            }
            g.drawPolygon(x, y, tam);
            g.fillPolygon(x, y, tam);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        ArrayList<Node> nodos = control.getNode();
        nodos.add(new Node(nodos.size(), e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //     System.out.println(e.getX()+ " "+e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //    System.out.println(e.getX()+ " "+e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //    System.out.println(e.getX()+ " "+e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //   System.out.println(e.getX()+ " "+e.getY());
    }
}
