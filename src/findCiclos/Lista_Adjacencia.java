package findCiclos;

import java.util.Vector;
/**
 * 
 * @author Frank Meyer
 */
public class Lista_Adjacencia {

    public static int[][] get_lista_adjacencia(boolean[][] adjacencyMatrix) {
        int[][] list = new int[adjacencyMatrix.length][];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            Vector v = new Vector();
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j]) {
                    v.add(new Integer(j));
                }
            }

            list[i] = new int[v.size()];
            for (int j = 0; j < v.size(); j++) {
                Integer in = (Integer) v.get(j);
                list[i][j] = in.intValue();
            }
        }

        return list;
    }
}
