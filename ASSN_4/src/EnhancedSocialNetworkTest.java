import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class EnhancedSocialNetworkTest {



    @Test
    void getStrongestPathTest(){
        EnhancedSocialNetwork network = new EnhancedSocialNetwork();

        network.addPerson("U"); // 0
        network.addPerson("I"); // 1
        network.addPerson("T"); // 2
        network.addPerson("M"); // 3
        network.addPerson("G");
        network.addPerson("Z");

        try {
            network.connectPeople("U","T", 1);
            network.connectPeople("U","I", 2);
            network.connectPeople("U","M", 1);
            network.connectPeople("U","G", 5);
            network.connectPeople("T","Z", 5);
            network.connectPeople("I","Z", 5);

            System.out.println(network.getStrongestPath("U", "Z"));
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLinearNetwork() {
        EnhancedSocialNetwork network = new EnhancedSocialNetwork();
        network.addPerson("A");
        network.addPerson("B");
        network.addPerson("C");
        network.addPerson("D");
        network.addPerson("E");
        network.addPerson("F");
        try {
            network.connectPeople("A", "B", 2);
            network.connectPeople("B", "C", 3);
            network.connectPeople("C", "D", 4);
            network.connectPeople("D", "E", 5);
            network.connectPeople("E", "F", 6);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals(Arrays.asList("A", "B", "C", "D", "E", "F"), network.getStrongestPath("A", "F"));
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testStarNetwork() {
        EnhancedSocialNetwork network = new EnhancedSocialNetwork();
        network.addPerson("Center");
        network.addPerson("Friend1");
        network.addPerson("Friend2");
        network.addPerson("Friend3");
        network.addPerson("Friend4");

        try {
            network.connectPeople("Center", "Friend2", 4);
            network.connectPeople("Center", "Friend1", 3);
            network.connectPeople("Center", "Friend3", 5);
            network.connectPeople("Center", "Friend4", 6);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals(Arrays.asList("Center", "Friend1"), network.getStrongestPath("Center", "Friend1"));
            assertEquals(Arrays.asList("Center", "Friend2"), network.getStrongestPath("Center", "Friend2"));
            assertEquals(Arrays.asList("Center", "Friend3"), network.getStrongestPath("Center", "Friend3"));
            assertEquals(Arrays.asList("Center", "Friend4"), network.getStrongestPath("Center", "Friend4"));
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void testFullyConnectedNetwork() {
        EnhancedSocialNetwork network = new EnhancedSocialNetwork();
        network.addPerson("Alice");
        network.addPerson("Bob");
        network.addPerson("Charlie");
        network.addPerson("David");
        try {
            network.connectPeople("Alice", "Bob", 2);
            network.connectPeople("Alice", "Charlie", 3);
            network.connectPeople("Alice", "David", 4);
            network.connectPeople("Bob", "Charlie", 2);
            network.connectPeople("Bob", "David", 3);
            network.connectPeople("Charlie", "David", 0);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals(Arrays.asList("Alice", "Bob", "Charlie"), network.getStrongestPath("Alice", "Charlie"));
            assertEquals(Arrays.asList("Bob", "Charlie", "David"), network.getStrongestPath("Bob", "David"));
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
