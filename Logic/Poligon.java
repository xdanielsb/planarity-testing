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
public class Poligon {
    
    private int id;
    private Integer[] poligono;

    public Poligon(int id, Integer[] poligono) {
        this.poligono = poligono;
    }

    public Poligon(int i, ArrayList<Nodo> recorridos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer[] getPoligono() {
        return poligono;
    }
    

    
    
    
    
    
}
