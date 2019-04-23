public class Edge {
    /*
        Done by:Thivya Thogesan
        w1698503
        2017343


         */
    public int from, to;
    public Edge residual;
    public long flow;
    public long capacity;

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public Edge(int from, int to, long capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;

    }

    public boolean isResidual() {

        return capacity == 0;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Edge getResidual() {
        return residual;
    }

    public void setResidual(Edge residual) {
        this.residual = residual;
    }

    public long getFlow() {
        return flow;
    }

    public void setFlow(long flow) {
        this.flow = flow;
    }

    public long getCapacity() {
        return capacity;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public long remainingCapacity() {


        return capacity - flow;
    }

    public void augment(long bottleNeck) {
        System.out.println("Aug" + flow);
        flow += bottleNeck;
        System.out.println("Aug" + flow);
        residual.flow -= bottleNeck;
    }

    public String toString(int s, int t) {
        String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
        String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
        return String.format("Edge %s -> %s | flow = %3d | capacity = %3d | is residual: %s",
                u, v, flow, capacity, isResidual());
    }

}

