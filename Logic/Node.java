/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;

/**
 *
 * @author daniel fernando santos bustos
 */
public class Node {
    private int id;
    private double posX;
    private double posyY;
    private ArrayList<Node> conectados;

    public Node(int id,double posX, double posyY) {
        this.id = id;
        this.posX = posX;
        this.posyY = posyY;
        conectados = new  ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosyY() {
        return posyY;
    }

    public void setPosyY(double posyY) {
        this.posyY = posyY;
    }

    public void setPosyY(int posyY) {
        this.posyY = posyY;
    }

    public ArrayList<Node> getConectados() {
        return conectados;
    }

    public void setConectados(ArrayList<Node> conectados) {
        this.conectados = conectados;
    }


    
}
