package Programming_Assignment_2;
/*
 * Name: Nikhil George
 * EID: ng25762
 */

// Implement your heap here
// Private methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Heap implements HeapInterface {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     * 
     * Should assign cities to the minHeap instance variable
     *
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        minHeap = cities;
        // TODO: implement this method
        for(int i = 0; i<cities.size(); i++){
            // System.out.println("Currently inserting "+cities.get(i).getName()+" at index "+i);
            this.minHeap.set(i, cities.get(i));
            heapifyUp(cities.get(i), i);
            // System.out.println();
        }
        // for(int i = 0; i<cities.size(); i++){
        //     System.out.println("Currently inserting "+cities.get(i).getName()+" at index "+i);
        //     insertNode(cities.get(i));
        //     System.out.println();
        // }
        // System.out.println(this.minHeap.toString());
        // String output = "";
        // for (int i = 0; i < minHeap.size(); i++) {
        //     output += minHeap.get(i).getName() + " ";
        // }
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        // TODO: implement this method
        this.minHeap.add(in);
        int index = this.minHeap.size()-1;
        // System.out.println("Inserted "+in.getName()+" with weight "+in.getMinDist()+" at index "+index);
        heapifyUp(in, index);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public City findMin() {
        // TODO: implement this method
        return this.minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        // TODO: implement this method
        City min = findMin();
        delete(0);
        return min;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
        int lastIndex = this.minHeap.size()-1;
        City toDelete = this.minHeap.get(index);
        City lastCity = this.minHeap.get(lastIndex);

        // System.out.println("Initial heap");
        // for(City x: this.minHeap){
        //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
        // }

        this.minHeap.set(index, lastCity);
        this.minHeap.set(lastIndex, toDelete);
        this.minHeap.remove(lastIndex);
        // System.out.println("After deleting, before heapify");
        // for(City x: this.minHeap){
        //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
        // }

        heapifyUp(lastCity, index);
        // System.out.println("Finished Heapify Up");
        heapifyDown(lastCity, index);
        // System.out.println("Finished Heapify Down");
        // for(City x: this.minHeap){
        //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
        // }
    }

    /**
     * changeKey(City r, int newDist)
     * Changes minDist of City s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the City in the heap that needs to be updated.
     * @param newDist - the new cost of City r in the heap (note that the heap is keyed on the values of minDist)
     */
    public void changeKey(City r, int newDist) {
        // TODO: implement this method
        // System.out.println("Current key of city "+ r.getName()+" at index "+ r.getIndex() + " is " + r.getMinDist());
        this.minHeap.get(r.getIndex()).setMinDist(newDist);
        // System.out.println("Changed key of city "+ r.getName()+" at index "+ r.getIndex() + " is " + r.getMinDist());
        heapifyUp(this.minHeap.get(r.getIndex()), r.getIndex());
        // System.out.println("Finished Heapify Up");
        heapifyDown(this.minHeap.get(r.getIndex()), r.getIndex());
        // System.out.println("Finished Heapify Down");
        // for(City x: this.minHeap){
        //     System.out.println(x.getName() +" "+ x.getIndex() +" "+ x.getMinDist());
        // }

    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

    private void heapifyUp(City city, int index){
        // System.out.println("Inside Heapify Up");
        int j;
        if(index>0){
            // System.out.println("More than 1 element so going into condition");
            j = (int)Math.floor((index-1)/2);
            // System.out.println("Parent of city "+ city.getName() + " at index " + index + " is city " + this.minHeap.get(j).getName()+ " at index " + j);
            // System.out.println("Weight of city "+ city.getName() + " is " + city.getMinDist() + " and weight of parent " + this.minHeap.get(j).getName()+ " is " + this.minHeap.get(j).getMinDist());
            if(city.getMinDist() < this.minHeap.get(j).getMinDist() || ((city.getMinDist() == this.minHeap.get(j).getMinDist()) && (city.getName() < this.minHeap.get(j).getName()))){
                // System.out.println("Need to swap");
                City tempCity = this.minHeap.get(j);

                tempCity.setIndex(index);
                city.setIndex(j);

                this.minHeap.set(j, city);
                this.minHeap.set(index, tempCity);
                heapifyUp(city, j);
            }
        }
        else{
            city.setIndex(0);
        }
        // return null;
    }

    private void heapifyDown(City city, int index){
        int n = minHeap.size();
        // System.out.println("Inside Heapify Down");
        // System.out.println("Size heap "+n);
        // System.out.println("City: " + city.getName() + " weight: "+city.getMinDist());
        int j;
        int left, right;
        if(2*index + 1 >= n){
            // System.out.println("No children");
            return;
        }
        else if(2*index + 1 < n-1){
            left = 2*index + 1;
            right = 2*index + 2;
            // System.out.println("Left child: " + this.minHeap.get(left).getName() + " weight: "+this.minHeap.get(left).getMinDist());
            // System.out.println("Right child: " + this.minHeap.get(right).getName() + " weight: "+this.minHeap.get(right).getMinDist());
            if(this.minHeap.get(left).getMinDist() < this.minHeap.get(right).getMinDist() || ((this.minHeap.get(left).getMinDist() == this.minHeap.get(right).getMinDist()) && (this.minHeap.get(left).getName() < this.minHeap.get(right).getName()))){
                j = left;
            }
            else{   
                j = right;
            }
        }
        else{
            j = 2*index + 1;
        }
        if(this.minHeap.get(j).getMinDist() < city.getMinDist() || ((this.minHeap.get(j).getMinDist() == city.getMinDist()) && (this.minHeap.get(j).getName() < city.getName()))){
            // System.out.println("Need to swap with city: "+this.minHeap.get(j).getName()+" at index "+j);
            City tempCity = this.minHeap.get(j);

            tempCity.setIndex(index);
            city.setIndex(j);

            this.minHeap.set(j, city);
            this.minHeap.set(index, tempCity);
            heapifyDown(city, j);
        }
    }

    
///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
