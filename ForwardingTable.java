package CS3700_HW10;

import java.io.*;
import java.util.ArrayList;

public class ForwardingTable {

    /*
    Validate node numbers and cost values and
    calculate the number of vertices in the input file
    @param fileName Name of file used
    @param n Number of routers in the network
    @return verts.size() Number of vertices found in the file
     */
    private static int numVerts(String fileName, int n) {
        int node1, node2, cost;
        int rowNum = 1;
        ArrayList<Integer> verts = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            String line = file.readLine();
            while (line != null) {
                String[] parse = line.split("\t");
                node1 = Integer.parseInt(parse[0]);
                node2 = Integer.parseInt(parse[1]);
                cost = Integer.parseInt(parse[2]);

                if (!(node1 >= 0 && node1 <= n - 1 && node2 >= 0 && node2 <= n - 1 && cost > 0)) {
                    System.out.println("Invalid node number or cost value at row " + rowNum);
                    file.close();
                    break;
                }

                if (!(verts.contains(node1)) && !(verts.contains(node2))) {
                    verts.add(node1);
                    verts.add(node2);
                } else if (!(verts.contains(node1))){
                    verts.add(node1);
                } else if (!(verts.contains(node2))) {
                    verts.add(node2);
                }
                line = file.readLine();
                rowNum++;
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verts.size();
    }

    /*
    Create adjusted matrix from file and initialize weight matrix
    @param fileName Name of file used
    @param W Partially initialized weight matrix
    @return W Fully initialized weight matrix
     */
    private static int[][] adjMatrix(String fileName, int[][] W) {
        int node1, node2, cost;

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            String line = file.readLine();
            while (line != null) {
                String[] parse = line.split("\t");
                node1 = Integer.parseInt(parse[0]);
                node2 = Integer.parseInt(parse[1]);
                cost = Integer.parseInt(parse[2]);
                W[node1][node2] = cost;
                line = file.readLine();
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return W;
    }

    /*
    Calculate the next vertex with the shortest distance
    @param D distances
    @param V visited vertices
    @return nmv next minimum vertex
    */
    private static int nextMinVertex(ArrayList<Integer> D, ArrayList<Boolean> V) {
       int min = Integer.MAX_VALUE;
       int nmv = 0;

       for (int i = 0; i < D.size(); i++) {
           if (D.get(i) < min && !V.get(i)) {
               min = D.get(i);
               nmv = i;
           }
       }
       return nmv;
    }

   /*
   Calculate the shortest distance from starting vertex to each vertex
    @param W  weight matrix
    @return D distances
    */
    private static ArrayList<Integer> dijkstra(int[][] W) {
        ArrayList<Boolean> V = new ArrayList<>();
        ArrayList<Integer> D = new ArrayList<>();
        ArrayList<Integer> N = new ArrayList<>();
        ArrayList<Integer> Y = new ArrayList<>();

        for (int i=0;i<W.length;i++) {
            D.add(W[0][i]);
            V.add(false);
        }

        for (int i=0;i<W.length;i++) {
            int nmv = nextMinVertex(D, V);
            V.set(nmv, true);

            for (int j=0;j<W.length;j++) {
                if (W[nmv][j] > 0 && !V.get(i) && D.get(i) > D.get(nmv) + W[nmv][j]) {
                    D.set(j, D.get(nmv) + W[nmv][j]);
                    // TODO: 3. Add to and print ArrayLists of N', Y', D.get(i), P.get(i-1)
                    /*
                    N.add(nmv);
                    Y.add(D.get(j));
                    System.out.println("N': " + N);
                    System.out.println("Y': " + Y);
                    */
                }
            }
        }
        return D;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

        // 1. Display a prompt message to ask the user to input the total number of routers, n, in the network.
        //          Validate n to make sure that it is greater than or equal to 2.

        int n = 0;

        boolean loop = true;
        while (loop) {

            System.out.print("Please enter the total number of routers in the network: ");
            n = Integer.parseInt(sysIn.readLine());

            if (n < 2)
                System.out.println("Total number of routers must be greater than or equal to 2!");
            else {
                System.out.println("A total of " + n + " routers have been entered.");
                loop = false;
            }
        }

        // TODO: 1. Use a topo.txt file that contains costs of all links, ONE line for every link. If there is NO link between two routers, i.e., the
        //       link cost between these two routers is infinite, NO line is included in the topo.txt file for these two routers. Each line in
        //       this file provides the cost between a pair of routers as below, where tab (‘\t’) is used to separate the numbers in each line.

        int numVerts = numVerts("topo.txt", n);
        int[][] W = new int[numVerts][numVerts];
        for (int i=0;i<numVerts;i++) {
            for (int j=0;j<numVerts;j++) {
                if (j == i) {
                    W[i][j] = 0;
                }
                W[i][j] = Integer.MAX_VALUE;
            }
        }
        W = adjMatrix("topo.txt", W);
        ArrayList<Integer> D = dijkstra(W);

        // TODO: 3. Use the shortest-path tree resulted from the Dijsktra’s algorithm to build up the forwarding table for router V0 . Display the
        //          forwarding table in the following format:


    }
}
