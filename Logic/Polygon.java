/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
/**
 *
 * @author daniel
 */
public class Polygon {
    
    private int id;
    private Integer[] poligono; //The nodes id

    public Polygon(int id, Integer[] poligono) {
        this.poligono = poligono;
    }
    
    public Integer[] getPoligono() {
        return poligono;
    }
        
}
