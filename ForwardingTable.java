package CS3700_HW10;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class ForwardingTable {

    // Static int to use as "infinity"
    private static int max_value = 1000000000;

    /*
    Validate node and costs in topo.txt file
    @param fileName Name of file used
     */
    private static boolean validate(String fileName, int n) throws IOException {
        int node1, node2, cost;
        int rowNum = 1;

        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line = file.readLine();
        while (line != null) {
            String[] parse = line.split("\t");
            node1 = Integer.parseInt(parse[0]);
            node2 = Integer.parseInt(parse[1]);
            cost  = Integer.parseInt(parse[2]);

            if (node1 < 0 || node1 > n - 1 || node2 < 0 || node2 > n - 1 || cost < 0) {
                System.out.println("Invalid node number or cost value at row " + rowNum);
                file.close();
                return false;
            }


            line = file.readLine();
            rowNum++;
        }
        file.close();
        return true;
    }

    /*
    Create adjusted matrix from file and initialize weight matrix
    @param fileName Name of file used
    @param W Partially initialized weight matrix
    @return W Fully initialized weight matrix
     */
    private static int[][] adjMatrix(String fileName, int[][] W) throws IOException {
        int node1, node2, cost;

        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line = file.readLine();
        while (line != null) {
            String[] parse = line.split("\t");
            node1 = Integer.parseInt(parse[0]);
            node2 = Integer.parseInt(parse[1]);
            cost  = Integer.parseInt(parse[2]);
            if (node1 < W.length && node2 < W.length) {
                W[node1][node2] = cost;
            }
            line = file.readLine();
        }
        file.close();

        return W;
    }

    private static void printMatrix(int[][] M) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                if (M[i][j] == max_value) {
                    System.out.print("INF\t");
                } else {
                System.out.print(M[i][j] + "\t");
                }
            }
            System.out.println();
        }
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
       return nmv;
    }

   /*
   Calculate the shortest distance from starting vertex to each vertex
    @param W  weight matrix
    @return D distances
    */
    // /*
    private static String[] dijkstra(int[][] W) {
        boolean[] V = new boolean[W.length];
        int[] D = new int[W.length];
        int[] N = new int[W.length];
        String[] Y = new String[W.length];
        int[] P = new int[W.length];

        for (int i = 0; i < W.length; i++) {
            D[i] = W[0][i];
            V[i] = false;
        }


        for (int i = 0; i < W.length - 1; i++) {
            int nmv = nextMinVertex(D, V);
            V[nmv] = true;
            N[i] = nmv;

            for (int j = 0; j < W.length; j++) {
                if (W[nmv][j] > 0 && !V[j] && D[j] > D[nmv] + W[nmv][j]) {
                    D[j] = D[nmv] + W[nmv][j];
                    // TODO: 2. Add to and print ArrayLists of N', Y', D.get(i), P.get(i-1)
                    Y[j] = "(V" + P[j] + ", V" + nmv + ")";
                    System.out.println(Y[j]);
                    P[j] = nmv;
                    System.out.println(P);
                }
            }
        }

        System.out.print("P: ");
        for (int i = 0; i < P.length; i++) {
            System.out.print(P[i] + " ");
        }
        System.out.println();

        System.out.print("V: ");
        for (int i = 0; i < V.length; i++) {
            System.out.print(V[i] + " ");
        }
        System.out.println();

        System.out.print("N: ");
        for (int i = 0; i < N.length; i++) {
            System.out.print(N[i] + " ");
        }
        System.out.println();

        printTable(P);
        printResults(D);
        return Y;
    }

    // */

    /*
    Prints the results of the dijkstra's algorithm
    @param D Array of shortest distances from dijkstra's
     */
    public static void printResults(int[] D) {
        System.out.print("D: ");
        for (int i = 0; i < D.length; i++) {
            System.out.print(D[i] + " ");
        }
        System.out.println();
    }

    /*
    Prints the shortest distances found by dijkstra's into forwarding table
    @param D Set of shortest distances between V0 and n
     */
    private static void printTable(int[] P) {
        System.out.println("Forwarding Table for V0:");
        System.out.println("Destination\t\tLink");
        for (int i = 1; i < P.length; i++) {
            int j = i;
            while (P[j] != 0) {
                j = P[j];
            }
            System.out.println("V" + i + "\t(V0, V" + j + ")");
        }
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

        // Validate file
        validate("topo.txt", n);

        // Initialize 2D int array
        int[][] W = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    W[i][j] = 0;
                } else {
                    W[i][j] = max_value;
                }
            }
        }

        System.out.println("Initial weight matrix:");
        printMatrix(W);

        // Set costs in W from "topo.txt"
        int[][] adjW = adjMatrix("topo.txt", W);
        System.out.println("Adjusted weight matrix:");
        printMatrix(adjW);

        // Calculate the shortest distances with dijkstra's
/*
        // Initialization:
        int[] N = new int[n];
        N[0] = 0;
        boolean[] V = new boolean[n];
        int[] P = new int[n];
        String[] Y = new String[n-1];
        int[] D = new int[n];
        for (int i = 0; i < W.length; i++) {
            if (W[0][i] > 0 && W[0][i] < max_value) {
                D[i] = W[0][i];
                P[i] = 0;
                V[i] = false;
            }
            else {
                D[i] = max_value;
                V[i] = false;
            }
        }

        // Loop:
        for (int k = 0; k < N.length - 1; k++) {
            int minVal = max_value;
            int minIndex = 0;
            for (int i = 0; i < D.length; i++) {
                if (D[i] < minVal && !V[i]) {
                    minVal = D[i];
                    minIndex = i;
                    System.out.println("minIndex: " + minIndex);
                }
            }
            N[k] = minIndex;
            System.out.println("N: " + N[k]);
            Y[k] = "(V" + P[minIndex] + ", V" + minIndex +")";
            System.out.println("Y: " + Y[k]);
            /* boolean contains = Arrays.asList(N).contains(minIndex);
            if (!contains) {
                N[k] = minIndex;
                System.out.println("N: " + N[k]);
                Y[k] = "(V" + P[minIndex] + ", V" + minIndex +")";
                System.out.println("Y: " + Y[k]);
            }
             // end comment here
        }
*/
        String[] Y = dijkstra(adjW);
        //printResults(D);
        //printTable(Y);
    }
}