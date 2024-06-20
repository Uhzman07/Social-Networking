import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

class SocialNetworkTest {

    ////////////////////////////////////////////////  ADD PERSON TEST
    String[] people = {"Usman", "Teslim", "Aishat", "Nafisat", "Naimat", "Gafar", "Ganiu"};

    @Test
    void addPersonValid() {
        SocialNetwork network = new SocialNetwork();
        boolean added = network.addPerson(people[0]);
        assertTrue(added);
    }

    @Test
    void addPersonDuplicate() {
        SocialNetwork network = new SocialNetwork();
        boolean added = network.addPerson(people[0]);
        added = network.addPerson(people[0]);
        assertFalse(added);
    }

    @Test
    void addMultiplePeopleValid() {
        SocialNetwork network = new SocialNetwork();
        boolean finalResult = true;
        for (int i = 0; i < people.length; i++) {
            finalResult = finalResult && network.addPerson(people[i]);
        }
        assertTrue(finalResult);
    }

    @Test
    void addMultiplePeopleInValid() {
        SocialNetwork network = new SocialNetwork();
        boolean finalResult = true;
        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }
        finalResult = finalResult && network.addPerson(people[2]);
        assertFalse(finalResult);
    }

    @Test
    void checkGraphSizeAllValid() {
        SocialNetwork network = new SocialNetwork();
        boolean finalResult = true;
        for (int i = 0; i < people.length; i++) {
            finalResult = finalResult && network.addPerson(people[i]);
        }
        // After adding the people, to check the size of the connection graph
        int graphSize = network.connectionGraphInFocus.getVertices().size();

        assertEquals(graphSize, people.length);
    }

    @Test
    void checkGraphSizeInvalid() {
        SocialNetwork network = new SocialNetwork();
        boolean finalResult = true;
        for (int i = 0; i < 4; i++) {
            network.addPerson(people[i]);
        }
        for (int i = 1; i < people.length; i++) {
            network.addPerson(people[i]);
        }

        // After adding the people, to check the size of the connection graph
        int graphSize = network.connectionGraphInFocus.getVertices().size();

        assertEquals(graphSize, people.length);
    }

    //////////////////////////////////////////////// REMOVE PERSON TEST
    @Test
    void removePersonValid() {
        SocialNetwork network = new SocialNetwork();
        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }
        // To remove
        try {
            boolean isRemoved = network.removePerson(people[0]);
            assertTrue(isRemoved);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void removePersonValidSize() {
        SocialNetwork network = new SocialNetwork();
        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }
        // To remove
        try {
            network.removePerson(people[0]);
            assertEquals(network.connectionGraphInFocus.getVertices().size(), people.length - 1);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removePersonInvalid() throws PersonNotFoundException {
        SocialNetwork network = new SocialNetwork();
        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }
        // To remove
        try {
            network.removePerson("Idris");
            assertTrue(false);
        } catch (PersonNotFoundException e) {
            //throw new PersonNotFoundException(e.getMessage());
            assertTrue(true);
        }
    }


    @Test
    void removePersonValidRemovedFromFriends() {
        SocialNetwork network = new SocialNetwork();
        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }


        // To remove
        try {
            // Connect
            network.connectPeople(people[0], people[1]);
            network.connectPeople(people[0], people[2]);
            network.connectPeople(people[1], people[2]);
            network.connectPeople(people[1], people[3]);
            network.connectPeople(people[2], people[3]);

            network.removePerson(people[0]);

            // First person
            Vertex person1 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[1]);
            Vertex person2 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[2]);

            int size1 = person1.getAdjacencyList().size();
            int size2 = person2.getAdjacencyList().size();

            boolean valid = size1 == 2 && size2 == 2;

            assertTrue(valid);
        } catch (PersonNotFoundException e) {
            //throw new PersonNotFoundException(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    void connectPeopleBothValid() {
        SocialNetwork network = new SocialNetwork();
        try {
            network.addPerson(people[0]);
            //System.out.println(network.connectionGraphInFocus.getVertices().size());
            network.addPerson(people[1]);
            //System.out.println(network.connectionGraphInFocus.getVertices().size());


            Vertex person1 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[0]);
            Vertex person2 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[1]);

            network.connectPeople(people[0], people[1]);

            boolean value1 = person1.getAdjacencyList().keySet().contains(person2);
            boolean value2 = person2.getAdjacencyList().keySet().contains(person1);

            boolean finalValue = value1 && value2;

            assertTrue(finalValue);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void connectPeopleTestSamePerson() {
        SocialNetwork network = new SocialNetwork();
        try {
            network.addPerson(people[0]);

            Vertex person1 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[0]);
            Vertex person2 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[0]);

            network.connectPeople(people[0], people[0]);

            boolean value1 = person1.getAdjacencyList().keySet().contains(person2);
            boolean value2 = person2.getAdjacencyList().keySet().contains(person1);

            boolean finalValue = value1 || value2;

            assertFalse(finalValue);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void connectPeopleAlreadyConnected() {
        SocialNetwork network = new SocialNetwork();
        try {
            network.addPerson(people[0]);
            network.addPerson(people[1]);
            network.addPerson(people[2]);

            Vertex person1 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[0]);
            Vertex person2 = (Vertex) network.connectionGraphInFocus.getVertices().get(people[1]);

            network.connectPeople(people[0], people[1]);

            // To connect them again
            network.connectPeople(people[0], people[1]);

            // Extra connection for the first person
            network.connectPeople(people[0], people[2]);

            int size1 = person1.getAdjacencyList().size();
            int size2 = person2.getAdjacencyList().size();

            boolean finalValue = (size1 == 2) && size2 == 1;

            assertTrue(finalValue);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void connectPeopleBothInvalid() {
        SocialNetwork network = new SocialNetwork();
        try {

            network.addPerson(people[2]);
            network.addPerson(people[3]);

            network.connectPeople(people[0], people[1]);

            assertTrue(false);
        } catch (PersonNotFoundException e) {
            //throw new RuntimeException(e);
            assertTrue(true);
        }
    }

    @Test
    void connectPeopleFirstInvalid() {
        SocialNetwork network = new SocialNetwork();
        try {
            network.addPerson(people[1]);
            network.addPerson(people[2]);
            network.addPerson(people[3]);

            network.connectPeople(people[0], people[1]);

            assertTrue(false);
        } catch (PersonNotFoundException e) {
            //throw new RuntimeException(e);
            assertTrue(true);
        }
    }


    @Test
    void connectPeopleSecondInvalid() {
        SocialNetwork network = new SocialNetwork();
        try {
            network.addPerson(people[0]);
            network.addPerson(people[2]);
            network.addPerson(people[3]);

            network.connectPeople(people[0], people[1]);

            assertTrue(false);
        } catch (PersonNotFoundException e) {
            //throw new RuntimeException(e);
            assertTrue(true);
        }
    }

    @Test
    void getFirstConnectionsTestAllValidNoConnection() {
        SocialNetwork network = new SocialNetwork();

        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }

        try {
            List<String> desiredList = network.getConnections(people[0]);
            assertTrue(desiredList.isEmpty());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFirstConnectionsWithConnectionsNormal() {
        SocialNetwork network = new SocialNetwork();

        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }

        // To connect people
        try {
            network.connectPeople(people[0], people[1]);
            network.connectPeople(people[0], people[2]);
            network.connectPeople(people[0], people[0]);
            network.connectPeople(people[0], people[3]);
            network.connectPeople(people[1], people[5]);
            network.connectPeople(people[0], people[6]);

            List<String> desiredList = network.getConnections(people[0]);

            int counter = 0;

            if (desiredList.contains(people[3]) && desiredList.contains(people[1]) &&
                    desiredList.contains(people[2]) && desiredList.contains(people[6])) {
                counter++;
            }
            assertTrue(desiredList.size() == 4 && counter == 1);

        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFirstConnectionsWithConnectionsNormalAndSorted() {
        SocialNetwork network = new SocialNetwork();

        for (int i = 0; i < people.length; i++) {
            network.addPerson(people[i]);
        }

        // To connect people
        try {
            network.connectPeople(people[0], people[1]);
            network.connectPeople(people[0], people[2]);
            network.connectPeople(people[0], people[0]);
            network.connectPeople(people[0], people[3]);
            network.connectPeople(people[1], people[5]);
            network.connectPeople(people[0], people[6]);

            List<String> desiredList = network.getConnections(people[0]);

            int counter = 0;
            int counter1 = 0;

            if (desiredList.contains(people[3]) && desiredList.contains(people[1]) &&
                    desiredList.contains(people[2]) && desiredList.contains(people[6])) {
                counter++;
            }
            // To check order
            if (desiredList.get(0).equals("Aishat")) {
                counter1++;
            }
            if (desiredList.get(1).equals("Ganiu")) {
                counter1++;
            }
            if (desiredList.get(2).equals("Nafisat")) {
                counter1++;
            }
            if (desiredList.get(3).equals("Teslim")) {
                counter1++;
            }


            assertTrue(desiredList.size() == 4 && counter == 1 && counter1 == 4);

        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFirstConnectionsWithNameInvalid() throws PersonNotFoundException {
        SocialNetwork network = new SocialNetwork();

        for (int i = 0; i < people.length - 1; i++) {
            network.addPerson(people[i]);
        }

        // To connect people
        try {
            network.connectPeople(people[0], people[1]);
            network.connectPeople(people[0], people[2]);
            network.connectPeople(people[0], people[0]);
            network.connectPeople(people[0], people[3]);
            network.connectPeople(people[1], people[5]);
            network.connectPeople(people[0], people[5]);

            network.getConnections(people[6]);
            assertTrue(false);

        } catch (PersonNotFoundException e) {
            //throw new PersonNotFoundException(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    void testMinimumPathAllValid() {
        SocialNetwork network = new SocialNetwork();

        network.addPerson("a");
        network.addPerson("b");
        network.addPerson("c");
        network.addPerson("d");
        network.addPerson("e");
        network.addPerson("f");
        network.addPerson("g");
        network.addPerson("h");
        network.addPerson("i");
        network.addPerson("j");
        network.addPerson("k");
        network.addPerson("l");
        network.addPerson("m");
        network.addPerson("n");
        network.addPerson("s");
        network.addPerson("t");
        try {
            network.connectPeople("t", "k");
            network.connectPeople("t", "f");
            network.connectPeople("t", "l"); //
            network.connectPeople("k", "e");
            network.connectPeople("k", "f");
            network.connectPeople("j", "k");//
            network.connectPeople("j", "i");
            network.connectPeople("j", "d");
            network.connectPeople("j", "e");
            network.connectPeople("i", "d");//
            network.connectPeople("l", "f");
            network.connectPeople("l", "g");
            network.connectPeople("l", "m");//
            network.connectPeople("f", "b");
            network.connectPeople("f", "e");
            network.connectPeople("f", "g");//
            network.connectPeople("e", "b");
            network.connectPeople("e", "a");
            network.connectPeople("e", "d"); //
            network.connectPeople("d", "a");
            network.connectPeople("m", "g");
            network.connectPeople("m", "h");
            network.connectPeople("m", "n");//
            network.connectPeople("g", "b");
            network.connectPeople("g", "c");
            network.connectPeople("g", "h"); //
            network.connectPeople("b", "a");
            network.connectPeople("b", "s");
            network.connectPeople("b", "c");//
            network.connectPeople("a", "s");
            network.connectPeople("n", "h");
            network.connectPeople("h", "c");
            network.connectPeople("s", "c");

            // And then to check shortest path

            int separation = network.getMinimumDegreeOfSeparation("s", "t");
            assertEquals(separation, 3);


        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMinimumPathNotConnected() {
        SocialNetwork network = new SocialNetwork();

        network.addPerson("a");
        network.addPerson("b");
        network.addPerson("c");
        network.addPerson("d");
        network.addPerson("e");
        network.addPerson("f");
        network.addPerson("g");
        network.addPerson("h");
        network.addPerson("i");
        network.addPerson("j");
        network.addPerson("k");
        network.addPerson("l");
        network.addPerson("m");
        network.addPerson("n");
        network.addPerson("s");
        network.addPerson("t");
        network.addPerson("u");
        try {
            network.connectPeople("t", "k");
            network.connectPeople("t", "f");
            network.connectPeople("t", "l"); //
            network.connectPeople("k", "e");
            network.connectPeople("k", "f");
            network.connectPeople("j", "k");//
            network.connectPeople("j", "i");
            network.connectPeople("j", "d");
            network.connectPeople("j", "e");
            network.connectPeople("i", "d");//
            network.connectPeople("l", "f");
            network.connectPeople("l", "g");
            network.connectPeople("l", "m");//
            network.connectPeople("f", "b");
            network.connectPeople("f", "e");
            network.connectPeople("f", "g");//
            network.connectPeople("e", "b");
            network.connectPeople("e", "a");
            network.connectPeople("e", "d"); //
            network.connectPeople("d", "a");
            network.connectPeople("m", "g");
            network.connectPeople("m", "h");
            network.connectPeople("m", "n");//
            network.connectPeople("g", "b");
            network.connectPeople("g", "c");
            network.connectPeople("g", "h"); //
            network.connectPeople("b", "a");
            network.connectPeople("b", "s");
            network.connectPeople("b", "c");//
            network.connectPeople("a", "s");
            network.connectPeople("n", "h");
            network.connectPeople("h", "c");
            network.connectPeople("s", "c");

            // And then to check shortest path

            int separation = network.getMinimumDegreeOfSeparation("u", "t");
            assertEquals(separation, -1);


        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMinimumPathNotAvailable() {
        SocialNetwork network = new SocialNetwork();

        network.addPerson("a");
        network.addPerson("b");
        network.addPerson("c");
        network.addPerson("d");
        network.addPerson("e");
        network.addPerson("f");
        network.addPerson("g");
        network.addPerson("h");
        network.addPerson("i");
        network.addPerson("j");
        network.addPerson("k");
        network.addPerson("l");
        network.addPerson("m");
        network.addPerson("n");
        network.addPerson("s");
        network.addPerson("t");
        network.addPerson("u");
        try {
            network.connectPeople("t", "k");
            network.connectPeople("t", "f");
            network.connectPeople("t", "l"); //
            network.connectPeople("k", "e");
            network.connectPeople("k", "f");
            network.connectPeople("j", "k");//
            network.connectPeople("j", "i");
            network.connectPeople("j", "d");
            network.connectPeople("j", "e");
            network.connectPeople("i", "d");//
            network.connectPeople("l", "f");
            network.connectPeople("l", "g");
            network.connectPeople("l", "m");//
            network.connectPeople("f", "b");
            network.connectPeople("f", "e");
            network.connectPeople("f", "g");//
            network.connectPeople("e", "b");
            network.connectPeople("e", "a");
            network.connectPeople("e", "d"); //
            network.connectPeople("d", "a");
            network.connectPeople("m", "g");
            network.connectPeople("m", "h");
            network.connectPeople("m", "n");//
            network.connectPeople("g", "b");
            network.connectPeople("g", "c");
            network.connectPeople("g", "h"); //
            network.connectPeople("b", "a");
            network.connectPeople("b", "s");
            network.connectPeople("b", "c");//
            network.connectPeople("a", "s");
            network.connectPeople("n", "h");
            network.connectPeople("h", "c");
            network.connectPeople("s", "c");

            // And then to check shortest path

            int separation = network.getMinimumDegreeOfSeparation("z", "d=o");
            assertTrue(false);

        } catch (PersonNotFoundException e) {
            //throw new RuntimeException(e);
            assertTrue(true);
        }
    }

    @Test
    void testConnectionToDegreeValid() {
        SocialNetwork network = new SocialNetwork();
        String name = "z";
        network.addPerson("a");
        network.addPerson("b");
        network.addPerson("c");
        network.addPerson("d");
        network.addPerson("e");
        network.addPerson("f");
        network.addPerson("g");
        network.addPerson("h");
        network.addPerson("i");
        network.addPerson("j");
        network.addPerson("k");
        network.addPerson("l");
        network.addPerson("m");
        network.addPerson("n");
        network.addPerson("s");
        network.addPerson("t");
        network.addPerson("u");
        try {
            network.connectPeople("t", "k");
            network.connectPeople("t", "f");
            network.connectPeople("t", "l"); //
            network.connectPeople("k", "e");
            network.connectPeople("k", "f");
            network.connectPeople("j", "k");//
            network.connectPeople("j", "i");
            network.connectPeople("j", "d");
            network.connectPeople("j", "e");
            network.connectPeople("i", "d");//
            network.connectPeople("l", "f");
            network.connectPeople("l", "g");
            network.connectPeople("l", "m");//
            network.connectPeople("f", "b");
            network.connectPeople("f", "e");
            network.connectPeople("f", "g");//
            network.connectPeople("e", "b");
            network.connectPeople("e", "a");
            network.connectPeople("e", "d"); //
            network.connectPeople("d", "a");
            network.connectPeople("m", "g");
            network.connectPeople("m", "h");
            network.connectPeople("m", "n");//
            network.connectPeople("g", "b");
            network.connectPeople("g", "c");
            network.connectPeople("g", "h"); //
            network.connectPeople("b", "a");
            network.connectPeople("b", "s");
            network.connectPeople("b", "c");//
            network.connectPeople("a", "s");
            network.connectPeople("n", "h");
            network.connectPeople("h", "c");
            network.connectPeople("s", "c");

            // And then to check shortest path

            System.out.println(network.getConnectionsToDegree(name, 3));

        } catch (PersonNotFoundException e) {
            //throw new RuntimeException(e);
            System.out.println(name + " not in the map.");
            assertTrue(true);
        }
    }

    @Test
    void allAreConnectedTestTwo() {
        SocialNetwork network = new SocialNetwork();
        network.addPerson(people[0]);
        network.addPerson(people[1]);
        try {
            network.connectPeople(people[0], people[1]);
            assertTrue(network.areWeAllConnected());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void allAreConnectedTestOnePerson() {
        SocialNetwork network = new SocialNetwork();
        network.addPerson(people[0]);
        //network.addPerson(people[1]);
        //network.connectPeople(people[0],people[1]);
        assertTrue(network.areWeAllConnected());

    }

    @Test
    void allAreConnectedStrongTest() {
        SocialNetwork network = new SocialNetwork();
        network.addPerson("w");
        network.addPerson("u");
        network.addPerson("t");
        network.addPerson("s");
        network.addPerson("v");

        try {
            network.connectPeople("t", "s");
            network.connectPeople("s", "u");
            network.connectPeople("v", "s");
            network.connectPeople("v", "u");
            assertFalse(network.areWeAllConnected());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testAllAreConnectedStrong() {
        SocialNetwork network = new SocialNetwork();
        String name = "z";
        network.addPerson("a");
        network.addPerson("b");
        network.addPerson("c");
        network.addPerson("d");
        network.addPerson("e");
        network.addPerson("f");
        network.addPerson("g");
        network.addPerson("h");
        network.addPerson("i");
        network.addPerson("j");
        network.addPerson("k");
        network.addPerson("l");
        network.addPerson("m");
        network.addPerson("n");
        network.addPerson("s");
        network.addPerson("t");

        try {
            network.connectPeople("t", "k");
            network.connectPeople("t", "f");
            network.connectPeople("t", "l"); //
            network.connectPeople("k", "e");
            network.connectPeople("k", "f");
            network.connectPeople("j", "k");//
            network.connectPeople("j", "i");
            network.connectPeople("j", "d");
            network.connectPeople("j", "e");
            network.connectPeople("i", "d");//
            network.connectPeople("l", "f");
            network.connectPeople("l", "g");
            network.connectPeople("l", "m");//
            network.connectPeople("f", "b");
            network.connectPeople("f", "e");
            network.connectPeople("f", "g");//
            network.connectPeople("e", "b");
            network.connectPeople("e", "a");
            network.connectPeople("e", "d"); //
            network.connectPeople("d", "a");
            network.connectPeople("m", "g");
            network.connectPeople("m", "h");
            network.connectPeople("m", "n");//
            network.connectPeople("g", "b");
            network.connectPeople("g", "c");
            network.connectPeople("g", "h"); //
            network.connectPeople("b", "a");
            network.connectPeople("b", "s");
            network.connectPeople("b", "c");//
            network.connectPeople("a", "s");
            network.connectPeople("n", "h");
            network.connectPeople("h", "c");
            network.connectPeople("s", "c");

            // Check if connected
            assertTrue(network.areWeAllConnected());

        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void allAreConnectedStrongest(){
        SocialNetwork network = new SocialNetwork();
        network.addPerson("a");
        network.addPerson("b");
        network.addPerson("c");
        network.addPerson("d");
        network.addPerson("e");
        network.addPerson("f");
        network.addPerson("g");
        network.addPerson("h");
        network.addPerson("i");
        network.addPerson("j");
        network.addPerson("k");
        network.addPerson("l");
        network.addPerson("m");
        network.addPerson("n");
        network.addPerson("s");
        network.addPerson("t");
        network.addPerson("u"); // Problem here

        try {
            // Establish connections
            network.connectPeople("t", "k");
            network.connectPeople("t", "f");
            network.connectPeople("t", "l");
            network.connectPeople("k", "e");
            network.connectPeople("k", "f");
            network.connectPeople("j", "k");
            network.connectPeople("j", "i");
            network.connectPeople("j", "d");
            network.connectPeople("j", "e");
            network.connectPeople("i", "d");
            network.connectPeople("l", "f");
            network.connectPeople("l", "g");
            network.connectPeople("l", "m");
            network.connectPeople("f", "b");
            network.connectPeople("f", "e");
            network.connectPeople("f", "g");
            network.connectPeople("e", "b");
            network.connectPeople("e", "a");
            network.connectPeople("e", "d");
            network.connectPeople("d", "a");
            network.connectPeople("m", "g");
            network.connectPeople("m", "h");
            network.connectPeople("m", "n");
            network.connectPeople("g", "b");
            network.connectPeople("g", "c");
            network.connectPeople("g", "h");
            network.connectPeople("b", "a");
            network.connectPeople("b", "s");
            network.connectPeople("b", "c");
            network.connectPeople("a", "s");
            network.connectPeople("n", "h");
            network.connectPeople("h", "c");
            network.connectPeople("s", "c");

            // Check if connected
            assertFalse(network.areWeAllConnected());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void allAreConnectedTest1(){
        SocialNetwork network = new SocialNetwork();
        network.addPerson("x");
        network.addPerson("y");
        network.addPerson("z");

        // Only add connections between x and y
        try {
            network.connectPeople("x", "y");

            // Check if connected
            assertFalse(network.areWeAllConnected());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void allAreConnectedTestBasic1(){
        SocialNetwork network = new SocialNetwork();
        network.addPerson("1");
        network.addPerson("0");
        network.addPerson("2");
        network.addPerson("3");
        network.addPerson("4");

        try {
            network.connectPeople("0","1");
            network.connectPeople("0","4");
            network.connectPeople("4","1");
            network.connectPeople("3","1");
            network.connectPeople("3","2");
            network.connectPeople("1","2");

            assertTrue(network.areWeAllConnected());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void allAreConnectedTestBasic2(){
        SocialNetwork network = new SocialNetwork();
        network.addPerson("1");
        network.addPerson("0");
        network.addPerson("2");
        network.addPerson("3");
        network.addPerson("4");

        try {
            network.connectPeople("0","1");
            network.connectPeople("0","4");
            network.connectPeople("4","1");
            network.connectPeople("3","1");

            assertFalse(network.areWeAllConnected());
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetConnections(){
        // To check
        EnhancedSocialNetwork network = new EnhancedSocialNetwork();

        network.addPerson("U"); // 0
        network.addPerson("I"); // 1
        network.addPerson("T"); // 2
        network.addPerson("M"); // 3
        network.addPerson("G");

        try {
            network.connectPeople("U","T", 1);
            network.connectPeople("U","I", 2);
            network.connectPeople("U","M", 1);
            network.connectPeople("U","G", 5);

            System.out.println(network.getStrongestConnection("U"));
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
