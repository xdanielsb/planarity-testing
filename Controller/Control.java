/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Logic.Nodo;
import Logic.Poligon;
import Logic.Recta;
import View.Ventana;
import ciclos.ElementaryCyclesSearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author daniel
 */
public class Control {

    private ArrayList<Nodo> nodos;
    private ArrayList<Recta> rectas;
    private ArrayList<Nodo> intersecciones;
    private ArrayList<Poligon> poligonos;
    HashMap hm;

    private Ventana ventana;

    public Control() {
        nodos = new ArrayList<>();
        rectas = new ArrayList<>();
        poligonos = new ArrayList<>();
        intersecciones = new ArrayList<>();
        hm = new HashMap();
        ventana = new Ventana(this);
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

    public Nodo getNodo(int id) {
        Nodo get = null;
        for (int i = 0; i < nodos.size(); i++) {
            Nodo n = nodos.get(i);
            if (n.getId() == id) {
                get = n;
            }
        }
        return get;
    }

    public void showNodo() {
        for (int i = 0; i < nodos.size(); i++) {
            nodos.get(i).getId();
            System.out.println("id: " + nodos.get(i).getId());
        }
    }

    public void showPoligonos() {
        System.out.println("Poligonos");
        for (int i = 0; i < poligonos.size(); i++) {
            Poligon po = poligonos.get(i);

/*            ArrayList<Nodo> nodos = po.getPoligono();
            for (int j = 0; j < nodos.size(); j++) {
                System.out.print(nodos.get(j).getId() + " ");
            }
            System.out.println("");
       */ }
    }

    public void showRectas() {
        for (int i = 0; i < rectas.size(); i++) {
            System.out.println(rectas.get(i).getOrigen().getId() + " " + rectas.get(i).getDestino().getId());
        }
    }

    public ArrayList<Recta> getRectas() {
        return rectas;
    }

    public void setRectas(ArrayList<Recta> rectas) {
        this.rectas = rectas;
    }

    public ArrayList<Nodo> getIntersecciones() {
        return intersecciones;
    }

    public void setIntersecciones(ArrayList<Nodo> intersecciones) {
        this.intersecciones = intersecciones;
    }

    public boolean chocan() {
        boolean chocan = false;
        System.out.println(rectas.size());
        for (int i = 0; i < rectas.size(); i++) {
            Recta origen = rectas.get(i);
            for (int j = 0; j < rectas.size(); j++) {
                Recta destino = rectas.get(j);
                if (origen != destino && origen.choca(destino)) {
                    //System.out.println("Las rectas chocan");
                    chocan = true;
                }
            }
        }
        return chocan;
    }

    public ArrayList<Poligon> getPoligonos() {

        //Mostrar las relaciones
        ArrayList<Nodo> nodos = ventana.getControl().getNodos();

        for (int j = 0; j < nodos.size(); j++) {
            Nodo aux = nodos.get(j);
            System.out.print(aux.getId() + " ->");
            ArrayList<Nodo> amigos = aux.getConectados();
            for (int k = 0; k < amigos.size(); k++) {
                Nodo amigo = amigos.get(k);
                System.out.print(amigo.getId() + " ");
            }
            System.out.println("");
        }

        poligonos = new ArrayList<>();

        ArrayList<Nodo> lista = new ArrayList<>();
        // lista .add(nodos.get(0));

        crearPoligono(nodos.get(0), lista);
        return poligonos;
    }

    public void crearPoligono(Nodo inicio, ArrayList<Nodo> recorridos) {
        getCiclos(ventana.obtenerMatriz(), getNodes());

    }

    public void polcrearPoligonoo(Nodo inicio, ArrayList<Nodo> recorridos) {
        ArrayList<Nodo> nodos = inicio.getConectados(); //Conectados al nodo papa

        recorridos.add(inicio);

        //Show the current elements in recorridos
        for (int i = 0; i < recorridos.size(); i++) {
            System.out.print(recorridos.get(i).getId() + " ");
        }
        System.out.println("");

        //Recorremos los nodos conectados al nodo padre
        for (int i = 0; i < nodos.size(); i++) {
            Nodo aux = nodos.get(i);
            //Verificamos  que  nodos hijos no se han recorrido
            if (!esta(recorridos, aux) || recorridos.size() < 2) {
                recorridos.add(aux);
                if (esta(aux, recorridos.get(0).getId()) && recorridos.size() > 2) {
                    poligonos.add(new Poligon(poligonos.size() + 1, recorridos));
                    break;
                } else {
                    crearPoligono(aux, recorridos);
                }

            } else {
                //  recorridos.add(aux);
                poligonos.add(new Poligon(poligonos.size() + 1, recorridos));
                break;
            }
        }
    }

    public boolean esta(ArrayList<Nodo> recorridos, Nodo element) {
        for (int i = 0; i < recorridos.size(); i++) {
            if (recorridos.get(i).getId() == element.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean esta(Nodo element, int desde) {
        System.out.print("(" + desde + " " + element.getId() + ") ");
        boolean val = false;
        for (int i = desde; i < nodos.size(); i++) {
            ArrayList<Nodo> nod = nodos.get(i).getConectados();
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

    public int coincidencias(ArrayList<Nodo> l1, ArrayList<Nodo> l2) {
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

    public void getCiclos(boolean adjMatrix[][], String nodes[]) {
        ArrayList<Integer[]> elementos = new ArrayList<>();
        hm = new HashMap();
        ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(adjMatrix, nodes);
        List cycles = ecs.getElementaryCycles();
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
                    // System.out.print(array[j] + " ");
                }
                hm.put(aux, array);
                // elementos.add(array);
            }
            //System.out.print("\n");
        }

        System.out.println("Poligonos");
        recorrer();
        recorrerOrder();
        recorrer();
        Set set = hm.entrySet();
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

        for (int j = 0; j < hm.size(); j++) {
            Set set = hm.entrySet();
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

        Set set = hm.entrySet();
        Iterator i = set.iterator();
        HashMap hm2 = new HashMap();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Integer[] array = (Integer[]) me.getValue();
            Integer[] array2 = (Integer[]) me.getValue();
            Arrays.sort(array);
            String aux = "";
            for (int j = 0; j < array.length; j++) {
                aux += array[j] + "-";
                // System.out.print(array[j] + " ");
            }
            hm2.put(aux,array2);

        }
        hm = hm2;
    }

    public boolean Duplicate(String key) {
        boolean el = false;
        Set set = hm.entrySet();
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
        String nodes[] = new String[10];

        for (int i = 0; i < nodos.size(); i++) {
            nodes[i] = i + "";
        }
        return nodes;
    }
    
    public ArrayList <Poligon> getSidesPolinomio() {
        ArrayList <Poligon> a = new ArrayList<>();
        Set set = hm.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Poligon p = new Poligon(a.size()+1, (Integer[]) me.getValue());
            a.add(p); 
        }
        return a;
    }
    

}
