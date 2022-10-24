import java.util.ArrayList;

// This class contains the main arguments for each function. Used the functions provided
// to access the necessary info for the function.


public class Problem {
    // public property for one of three types of problems

    private ArrayList<City> cities; // this is a list of all Cities, populated by Driver class
    public HeapInterface minHeap;   // this is the heap you will build and use to solve the problem
    private City start;             // this is the starting City, populated by Driver class for the findMinimumRouteDistance() function
    private City dest;              // this is the destination City, populated by Driver class for the findMinimumRouteDistance() function

    public Problem(HeapInterface heap) {
        minHeap = heap;
        cities = new ArrayList<City>();
    }

    public Problem(){}; // default constructor

    // returns a list of all cities
    public ArrayList<City> getCities() {
        return cities;
    }

    // returns the starting city
    public City getStart() {
        return start;
    }

    // returns the destination city
    public City getDest() {
        return dest;
    }

    //retunrs the heap
    public HeapInterface getHeap() {
        return minHeap;
    }

    // sets the starting city
    // Should only be called by Driver class
    public void setStart(City start) {
        this.start = start;
    }

    // sets the destination city
    // Should only be called by Driver class
    public void setDest(City dest) {
        this.dest = dest;
    }

    // set cities
    // Should only be called by Driver class
    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        this.minHeap.buildHeap(this.cities);
    }
    
}
