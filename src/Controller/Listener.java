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

    private Window ventana;

    public Listener(Window ventana) {
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ventana.getBorrarPuntos()) {
            System.out.println("Borrar Puntos");
            ventana.getControl().setNodos(new ArrayList<Node>());
            ventana.getControl().setRectas(new ArrayList<Straight>());
            ventana.getControl().setIntersecciones(new ArrayList<Node>());

            ventana.getComponent().repaint();
            ventana.addMatrix(0);
        } else if (e.getSource() == ventana.getColorear()) {

            System.out.println("Colorear");
            colorear();

        } else if (e.getSource() == ventana.getMatrixAdjacencia()) {
            System.out.println("Matrix de adjacencia");
            ventana.addMatrix(ventana.getControl().getNode().size());
        } else {

            int cant_vertices = ventana.getControl().getNode().size();
            System.out.println("num Vertices " + cant_vertices);
            for (int i = 0; i < cant_vertices; i++) {
                for (int j = 0; j < cant_vertices; j++) {
                    if (j != i) {
                        if (e.getSource().equals(ventana.getAdjMatrix()[i][j])) {
                            if (ventana.getAdjMatrix()[i][j].isSelected()) {
                                this.ventana.getAdjMatrix()[j][i].setSelected(true);
                            } else {
                                this.ventana.getAdjMatrix()[j][i].setSelected(false);
                            }
                        }
                    }
                }
            }
            colorear();
        }
    }

    public void colorear() {
        int cant_vertices = ventana.getControl().getNode().size();
        ventana.getControl().setIntersecciones(new ArrayList<Node>());
        boolean[][] mat = ventana.createMatrix();
        Node origen;
        Node destino;

        ventana.getControl().showNodo();
        ventana.getControl().setRectas(new ArrayList<Straight>());
        ArrayList<Straight> rectas = new ArrayList<>();
        boolean t = false;
        for (int i = 0; i < cant_vertices; i++) {
            origen = ventana.getControl().getNodo(i);
            ArrayList<Node> conectados = new ArrayList<>();
            for (int j = 0; j < cant_vertices; j++) {
                if (mat[i][j] && i < j) {
                    System.out.println(i + " " + j + " " + mat[i][j]);
                    destino = ventana.getControl().getNodo(j);
                    conectados.add(destino);
                    origen.setConectados(conectados);
                    rectas.add(new Straight(origen, destino, ventana.getControl()));
                }
            }
            ventana.getControl().setRectas(rectas);
            ventana.getControl().showRectas();
            t = ventana.getControl().chocan();
            System.out.println(t);
        }

        //Si no chocan las rectas
        if (!t) {
            ventana.getControl().setIntersecciones(new ArrayList<Node>());
            ventana.getControl().getPoligonos();
        }
        System.out.println("");
        ventana.getComponent().repaint();
    }

}
