import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class Graph {

    /*
        Done by:Thivya Thogesan
        w1698503
        2017343

         */
    private int noOfVertices; // No. of vertices
    protected LinkedList<Edge>[] adjList; //Adjacency Lists
    protected List list = new ArrayList();
    private int[] level;
    protected boolean solved;

    private int s;
    private int t;
    protected static org.graphstream.graph.Graph graph = new SingleGraph("Max Flow Problem");


    Graph(int totalNodes, int s, int t) {
        this.s = s;
        this.t = t;
        noOfVertices = totalNodes;


        level = new int[totalNodes];

        adjList = new LinkedList[totalNodes];
        //isVisited = new boolean[noOfVertices];
        //pathList = new ArrayList<>();


        for (int i = 0; i < totalNodes; i++) {

            adjList[i] = new LinkedList();
        }

    }

    public int[] getLevel() {
        return level;
    }

    public int getS() {
        return s;
    }

    public int getT() {
        return t;
    }


    // The maximum flow. Calculated by calling the {@link #solve} method.
    protected long maxFlow;

    public int getNoOfVertices() {
        return noOfVertices;
    }

    public LinkedList<Edge>[] getAdjList() {
        return adjList;
    }

    public boolean isSolved() {
        return solved;
    }


    public void addEdge(int from, int to, long capacity) {
        // System.out.println("add E from" + from + "to" + to);
        if (capacity <= 0)
            throw new IllegalArgumentException("Forward edge capacity <= 0");
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        String edgeName = String.valueOf(from) + " to " + String.valueOf(to);
        // System.out.println(edgeName);
        graph.addEdge(edgeName, from, to, true);
        adjList[from].add(e1);

        adjList[to].add(e2);
        graph.getEdge(edgeName).addAttribute("ui.label", "0/" + capacity);

    }

    public int[][] adjMatrix() {
        int rGraph[][] = new int[noOfVertices][noOfVertices];
        for (int r = 0; r < noOfVertices; r++) {


            for (Edge pCrawl : adjList[r]) {
                //  System.out.print("from -> " + pCrawl.from + "to -> " + pCrawl.to + "\n");

                int l = pCrawl.from;
                int q = pCrawl.to;
                rGraph[l][q] = Math.toIntExact(pCrawl.capacity);
            }
        }
        return rGraph;
    }


    public void drawGraph() {
        // System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);

        graph.setStrict(true);
        String alpha = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < noOfVertices; i++) {
            graph.addNode(String.valueOf(i));
            System.out.println("Sdd" + i);
        }
        int i = 0;
        int r = 0;
        for (Node node : graph) {

            if (i == 0) node.addAttribute("ui.label", "S");
            else if (i == noOfVertices - 1) node.addAttribute("ui.label", "T");
            else {
                // node.addAttribute("ui.style", "shape:circle;fill-color: yellow;size: 90px; text-alignment: center; text-color:red;");
                node.addAttribute("ui.label", alpha.toUpperCase().charAt(i - 1));

            }
            i++;

        }
    }


    public void printpath(List<Integer> list) {
        list.add(0);
        Collections.reverse(list);

        String alpha = "abcdefghijklmnopqrstuvwxyz";
        System.out.print("Augmenting Path:");
        for (Integer i : list) {

            if (i == 0) System.out.print("S" + "-->");
            else if (i == noOfVertices - 1) System.out.println("T");
            else {

                System.out.print(String.valueOf(alpha.toUpperCase().charAt(i - 1)) + "-->");
            }


        }
        graph.getNode(0).setAttribute("ui.class", "marked");
        sleep();
        graph.getNode(0).setAttribute("ui.class", "depth");
        for (Integer v : list) {
            graph.getNode(v).setAttribute("ui.class", "marked");
            sleep();
            graph.getNode(v).setAttribute("ui.class", "depth");
        }
    }

    protected String styleSheet =
            "node {" +
                    "	shape: circle;size:20px;fill-mode: plain;fill-color: blue; " +
                    "stroke-mode: plain;stroke-color: blue; text-size:20px;text-color:blue; " +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}" +
                    "node.depth {" +
                    "	fill-color: blue;" +
                    "}" + "edge {" +
                    "	size: 2px;fill-color: black; text-size:20px;" +
                    "}";


    boolean bfs(int rGraph[][], int s, int t, int parent[]) {

        boolean visited[] = new boolean[noOfVertices];
        for (int i = 0; i < noOfVertices; ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();

        queue.add(s);
        visited[s] = true;
        parent[s] = -1;
        level[s] = 0;
        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < noOfVertices; v++) {

                if (!visited[v] && rGraph[u][v] > 0) {
                    level[v] = level[u] + 1;
                    queue.add(v);
                    parent[v] = u;


                    visited[v] = true;
                }
            }
        }
        if (visited[t])
            System.out.println("BFS levels:" + Arrays.toString(level));
        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t]);
    }

    // Returns tne maximum flow from s to t in the given graph
    int findMaxFlow(int[][] graphI, int s, int t) {
        int u, v;

        int rGraph[][] = new int[noOfVertices][noOfVertices];

        for (u = 0; u < noOfVertices; u++)
            for (v = 0; v < noOfVertices; v++)
                rGraph[u][v] = graphI[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[noOfVertices];

        int max_flow = 0; // There is no flow initially


        while (bfs(rGraph, s, t, parent)) {

            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {

                list.add(v);

                u = parent[v];

                path_flow = Math.min(path_flow, rGraph[u][v]);

            }

            printpath(list);
            System.out.println("Flow in this path:" + path_flow + "\n");
            list.clear();
            String edgeName = "";
            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {

                u = parent[v];
                int cap = rGraph[u][v];
                // System.out.println(rGraph[u][v]+"before add");
                rGraph[u][v] -= path_flow;

                rGraph[v][u] += path_flow;

                edgeName = String.valueOf(u) + " to " + String.valueOf(v);
                graph.getEdge(edgeName).addAttribute("ui.style", "text-alignment: center; text-color:red;");
                graph.getEdge(edgeName).addAttribute("ui.label", rGraph[v][u] + "/" + graphI[u][v]);

                //  graph.getEdge(edgeName).addAttribute("ui.style", "size: 2px;fill-color: black;");
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
    }

    protected static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

}
