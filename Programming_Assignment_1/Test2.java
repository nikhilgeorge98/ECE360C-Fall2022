package Programming_Assignment_1;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Test2 {
    public static void main(String[] args) {
        studentOptimal();
    }

    public static void studentOptimal(){
        ArrayList<ArrayList<Integer>> university_preference = new ArrayList<ArrayList<Integer>>();
        university_preference.add(new ArrayList<Integer>(Arrays.asList(4,0,3,2,5,1)));
        university_preference.add(new ArrayList<Integer>(Arrays.asList(5,2,1,4,0,3)));
        university_preference.add(new ArrayList<Integer>(Arrays.asList(2,5,1,4,3,0)));

        ArrayList<ArrayList<Integer>> student_preference = new ArrayList<ArrayList<Integer>>();
        student_preference.add(new ArrayList<Integer>(Arrays.asList(0,2,1)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(1,0,2)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(1,2,0)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(0,1,2)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(2,1,0)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(0,1,2)));

        Integer m = new Integer(3);
        Integer n = new Integer(6);

        ArrayList<Integer> university_positions = new ArrayList<>();
        university_positions.add(1);
        university_positions.add(2);
        university_positions.add(1);

        System.out.println("No. of universities: " + m + "\nNo. of students: " + n);
        System.out.println("Student preference list-> " + student_preference);
        System.out.println("University preference list-> " + university_preference);
        System.out.println("University positions-> " + university_positions);

        Integer[][] iup_array = new Integer[m][n];
        Integer[][] isp_array = new Integer[n][m];

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

        for(int i = 0; i < n; i++){
            for(int j = 0; j<m; j++){
                System.out.print(isp_array[i][j] + " ");
            }
            System.out.println();
        }

        Queue<Integer> freeStudents = new LinkedList<>(); //List of students still looking for universities
        for(int i=0;i<n;i++){
            freeStudents.add(i);
        }

        int[] count = new int[n]; //Array to store no. of offers made by each student

        ArrayList<Integer> final_matching = new ArrayList<>();
        for(int i =0; i<n;i++){
            final_matching.add(-1);
        }

        int[] pos_filled = new int[m];

        System.out.println(freeStudents);
        Integer currentStudent;
        Integer topUniversity;
        Integer lowestMatchedStudent;

        Integer[] lowestMatched = new Integer[m];
        for(int i =0; i<m;i++){
            lowestMatched[0] = -1;
        }

        ArrayList<ArrayList<Integer>> universityMatching = new ArrayList<>();
        for(int i =0; i<m;i++){
            universityMatching.add(new ArrayList<Integer>());
        }

        while(!freeStudents.isEmpty()){
            currentStudent = freeStudents.poll();
            topUniversity = student_preference.get(currentStudent).get(count[currentStudent]);
            if(university_positions.get(topUniversity)>pos_filled[topUniversity]){//more positions to be filled
                final_matching.set(currentStudent,topUniversity);
                universityMatching.get(topUniversity).add(new Integer(currentStudent));
                pos_filled[topUniversity]++;
                if(lowestMatched[topUniversity]==-1 || iup_array[topUniversity][currentStudent] > iup_array[topUniversity][lowestMatched[topUniversity]])
                    lowestMatched[topUniversity] = currentStudent;
            }
            else{
                lowestMatchedStudent = lowestMatched[topUniversity];
                if(iup_array[topUniversity][lowestMatchedStudent] > isp_array[topUniversity][currentStudent]){
                    final_matching.set(currentStudent,topUniversity);
                    universityMatching.get(topUniversity).add(new Integer(currentStudent));
                    final_matching.set(lowestMatchedStudent, -1);
                    universityMatching.get(topUniversity).remove(new Integer(lowestMatchedStudent));
                    freeStudents.add(new Integer(lowestMatchedStudent));
                    int least = currentStudent;
                    int pref_least = iup_array[topUniversity][currentStudent];
                    for(int i = 0; i<university_positions.get(topUniversity); i++){
                        if(iup_array[topUniversity][i] > pref_least){
                            least = i;
                            pref_least = iup_array[topUniversity][i];
                        }
                    }
                    lowestMatched[topUniversity] = least;
                }
                else{
                    freeStudents.add(currentStudent);
                }
            }
            count[currentStudent]++;
            System.out.println("Current free students: "+freeStudents);
        }

        for(int i = 0; i<n;i++){
            // System.out.println(i +" "+ hire[i]);
            System.out.println(i +" "+ final_matching.get(i));
        }

    }
}
