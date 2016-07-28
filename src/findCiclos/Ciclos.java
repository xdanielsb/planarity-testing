package findCiclos;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Ciclos {

    private Set id = null;
    private Vector[] adj = null;
    private int idnod = -1;

    public Ciclos(Vector[] adjList, int lowestNodeId) {
        this.adj = adjList;
        this.idnod = lowestNodeId;
        this.id = new HashSet();
        if (this.adj != null) {
            for (int i = this.idnod; i < this.adj.length; i++) {
                if (this.adj[i].size() > 0) {
                    this.id.add(new Integer(i));
                }
            }
        }
    }

    public Vector[] get_matrix_adjacencia() {
        return adj;
    }

    public int get_id() {
        return idnod;
    }
}
