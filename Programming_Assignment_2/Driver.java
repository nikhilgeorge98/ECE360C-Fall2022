// This Driver file will be replaced by ours during grading
// Do not include this file in your final submission

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver {
    private static String filename; // input file name
    private static boolean testHeap; // set to true by -h flag
    private static boolean testDijkstra; // set to true by -s flag
    private static boolean testMST; // set to true by -c flag
    private static boolean testINDV; // set to true by -c flag
    public static Program2 program = new Program2(); // instance of your graph

    private static void usage() { // error message
        System.err.println("usage: java Driver [-h] [-d] [-m] [-i] <filename>");
        System.err.println("\t-h\tTest Heap implementation");
        System.err.println("\t-s\tTest findMinimumRouteDistance implementation");
        System.err.println("\t-c\tTest findMinimumLength implementation");
        System.err.println("\t-i\tTest individual test cases");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        Heap heap = new Heap(); // Students heap implementation
        Problem problem = new Problem(heap);   

        parseArgs(args);
        problem.setCities(parseInputFile(filename));;
        testRun(problem);
    }

    public static void parseArgs(String[] args) {
        boolean flagsPresent = false;
        if (args.length == 0) {
            usage();
        }
        filename = "";
        testHeap = false;
        testDijkstra = false;
        testMST = false;
        for (String s : args) {
            if (s.equals("-h")) {
                flagsPresent = true;
                testHeap = true;
            } else if (s.equals("-d")) {
                flagsPresent = true;
                testDijkstra = true;
            } else if (s.equals("-m")) {
                flagsPresent = true;
                testMST = true;
            } else if(s.equals("-i")) {
                flagsPresent = true;
                testINDV = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testHeap = true;
            testDijkstra = true;
            testMST = true;
            testINDV = true;
        }
    }

    public static ArrayList<City> parseInputFile(String filename) throws FileNotFoundException {
        ArrayList<City> cities = new ArrayList<>();

        int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        numE = Integer.parseInt(inputSize[1]);
        HashMap<Integer, ArrayList<NeighborPriceTuple>> tempNeighbors = new HashMap<>();
        for (int i = 0; i < numV; ++i) {

            String[] pairs = sc.nextLine().split(" ");
            String[] pricePairs = sc.nextLine().split(" ");

            Integer currNode = Integer.parseInt(pairs[0]);
            City currentStudent = new City(currNode);
            cities.add(currNode, currentStudent);
            ArrayList<NeighborPriceTuple> currNeighbors = new ArrayList<>();
            tempNeighbors.put(currNode, currNeighbors);

            for (int k = 1; k < pairs.length; k++) {
                Integer neighborVal = Integer.parseInt(pairs[k]);
                Integer priceVal = Integer.parseInt(pricePairs[k]);
                currNeighbors.add(new NeighborPriceTuple(neighborVal, priceVal));
            }
        }
        for (int i = 0; i < cities.size(); ++i) {
            City currStudent = cities.get(i);
            ArrayList<NeighborPriceTuple> neighbors = tempNeighbors.get(i);
            for (NeighborPriceTuple neighbor : neighbors) {
                currStudent.setNeighborAndWeight(cities.get(neighbor.neighborID), neighbor.price);
            }
        }
        sc.close();
        return cities;
    }

    // feel free to alter this method however you wish, we will replace it with our own version during grading
    public static void testRun(Problem problem) throws FileNotFoundException {
        if (testHeap) {
            // test out Heap.java here
            // the code below is an example of how to test your heap
            // you will want to do more extensive testing than just this
            City zero = new City(0);
            zero.setMinDist(10);
            City one = new City(1);
            one.setMinDist(10);
            City two = new City(2);
            two.setMinDist(10);
            City three = new City(3);
            three.setMinDist(10);
            City four = new City(4);
            four.setMinDist(10);
            City five = new City(5);
            five.setMinDist(10);

            City seven = new City(7);
            seven.setMinDist(33);

            ArrayList<City> tester = new ArrayList<>();
            tester.add(two);
            tester.add(four);
            tester.add(one);
            tester.add(three);
            tester.add(five);
            tester.add(zero);

            // System.out.println("Tester array: "+ tester+"\n");

            problem.setCities(tester);
            System.out.println(problem.getHeap());
            if(problem.getHeap().toArrayList().contains(two)){
                System.out.println("**************************");
                System.out.println(two.getIndex());
            }
            
            problem.getHeap().changeKey(zero, 10);
            problem.getHeap().changeKey(one, 10);
            // System.out.println(problem.getHeap());
            System.out.println(verifyHeap(problem.getHeap()));
            System.out.println("##################################################3");
            System.out.println("Finding min");
            System.out.println("Minimum:"+problem.getHeap().findMin().getName());
            System.out.println("##################################################3");
            System.out.println("Extracting min");
            System.out.println("Minimum extracted:"+problem.getHeap().extractMin().getName());
            System.out.println(problem.getHeap());
            System.out.println(verifyHeap(problem.getHeap()));
            if(problem.getHeap().toArrayList().contains(two)){
                System.out.println("**************************");
                System.out.println(two.getIndex());
            }

            System.out.println("##################################################3");
            System.out.println("Deleting");
            problem.getHeap().delete(1);
            System.out.println(problem.getHeap());
            System.out.println(verifyHeap(problem.getHeap()));
            System.out.println("##################################################3");
            System.out.println("Finding min");
            System.out.println("Minimum:"+problem.getHeap().findMin().getName());
            System.out.println("##################################################3");
            System.out.println("Extracting min");
            System.out.println("Minimum extracted:"+problem.getHeap().extractMin().getName());
            System.out.println(problem.getHeap());
            System.out.println(verifyHeap(problem.getHeap()));
            if(problem.getHeap().toArrayList().contains(two)){
                System.out.println("**************************");
                System.out.println(two.getIndex());
            }

            System.out.println("##################################################3");
            System.out.println("Adding seven");
            problem.getHeap().insertNode(seven);
            System.out.println(problem.getHeap());
            System.out.println(verifyHeap(problem.getHeap()));
            System.out.println("##################################################3");
            System.out.println("Finding min");
            System.out.println("Minimum:"+problem.getHeap().findMin().getName());
            System.out.println("##################################################3");
            System.out.println("Extracting min");
            System.out.println("Minimum extracted:"+problem.getHeap().extractMin().getName());
            System.out.println(problem.getHeap());
            System.out.println(verifyHeap(problem.getHeap()));

            if(problem.getHeap().toArrayList().contains(two)){
                System.out.println("**************************");
                System.out.println(two.getIndex());
            }
        }

        if (testDijkstra) {
            // test out Program2.java findMinimumRouteDistance here
            System.out.println("\nGiven graph: ");
            problem.setStart(problem.getCities().get(0));
            problem.setDest(problem.getCities().get(1));
            System.out.println("Length of shortest path from start to dest: \n" +
                program.findMinimumRouteDistance(problem));
        }

        if (testMST) {
            // test out Program2.java findMinimumLength here
            System.out.println("\nGiven graph: ");
            System.out.println(problem);
            System.out.println("Minimum total optical line distance: \n" + program.findMinimumLength(problem));
        }

        if (testINDV) {
            test_findMinRoute();
            test_heap_find_min();
            test_heap_insert_and_delete();
        }
    }

    // The next 3 methods are examples of functions you can use to test your code.
    // You will want to write more tests than just these.
    // These do not guarantee that your code is correct, but they can help you find some bugs.
    
    public static void test_findMinRoute() throws FileNotFoundException {
        Verifier runner = new Verifier("tests/2.txt",0,3);
		Program2 student_program = new Program2();
		assertEquals(6,student_program.findMinimumRouteDistance(runner.student_problem));
    }

    public static  void test_heap_find_min() throws FileNotFoundException{
        Verifier runner = new Verifier("tests/3.txt");
		runner.student_heap.buildHeap(runner.cities);
		City min = runner.student_heap.findMin();
		City removed = runner.student_heap.extractMin();
        
        // try catch null pointer exception
        try {
            assertEquals(removed.getName(), min.getName()); 
        } catch (NullPointerException e) {
            System.out.println("Null pointer exception in test_heap_find_min()\ntest_heap_find_min() failed\n\n");
        }
    }

    public static void test_heap_insert_and_delete() throws FileNotFoundException{
        Verifier runner = new Verifier();

        City one = new City(1);
        one.setMinDist(20);
        ArrayList<City> tester = new ArrayList<>();

        tester.add(one);
		runner.student_heap.buildHeap(tester);

        try {
            assertEquals(runner.student_heap.toArrayList().size(), 1);
            runner.student_heap.delete(0);
            assertEquals(runner.student_heap.toArrayList().size(), 0);
        } catch (NullPointerException e) {
            System.out.println("Null pointer exception in test_heap_insert_and_delete()\n test_heap_insert_and_delete() failed\n\n");
        }
}


    private static boolean assertEquals(int i, int findMinimumRouteDistance) {
        // print out the two values and if they are equal or not
        System.out.println(i + " == " + findMinimumRouteDistance + " : " + (i == findMinimumRouteDistance));
        return i == findMinimumRouteDistance;
    }

    private static class NeighborPriceTuple {
        public Integer neighborID;
        public Integer price;

        NeighborPriceTuple(Integer neighborID, Integer price) {
            this.neighborID = neighborID;
            this.price = price;
        }
    }

    public static boolean verifyHeap(HeapInterface h) {
        for (int i = 0; i < h.toArrayList().size(); i++) {
            if (2*i+1 < h.toArrayList().size() && h.toArrayList().get(2*i + 1).getMinDist() < h.toArrayList().get(i).getMinDist()) {
                return false;
            }

            if (2*i+2 < h.toArrayList().size() && h.toArrayList().get(2*i + 2).getMinDist() < h.toArrayList().get(i).getMinDist()) {
                return false;
            }
        }
        return true;
    }
}
