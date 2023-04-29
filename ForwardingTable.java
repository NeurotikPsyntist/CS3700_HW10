package CS3700_HW10;

import java.io.*;
import java.util.ArrayList;

public class ForwardingTable {

    // Static int to use as "infinity"
    private static int max_value = 1000000000;

    /*
    Validate node and costs in topo.txt file
    @param fileName Name of file used
     */
    private static boolean validate(String fileName) {
        int node1, node2, cost;
        int rowNum = 1;

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            String line = file.readLine();
            while (line != null) {
                String[] parse = line.split("\t");
                node1 = Integer.parseInt(parse[0]);
                node2 = Integer.parseInt(parse[1]);
                cost = Integer.parseInt(parse[2]);

                // TODO: 1. Fix number validator
                /*
                if (!(node1 >= 0 && node1 <= n - 1 && node2 >= 0 && node2 <= n - 1 && cost > 0)) {
                    System.out.println("Invalid node number or cost value at row " + rowNum);
                    file.close();
                    return false
                    break;
                }
                */

                line = file.readLine();
                rowNum++;
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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
                if (node1 < W.length && node2 < W.length) {
                    W[node1][node2] = cost;
                }
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
    private static int nextMinVertex(int[] D, boolean[] V) {
       int min = max_value;
       int nmv = 0;

       for (int i = 0; i < D.length; i++) {
           if (D[i] < min && !V[i]) {
               min = D[i];
               nmv = i;
           }
       }
       System.out.println("min (nmv): " + min);
       return nmv;
    }

   /*
   Calculate the shortest distance from starting vertex to each vertex
    @param W  weight matrix
    @return D distances
    */
    private static int[] dijkstra(int[][] W) {
        boolean[] V = new boolean[W.length];
        int[] D = new int[W.length];
        ArrayList<Integer> N = new ArrayList<>();
        ArrayList<Integer> Y = new ArrayList<>();

        for (int i = 0; i < W.length; i++) {
            D[i] = W[0][i];
            V[i] = false;
        }

        for (int i = 0; i < W.length - 1; i++) {
            int nmv = nextMinVertex(D, V);
            V[nmv] = true;

            for (int j = 0; j < W.length; j++) {
                int temp = D[nmv] + W[nmv][j];
                if (W[nmv][j] > 0 && !V[j] && D[j] > temp) {
                    D[j] = D[nmv] + W[nmv][j];
                    // TODO: 2. Add to and print ArrayLists of N', Y', D.get(i), P.get(i-1)

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

    /*
    Prints the shortest distances found by dijkstra's into forwarding table
    @param D Set of shortest distances between V0 and n
     */
    private static void printTable(ArrayList<Integer> D) {
        // TODO: 3. Use the shortest-path tree resulted from the Dijkstra’s algorithm to build up the forwarding table for router V-1 . Display the
        //          forwarding table in the following format:
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

        // Initialize 2D int array
        int[][] W = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                W[i][j] = max_value;
            }
        }
        for (int i = 0; i < W.length; i++) {
            W[i][i] = 0;
        }

        // Set costs in W from "topo.txt"
        int[][] adjW = adjMatrix("topo.txt", W);

        // Calculate the shortest distances with dijkstra's
        int[] D = dijkstra(adjW);
        System.out.print("D: ");
        for (int i = 0; i < D.length; i++) {
            System.out.print(D[i] + " ");
        }
        System.out.println();

    }
}
