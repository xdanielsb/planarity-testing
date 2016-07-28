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
 * @author daniel fernando santos bustos
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

    public ArrayList<Node> getNode() {
        return nodes;
    }

    public void setNodos(ArrayList<Node> nodos) {
        this.nodes = nodos;
    }

    public Node getNodo(int id) {
        Node get = null;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n.getId() == id) {
                get = n;
            }
        }
        return get;
    }

    public void showNodo() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).getId();
            System.out.println("id: " + nodes.get(i).getId());
        }
    }

    public void showRectas() {
        for (int i = 0; i < straights.size(); i++) {
            System.out.println(straights.get(i).getOrigin().getId() + " " + straights.get(i).getDestination().getId());
        }
    }

    public ArrayList<Straight> getRectas() {
        return straights;
    }

    public void setRectas(ArrayList<Straight> rectas) {
        this.straights = rectas;
    }

    public ArrayList<Node> getIntersecciones() {
        return intersections;
    }

    public void setIntersecciones(ArrayList<Node> intersecciones) {
        this.intersections = intersecciones;
    }

    public boolean chocan() {
        boolean chocan = false;
        System.out.println(straights.size());
        for (int i = 0; i < straights.size(); i++) {
            Straight origen = straights.get(i);
            for (int j = 0; j < straights.size(); j++) {
                Straight destino = straights.get(j);
                if (origen != destino && origen.collide(destino)) {
                    //System.out.println("Las rectas chocan");
                    chocan = true;
                }
            }
        }
        return chocan;
    }

    public ArrayList<Polygon> getPoligonos() {
        ArrayList<Node> nodos = window.getControl().getNode();
        for (int j = 0; j < nodos.size(); j++) {
            Node aux = nodos.get(j);
            System.out.print(aux.getId() + " ->");
            ArrayList<Node> amigos = aux.getConectados();
            for (int k = 0; k < amigos.size(); k++) {
                Node amigo = amigos.get(k);
                System.out.print(amigo.getId() + " ");
            }
            System.out.println("");
        }
        polygons = new ArrayList<>();
        ArrayList<Node> lista = new ArrayList<>();
        crearPoligono(nodos.get(0), lista);
        return polygons;
    }

    public void crearPoligono(Node inicio, ArrayList<Node> recorridos) {
        getCiclos(window.createMatrix(), getNodes());
    }

    public boolean esta(ArrayList<Node> recorridos, Node element) {
        for (int i = 0; i < recorridos.size(); i++) {
            if (recorridos.get(i).getId() == element.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean esta(Node element, int desde) {
        System.out.print("(" + desde + " " + element.getId() + ") ");
        boolean val = false;
        for (int i = desde; i < nodes.size(); i++) {
            ArrayList<Node> nod = nodes.get(i).getConectados();
            for (int j = 0; j < nod.size(); j++) {
                // System.out.println("\t"+nod.get(j).getId()+" "+element.getId());
                if (nod.get(j).getId() == element.getId()) {
                    val = true;
                }
            }
        }
        System.out.println("val = " + val);
        return val;
    }

    public int coincidencias(ArrayList<Node> l1, ArrayList<Node> l2) {
        int cont = 0;
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                if (l1.get(i).getId() == l2.get(j).getId()) {
                    cont++;
                }
            }
        }
        return cont;
    }

    public void crearPoligonoo(Node inicio, ArrayList<Node> recorridos) {
        ArrayList<Node> nodos = inicio.getConectados(); //Conectados al nodo papa
        ArrayList<Polygon> poligonos = null;
        recorridos.add(inicio);

        System.out.println("");
        //Recorremos los nodos conectados al nodo padre
        for (int i = 0; i < nodos.size(); i++) {
            Node aux = nodos.get(i);
            //Verificamos  que  nodos hijos no se han recorrido
            if (!esta(recorridos, aux) || recorridos.size() < 2) {
                recorridos.add(aux);
                if (esta(aux, recorridos.get(0).getId()) && recorridos.size() > 2) {
                    poligonos.add(new Polygon(i, (Integer[]) recorridos.toArray()));
                     break;
                } else {
                    crearPoligono(aux, recorridos);
                }
            } else {
                   poligonos.add(new Polygon(i, (Integer[]) recorridos.toArray()));
                 break;
            }
        }
    }

    public void getCiclos(boolean adjMatrix[][], String nodes[]) {
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

        System.out.println("Poligonos");
        recorrer();
        recorrerOrder();
        recorrer();
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

    public void recorrer() {

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

    public void recorrerOrder() {

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

    public String[] getNodes() {
        String nodes[] = new String[this.nodes.size()];
        for (int i = 0; i < this.nodes.size(); i++) {
            nodes[i] = i + "";
        }
        return nodes;
    }

    public ArrayList<Polygon> getSidesPolinomio() {
        ArrayList<Polygon> a = new ArrayList<>();
        Set set = hashCicles.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Polygon p = new Polygon(a.size() + 1, (Integer[]) me.getValue());
            a.add(p);
        }
        return a;
    }

}
