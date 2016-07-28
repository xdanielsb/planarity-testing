package findCiclos;

import java.util.List;
import java.util.Vector;
/**
 * 
 * @author Frank Meyer
 */
public class Ciclos_elementales {

    private List ciclos = null;
    private int[][] adj = null;
    private Object[] graph = null;
    private boolean[] bloqueados = null;
    private Vector[] B = null;
    private Vector pila = null;

    public Ciclos_elementales(boolean[][] matrix, Object[] graphNodes) {
        this.graph = graphNodes;
        this.adj = Lista_Adjacencia.get_lista_adjacencia(matrix);
    }

    public List get_ciclos_basicos() {
        this.ciclos = new Vector();
        this.bloqueados = new boolean[this.adj.length];
        this.B = new Vector[this.adj.length];
        this.pila = new Vector();
        Connectados sccs = new Connectados(this.adj);
        int s = 0;

        while (true) {
            Ciclos sccResult = sccs.getAdjacencyList(s);
            if (sccResult != null && sccResult.get_matrix_adjacencia() != null) {
                Vector[] scc = sccResult.get_matrix_adjacencia();
                s = sccResult.get_id();
                for (int j = 0; j < scc.length; j++) {
                    if ((scc[j] != null) && (scc[j].size() > 0)) {
                        this.bloqueados[j] = false;
                        this.B[j] = new Vector();
                    }
                }

                this.getCiclos(s, s, scc);
                s++;
            } else {
                break;
            }
        }

        return this.ciclos;
    }

    private boolean getCiclos(int v, int s, Vector[] adjList) {
        boolean f = false;
        this.pila.add(new Integer(v));
        this.bloqueados[v] = true;

        for (int i = 0; i < adjList[v].size(); i++) {
            int w = ((Integer) adjList[v].get(i)).intValue();
            // found cycle
            if (w == s) {
                Vector cycle = new Vector();
                for (int j = 0; j < this.pila.size(); j++) {
                    int index = ((Integer) this.pila.get(j)).intValue();
                    cycle.add(this.graph[index]);
                }
                this.ciclos.add(cycle);
                f = true;
            } else if (!this.bloqueados[w]) {
                if (this.getCiclos(w, s, adjList)) {
                    f = true;
                }
            }
        }

        if (f) {
            this.desbloquear(v);
        } else {
            for (int i = 0; i < adjList[v].size(); i++) {
                int w = ((Integer) adjList[v].get(i)).intValue();
                if (!this.B[w].contains(new Integer(v))) {
                    this.B[w].add(new Integer(v));
                }
            }
        }

        this.pila.remove(new Integer(v));
        return f;
    }

    private void desbloquear(int node) {
        this.bloqueados[node] = false;
        Vector Bnode = this.B[node];
        while (Bnode.size() > 0) {
            Integer w = (Integer) Bnode.get(0);
            Bnode.remove(0);
            if (this.bloqueados[w.intValue()]) {
                this.desbloquear(w.intValue());
            }
        }
    }
}
