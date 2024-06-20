import java.util.List;

public class EnhancedSocialNetwork extends EnhancedSocialConnections {

    // To get the graph
    Graph enhancedGraphInFocus;
    SocialNetwork network;
    public EnhancedSocialNetwork(){
        network = new SocialNetwork();
        enhancedGraphInFocus = network.getNewConnectionGraphInFocus();
    }


    /**
     * Connects two individuals on the social graph given an affinity from 1-10 where 1 is the strongest
     * The method will return without changing the graph if firstPerson == secondPerson.
     * If there is already connection, the method will update the affinity level
     *
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @param level        the affinity level from 1-10 (inclusive). 1 is strongest, 10 is weakest
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    @Override
    public void connectPeople(String firstPerson, String secondPerson, int level) throws PersonNotFoundException {
            enhancedGraphInFocus.connectPeopleGraph(firstPerson, secondPerson, level);
    }

    /**
     * Returns an ordered list (A-Z) containing the strongest connections to the individual.
     * E.g. the individual has three affinity 2 connections and five affinity 6 the method should only return a list if the three affinity 2 Individuals
     *
     * @param name the person's name
     * @return and ordered list containing the strongest connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public List<String> getStrongestConnection(String name) throws PersonNotFoundException {
        List<String> strongestConnections =  enhancedGraphInFocus.getStrongestConnectionGraph(name);

        return strongestConnections;
    }

    /**
     * Returns a list containing the path from the first person to the second one with the strongest affinity.
     * THIS LIST CANNOT BE SORTED
     *
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @return the path (names) between both individuals connecting them with the strongest affinity possible
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    @Override
    public List<String> getStrongestPath(String firstPerson, String secondPerson) throws PersonNotFoundException {
        return this.enhancedGraphInFocus.getStrongestPath(firstPerson, secondPerson);
    }

    /**
     * Returns a list containing the path from the first person to the second one with the weakest affinity overall.
     * THIS LIST CANNOT BE SORTED
     *
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @return the path (names) between both individuals connecting them with the weakest affinity possible
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    @Override
    public List<String> getWeakestPath(String firstPerson, String secondPerson) throws PersonNotFoundException {
        return null;
    }

    /**
     * Adds a new person to the social graph. Names are unique i.e, can be used as keys
     * @param name the name of the individual
     * @return returns true if the person was added; false if the person was already in the graph
     */
    @Override
    public boolean addPerson(String name) {
         return network.addPerson(name);
    }

    /**
     * Removes a person from the social graph (remember to remove the references!)
     * @param name the name of the individual
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public boolean removePerson(String name) throws PersonNotFoundException {
        return network.removePerson(name);
    }

    /**
     * Returns a sorted list (A-Z) of connections (1st degree only)
     *
     * @param name the name of the person we want the list
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public List<String> getConnections(String name) throws PersonNotFoundException {
        return network.getConnections(name);
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
    @Override
    public int getMinimumDegreeOfSeparation(String firstPerson, String secondPerson) throws PersonNotFoundException {
        return network.getMinimumDegreeOfSeparation(firstPerson, secondPerson);
    }

    /**
     * Gets a list (sorted A-z) of the connections of a given individual up to a certain degree of separation
     *
     * @param name     the person's name
     * @param maxLevel the max level (1-n) where 1 is same as getConnections. Value is inclusive.
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public List<String> getConnectionsToDegree(String name, int maxLevel) throws PersonNotFoundException {
        return network.getConnectionsToDegree(name, maxLevel);
    }

    /**
     * Determines if everyone in the graph is connected to everyone else (that is the graph is connected)
     * This method has an undefined behaviour if the social graph is empty
     *
     * @return true if there is path to everyone;
     */
    @Override
    public boolean areWeAllConnected() {
        return network.areWeAllConnected();
    }
}
