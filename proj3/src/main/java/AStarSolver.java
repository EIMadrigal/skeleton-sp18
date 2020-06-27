import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class AStarSolver {
    private static PriorityQueue<AugmentNode> pq = new PriorityQueue<>();
    private Stack<Long> path = new Stack<>();
    private Map<GraphDB.Node, Double> estimateDisCach = new HashMap<>();

    Set<Long> visited = new HashSet<>();

    private class AugmentNode implements Comparable<AugmentNode> {

        private GraphDB.Node node;
        private AugmentNode prev;

        private double disToCur;
        private double estimateDis;
        private double priority;

        @Override
        public int compareTo(AugmentNode n) {
            //return (int) (this.priority - n.priority);
            return Double.compare(this.priority, n.priority);
        }

        private AugmentNode(GraphDB.Node node, double disToCur, AugmentNode prev, GraphDB.Node goal) {
            this.disToCur = disToCur;
            this.node = node;
            this.prev = prev;
            if (estimateDisCach.containsKey(this.node)) {
                this.estimateDis = estimateDisCach.get(this.node);
            } else {
                this.estimateDis = GraphDB.distance(node.lon, node.lat, goal.lon, goal.lat);
            }
            this.priority = this.estimateDis + disToCur;
        }
    }

    public AStarSolver(GraphDB g, GraphDB.Node initial, GraphDB.Node goal) {

        AugmentNode curNode = new AugmentNode(initial, 0, null, goal);
        while (curNode.node.id != goal.id) {
            for (long neighbor : g.adjacent(curNode.node.id)) {
                GraphDB.Node n = g.vertex.get(neighbor);
                if (visited.contains(n.id)) {
                    continue;
                }
                if (curNode.prev == null || n.id != curNode.prev.node.id) {
                    AugmentNode node = new AugmentNode(n, curNode.disToCur + g.distance(curNode.node.id, n.id), curNode, goal);
                    pq.add(node);
                }
            }
            curNode = pq.poll();
            visited.add(curNode.node.id);
        }

        for (AugmentNode n = curNode; n != null; n = n.prev) {
            path.push(n.node.id);
        }
    }

    public Stack<Long> solution() {
        return path;
    }

}
