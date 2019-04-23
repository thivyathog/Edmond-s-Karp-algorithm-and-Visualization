// Java program for implementation of Ford Fulkerson algorithm

import java.util.Random;
import java.util.Scanner;

public class MaxFlow {
    /*
    Done by:Thivya Thogesan
    w1698503
    2017343


     */


    public static void main(String[] args) throws Exception {
        int n;
        int s;
        int t;
        Graph solver;
        Scanner sc = new Scanner(System.in);

        System.out.println("Do you want the nodes to randomnly generate[1] or enter by yourself[2]");
        int choice = sc.nextInt();
        if (choice == 1) {
            n = new Random().nextInt((10 - 4) + 1) + 4;
            // n = new Random().nextInt(10 + 1) + 4;
            n += 2;
            s = 0;
            t = n - 1;
            solver = new Graph(n, s, t);
            solver.drawGraph();
            System.out.println("Total number of nodes:" + n);
            int from;
            int maxEdges = (n * (n - 1)) / 2;

            int edges = new Random().nextInt((maxEdges - 1) + 1) + 1;
            // new Random().nextInt(maxEdges + 1);

            for (int i = 0; i < edges; i++) {

                int capacity = new Random().nextInt((20 - 5) + 1) + 5;
                //   new Random().nextInt(20 + 1) + 5;
                if (i == 0) from = 0;
                else from = new Random().nextInt((t - 1) + 1) + 1;
                //new Random().nextInt(t + 1);
                int to = new Random().nextInt((t - 1) + 1) + 1;
                //new Random().nextInt(t + 1);


                solver.addEdge(from, to, capacity);
            }


        } else {
            System.out.println("Enter the number of nodes:(excluding s and t)(Range between 4-10)");
            int nodes = sc.nextInt();
            while (!(nodes > 3 && nodes < 11)) {
                System.out.println("Node is not between the range! Please re-Enter!");
                nodes = sc.nextInt();
            }
            n = nodes + 2;
            s = 0;
            t = 5;
            solver = new Graph(n, s, t);
            solver.drawGraph();
            System.out.println("The source is:" + s);
            System.out.println("The sink is" + t);
            System.out.println("Enter the number of edges you require");
            int r = sc.nextInt();
            for (int i = 0; i < r; i++) {
                System.out.println("Enter the from  and to Edge");
                int from = sc.nextInt();
                int to = sc.nextInt();
                System.out.println("Enter the capacity of Edge (Range should be  5-20)");
                int capacity = sc.nextInt();
                while (!(capacity > 4 && capacity < 21)) {

                    if (capacity < 0) {
                        System.out.println("Capacity cannot be negative");
                    }
                    System.out.println("Capacity is not between the range! Please re-Enter!");
                    capacity = sc.nextInt();
                }

                solver.addEdge(from, to, capacity);
            }

        }

        solver.graph.display();

        System.out.println("\n The maximum possible flow is " +
                solver.findMaxFlow(solver.adjMatrix(), 0, t));

        System.exit(0);
       /* Graph solver = new Graph(6, 0, 5);
        solver.drawGraph();
        // Source edges
        solver.addEdge(0, 1, 10);
        solver.addEdge(0, 2, 10);
        solver.addEdge(1, 2, 2);

        solver.addEdge(1, 3, 4);
        solver.addEdge(1, 4, 8);
        // solver.addEdge(2, 1, 1);
        solver.addEdge(2, 4, 9);
        //solver.addEdge(3, 2, 9);
        solver.addEdge(3, 5, 10);
        solver.addEdge(4, 3, 6);
        solver.addEdge(4, 5, 10);



       // long start=System.currentTimeMillis();
        //  System.out.println("The maximum possible flow is " +
        //  m.findMaxFlow( 0, 5));

        System.out.println("\nThe maximum possible flow is " +
                solver.findMaxFlow(solver.adjMatrix(), 0, 5));*/

    }
}


