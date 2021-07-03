import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignmentStringTest {
    AssignmentString assignmentString = new AssignmentString();

    @Test
    void testStringToken() {
    }

    @Test
    void reverseString() {
        String testString = "batista";
        String result = assignmentString.reverseString(testString);
        assertEquals("atsitab", result);

        // Failed Test
        assertNotEquals("batista", result);
    }

    @Test
    void reverseStringCollection() {
        String testString = "batista";
        String result = assignmentString.reverseStringCollection(testString);
        assertEquals("atsitab", result);

        // Failed Test
        assertNotEquals("batista", result);
    }

    @Test
    void findLadders() {

        Set<String> dict = new HashSet<>();
        dict.add("hot");
        dict.add("dot");
        dict.add("dog");
        dict.add("lot");
        dict.add("log");
        List<List<String>> shortest = assignmentString.findLadders("hit", "cog", dict);
    }

    @Test
    void combinations(){
        Integer[] numbers = {1,2,3}; //3,9,8,4,5,7,10
        Integer target = 4;
        Integer nCombinations = assignmentString.sum_up(new ArrayList<Integer>(Arrays.asList(numbers)),target);
        assertEquals(nCombinations, 7);
    }

    @Test
    void findFrequency(){
        // char[][[] board =
        // String[] words = new String(){"oath","pea","eat","rain"};
        String sentence = "I love important things that I can deal with it.";
        Map<String, Integer> result = assignmentString.findFrequency(sentence, 2);
        assertNotEquals(result, null);
        if (result != null) {
            assertEquals(result.get("I"), 2);
        }
    }

    @Test
    void sumArray2D() {
        List line1 = new ArrayList();
        line1.add(1);
        line1.add(1);
        line1.add(1);
        line1.add(0);
        line1.add(0);
        line1.add(0);

        List line2 = new ArrayList();
        line2.add(0);
        line2.add(1);
        line2.add(0);
        line2.add(0);
        line2.add(0);
        line2.add(0);

        List line3 = new ArrayList();
        line3.add(1);
        line3.add(1);
        line3.add(1);
        line3.add(0);
        line3.add(0);
        line3.add(0);

        List line4 = new ArrayList();
        line4.add(0);
        line4.add(0);
        line4.add(2);
        line4.add(4);
        line4.add(4);
        line4.add(0);

        List line5 = new ArrayList();
        line5.add(0);
        line5.add(0);
        line5.add(0);
        line5.add(2);
        line5.add(0);
        line5.add(0);

        List line6 = new ArrayList();
        line6.add(0);
        line6.add(0);
        line6.add(1);
        line6.add(2);
        line6.add(4);
        line6.add(0);


        List<List<Integer>> array = new ArrayList<>();
        array.add(line1);
        array.add(line2);
        array.add(line3);
        array.add(line4);
        array.add(line5);
        array.add(line6);

        Integer sum = assignmentString.sumArray(array);

        assertEquals(19, sum);
    }

    @Test
    public void candidatesCourseraTest() {
        Map<List<String>, Integer> votesTest = Map.ofEntries(
                Map.entry(Arrays.asList("A", "B", "C"), 4),
                Map.entry(Arrays.asList("A", "C", "B"), 4),
                Map.entry(Arrays.asList("B", "A", "C"), 2),
                Map.entry(Arrays.asList("C", "A", "B"), 3)
        );
        assertEquals("A", assignmentString.winnerCandidate(votesTest));

        Map<List<String>, Integer> votesTest1 = Map.ofEntries(
                Map.entry(Arrays.asList("A", "B", "C"), 4),
                Map.entry(Arrays.asList("A", "C", "B"), 4),
                Map.entry(Arrays.asList("B", "A", "C"), 8),
                Map.entry(Arrays.asList("C", "A", "B"), 3)
        );
        List<String> mostVotted = assignmentString.getMostVoted(votesTest);
        assertEquals(Arrays.asList("A, B"), mostVotted);
    }
}