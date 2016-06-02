/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Control;
import Logic.Nodo;
import Logic.Poligon;
import Logic.Recta;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author daniel
 */
public class Caja extends JComponent implements MouseListener {

    private Control control;
    Graphics g;

    public Caja(Control control) {
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
        dibujarNodos(g);
        g.setColor(Color.RED);
        dibujarRelaciones(g);
        g.setColor(Color.GREEN);

        dibujarIntersecciones(g);
        if (control.getIntersecciones().size() == 0) {
            dibujarPlanos(g);
        }

        g.setColor(Color.BLACK);
        dibujarNodos(g);
        g.setColor(Color.RED);
        dibujarRelaciones(g);
    }

    public void limpiar() {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void dibujarNodos(Graphics g) {
        ArrayList<Nodo> nodos = control.getNodos();
        for (int i = 0; i < nodos.size(); i++) {
            Nodo aux = nodos.get(i);
            if (aux.getId() == -1) {
                g.setColor(Color.BLUE);
               // g.drawString(Integer.toString(aux.getId()) + "(" + aux.getPosX() + " " + aux.getPosyY() + ")", (int) aux.getPosX(), (int) aux.getPosyY());
                g.drawString(Integer.toString(aux.getId()+1) , (int) aux.getPosX(), (int) aux.getPosyY());
                g.setColor(Color.BLACK);
            } else {
               // g.drawString(Integer.toString(aux.getId()) + "(" + aux.getPosX() + " " + aux.getPosyY() + ")", (int) aux.getPosX(), (int) aux.getPosyY());
                g.drawString(Integer.toString(aux.getId()+1), (int) aux.getPosX(), (int) aux.getPosyY());
            }
        }
    }

    public void dibujarRelaciones(Graphics g) {
        ArrayList<Recta> rectas = control.getRectas();
        for (int i = 0; i < rectas.size(); i++) {
            Nodo origen = rectas.get(i).getOrigen();
            Nodo destino = rectas.get(i).getDestino();
            g.drawLine((int) origen.getPosX(), (int) origen.getPosyY(), (int) destino.getPosX(), (int) destino.getPosyY());

        }
    }

    public void dibujarIntersecciones(Graphics g) {
        ArrayList<Nodo> inter = control.getIntersecciones();
        for (int i = 0; i < inter.size(); i++) {
            Nodo aux = inter.get(i);
            g.drawString(Integer.toString(aux.getId()) + "(" + aux.getPosX() + " " + aux.getPosyY() + ")", (int) aux.getPosX(), (int) aux.getPosyY());
        }
    }

    public void dibujarPlanos(Graphics g) {
        ArrayList<Poligon> inter = control.getSidesPolinomio();
        Color arry[] = {Color.PINK, Color.CYAN, Color.YELLOW, Color.ORANGE, Color.green};
        for (int i = 0; i < inter.size(); i++) {
            g.setColor(arry[i]);
            Integer[] nodes = inter.get(i).getPoligono();
            int tam = nodes.length;
            int x[] = new int[tam];
            int y[] = new int[tam];
            for (int j = 0; j < tam; j++) {
                Nodo a = control.getNodo(nodes[j]);
                x[j] = (int) a.getPosX();
                y[j] = (int) a.getPosyY();

            }
            g.drawPolygon(x, y, tam);
            g.fillPolygon(x, y, tam);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        ArrayList<Nodo> nodos = control.getNodos();
        nodos.add(new Nodo(nodos.size(), e.getX(), e.getY()));
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
