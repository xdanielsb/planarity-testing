/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Logic.Node;
import Logic.Polygon;
import Logic.Straight;
import View.Window;
import findCiclos.Ciclos_elementales;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

/**
 *
 * @author Daniel Santos
 */
public class Control {

    private ArrayList<Node> nodes;
    private ArrayList<Straight> straights;
    private ArrayList<Node> intersections;
    private ArrayList<Polygon> polygons;
    private HashMap hashCicles;
    private Window window;

    public Control() {
        nodes = new ArrayList<>();
        straights = new ArrayList<>();
        polygons = new ArrayList<>();
        intersections = new ArrayList<>();
        hashCicles = new HashMap();
        window = new Window(this);
    }

   /**
    * Get the Nodo based on its id
    * @param id
    * @return 
    */
    public Node getNode(int id) {
        Node get = null;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n.getId() == id) {
                get = n;
            }
        }
        return get;
    }

    /**
     * Show  the nodes of the graph
     */
    public void showNode() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).getId();
            System.out.println("id: " + nodes.get(i).getId());
        }
    }
    /**
     * Show the straight of the graph
     */
    public void showStraights() {
        for (int i = 0; i < straights.size(); i++) {
            System.out.println(straights.get(i).getOrigin().getId() + " " + straights.get(i).getDestination().getId());
        }
    }
    
    /**
     * 
     * @param intersecciones 
     */
    public void setIntersections(ArrayList<Node> intersecciones) {
        this.intersections = intersecciones;
    }

    /**
     * This method help me to know wheter straights collide
     * @return bool
     */
    public boolean straightCollide() {
        boolean chocan = false;
        System.out.println(straights.size());
        for (int i = 0; i < straights.size(); i++) {
            Straight origen = straights.get(i);
            for (int j = 0; j < straights.size(); j++) {
                Straight destino = straights.get(j);
                if (origen != destino && origen.collide(destino)) {
                    chocan = true;
                }
            }
        }
        return chocan;
    }

    /**
     * Get the poligonos of the graph
     * @return 
     */
    public ArrayList<Polygon> getPoligonos() {
        ArrayList<Node> nodos = window.getControl().getNode();
        for (int j = 0; j < nodos.size(); j++) {
            Node aux = nodos.get(j);
            System.out.print(aux.getId() + " ->");
            ArrayList<Node> amigos = aux.getLinkedNodes();
            for (int k = 0; k < amigos.size(); k++) {
                Node amigo = amigos.get(k);
                System.out.print(amigo.getId() + " ");
            }
            System.out.println("");
        }
        polygons = new ArrayList<>();
        ArrayList<Node> lista = new ArrayList<>();
        createPolygon(nodos.get(0), lista);
        return polygons;
    }

    /**
     * Create the poligonos based on the cycles of the graph
     * @param inicio
     * @param recorridos 
     */
    public void createPolygon(Node inicio, ArrayList<Node> recorridos) {
        getCycles(window.createMatrix(), getNodes());
    }

    /**
     * Get cycles of the graph based on its adjcency matrix
     * @param adjMatrix
     * @param nodes 
     */
    public void getCycles(boolean adjMatrix[][], String nodes[]) {
        ArrayList<Integer[]> elementos = new ArrayList<>();
        hashCicles = new HashMap();
        Ciclos_elementales ecs = new Ciclos_elementales(adjMatrix, nodes);
        List cycles = ecs.get_ciclos_basicos();
        for (int i = 0; i < cycles.size(); i++) {
            List cycle = (List) cycles.get(i);
            Integer[] array = new Integer[cycle.size()];
            for (int j = 0; j < cycle.size(); j++) {
                String node = (String) cycle.get(j);
                if (cycle.size() > 2) {
                    array[j] = Integer.parseInt(node);
                }
            }
            if (array.length > 2) {
                //Arrays.sort(array);
                String aux = "";
                for (int j = 0; j < array.length; j++) {
                    aux += array[j] + "-";
                }
                hashCicles.put(aux, array);
            }
        }


        iterate();
        iterateOrderlyPoligons();
        iterate();
        Set set = hashCicles.entrySet();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();

            System.out.print(me.getKey() + ": ");
            Integer[] array2 = (Integer[]) me.getValue();
            for (int j = 0; j < array2.length; j++) {
                System.out.print(array2[j] + " ");
            }
            System.out.println();
        }

    }
    /**
     * Iterate over the polygons
     */
    public void iterate() {
        for (int j = 0; j < hashCicles.size(); j++) {
            Set set = hashCicles.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                if (Duplicate((String) me.getKey())) {
                    break;
                }
            }
        }
    }

    /**
     * Iterate over the polygons for removing the composed 
     * Polygons
     */
    public void iterateOrderlyPoligons() {
        Set set = hashCicles.entrySet();
        Iterator i = set.iterator();
        HashMap hm2 = new HashMap();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Integer[] array = (Integer[]) me.getValue();
            Integer[] array2 = array.clone();
            Arrays.sort(array);
            String aux = "";
            for (int j = 0; j < array.length; j++) {
                aux += array[j] + "-";
            }
            hm2.put(aux, array2);
        }
        hashCicles = hm2;
    }

    public boolean Duplicate(String key) {
        boolean el = false;
        Set set = hashCicles.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            String k = (String) me.getKey();
            if (k.contains(key) && k.length() != key.length()) {
                i.remove();
                el = true;
            }
        }
        return el;
    }

    /**
     * Get Nodes of the graph
     * @return nodes 
     */
    public String[] getNodes() {
        String nodes[] = new String[this.nodes.size()];
        for (int i = 0; i < this.nodes.size(); i++) {
            nodes[i] = i + "";
        }
        return nodes;
    }
    
    /**
     * Get the sides of the Poligon
     * @return 
     */
    public ArrayList<Polygon> getSidesPolinomio() {
        ArrayList<Polygon> poligonos = new ArrayList<>();
        Set set = hashCicles.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Polygon p = new Polygon(poligonos.size() + 1, (Integer[]) me.getValue());
            poligonos.add(p);
        }
        return poligonos;
    }
    
    public ArrayList<Node> getNode() {
        return nodes;
    }

    public void setNodos(ArrayList<Node> nodos) {
        this.nodes = nodos;
    }
    
    /**
     * Get Straight of the graph
     * @return 
     */
    public ArrayList<Straight> getStraights() {
        return straights;
    }

    public void setRectas(ArrayList<Straight> rectas) {
        this.straights = rectas;
    }

    public ArrayList<Node> getIntersecciones() {
        return intersections;
    }

}
