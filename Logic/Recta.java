/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Controller.Control;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Recta {

    private Nodo origen;
    private Nodo destino;

    private double pendiente;
    private double intercepto;

    private Control control;

    public Recta(Nodo origen, Nodo destino, Control control) {
        this.destino = destino;
        this.origen = origen;
        this.control = control;

        calcPendinte();
        calcIntercepto();
    }

    public void calcPendinte() {
        System.out.println("m= (" + destino.getPosyY() + "-" + origen.getPosyY() + ")/(" + destino.getPosX() + "-" + origen.getPosX() + ")");
        this.pendiente = (destino.getPosyY() - origen.getPosyY()) / (destino.getPosX() - origen.getPosX());
    }

    public void calcIntercepto() {
        this.intercepto = origen.getPosyY() - pendiente * origen.getPosX();

        System.out.println("y = " + Double.toString(pendiente) + "x +" + Double.toString(intercepto));
        System.out.println("");
    }

    //this method said me if the  straights collide 
    public boolean choca(Recta r2) {
        double xintercpt = (r2.getIntercepto() - this.getIntercepto()) / (this.getPendiente() - r2.getPendiente());

        double y1 = r2.getPendiente() * xintercpt + r2.getIntercepto();
        double y2 = this.getPendiente() * xintercpt + this.getIntercepto();
        boolean r1e = false, r2e = false, esquinas = true;
        //Locations bounds straights
        //r1
        //chech that the intercept in the nodes
        //  System.out.println("y1 = " + y1);
        // System.out.println("y2 = " + y2);
        if ((int) y1 == (int) y2) {

            //System.out.println("possible intercept : (" + xintercpt + " " + y1 + ")");
            double origenr1x = origen.getPosX();
            double origenr1y = origen.getPosyY();
            double destinor1x = destino.getPosX();
            double destinor1y = destino.getPosyY();

            //r2
            double origenr2x = r2.getOrigen().getPosX();
            double origenr2y = r2.getOrigen().getPosyY();
            double destinor2x = r2.getDestino().getPosX();
            double destinor2y = r2.getDestino().getPosyY();
            if (destinor1x != destinor2x && destinor1y != destinor2y) {
                if (origenr1x != origenr2x && origenr1y != origenr2y) {
                    if (origenr1x != destinor2x && origenr1y != destinor2y) {
                        if (destinor1x != origenr2x && destinor1y != origenr2y) {
                            esquinas = false;
                        }
                    }
                }
            }
            
            r1e = checkLimites(origenr1x, origenr1y, destinor1x, destinor1y, xintercpt, y1);
            r2e = checkLimites(origenr2x, origenr2y, destinor2x, destinor2y, xintercpt, y1);
            //       System.out.println(r1e + " (" + origenr1x + " " + origenr1y + ") (" + destinor1x + " " + destinor1y + ")");
            //      System.out.println(r2e + " (" + origenr2x + " " + origenr2y + ") (" + destinor2x + " " + destinor2y + ")");

        }
        if (r1e && r2e && (int) y1 == (int) y2 && !esquinas) {
            System.out.println("Las rectas (" + origen.getId() + " " + destino.getId() + ")" + "( " + r2.getOrigen().getId() + " " + r2.getDestino().getId() + ")");
            System.out.println("punto de choque = (" + xintercpt + "," + y1 + ")");

            //For plotting the intercept
            ArrayList<Nodo> inter = control.getIntersecciones();
            inter.add(new Nodo(-1, (int) xintercpt, (int) y1));
            return true;
        } else {
            //control.setIntersecciones(new ArrayList<>());
            //System.out.println("The straights dont collide");
            return false;
        }

    }

    public boolean checkLimites(double origenr1x, double origenr1y, double destinor1x, double destinor1y, double xintercpt, double y1) {
        //Check the bounds of x
        boolean estalimites = false; //si se encuentra dentro de los limites
        if (origenr1x < destinor1x) {
            if (origenr1x < xintercpt && destinor1x > xintercpt) {
                estalimites = true;
            } else {
                estalimites = false;
            }
        } else if (origenr1x > xintercpt && destinor1x < xintercpt) {
            estalimites = true;
        } else {
            estalimites = false;
        }
        //check the bounds of y
        if (origenr1y < destinor1y) {
            if (origenr1y < y1 && destinor1y > y1) {
                estalimites = true;
            } else {
                estalimites = false;
            }
        } else if (origenr1y > y1 && destinor1y < y1) {
            estalimites = true;
        } else {
            estalimites = false;
        }
        return estalimites;

    }

    public double getPendiente() {
        return pendiente;
    }

    public void setPendiente(double pendiente) {
        this.pendiente = pendiente;
    }

    public double getIntercepto() {
        return intercepto;
    }

    public void setIntercepto(double intercepto) {
        this.intercepto = intercepto;
    }

    public Nodo getOrigen() {
        return origen;
    }

    public Nodo getDestino() {
        return destino;
    }

}
