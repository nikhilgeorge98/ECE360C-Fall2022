package Programming_Assignment_1;
/*
 * Name: Nikhil George
 * EID: ng25762
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        /* TODO implement this function */

        Integer m = problem.getUniversityCount();
        Integer n = problem.getStudentCount();

        ArrayList<Integer> test_matching = problem.getStudentMatching();

        ArrayList<ArrayList<Integer>> university_preference = problem.getUniversityPreference();
        ArrayList<ArrayList<Integer>> student_preference = problem.getStudentPreference();
        
        for(int s=0; s<test_matching.size(); s++){
            int u = test_matching.get(s);
            if(u != -1){
                int i = 0;
                while(university_preference.get(u).get(i) != s){
                    int ss = university_preference.get(u).get(i);
                    int uu = test_matching.get(ss);
                    int j = 0;
                    while(student_preference.get(ss).get(j) != uu){
                        if(student_preference.get(ss).get(j) == u){
                            return false;
                        }
                        j++;
                    }
                    i++;
                }
            }
            else{
                int i = 0;
                while(i<m){
                    int uni = student_preference.get(s).get(i);
                    int stud = test_matching.indexOf(uni);
                    int j = 0;
                    while(university_preference.get(uni).get(j) != stud){
                        if(university_preference.get(uni).get(j) == s)
                            return false;
                        j++;
                    }
                    i++;
                }
                
            }
        }
        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_universityoptimal(Matching problem) {
        /* TODO implement this function */

        Integer m = problem.getUniversityCount();
        Integer n = problem.getStudentCount();

        ArrayList<Integer> test_matching = problem.getStudentMatching();

        ArrayList<ArrayList<Integer>> university_preference = problem.getUniversityPreference();
        ArrayList<ArrayList<Integer>> student_preference = problem.getStudentPreference();

        ArrayList<Integer> university_positions = problem.getUniversityPositions();

        Integer[][] iup_array = new Integer[m][n]; //Inverse university preference list
        Integer[][] isp_array = new Integer[n][m]; //Inverse student preference list

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                // System.out.println("outer: "+ i + " middle: "+ j + " inner: "+ university_preference.get(i).get(j));
                iup_array[i][university_preference.get(i).get(j)] = j;
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                // System.out.println("outer: "+ i + " middle: "+ j + " inner: "+ university_preference.get(i).get(j));
                isp_array[i][student_preference.get(i).get(j)] = j;
            }
        }

        LinkedList<Integer> freeUniversities = new LinkedList<>(); //List of universities still looking to add students
        for(int i=0;i<m;i++){
            freeUniversities.add(i);
        }

        int[] count = new int[m]; //Array to store no. of offers made by a university

        int[] waitlist = new int[m]; //to ensure each university gets only one spot in the queue at any instant
        for(int i =0; i<m;i++){
            waitlist[i] = 1;
        }

        ArrayList<Integer> final_matching = new ArrayList<>(); //stores the final matching
        for(int i =0; i<n;i++){
            final_matching.add(-1);
        }

        int[] pos_filled = new int[m]; //counts no. of postions filled by each uni

        // System.out.println(freeUniversities);
        Integer currentUniversity; //current university u
        Integer topStudent; //top preference of u --> s
        Integer curMatchedUniversity; // current matching of s --> u'

        while(!freeUniversities.isEmpty()){
            currentUniversity = freeUniversities.poll();
            waitlist[currentUniversity]--;
            // System.out.println("Current uni: "+currentUniversity);
            // System.out.println("Current uni count: "+count[currentUniversity]);
            topStudent = university_preference.get(currentUniversity).get(count[currentUniversity]);
            // System.out.println("Current highest student: "+topStudent);
            if(final_matching.get(topStudent)==-1){
                final_matching.set(topStudent, currentUniversity);
                // System.out.println(currentUniversity +" and " + topStudent + " are matched");
                pos_filled[currentUniversity]++;
                if(university_positions.get(currentUniversity)>pos_filled[currentUniversity] && waitlist[currentUniversity]==0){ //could lead to bugs
                    freeUniversities.add(currentUniversity);
                    waitlist[currentUniversity]++;
                }
            }
            else{
                curMatchedUniversity = final_matching.get(topStudent);
                // System.out.println("Current highest student's match: "+curMatchedUniversity);
                if(isp_array[topStudent][currentUniversity]<isp_array[topStudent][curMatchedUniversity]){
                    // System.out.println(topStudent + " prefers " + currentUniversity + " to " + curMatchedUniversity);
                    final_matching.set(topStudent, currentUniversity);
                    pos_filled[currentUniversity]++;
                    if(waitlist[curMatchedUniversity]==0){
                        freeUniversities.add(curMatchedUniversity);
                        waitlist[curMatchedUniversity]++;
                    }
                    // System.out.println(curMatchedUniversity + " is free");
                    pos_filled[curMatchedUniversity]--;
                    if(university_positions.get(currentUniversity)>pos_filled[currentUniversity] && waitlist[currentUniversity]==0){ //could lead to bugs
                        freeUniversities.add(currentUniversity);
                        waitlist[currentUniversity]++;
                    }
                }
                else{
                    // System.out.println(topStudent + " prefers " + curMatchedUniversity + " to " + currentUniversity);
                    if(waitlist[currentUniversity]==0){
                        freeUniversities.add(currentUniversity);
                        waitlist[currentUniversity]++;
                    }
                }
            }
            count[currentUniversity]++;
            // System.out.println("Current free universities: "+freeUniversities);
        }

        // for(int i = 0; i<n;i++){
        //     System.out.println(i +" "+ final_matching.get(i));
        // }

        problem.setStudentMatching(final_matching);

        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        /* TODO implement this function */

        Integer m = problem.getUniversityCount();
        Integer n = problem.getStudentCount();

        ArrayList<Integer> test_matching = problem.getStudentMatching();

        ArrayList<ArrayList<Integer>> university_preference = problem.getUniversityPreference();
        ArrayList<ArrayList<Integer>> student_preference = problem.getStudentPreference();

        ArrayList<Integer> university_positions = problem.getUniversityPositions();

        Integer[][] iup_array = new Integer[m][n]; //Inverse university preference list
        Integer[][] isp_array = new Integer[n][m]; //Inverse student preference list

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                // System.out.println("outer: "+ i + " middle: "+ j + " inner: "+ university_preference.get(i).get(j));
                iup_array[i][university_preference.get(i).get(j)] = j;
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                // System.out.println("outer: "+ i + " middle: "+ j + " inner: "+ university_preference.get(i).get(j));
                isp_array[i][student_preference.get(i).get(j)] = j;
            }
        }

        LinkedList<Integer> freeStudents = new LinkedList<>(); //List of students still looking for universities
        for(int i=0;i<n;i++){
            freeStudents.add(i);
        }

        int[] count = new int[n]; //Array to store no. of offers made by each student

        ArrayList<Integer> final_matching = new ArrayList<>();
        for(int i =0; i<n;i++){
            final_matching.add(-1);
        }

        int[] pos_filled = new int[m];

        // System.out.println(freeStudents);
        Integer currentStudent;
        Integer topUniversity;
        Integer lowestMatchedStudent;

        Integer[] lowestMatched = new Integer[m];
        for(int i =0; i<m;i++){
            lowestMatched[i] = -1;
        }

        ArrayList<ArrayList<Integer>> universityMatching = new ArrayList<>();
        for(int i =0; i<m;i++){
            universityMatching.add(new ArrayList<Integer>());
        }

        // for(int i=0; i<m; i++){
        //     for(int j=0; j<n; j++){
        //         // System.out.println("outer: "+ i + " middle: "+ j + " inner: "+ university_preference.get(i).get(j));
        //         System.out.print(iup_array[i][j]+" ");
        //     }
        //     System.out.println();
        // }

        while(!freeStudents.isEmpty()){
            currentStudent = freeStudents.poll();
            // System.out.println("Current Student: "+ currentStudent);
            // System.out.println("Current student count is: "+count[currentStudent]);
            topUniversity = student_preference.get(currentStudent).get(count[currentStudent]);
            count[currentStudent]++;
            // System.out.println("Current Student's top University: "+ topUniversity);
            if(university_positions.get(topUniversity)>pos_filled[topUniversity]){//more positions to be filled
                final_matching.set(currentStudent,topUniversity);
                // System.out.println("Student: "+currentStudent+" and university: "+topUniversity+" are matched");
                universityMatching.get(topUniversity).add(currentStudent);
                // System.out.println("Current matching list of university: "+topUniversity+"-"+universityMatching.get(topUniversity));
                pos_filled[topUniversity]++;

                // System.out.println(lowestMatched[topUniversity]);
                // if(lowestMatched[topUniversity]!=-1){
                    // System.out.println("first "+iup_array[topUniversity][currentStudent]);
                    // System.out.println("second "+iup_array[topUniversity][lowestMatched[topUniversity]]);
                // }
                
                if(lowestMatched[topUniversity]==-1 || iup_array[topUniversity][currentStudent] > iup_array[topUniversity][lowestMatched[topUniversity]])
                    lowestMatched[topUniversity] = currentStudent;
                // System.out.println("Current lowest matched of university: "+topUniversity+"-"+lowestMatched[topUniversity]);
            }
            else{
                lowestMatchedStudent = lowestMatched[topUniversity];
                // System.out.println("Current Student's top University: "+ topUniversity+" has lowest matched student "+lowestMatchedStudent);
                if(iup_array[topUniversity][lowestMatchedStudent] > iup_array[topUniversity][currentStudent]){
                    final_matching.set(currentStudent,topUniversity);
                    // System.out.println("Student: "+currentStudent+" and university: "+topUniversity+" are matched");
                    universityMatching.get(topUniversity).add(currentStudent);
                    final_matching.set(lowestMatchedStudent, -1);
                    // System.out.println(lowestMatchedStudent+" is free");
                    universityMatching.get(topUniversity).remove(new Integer(lowestMatchedStudent));
                    // System.out.println("Current matching list of university: "+topUniversity+"-"+universityMatching.get(topUniversity));
                    if(count[lowestMatchedStudent]<m)
                        freeStudents.add(new Integer(lowestMatchedStudent));
                    int least = currentStudent;
                    int pref_least = iup_array[topUniversity][currentStudent];
                    for(int i = 0; i<university_positions.get(topUniversity); i++){
                        if(iup_array[topUniversity][universityMatching.get(topUniversity).get(i)] > pref_least){
                            least = universityMatching.get(topUniversity).get(i);
                            pref_least = iup_array[topUniversity][universityMatching.get(topUniversity).get(i)];
                        }
                    }
                    lowestMatched[topUniversity] = least;
                    // System.out.println("Current lowest matched of university: "+topUniversity+"-"+lowestMatched[topUniversity]);
                }
                else{
                    if(count[currentStudent]<m)
                        freeStudents.add(currentStudent);
                }
            }
        //    System.out.println("Current free students: "+freeStudents);
        }

        problem.setStudentMatching(final_matching);

        return problem;
    }
}