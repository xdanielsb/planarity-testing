/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Nodo {
    private int id;
    private double posX;
    private double posyY;
    private ArrayList<Nodo> conectados;

    public Nodo(double posX, double posyY) {
        this.posX = posX;
        this.posyY = posyY;
        conectados = new  ArrayList<>();
    }
    
    
    public Nodo(int id,double posX, double posyY) {
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

    public ArrayList<Nodo> getConectados() {
        return conectados;
    }

    public void setConectados(ArrayList<Nodo> conectados) {
        this.conectados = conectados;
    }

    public int tieneHijo(int id) {
        Nodo get = null;
        int cont =0;
        for (int i = 0; i < conectados.size(); i++) {
            Nodo n = conectados.get(i);
            if (n.getId() == id) {
                get = n;
                cont++;
            }
        }
        System.out.println("nhijos "+cont);
        return cont;
    }
    
}
