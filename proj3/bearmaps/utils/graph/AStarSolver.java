package bearmaps.utils.graph;
import bearmaps.utils.pq.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import org.apache.commons.math3.geometry.spherical.twod.Vertex;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    double timeSpent;
    List<Vertex> solution;
    double solutionWeight;
    int numStatesExplored; //number of times .poll() was called
    SolverOutcome outcome;
    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, WeightedEdge<Vertex>> edgeTo;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        // Constructor which finds the solution, computing everything necessary for all other methods to
        //return their results in constant time

        //note that TIMEOUT passes in is in seconds

        Stopwatch sw = new Stopwatch();

        DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        edgeTo =  new HashMap<>();
        numStatesExplored = 0;
        solutionWeight = 0.0;
        solution = new ArrayList<>();

        //keep track of distance
        //keep track of edge to each node


        pq.insert(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);


        if (start.equals(end)) {
            solution.add(start);
            solutionWeight = 0.0;
            outcome = SolverOutcome.SOLVED;
            timeSpent = sw.elapsedTime();

            return;
        }

        while (pq.size()!= 0 && !pq.peek().equals(end) && sw.elapsedTime() <= timeout) {
            Vertex p = pq.poll();
            numStatesExplored++;


            // ***** RELAXING EDGES OUTGOING FROM P ****** //
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                Vertex fromVertex = e.from();
                Vertex toVertex = e.to();
                Double w = e.weight();

                if (!distTo.containsKey(toVertex)) {
                    distTo.put(toVertex, Double.MAX_VALUE);
                }

                if (distTo.get(fromVertex) + w < distTo.get(toVertex)) {
                    distTo.replace(toVertex, (distTo.get(fromVertex) + w)); //this should update enough so that in the end
                    //you can just call .get(end) and it returns the cumulative result
                    edgeTo.put(toVertex, e); //should i be replacing or put?

                    if (pq.contains(toVertex)) {
                        pq.changePriority(toVertex, distTo.get(toVertex) + input.estimatedDistanceToGoal(toVertex, end));
                    }
                    if (!pq.contains(toVertex)) {
                        pq.insert(toVertex, distTo.get(toVertex) + input.estimatedDistanceToGoal(toVertex, end));
                    }

                }

            }

        }

        //is this where I am supposed to be doing all the final assignments that I will be returning?
        if (pq.peek().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            timeSpent = sw.elapsedTime();
            solutionWeight = distTo.get(end); //make sure you are updating this correctly so that u can call end

            //writing the final solution list
            Vertex j = end;
            solution.add(0, end);

            while(!j.equals(start)) {
                WeightedEdge<Vertex> e = edgeTo.get(j);
                solution.add(0, e.from());
                j = e.from();
            }

            return;
        }

        if (timeout < sw.elapsedTime()) {
            outcome = SolverOutcome.TIMEOUT;
            solution.clear();
            timeSpent = sw.elapsedTime();
            return;
        }

        if (pq.size() ==0) {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();
        }

    }

//    private List<Vertex> pathWriter(WeightedEdge<Vertex> vertex, Vertex start) {
//        ArrayList<Vertex> path = new ArrayList<>();
//        path.add(vertex.to());
//        path.add(vertex.from());
//        while (!vertex.from().equals(start))
//
//        Vertex j = end;
//        solution.add(0, end);
//
//        while(j != start) {
//            solution.add(0, edgeTo.get(j));
//            j = visitedPred[j];
//        }
//    }


    public SolverOutcome outcome() {
        return outcome;

        //returns one of SolverOutcome.SOLVED, SOlverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE

        //return SOLVED if the AStarSolver was able to complete all the world in the time given

        //UNSOLVABLE if the PQ became empty before finding the solution

        //TIMEOUT if the solver ran out of tiem

        // check too see if you have run out of time every time you dequeue

    }
    public List<Vertex> solution() {
//        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome().equals((SolverOutcome.UNSOLVABLE))) {
//            return null; //or just return solution if we end up not adding anythign in this list
//        }
        return solution;

        //return a list of verticies corresponding to a solution.
        //should be empty if result was TIMEOUT or UNSOLVABLE

    }
    public double solutionWeight() {

        return solutionWeight;

        //the total weight of the given solution, taking into account edge weights
        //should be 0 if result was TIMEOUT or UNSOLVABLE

    }
    public int numStatesExplored() {
        return numStatesExplored;

        //the total number of prority queue poll() operations
        //should be the number of states explored so far if result was TIMEOUT or UNSOLVABLE

    }
    public double explorationTime() {
        return timeSpent;

        //the total time spent in seconds by the construcy0r

    }
}
