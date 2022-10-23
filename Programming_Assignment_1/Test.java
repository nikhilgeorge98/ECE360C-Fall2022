package Programming_Assignment_1;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Test {
    public static void main(String[] args) {
        univeristyOptimal();
        // boolean match = testthismatch();
        // if(match)
        //     System.out.println("Stable matching");
        // else
        //     System.out.println("Unstable matching");
    }

    public static boolean testthismatch(){
        ArrayList<ArrayList<Integer>> university_preference = new ArrayList<ArrayList<Integer>>();
        university_preference.add(new ArrayList<Integer>(Arrays.asList(0,1,2,3,4)));
        university_preference.add(new ArrayList<Integer>(Arrays.asList(0,2,1,4,3)));
        university_preference.add(new ArrayList<Integer>(Arrays.asList(2,1,4,3,0)));
        university_preference.add(new ArrayList<Integer>(Arrays.asList(3,0,4,2,1)));
        university_preference.add(new ArrayList<Integer>(Arrays.asList(0,4,3,1,2)));

        ArrayList<ArrayList<Integer>> student_preference = new ArrayList<ArrayList<Integer>>();
        student_preference.add(new ArrayList<Integer>(Arrays.asList(4,1,2,3,0)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(0,4,3,2,1)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(3,1,2,0,4)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(0,3,4,2,1)));
        student_preference.add(new ArrayList<Integer>(Arrays.asList(0,2,4,1,3)));

        Integer m = new Integer(5);
        Integer n = new Integer(5);

        ArrayList<Integer> test_matching = new ArrayList<Integer>();
        test_matching.add(4);
        test_matching.add(0);
        test_matching.add(-1);
        test_matching.add(3);
        test_matching.add(2);

        System.out.println("Matching->"+test_matching);
        for(int s=0; s<test_matching.size(); s++){
            int u = test_matching.get(s);
            System.out.println("student1 = "+s+" university1 = "+u);
            if(u != -1){
                int i = 0;
                System.out.println("Preference list of university = "+u+"->"+university_preference.get(u));
                while(university_preference.get(u).get(i) != s){
                    int ss = university_preference.get(u).get(i);
                    int uu = test_matching.get(ss);
                    System.out.println("student2 = "+ss+" university2 = "+uu);
                    int j = 0;
                    System.out.println("Preference list of student = "+ss+"->"+student_preference.get(ss));
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
                while(i<=m){
                    int uni = student_preference.get(s).get(i);
                    int stud = test_matching.indexOf(uni);
                    int j = 0;
                    System.out.println("Pairing: ("+uni+","+stud+")");
                    System.out.println(university_preference.get(uni));
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

    public static void univeristyOptimal(){
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

        Queue<Integer> freeUniversities = new LinkedList<>(); //List of universities still looking to add students
        for(int i=0;i<m;i++){
            freeUniversities.add(i);
        }

        int[] count = new int[m]; //Array to store no. of offers made by a university

        int[] waitlist = new int[m]; //to ensure each university gets only one spot in the queue at any instant
        for(int i =0; i<m;i++){
            waitlist[i] = 1;
        }

        ArrayList<Integer> matching = new ArrayList<>();
        for(int i =0; i<n;i++){
            matching.add(-1);
        }

        int[] pos_filled = new int[m];

        System.out.println(freeUniversities);
        Integer currentUniversity;
        Integer topStudent;
        Integer curMatchedUniversity;

        while(!freeUniversities.isEmpty()){
            currentUniversity = freeUniversities.poll();
            waitlist[currentUniversity]--;
            System.out.println("Current uni: "+currentUniversity);
            System.out.println("Current uni count: "+count[currentUniversity]);
            topStudent = university_preference.get(currentUniversity).get(count[currentUniversity]);
            System.out.println("Current highest student: "+topStudent);
            if(matching.get(topStudent)==-1){//hire[topStudent]
                matching.set(topStudent, currentUniversity);
                // hire[topStudent] = currentUniversity;
                System.out.println(currentUniversity +" and " + topStudent + " are matched");
                pos_filled[currentUniversity]++;
                if(university_positions.get(currentUniversity)>pos_filled[currentUniversity] && waitlist[currentUniversity]==0){ //could lead to bugs
                    freeUniversities.add(currentUniversity);
                    waitlist[currentUniversity]++;
                }
            }
            else{
                // curMatchedUniversity = hire[topStudent];
                curMatchedUniversity = matching.get(topStudent);
                System.out.println("Current highest student's match: "+curMatchedUniversity);
                if(isp_array[topStudent][currentUniversity]<isp_array[topStudent][curMatchedUniversity]){
                    System.out.println(topStudent + " prefers " + currentUniversity + " to " + curMatchedUniversity);
                    // hire[topStudent] = currentUniversity;
                    matching.set(topStudent, currentUniversity);
                    pos_filled[currentUniversity]++;
                    if(waitlist[curMatchedUniversity]==0){
                        freeUniversities.add(curMatchedUniversity);
                        waitlist[curMatchedUniversity]++;
                    }
                    System.out.println(curMatchedUniversity + " is free");
                    pos_filled[curMatchedUniversity]--;
                    if(university_positions.get(currentUniversity)>pos_filled[currentUniversity] && waitlist[currentUniversity]==0){ //could lead to bugs
                        freeUniversities.add(currentUniversity);
                        waitlist[currentUniversity]++;
                    }
                }
                else{
                    System.out.println(topStudent + " prefers " + curMatchedUniversity + " to " + currentUniversity);
                    if(waitlist[currentUniversity]==0){
                        freeUniversities.add(currentUniversity);
                        waitlist[currentUniversity]++;
                    }
                }
            }
            count[currentUniversity]++;
            System.out.println("Current free universities: "+freeUniversities);
        }

        for(int i = 0; i<n;i++){
            // System.out.println(i +" "+ hire[i]);
            System.out.println(i +" "+ matching.get(i));
        }

    }
}
