import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Vertex class
 * This is the class that represents each person
 * It also holds a list of the person's friends (adjacency list) --  This is also a map of the Vertex of the user's friend and the weight
 * */
public class Vertex <T>{

    // Variables
    private T name;

    // This is used to get the friend list of a user and the friend list with weight
    private Map<Vertex<T>, Integer> adjacencyList;

    private Vertex<T> next;
    private Vertex<T> parent;
    private boolean visited;

    private int weight = 10;

    public Vertex (T name){
        this.name = name;
        adjacencyList = new HashMap<>();
        visited = false;
        parent = null;
    }

    /**
     * This is used to return the adjacency list of a vertex
     * @params none
     * @return Map<Vertex<T>, Integer>
     */
    public Map<Vertex<T>, Integer> getAdjacencyList(){
        return this.adjacencyList;
    }

    /**
     * This is used to derive a set of Vertex representing the user friends.
     * @return ArrayList<Vertex<T>> That represents the friends of a particular user (Vertex)
     */
    public ArrayList<Vertex<T>> getNormalAdjacencyList(){
        ArrayList<Vertex<T>> adjacencyListToReturn = new ArrayList<>();

        for(Vertex<T> eachVertex : this.adjacencyList.keySet()){
            adjacencyListToReturn.add(eachVertex);
        }
        return adjacencyListToReturn;
    }

    /**
     * This is used to clear the adjacency list of the vertex (if the vertex has been deleted)
     */
    public void clearAdjacencyList(){
        this.adjacencyList = null;
    }

    /**
     * This is used to check the hash code of a particular name
     * @params none
     * @return integer representing the hash code of the name
     */
    public int getHasCode(){
        return this.name.hashCode();
    }

    /**
     * This is used to add a new person to the adjacency list (Friend list)
     * @param personToConnect
     * @return
     */
    public boolean connectPerson(Vertex<T> personToConnect){
        if(personToConnect!=null){
            // To check if the person to be connected is not already in the adjacency list of the user
            if (!this.adjacencyList.containsKey(personToConnect) && personToConnect.name != this.name){
                // Unweighted Connection
                this.adjacencyList.put(personToConnect , 10);
                return true;
            }
        }
        return false;
    }

    /**
     * This is used to connect two people with an affinity
     * @param personToConnect
     * @param affinity
     * @return
     */
    public boolean connectPersonWithAffinity(Vertex<T> personToConnect, int affinity){
        // To check if we are not trying to connect the same person
        if (!personToConnect.name.equals(this.name)){

            this.adjacencyList.put(personToConnect ,affinity);

            return true;
        }
        return false;
    }

    /**
     * This is used to get the name of the vertex
     * @params none
     * @return the name of the person
     */
    public T getName(){
        return this.name;
    }

    /**
     * setVisited() method
     * This is used to mark the vertex "visited" after a visit during programs
     */
    public void setVisited(){
        this.visited = true;
    }

    /**
     * getVisited() method
     * This is used to check if a vertex has already been set visited
     * @return boolean indicating if visited already or not
     */
    public boolean getVisited(){
        return this.visited;
    }

    /**
     * setParent() method
     * This is used to set the parent of the particular vertex
     */
    public void setParent(Vertex<T> givenParent){
        this.parent = givenParent;
    }

    /**
     * getParent() method
     * This is used to return the vertex which is the parent of a vertex in the program
     */
    public Vertex<T> getParent(){
        return this.parent;
    }

    /**
     * This is used to clear all the constraints when done with a program
     * That is, visited, or parent
     */
    public void clearConstraints(){
        this.visited = false;
        this.parent = null;
        this.weight = 10;
    }

    /**
     * This is used to get the highest affinity of a particular vertex
     * @return
     */
    public int getHighestAffinity(){
        int highestAffinity = 10;
        for(int each : this.adjacencyList.values()){
            highestAffinity = Math.min(highestAffinity, each);
        }
        return highestAffinity;
    }

    /**
     * This is used to get the affinity between a user and another user
     * @param consideredVertex
     * @return
     */
    public int getAffinity(Vertex<T> consideredVertex){
        int affinity = this.adjacencyList.get(consideredVertex);
        return affinity;
    }

    /**
     * This is used to give an affinity in between two vertices
     * @param firstPerson
     * @param secondPerson
     */
    public void setAffinity(Vertex<T> firstPerson, Vertex<T> secondPerson){

    }

    /**
     * This is used to get an assigned weight of a vertex - This is to be used when using Prims algorithm
     * @return
     */
    public int getWeight(){
        return this.weight;
    }

    /**
     * This used to set the weight of a Vertex
     * @param value
     */
    public void setWeight(int value){
        this.weight = value;
    }


}
