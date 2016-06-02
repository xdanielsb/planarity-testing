/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planar;

import Controller.Control;
import Logic.Nodo;
import Logic.Recta;
import View.Ventana;

/**
 *
 * @author daniel
 */
public class Planar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*  
        //y = x + 2 x [-3,1]  y [1,3]
        Nodo n1  = new Nodo(-3, -1); 
        Nodo n2  = new Nodo(1, 3);
        
        //y = -x -3
        Nodo n3  = new Nodo(-3, 0);
        Nodo n4  = new Nodo(1, -4);
        
        //Creation of rects
        Recta r1 = new Recta(n1, n2,new Control());
        Recta r2 = new Recta(n3, n4, new Control());
        
        System.out.println(r1.choca(r2));
         */
        new Control();
    }

}
