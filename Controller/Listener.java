/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Logic.Nodo;
import Logic.Recta;
import View.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Listener implements ActionListener {

    private Ventana ventana;

    public Listener(Ventana ventana) {
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ventana.getBorrarPuntos()) {
            System.out.println("Borrar Puntos");
            ventana.getControl().setNodos(new ArrayList<>());
            ventana.getControl().setRectas(new ArrayList<>());
            ventana.getControl().setIntersecciones(new ArrayList<>());
            ventana.getCaja().repaint();
            ventana.addMatrix(0);
        } else if (e.getSource() == ventana.getColorear()) {

            System.out.println("Colorear");
            colorear();

        } else if (e.getSource() == ventana.getMatrixAdjacencia()) {
            System.out.println("Matrix de adjacencia");
            ventana.addMatrix(ventana.getControl().getNodos().size());
        } else {

            int cant_vertices = ventana.getControl().getNodos().size();
            System.out.println("num Vertices " + cant_vertices);
            for (int i = 0; i < cant_vertices; i++) {
                for (int j = 0; j < cant_vertices; j++) {
                    if (j != i) {
                        if (e.getSource().equals(ventana.getMatriz_grafo()[i][j])) {
                            if (ventana.getMatriz_grafo()[i][j].isSelected()) {
                                this.ventana.getMatriz_grafo()[j][i].setSelected(true);
                            } else {
                                this.ventana.getMatriz_grafo()[j][i].setSelected(false);
                            }
                        }
                    }
                }
            }
            colorear();

        }

    }

    public void colorear() {
        int cant_vertices = ventana.getControl().getNodos().size();
        ventana.getControl().setIntersecciones(new ArrayList<>());
        boolean[][] mat = ventana.obtenerMatriz();
        Nodo origen, destino;

        ventana.getControl().showNodo();
        ventana.getControl().setRectas(new ArrayList<>());
        ArrayList<Recta> rectas = new ArrayList<>();
        boolean t = false;
        for (int i = 0; i < cant_vertices; i++) {
            origen = ventana.getControl().getNodo(i);
            ArrayList<Nodo> conectados = new ArrayList<>();
            for (int j = 0; j < cant_vertices; j++) {
                if (mat[i][j] && i<j) {
                    System.out.println(i + " " + j + " " + mat[i][j]);
                    destino = ventana.getControl().getNodo(j);
                    conectados.add(destino);
                    origen.setConectados(conectados);
                    rectas.add(new Recta(origen, destino, ventana.getControl()));
                }
            }
            ventana.getControl().setRectas(rectas);
            ventana.getControl().showRectas();
            t = ventana.getControl().chocan();
            System.out.println(t);
        }

        //Si no chocan las rectas
        if (!t) {
            ventana.getControl().setIntersecciones(new ArrayList<>());
            ///Ahora a dibujar los poligonos
            
            //Crear los poligonos
            ventana.getControl().getPoligonos();
            ventana.getControl().showPoligonos();
            
            
        }
        System.out.println("");

        ventana.getCaja().repaint();
    }

}
