import java.util.ArrayList;


// This interface is used so that the tester can use the same interface for both the student's
// heap and the solved heap. This way, the tester can test the student's alogirthm with a correct heap implementation.

public interface HeapInterface {
    public void buildHeap(ArrayList<City> cities);
    public void insertNode(City in);
    public City findMin();
    public City extractMin();
    public void delete(int index);
    public void changeKey(City r, int newKey);
    public ArrayList<City> toArrayList();
    public String toString();
}
