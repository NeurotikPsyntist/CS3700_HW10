package CS3700_HW10;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ForwardingTable {

    /* Calculate the next vertex with the shortest distance
    @param D distances
    @param V visited vertices
    @return nmv next minimum vertex
    */
    private int next_min_vertex(ArrayList<Integer> D, ArrayList<Boolean> V) {
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

   /* Calculate the shortest distance from starting vertex to each vertex
    @param W  weight matrix
    @param sv starting vertex
    @return D distances
    */
    private ArrayList<Integer> dijkstra(int W[][], int sv) {
        ArrayList<Boolean> V = new ArrayList<>();
        ArrayList<Integer> D = new ArrayList<>();

        for (int i=0;i<W.length;i++) {
            D.add(W[sv][i]);
            V.add(false);
        }

        for (int i=0;i<W.length;i++) {
            int nmv = next_min_vertex(D, V);
            V.set(nmv, true);

            for (int j=0;j<W.length;j++) {
                if (W[nmv][j] > 0 && !V.get(i) && D.get(i) > D.get(nmv) + W[nmv][j]) {
                    D.set(j, D.get(nmv) + W[nmv][j]);
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

        // TODO: 2. Use a topo.txt file that contains costs of all links, ONE line for every link. If there is NO link between two routers, i.e., the
        //       link cost between these two routers is infinite, NO line is included in the topo.txt file for these two routers. Each line in
        //       this file provides the cost between a pair of routers as below, where tab (‘\t’) is used to separate the numbers in each line.

        BufferedReader reader;
        int node1 = 0;
        int node2 = 0;
        int cost  = 0;

        try {
            reader = new BufferedReader(new FileReader("topo.txt"));
            String line = reader.readLine();
            int rowNum = 1;

            while (line != null) {
                String[] parse = line.split("\t");
                node1 = Integer.parseInt(parse[0]);
                node2 = Integer.parseInt(parse[1]);
                cost  = Integer.parseInt(parse[2]);

                if (!(node1 >= 0 && node1 <= n - 1 && node2 >= 0 && node2 <= n - 1 && cost > 0)) {
                    System.out.println("Invalid node number or cost value at row " + rowNum);
                    reader.close();
                    break;
                }
                line = reader.readLine();
                rowNum++;
            }


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // TODO: 3. Implement the Dijsktra’s algorithm to build up the shortest-path tree rooted at source router V0.


        // TODO: 4. Use the shortest-path tree resulted from the Dijsktra’s algorithm to build up the forwarding table for router V0 . Display the
        //          forwarding table in the following format:


    }
}
