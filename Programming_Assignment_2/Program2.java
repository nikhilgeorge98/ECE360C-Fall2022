/*
 * Name: Nikhil George
 * EID: ng25762
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 implements ProgramTwoInterface {
    
    
    // additional constructor fields may be added, but don't delete or modify anything already here


    /**
     * findMinimumRouteDistance(Problem problem)
     *
     *  @param problem - the problem to solve.
     * 
     *  @param problem  - contains the cities, start, dest, and heap.
     * 
     * @return the minimum distance possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(Problem problem) {
        // System.out.println("We are here");

        // Some code to get you started
        City start = problem.getStart();
        City dest = problem.getDest();
        start.setMinDist(0);

        HeapInterface heap = problem.getHeap();     // get the heap
        heap.buildHeap(problem.getCities());        // build the heap

        // TODO: implement this function
        // System.out.println("source: "+start.getName());
        // System.out.println("dest: "+dest.getName());
        // System.out.println();
        // System.out.println("Initial heap");
        // for(City x: heap.toArrayList()){
        //     // System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
        //     for(int i = 0; i<x.getNeighbors().size(); i++){
        //         City neighbor = x.getNeighbors().get(i);
        //         int weight = x.getWeights().get(i);
        //         // System.out.print(neighbor.getName() + "--" + weight + "; ");
        //     }
        //     // System.out.println();
        // }

        City min;
        // ArrayList<City> visited = new ArrayList<>(); 

        while(heap.toArrayList().size()!=0){
            // System.out.println("Heap------>");
            // for(City x: heap.toArrayList()){
            //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
            // }
            // System.out.println();
            min = heap.extractMin();
            // System.out.println("Heap after min extraction------>");
            // for(City x: heap.toArrayList()){
            //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
            // }
            // System.out.println();
            // System.out.println("Extracted node: "+min.getName()+" mindist:"+min.getMinDist());
            if(min.getName() == dest.getName()) return min.getMinDist();
            // visited.add(min);
            // for(City m : visited){
            //     System.out.print(m.getName()+"----"+m.getMinDist()+" ");
            // }
            // System.out.println();
            // for(City neighbor: min.getNeighbors()){
            for(int i = 0; i<min.getNeighbors().size(); i++){
                City neighbor = min.getNeighbors().get(i);
                int weightFromMin = min.getWeights().get(i);
                // System.out.println(i + " neighbor: "+ neighbor.getName()+ " weightfromMin: " +weightFromMin);
                // System.out.println("neighbor mindist: "+ neighbor.getMinDist()+ " min mindist: " +min.getMinDist()+" weightfromMin: " +weightFromMin);
                if(neighbor.getMinDist() > min.getMinDist()+weightFromMin){
                    heap.changeKey(neighbor, min.getMinDist()+weightFromMin);
                    // System.out.println();
                }
            }
        }


        return dest.getMinDist();
    }

    /**
     * findMinimumLength()
     *
     * @return The minimum total optical line length required to connect (span) each city on the given graph.
     * Assume the given graph is always connected.
     */
    public int findMinimumLength(Problem problem) {
        // TODO: implement this function
        // City start = problem.getStart();
        City start = problem.getCities().get(0);
        start.setMinDist(0);

        HeapInterface heap = problem.getHeap();     // get the heap
        heap.buildHeap(problem.getCities());        // build the heap

        City min;
        int length = 0;

        while(heap.toArrayList().size()!=0){
            // System.out.println("Heap------>");
            // for(City x: heap.toArrayList()){
            //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
            // }
            // System.out.println();
            min = heap.extractMin();
            length += min.getMinDist();
            // System.out.println("current Length: "+ length);
            // System.out.println("Heap after min extraction------>");
            // for(City x: heap.toArrayList()){
            //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
            // }
            // System.out.println();
            // System.out.println("Extracted node: "+min.getName()+" mindist:"+min.getMinDist());
            for(int i = 0; i<min.getNeighbors().size(); i++){
                City neighbor = min.getNeighbors().get(i);
                int weightFromMin = min.getWeights().get(i);
                // System.out.println(i + " neighbor: "+ neighbor.getName()+ " weightfromMin: " +weightFromMin);
                // System.out.println("neighbor mindist: "+ neighbor.getMinDist()+ " min mindist: " +min.getMinDist()+" weightfromMin: " +weightFromMin);
                if(heap.toArrayList().contains(neighbor) && weightFromMin < neighbor.getMinDist()){
                    heap.changeKey(neighbor, weightFromMin);
                }
            }

        }
        // System.out.println("Heap------>");
        // for(City x: heap.toArrayList()){
        //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
        // }

        return length;
    }

    //returns edges and weights in a string.
    public String toString(Problem problem){ 
        String o = "";
        for (City v : problem.getCities()) {
            boolean first = true;
            o += "City ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with distances ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

}
