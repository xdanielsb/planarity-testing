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
public class Straight {

    private Node origin;
    private Node destinination;
    private double slope;
    private double intercept;
    private Control control;

    public Straight(Node origen, Node destino, Control control) {
        this.destinination = destino;
        this.origin = origen;
        this.control = control;
        calcSlope();
        calcIntercept();
    }

    public void calcSlope() {
        //System.out.println("m= (" + destinination.getPosyY() + "-" + origin.getPosyY() + ")/(" + destinination.getPosX() + "-" + origin.getPosX() + ")");
        this.slope = (destinination.getPosyY() - origin.getPosyY()) / (destinination.getPosX() - origin.getPosX());
    }

    public void calcIntercept() {
        this.intercept = origin.getPosyY() - slope * origin.getPosX();
       // System.out.println("y = " + Double.toString(slope) + "x +" + Double.toString(intercept));
       // System.out.println("");
    }

    //this method said me if the  straights collide 
    public boolean collide(Straight r2) {
        double xintercpt = (r2.getIntercept() - this.getIntercept()) / (this.getSlope() - r2.getSlope());

        double y1 = r2.getSlope() * xintercpt + r2.getIntercept();
        double y2 = this.getSlope() * xintercpt + this.getIntercept();
        boolean r1e = false, r2e = false, esquinas = true;
        //Locations bounds straights
        if ((int) y1 == (int) y2) {
            
            //System.out.println("possible intercept : (" + xintercpt + " " + y1 + ")");
            double origenr1x = origin.getPosX();
            double origenr1y = origin.getPosyY();
            double destinor1x = destinination.getPosX();
            double destinor1y = destinination.getPosyY();

            //r2
            double origenr2x = r2.getOrigin().getPosX();
            double origenr2y = r2.getOrigin().getPosyY();
            double destinor2x = r2.getDestination().getPosX();
            double destinor2y = r2.getDestination().getPosyY();
            if (destinor1x != destinor2x && destinor1y != destinor2y) {
                if (origenr1x != origenr2x && origenr1y != origenr2y) {
                    if (origenr1x != destinor2x && origenr1y != destinor2y) {
                        if (destinor1x != origenr2x && destinor1y != origenr2y) {
                            esquinas = false;
                        }
                    }
                }
            }           
            r1e = checkBounds(origenr1x, origenr1y, destinor1x, destinor1y, xintercpt, y1);
            r2e = checkBounds(origenr2x, origenr2y, destinor2x, destinor2y, xintercpt, y1);
        }
        if (r1e && r2e && (int) y1 == (int) y2 && !esquinas) {
            System.out.println("Las rectas (" + origin.getId() + " " + destinination.getId() + ")" + "( " + r2.getOrigin().getId() + " " + r2.getDestination().getId() + ")");
            System.out.println("punto de choque = (" + xintercpt + "," + y1 + ")");
            //For plotting the intercept
            ArrayList<Node> inter = control.getIntersecciones();
            inter.add(new Node(-1, (int) xintercpt, (int) y1));
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBounds(double origenr1x, double origenr1y, double destinor1x, double destinor1y, double xintercpt, double y1) {
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

    public double getSlope() {
        return slope;
    }

    public double getIntercept() {
        return intercept;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destinination;
    }

}
