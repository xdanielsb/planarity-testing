/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Logic.Node;
import Logic.Straight;
import View.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 *
 * @author Daniel Santos
 */
public class Listener implements ActionListener {

    private Window window;

    public Listener(Window window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == window.getBorrarPuntos()) {
            window.getControl().setNodos(new ArrayList<Node>());
            window.getControl().setRectas(new ArrayList<Straight>());
            window.getControl().setIntersections(new ArrayList<Node>());
            window.getComponent().repaint();
            window.addMatrix(0);
        } else if (e.getSource() == window.getMatrixAdjacencia()) {
            window.addMatrix(window.getControl().getNode().size());
        } else {
            int cant_vertices = window.getControl().getNode().size();
            System.out.println("num Vertices " + cant_vertices);
            for (int i = 0; i < cant_vertices; i++) {
                for (int j = 0; j < cant_vertices; j++) {
                    if (j != i) {
                        if (e.getSource().equals(window.getAdjMatrix()[i][j])) {
                            if (window.getAdjMatrix()[i][j].isSelected()) {
                                this.window.getAdjMatrix()[j][i].setSelected(true);
                            } else {
                                this.window.getAdjMatrix()[j][i].setSelected(false);
                            }
                        }
                    }
                }
            }
            colorGraph();
        }
    }

    public void colorGraph() {
        int cant_vertices = window.getControl().getNode().size();
        window.getControl().setIntersections(new ArrayList<Node>());
        boolean[][] mat = window.createMatrix();
        Node origen;
        Node destino;

        window.getControl().showNode();
        window.getControl().setRectas(new ArrayList<Straight>());
        ArrayList<Straight> rectas = new ArrayList<>();
        boolean t = false;
        for (int i = 0; i < cant_vertices; i++) {
            origen = window.getControl().getNode(i);
            ArrayList<Node> conectados = new ArrayList<>();
            for (int j = 0; j < cant_vertices; j++) {
                if (mat[i][j] && i < j) {
                    System.out.println(i + " " + j + " " + mat[i][j]);
                    destino = window.getControl().getNode(j);
                    conectados.add(destino);
                    origen.setLinkedNodes(conectados);
                    rectas.add(new Straight(origen, destino, window.getControl()));
                }
            }
            window.getControl().setRectas(rectas);
            window.getControl().showStraights();
            t = window.getControl().straightCollide();
            System.out.println(t);
        }

        //Si no chocan las rectas
        if (!t) {
            window.getControl().setIntersections(new ArrayList<Node>());
            window.getControl().getPoligonos();
        }
        System.out.println("");
        window.getComponent().repaint();
    }

}
