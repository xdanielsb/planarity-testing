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
 * @author Daniel Santos
 */
public class Window extends JFrame {

    private JButton matrixAdjacencia;
    private JButton borrarPuntos;
    private ComponentPlot caja;
    private Listener listener;
    private JPanel panel_matriz;
    private JRadioButton[][] matriz_grafo;
    private Control control;
    private JScrollPane scroll_matriz;
    private int width;
    private int height;

    public Window(Control control) {
        this.width = 1000;
        this.height = 500;

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.control = control;
        listener = new Listener(this);

        matrixAdjacencia = new JButton("Adjacency matrix");
        borrarPuntos = new JButton("Erase Nodes");
        
        caja = new ComponentPlot(control);

        matrixAdjacencia.addActionListener(listener);
        borrarPuntos.addActionListener(listener);
        
        int x = 200;
        int y = 10;

        add(matrixAdjacencia);
        matrixAdjacencia.setBounds(100 + x, 20 + y, 150, 40);
        add(borrarPuntos);
        borrarPuntos.setBounds(300 + x, 20 + y, 150, 40);
        //  add(colorear);
        add(caja);
        caja.setBounds(40, 70 + y, 400, 400);
        addMatrix(0);

        this.setVisible(true);
    }

    public void addMatrix(int vertices) {
        if (scroll_matriz != null) {
            this.remove(this.scroll_matriz);
        }
        this.panel_matriz = new JPanel();
        this.panel_matriz.setBackground(Color.white);
        this.panel_matriz.setLayout(null);
        this.panel_matriz.setPreferredSize(new Dimension(20 + getSizeMatriz(), 20 + getSizeMatriz()));

        this.matriz_grafo = new JRadioButton[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {

                if (j == 0) {
                    JLabel digito = new JLabel((i + 1) + "");
                    this.panel_matriz.add(digito);
                  
                    digito.setBounds(35 + getCenter() + 40 * i, getCenter()  + 40 * j, 40, 40);
                    
                }
                if (i == 0) {
                    JLabel digito = new JLabel((j + 1) + "");
                    this.panel_matriz.add(digito);
                    digito.setBounds( getCenter() + 40 * i, 40+getCenter() - 2 + 40 * j, 40, 40);
                }

                this.matriz_grafo[i][j] = new JRadioButton();
                this.panel_matriz.add(this.matriz_grafo[i][j]);
                this.matriz_grafo[i][j].setBounds(getCenter()+20 + 40 * i, getCenter()+40 + 40 * j, 40, 40);
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

    public JButton getAdjacencyMatriz() {
        return matrixAdjacencia;
    }

    public JButton getEraseNodes() {
        return borrarPuntos;
    }


    private int getCenter() {
        int numVertices = this.control.getNode().size();
        
        if (400 - (numVertices * 40) <= 0) {
            return 0;
        }
        return ((400 - (numVertices * 40)) / 2);
    }
    
    private int getSizeMatriz() {
        int numVertices = this.control.getNode().size();
        if ((numVertices * 40) <= 480) {
            return 480;
        }
        return ((numVertices * 40));
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
