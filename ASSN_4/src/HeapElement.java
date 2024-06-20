import java.beans.VetoableChangeListener;

/**
 * This is used to represent an element in a Heap
 * That is the Vertex and its affinity which will be used as the priority
 */
public class HeapElement <T>{
    private int affinity = 10; // This will be priority
    private Vertex<T> element = null; // This is the actual element that is going to be in the heap

    /**
     * This is the constructor that is used to set the affinity and initialize the element
     * @param affinity
     * @param givenVertex
     */
    public HeapElement(int affinity, Vertex<T> givenVertex){
        this.affinity = affinity;
        this.element = givenVertex;
    }

    /**
     * This is used to get the affinity of the element
     * @return int
     */
    public int getAffinity(){
        return this.affinity;
    }

    /**
     * This is used to get the Vertex that is related to an element on the heap
     * @return Vertex<T>
     */
    public Vertex<T> getElement(){
        return this.element;
    }
}
