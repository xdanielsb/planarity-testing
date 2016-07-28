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
    private Control control;

    public Listener(Window window) {
        this.window = window;
        this.control = window.getControl();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == window.getEraseNodes()) {
            control.setNodos(new ArrayList<Node>());
            control.setRectas(new ArrayList<Straight>());
            control.setIntersections(new ArrayList<Node>());
            window.getComponent().repaint();
            window.addMatrix(0);
        } else if (e.getSource() == window.getAdjacencyMatriz()) {
            window.addMatrix(window.getControl().getNode().size());
        } else {
            int cant_vertices = window.getControl().getNode().size();
            System.out.println("num Vertices " + cant_vertices);
            //Help me to know what raddio button has been clicked
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

    /**
     * This method color the planes of the graph
     */
    public void colorGraph() {
        int numEdges = control.getNode().size();
        control.setIntersections(new ArrayList<Node>());
        boolean[][] mat = window.createMatrix();
        Node origen;
        Node destino;
        control.showNode();
        control.setRectas(new ArrayList<Straight>());
        ArrayList<Straight> rectas = new ArrayList<>();
        boolean collideStraights = false;
        for (int i = 0; i < numEdges; i++) {
            origen = control.getNode(i);
            ArrayList<Node> conectados = new ArrayList<>();
            for (int j = 0; j < numEdges; j++) {
                if (mat[i][j] && i < j) {
                    destino = control.getNode(j);
                    conectados.add(destino);
                    origen.setLinkedNodes(conectados);
                    rectas.add(new Straight(origen, destino, window.getControl()));
                }
            }
            control.setRectas(rectas);
            control.showStraights();
            collideStraights = control.straightCollide();
        };
        
        //Si no chocan las rectas
        if (!collideStraights) {
            control.setIntersections(new ArrayList<Node>());
            control.getPoligonos();
        }        
        window.getComponent().repaint();
    }

}
