import java.io.FileNotFoundException;
import java.util.ArrayList;


// DO NOT MODIFY THIS FILE IN ANY WAY !!! 😂
// This class is used to generate a class that has 

// Contains varios variables and objects that are used by the tester

public class Verifier {
    String path1 = System.getProperty("user.dir");
    String path2 = "/src/";     // MAY NEED TO CHANGE THIS
    String filename;            // The file path of the input file
    // Solved_Heap solved_heap;    // Correct heap implementation
    Heap student_heap;          // Student's heap implementation
    // Problem solved_problem;     // Correct problem implementation (uses solved_heap)
    Problem student_problem;    // Student's problem implementation (uses student_heap)
    ArrayList<City> cities;     // Cities from the input file (if given) :: You can only use cities once per verifier object

    // Verifier constructor
    public Verifier(String file_path) throws FileNotFoundException {
        filename = path1 + path2 + file_path;
        student_heap = new Heap();
        // solved_heap = new Solved_Heap();
        // solved_problem = new Problem(solved_heap);
        student_problem = new Problem(student_heap);
        try{
			System.out.println(filename);
			cities = Driver.parseInputFile(filename);
		} catch (FileNotFoundException e) {
			System.out.println("File: "+filename+" not found");
            System.exit(1);
		}
        // solved_problem.setCities(cities);
		student_problem.setCities(cities);
    }

    // Verifier constructor for findMinimumRouteDistance() test cases
    public Verifier(String file_path,int start, int dest) throws FileNotFoundException {
        filename = path1 + path2 + file_path;
        student_heap = new Heap();
        // solved_heap = new Solved_Heap();

        // solved_problem = new Problem(solved_heap);
        student_problem = new Problem(student_heap);
        try{
			// System.out.println(filename);
			cities = Driver.parseInputFile(filename);
		} catch (FileNotFoundException e) {
			System.out.println("File: "+filename+" not found");
            System.exit(1);
		}
        // solved_problem.setCities(cities);
		student_problem.setCities(cities);

        // solved_problem.setStart(cities.get(start));
        // solved_problem.setDest(cities.get(dest));

        student_problem.setStart(cities.get(start));
        student_problem.setDest(cities.get(dest));
    }

    // Verifier constructor to test building a heap
    public Verifier() {
        filename = "";
        student_heap = new Heap();
        // solved_heap = new Solved_Heap();
        // solved_problem = new Problem(solved_heap);
        student_problem = new Problem(student_heap);

    }
}
