/**
 * GRAPH CLASS
 * This is the Graph class that holds all the vertices
 */

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Graph <T>{
    private final Map<T, Vertex<T>> vertices;

    private List<T> minimumList;

    private boolean flagFound = false;

    // Constructor
    public Graph(){
        vertices = new HashMap<>();
    }

    /**
     * getVertices() method
     * This is used to get all the vertices present in the graph
     * @return
     */

    protected Map<T,Vertex<T>> getVertices(){
        return this.vertices;
    }

    /**
     * Adds a new person to the social graph. Names are unique i.e, can be used as keys
     *
     * @param name the name of the individual
     * @return returns true if the person was added; false if the person was already in the graph
     */
    public boolean addPersonGraph(String name) {
        // To create the new vertex

        // To check if the graph doesn't contain the name to be added
        if(!vertices.containsKey(name)){
            Vertex vertex = new Vertex(name);
            Vertex newVertex = vertices.put((T) name, vertex);
            return true; // This is when the
        }
        return false;
    }

    /**
     * Removes a person from the social graph (remember to remove the references!)
     *
     * @param name the name of the individual
     * @throws PersonNotFoundException if the person is not in the graph.
     */

    public boolean removePersonGraph(String name) throws PersonNotFoundException {
        // To check if the person name to remove is on the graph
        if(vertices.containsKey(name)){
            // To remove the name specified from the map of users
           Vertex<T> mainVertexToRemove = this.vertices.remove(name);

            for(int i=0; i< mainVertexToRemove.getNormalAdjacencyList().size(); i++){

                Vertex<T> toRemove = mainVertexToRemove.getNormalAdjacencyList().get(i);
                // The Vertex on the adjacency list
               // Vertex<T> otherVertexOfMainVertex = mainVertexToRemove.getAdjacencyList().remove();

                // This is used to remove the main user from the adjacency list of his friends as well
                toRemove.getAdjacencyList().remove(mainVertexToRemove);
            }
            //This is to remove the main user's friends from the main user adjacency list
            mainVertexToRemove.clearAdjacencyList();

            return true;
        }
        else{
            throw new PersonNotFoundException();
        }
    }

    /**
     * Connects two people in the social graph.
     * The method will return without changing the graph if firstPerson == secondPerson or if a connection already exist between both
     *
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    public void connectPeopleGraph(String firstPerson, String secondPerson) throws PersonNotFoundException {

        // Then to get the vertex of each of the two people
        Vertex<T> firstPersonVertex = this.vertices.get(firstPerson);
        Vertex<T> secondPersonVertex = this.vertices.get(secondPerson);

        // Then to check if any of the derived vertices are null that is; not present
        if(firstPersonVertex == null){
            throw new PersonNotFoundException(firstPerson + " not found");
        }
        if(secondPersonVertex == null){
            throw new PersonNotFoundException(secondPerson + " not found");
        }

        // Then to connect the users when both users are present in the graph
        firstPersonVertex.connectPerson(secondPersonVertex);
        secondPersonVertex.connectPerson(firstPersonVertex);
    }

    /**
     * Returns a sorted list (A-Z) of connections (1st degree only)
     *
     * @param name the name of the person we want the list
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    public List<String> getConnectionsGraph(String name) throws PersonNotFoundException {
        List<String> connectedPeople;
        // To check if the name being considered is present in the Graph
        Vertex<T> mainVertex = this.vertices.get(name);
        if(mainVertex == null){
            throw new PersonNotFoundException(name + " is not present in the graph so there should be no expected connections");
        }
        // To instantiate the list
        connectedPeople = new ArrayList<>();
        for(Vertex<T> connectedPerson : mainVertex.getAdjacencyList().keySet()){
            connectedPeople.add(String.valueOf(connectedPerson.getName()));
        }

        // To sort the list
        Collections.sort(connectedPeople);

        return connectedPeople;

    }

    /**
     * Gets the social degree of separation between two individuals.
     * Individuals may have multiple paths connecting them; this method should look for the smallest path
     *
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @return an integer representing the degree of separation between both people; -1 if they are not connected
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    public int getMinimumDegreeOfSeparationGraph(String firstPerson, String secondPerson) throws PersonNotFoundException {
        // To check if there is a possibility that the names being searched
        if(!this.vertices.containsKey(firstPerson)){
            throw new PersonNotFoundException(firstPerson + " not found");
        }
        else if(!this.vertices.containsKey(secondPerson)){
            throw new PersonNotFoundException(secondPerson+ " not found");
        }

        // To perform a breadth first search
        searchPathUsingBFS(firstPerson,secondPerson);

        // To get the list
        List<Vertex<T>> minimumPathList = new ArrayList<>();

        Vertex<T> person1 = this.vertices.get(firstPerson);
        Vertex<T> person2 = this.vertices.get(secondPerson);

        Vertex<T> tempPerson = person1;

        // Then to get the list out based on the parents of one vertex till the end (Since we have added parents from the BST)
        while(tempPerson!=null){
            System.out.println(tempPerson.getName());
            minimumPathList.add(tempPerson);
            tempPerson = tempPerson.getParent();
        }

        // To clear the constraints of marking as visited and setting parent
        this.clearAllConstraints();

        // To check if there is a possible connection
        // Then we return the size of the list but without the starting point (-1)
        if(minimumPathList.contains(person2)){
            return minimumPathList.size() -1;
        }
        else{
            return -1; // If not connected
        }

    }


    /**
     * This is used to perform a breadth first search for two people
     * @param firstPerson
     * @param secondPerson
     */
    public void searchPathUsingBFS(String firstPerson, String secondPerson){
        Vertex<T> person1 = this.vertices.get(firstPerson);
        Vertex<T> person2 = this.vertices.get(secondPerson);

        // Implement a queue
        Queue<Vertex<T>> pathQueue = new LinkedList<>();

        // To add the initial person
        pathQueue.add(person2);

        // Mark as visited (initial)
        person2.setVisited();

        // For the iteration
        while(pathQueue.size() > 0){
            Vertex<T> personInFocus = pathQueue.remove(); // Remove first element

            // To get the friend list of a User
            Set<Vertex<T>> adjacency  = personInFocus.getAdjacencyList().keySet();

            // Iterate through its adjacency
            for(Vertex iterVertex: adjacency){
                // To check if it has not been visited
                if(!iterVertex.getVisited()){

                    iterVertex.setParent(personInFocus); // To set its parent
                    if(person1.getName() == iterVertex.getName()){ // That is found already
                        return;
                    }
                    // To mark as visited
                    iterVertex.setVisited();
                    // To add to queue
                    pathQueue.add(iterVertex);
                }
            }
        }


    }

    /**
     * peopleAreConnectedMethod()
     * This is used to check if two valid people are connected
     * @param person1
     * @param person2
     * @return This returns "true" if person1 and person 2 are connected and false otherwise
     */
    public boolean peopleAreConnected(String person1, String person2){
        Vertex firstPerson = this.vertices.get(person1);
        Vertex secondPerson = this.vertices.get(person2);

        // To check if they are friends
        boolean check1 = firstPerson.getAdjacencyList().keySet().contains(person2);
        boolean check2 = secondPerson.getAdjacencyList().keySet().contains(person1);

        return check2 && check1;
    }

    /**
     * This is to remove all the constraints from the graph vertices
     * That is parent, visited
     */
    public void clearAllConstraints(){
        for(Vertex<T> iterVertex : this.vertices.values()){
            iterVertex.clearConstraints();
        }
    }

    /**
     * Gets a list (sorted A-z) of the connections of a given individual up to a certain degree of separation
     *
     * @param name     the person's name
     * @param maxLevel the max level (1-n) where 1 is same as getConnections. Value is inclusive.
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    public List<String> getConnectionsToDegreeGraph(String name, int maxLevel) throws PersonNotFoundException {
        // To get the person from the graph
        Vertex<T> personBeingConsidered = this.vertices.get(name);

        List<T> connectionsToDegreeList = new ArrayList<>();

        // If the person is not present then we throw an exception
        if(personBeingConsidered == null){
            throw new PersonNotFoundException();
        }
        else if(maxLevel == 1){
            return this.getConnectionsGraph(name);
        }
        // For the normal instance that is, max level >1
        this.getConnectionsToDegreeGraphHelper(personBeingConsidered, maxLevel,connectionsToDegreeList);

        // To clear all necessary constraints like marking vertices visited
        this.clearAllConstraints();

        // To sort the list
        Collections.sort((List<String>) connectionsToDegreeList);

        return (List<String>) connectionsToDegreeList;

    }

    /**
     * getConnectionsToDegreeGraphHelper() method
     * This is the helper method for deriving the connections up to a particular degree
     * @param person
     * @param maxLevel
     * @param listGiven
     */
    public void getConnectionsToDegreeGraphHelper(Vertex<T> person, int maxLevel, List<T> listGiven){
       Queue<Map<Vertex<T>,Integer>> newQueue = new LinkedList<>();
       //Queue<Vertex<T>> newQueue = new LinkedList<>();

        String mainPerson = (String) person.getName();

        Map<Vertex<T>, Integer> mapConsidered = new HashMap<>();
        person.setVisited();
        mapConsidered.put(person, maxLevel);
        Map<Vertex<T>, Integer> mapInFocus = mapConsidered;
        Set<Vertex<T>> setInFocus = null;
        Vertex<T> vertexInFocus = person;
        newQueue.add(mapConsidered);

        while(mapInFocus.get(vertexInFocus) > -1 && !newQueue.isEmpty()){
             mapInFocus = newQueue.remove();
             setInFocus = mapInFocus.keySet();

            for(Vertex<T> one : setInFocus){
                vertexInFocus = one;
            }

            for(Vertex<T> each : vertexInFocus.getAdjacencyList().keySet()){
                if(!each.getVisited()){
                    each.setVisited();
                    System.out.println(mapInFocus.get(vertexInFocus));
                    mapConsidered = new HashMap<>();
                    mapConsidered.put(each,mapInFocus.get(vertexInFocus)-1);
                    newQueue.add(mapConsidered);
                }
            }

            // To not add the last element and possibly the element in consideration
            if(mapInFocus.get(vertexInFocus) > -1 && !mainPerson.equals(vertexInFocus.getName()) ){
                listGiven.add(vertexInFocus.getName());
            }
        }

    }

    /**
     * Determines if everyone in the graph is connected to everyone else (that is the graph is connected)
     * This method has an undefined behaviour if the social graph is empty
     *
     * @return true if there is path to everyone;
     */

    public boolean areWeAllConnectedGraph() {
        // If we only have less than two vertices then we can be sure that they are connected
        if(this.vertices.size() <2){
            return true;
        }

        // To get a random placement to start checking from
        int randomPlacement = (int) Math.round(Math.random() * (vertices.size()-1));

        // For testing purpose, I will be choosing names randomly
        // To store the names
        String[] namesConsidered = new String[this.vertices.size()];
        int counter = 0;

        for(Vertex<T> rand : this.vertices.values()){
            namesConsidered[counter] = String.valueOf(rand.getName());
            counter++;
        }

        Vertex<T> randomVertex = vertices.get(namesConsidered[randomPlacement]);

        // This is the actual size of the graph, that is the vertices available in the connection
        int connectionSize = this.vertices.size();

        // To get number of vertices reached by the randomly selected vertex
        int randomVertexCount = this.countVertices(randomVertex);

        // To clear all possible constraints of visited or parent assigning
        this.clearAllConstraints();

        // If the social network(graph) was connected randomVertexCount == connectionSize
        return connectionSize == randomVertexCount;



    }

    /**
     * This is to add to count the vertices from a given point
     * @param randomVertex
     * @return int representing the count of the vertices
     */
    public int countVertices(Vertex<T> randomVertex){
        int counter = 0;
        if(!randomVertex.getVisited()){
            randomVertex.setVisited();
            // Set counter as 1
            counter = 1;
            // Recursively call inner vertices
            for(Vertex<T> followingVertex : randomVertex.getAdjacencyList().keySet()){
                // Starting from the bottom the counter will be added up
                counter = counter + countVertices(followingVertex);
            }
        }

        // To return the final count
        return counter;
    }

    /**
     * This is used to connect people with a weight of connection
     * @param firstPerson
     * @param secondPerson
     * @param affinity
     * @throws PersonNotFoundException
     */
    public void connectPeopleGraph(String firstPerson, String secondPerson , int affinity) throws PersonNotFoundException{
        // Then to get the vertex of each of the two people
        Vertex<T> firstPersonVertex = this.vertices.get(firstPerson);
        Vertex<T> secondPersonVertex = this.vertices.get(secondPerson);

        // Then to check if any of the derived vertices are null that is; not present
        if(firstPersonVertex == null){
            throw new PersonNotFoundException(firstPerson + " not found");
        }
        if(secondPersonVertex == null){
            throw new PersonNotFoundException(secondPerson + " not found");
        }

        // Then to connect the users when both users are present in the graph with affinity
        firstPersonVertex.connectPersonWithAffinity(secondPersonVertex,affinity);
        secondPersonVertex.connectPersonWithAffinity(firstPersonVertex, affinity);
    }

    /**
     * This is used to get the strongest connections from one user to other users on the graph
     * @param name
     * @return
     * @throws PersonNotFoundException
     */
    public List<String> getStrongestConnectionGraph(String name) throws PersonNotFoundException {
        Vertex<T> personVertex = this.vertices.get(name);

        if(personVertex == null){
            throw new PersonNotFoundException();
        }

        List<String> strongestList = new ArrayList<>();

        int highestAffinity = personVertex.getHighestAffinity();

        for(Vertex<T> each : personVertex.getAdjacencyList().keySet()){
            int affinity = personVertex.getAffinity(each);
            if(affinity == highestAffinity){
                strongestList.add((String) each.getName());
            }
        }

        // To sort the list
        Collections.sort(strongestList);

        return strongestList;
    }

    /**
     * This is used to add an edge between two vertices
     * @param firstPerson
     * @param secondPerson
     */

    public void addEdge(Vertex<T> firstPerson, Vertex<T> secondPerson) {

        // Then to connect the users when both users are present in the graph
        if(firstPerson != null && secondPerson != null){
            Vertex<T> person1 = this.vertices.get(firstPerson.getName());
            Vertex<T> person2 = this.vertices.get(secondPerson.getName());
            person1.connectPerson(person2);
            person2.connectPerson(person1);
        }

    }

    /**
     * This is used to get the strongest path between two users that is the path that has the lowest affinity overall
     * @param firstPerson
     * @param secondPerson
     * @return
     */
    public List<String> getStrongestPath(String firstPerson , String secondPerson) throws PersonNotFoundException {
        // To get the First Person and the Second Person
        Vertex<T> person1 = this.vertices.get(firstPerson);
        Vertex<T> person2 = this.vertices.get(secondPerson);

        // To check if any of the people being considered is null
        if(person1 == null || person2 == null){
            // To check if the first person is null and throw PersonNotFoundException
            if(person1 == null){
                throw new PersonNotFoundException(firstPerson +" not found");
            }
            // To check if the second person is null and throw PersonNotFoundException
            else if(person2 == null){
                throw new PersonNotFoundException(secondPerson + " not found");
            }
        }

        clearAllConstraints();

        // Create a new graph to add the possible shortest path;
        Graph<T> temporaryGraph = new Graph<>();

        // Then to create our custom priority queue
        CustomPriorityQueue<T> customPriorityQueue = new CustomPriorityQueue<>(this.vertices.size());

        // To choose our first person as the first person
        customPriorityQueue.enqueue(0, this.vertices.get(firstPerson));

        for(Vertex<T> eachPerson : this.vertices.values()){
            eachPerson.setWeight((int) (Math.pow(2, 64) - 1)); // To set it as the maximum possible value based on test

            temporaryGraph.addPersonGraph((String) eachPerson.getName());


        }


        while (customPriorityQueue.getSize() > 0){
            // To get the minimum value from the priority queue
            Vertex<T> minimumVertex = customPriorityQueue.dequeue();


            if(!minimumVertex.getVisited()){
                // Set the vertex visited
                minimumVertex.setVisited();


                temporaryGraph.addEdge(minimumVertex.getParent(), minimumVertex);
                /**
                if(minimumVertex.getParent()!=null){
                    System.out.print(minimumVertex.getParent().getName());
                }
                else{
                    System.out.print(minimumVertex.getParent());
                }
                System.out.println(" "+ minimumVertex.getName());
                 **/
                // System.out.println(minimumVertex.getAdjacencyList().size());

                for(Vertex<T> eachVertex : minimumVertex.getAdjacencyList().keySet()){
                    int tempAffinity = minimumVertex.getAffinity(eachVertex);
                    if(eachVertex.getWeight() > tempAffinity){
                        eachVertex.setWeight(tempAffinity);
                        eachVertex.setParent(minimumVertex);
                        customPriorityQueue.enqueue(tempAffinity,eachVertex);
                    }
                }
            }
        }
        clearAllConstraints();

        List<T> finalList = new ArrayList<>();
        List<T> finalList1 = new ArrayList<>();
        //System.out.println(temporaryGraph.vertices.size());




        dfsSearch(temporaryGraph, temporaryGraph.vertices.get(firstPerson), temporaryGraph.vertices.get(secondPerson), finalList);

        this.flagFound = false;


        return (List<String>) finalList;
    }


    /**
     * This is used to get the path between two possible points
     * @param graphInFocus
     * @param start
     * @param endPoint
     * @param listInFocus
     * @return
     */
    public void dfsSearch(Graph<T> graphInFocus, Vertex<T> start, Vertex<T> endPoint, List<T> listInFocus){
        if(this.flagFound){
            return;
        }


        if(start.getName().equals(endPoint.getName())){
            this.flagFound = true;
        }


        start.setVisited();
        listInFocus.add(start.getName());

        for(Vertex<T> eachVertex: start.getAdjacencyList().keySet()){
            if(!eachVertex.getVisited()){
                eachVertex.setVisited();
                dfsSearch(graphInFocus, eachVertex, endPoint, listInFocus);
            }
        }

        if(!this.flagFound){
            listInFocus.remove(start.getName());
        }

    }
}
