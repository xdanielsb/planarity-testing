/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Control;
import Controller.Listener;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author daniel
 */
public class Window extends JFrame {

    private JButton matrixAdjacencia;
    private JButton borrarPuntos;
    private JButton colorear;
    private ComponentPlot caja;
    private Listener listener;
    private JPanel panel_matriz;
    private JRadioButton[][] matriz_grafo;
    private Control control;
    private JScrollPane scroll_matriz;

    public Window(Control control) {
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.control = control;
        listener = new Listener(this);

        matrixAdjacencia = new JButton("Matriz de Adjacencia");
        borrarPuntos = new JButton("borrar puntos");
        colorear = new JButton("colorear");
        caja = new ComponentPlot(control);

        matrixAdjacencia.addActionListener(listener);
        borrarPuntos.addActionListener(listener);
        colorear.addActionListener(listener);

        int x = 200;
        int y = 10;

        add(matrixAdjacencia);
        matrixAdjacencia.setBounds(100 + x, 20 + y, 150, 40);
        add(borrarPuntos);
        borrarPuntos.setBounds(300 + x, 20 + y, 150, 40);
      //  add(colorear);
        colorear.setBounds(500 + x, 20 + y, 150, 40);
        add(caja);
        caja.setBounds(40, 70 + y, 400, 400);
        addMatrix(0);
        this.setVisible(true);
    }

    public void addMatrix(int vertices) {
        this.panel_matriz = new JPanel();
        this.panel_matriz.setBackground(Color.white);
        this.panel_matriz.setLayout(null);
        this.panel_matriz.setPreferredSize(new Dimension(20 + getCenter(), 20 + getCenter()));

        this.matriz_grafo = new JRadioButton[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {

                if (j == 0) {
                    JLabel digito = new JLabel((i + 1) + "");
                    this.panel_matriz.add(digito);
                    digito.setBounds(15 + getCenter() + 40 * i, getCenter() - 30 + 40 * j, 40, 40);
                }
                if (i == 0) {
                    JLabel digito = new JLabel((j + 1) + "");
                    this.panel_matriz.add(digito);
                    digito.setBounds(-20 + getCenter() + 40 * i, getCenter() - 2 + 40 * j, 40, 40);
                }

                this.matriz_grafo[i][j] = new JRadioButton();
                this.panel_matriz.add(this.matriz_grafo[i][j]);
                this.matriz_grafo[i][j].setBounds(getCenter() + 40 * i, getCenter() + 40 * j, 40, 40);
                this.matriz_grafo[i][j].setBackground(Color.white);
                this.matriz_grafo[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                if (j >= i) {
                    this.matriz_grafo[i][j].setEnabled(false);
                } else {
                    this.matriz_grafo[i][j].addActionListener(listener);
                }

            }
        }
        this.scroll_matriz = new JScrollPane(panel_matriz);
        this.add(scroll_matriz, 0);
        this.scroll_matriz.setBounds(550, 80, 400, 400);
        this.scroll_matriz.updateUI();
    }

    public JButton getMatrixAdjacencia() {
        return matrixAdjacencia;
    }

    public JButton getBorrarPuntos() {
        return borrarPuntos;
    }


    public JButton getColorear() {
        return colorear;
    }


    private int getCenter() {
        int numVertices = this.control.getNode().size();
        if (400 - (numVertices * 40) <= 0) {
            return 0;
        }
        return (400 - (numVertices * 40)) / 2;
    }

    public Control getControl() {
        return control;
    }

    public ComponentPlot getComponent() {
        return caja;
    }


    public JRadioButton[][] getAdjMatrix() {
        return matriz_grafo;
    }

    public boolean[][] createMatrix() {
        int numVertices = this.control.getNode().size();
        boolean[][] retorno = new boolean[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matriz_grafo != null) {
                    if (this.matriz_grafo[i][j].isSelected()) {
                        retorno[i][j] = true;
                    } else {
                        retorno[i][j] = false;
                    }
                }
            }
        }
        return retorno;
    }

}
