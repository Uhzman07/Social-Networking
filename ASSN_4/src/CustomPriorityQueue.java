/**
 * This is a custom priority queue that is used to hold values based on their priority that is affinity
 * This makes use of an array based heap to implement the heap
 * @param <T>
 */
public class CustomPriorityQueue<T>{

    // This is the size of elements in the heap
    private int size = 0;
    private int capacity = 0;

    // This is the array that holds the elements that are on the heap
    private HeapElement<T> [] elementArray;

    /**
     * This is the constructor to set the size of the array based heap
     * @param sizeOfGraph
     */
    public CustomPriorityQueue(int sizeOfGraph){
        // Then to change the capacity of the heap
        this.capacity = sizeOfGraph + 1; // Note that the additional one is to represent the empty space that is left in the array
        elementArray = new HeapElement[this.capacity];
    }

    /**
     * This is used to remove an element in the heap array
     * @param affinity
     * @param elementToAdd
     */

    public void enqueue(int affinity, Vertex<T> elementToAdd){
        // To increase the size of the array since we are adding an element
        this.size = this.size + 1;

        // Then to create a Heap Element
        HeapElement<T> elementCreated = new HeapElement<>(affinity, elementToAdd);

        //System.out.println(this.size);

        // Then to add the element to the heap array
        if(this.size == this.capacity){
            this.elementArray[this.size]  = elementCreated;
        }
        else{
            reshapeArray();
            this.elementArray[this.size]  = elementCreated;
        }


        int checker = this.size;

        while(checker > 1 && this.elementArray[checker].getAffinity() < this.elementArray[checker/2].getAffinity()){
            HeapElement elementConsidered = this.elementArray[checker];

            // Then to swap the values
            this.elementArray[checker] = this.elementArray[checker/2];
            this.elementArray[checker/2] = elementConsidered;

            // Move to the new spot
            checker = checker/2;
        }

    }

    /**
     * This is used to remove the element at the top of the heap that is the element that has the lowest affinity
     * This also calls the sift down operation to reshape the heap array after removing an element
     */
    public Vertex<T> dequeue(){

        HeapElement firstElement = this.elementArray[1];

        // Move to the end of the array heap
        this.elementArray[1] = this.elementArray[size];

        // Reduce the size
        this.size = this.size-1;

        siftDown(this.elementArray, 1, this.size);

        //System.out.println(size);

        return firstElement.getElement();

    }

    /**
     * This is used to perform a siftDown operation on the heap array after removing the element at the front of the array
     * This is used the heap array in the appropriate form after removing the least affinity element at the front of the array
     * @param givenElementArray
     * @param startingPoint
     * @param customSize
     */
    public void siftDown(HeapElement<T> [] givenElementArray, int startingPoint, int customSize){

        int minimum = customSize;

        while(2* startingPoint <= customSize){
            int leftChild = 2 * startingPoint;
            int rightChild = 2* startingPoint +1;

            // To get the lowest value between the left and right child
            if(size > 1 && givenElementArray[rightChild].getAffinity() < givenElementArray[leftChild].getAffinity()){
                minimum = rightChild;
            }
            else{
                minimum = leftChild;
            }

            if(givenElementArray[startingPoint].getAffinity() < givenElementArray[minimum].getAffinity()){
                // Then we can be certain that the rest of the heap will maintain the MIN_HEAP property
                break; // That is the first element is less than the smallest value of its children
            }
            // Else then swap
            HeapElement<T> elementConsidered = givenElementArray[startingPoint];
            givenElementArray[startingPoint] = givenElementArray[minimum];
            givenElementArray[minimum] = elementConsidered;
            startingPoint = minimum;
        }

    }

    /**
     * This is used to get the size of the Queue
     * @return Integer
     */
    public int getSize(){
         return this.size;
    }

    public void reshapeArray(){
        int newSize = this.capacity * 2;

        HeapElement<T> [] elementArray2 = new HeapElement[newSize];

        for(int i=0; i<elementArray.length; i++){
            elementArray2[i] = elementArray[i];
        }

        this.elementArray = elementArray2;
    }

}
