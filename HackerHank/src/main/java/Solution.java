import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Solution {

    public static final String JSONTEXT = "Hello, John! It looks like you booked a Basic Economy flight.\n" +
            "Are you aware that this flight doesn't have any storage for carry-on luggage?\n" +
            " -No\n" +
            "  Some other limitations you may want to consider is that you will not be able to pick a seat.\n" +
            "  1:We're happy to let you know that we can upgrade you today for just $25!\n" +
            "  Would you like to do that now?\n" +
            "   -Not right now\n" +
            "    =Okay, please let our customer service team know if you change your mind.\n" +
            "   -Yes, please upgrade\n" +
            "    =Okay, you've been upgraded!\n" +
            " -Yes   \n" +
            "  >1\n" +
            "---\n" +
            "No\n" +
            "Not right now";
    public static final String JSONTEXT1 = "Hello world.\n" +
            "Good luck on your test!\n" +
            " -Thanks!\n" +
            "  =Goodbye!\n" +
            "---\n" +
            "Thanks!";
    public static final String JSONTEXT2 = "Hello, John! It looks like you booked a Basic Economy flight.\n" +
            "Are you aware that this flight doesn't have any storage for carry-on luggage?\n" +
            " -No\n" +
            "  Some other limitations you may want to consider is that you will not be able to pick a seat.\n" +
            "  We're happy to let you know that we can upgrade you today for just $25!\n" +
            "  Would you like to do that now?\n" +
            "   -Not right now\n" +
            "    =Okay, please let our customer service team know if you change your mind.\n" +
            "   -Yes, please upgrade\n" +
            "    =Okay, you've been upgraded!\n" +
            " -Yes\n" +
            "  We're happy to let you know that we can upgrade you today for just $25!\n" +
            "  Would you like to do that now?\n" +
            "   -Not right now\n" +
            "    =Okay, please let our customer service team know if you change your mind.\n" +
            "   -Yes, please upgrade\n" +
            "    =Okay, you've been upgraded!\n" +
            "---\n" +
            "No\n" +
            "Not right now";

    public static final String JSONTEXT3 = "Hello, John! It looks like you booked a Basic Economy flight.\n" +
            "Are you aware that this flight doesn't have any storage for carry-on luggage?\n" +
            " -Yes\n" +
            "  >1\n" +
            " -No\n" +
            "  Some other limitations you may want to consider is that you will not be able to pick a seat.\n" +
            "  1:We're happy to let you know that we can upgrade you today for just $25!\n" +
            "  Would you like to do that now?\n" +
            "   -Not right now\n" +
            "    =Okay, please let our customer service team know if you change your mind.\n" +
            "   -Yes, please upgrade\n" +
            "    =Okay, you've been upgraded!\n" +
            "---\n" +
            "No\n" +
            "Not right now";

    public static final String JSONTEXT4 = "Hello, John! It looks like you booked a Basic Economy flight.\n" +
            "Are you aware that this flight doesn't have any storage for carry-on luggage?\n" +
            " -No\n" +
            "  Some other limitations you may want to consider is that you will not be able to pick a seat.\n" +
            "  1:We're happy to let you know that we can upgrade you today for just $25!\n" +
            "  Would you like to do that now?\n" +
            "   -Not right now\n" +
            "    =Okay, please let our customer service team know if you change your mind.\n" +
            "   -Yes, please upgrade\n" +
            "    =Okay, you've been upgraded!\n" +
            " -Yes\n" +
            "  >1\n" +
            "---\n" +
            "Yes\n" +
            "Yes, please upgrade";


    public static final String HOPPER_MESSAGE_PREFIX = "Hopper: ";
    public static final String USER_MESSAGE_PREFIX = "User: ";
    public static final String CONCLUSION_MESSAGE_PREFIX = "Conclusion: ";


    public void printConversation(List<IndentationAndInputObject> flatTree, List<String> userAnswers) {
        // Write your solution below
        // Some example code is provided to show you how to access our data structures, feel free to modify/delete

        IndentationAndInputObject iaioGoto = flatTree.stream().filter(i -> i.getInputObject() instanceof Goto).findFirst().get();
        IndentationAndInputObject iaioOutput = flatTree.stream().filter(i -> i.getInputObject() instanceof Output
                && i.getInputObject().getLabel().getAsInt() == iaioGoto.getInputObject().getLabel().getAsInt()).findFirst().get();
        List<IndentationAndInputObject> newFlatTree = new LinkedList<>();
        int index = 0;
        if (iaioGoto != null) {
                index = flatTree.indexOf(iaioOutput) - 1;
            int currentIndex = 0;
            for (IndentationAndInputObject ioTest: newFlatTree) {
                newFlatTree.add(ioTest);
                if (currentIndex == index) {
                    break;
                }
                currentIndex++;
            }
        }

        LinkedList<String> prints = new LinkedList<>();
        for(IndentationAndInputObject iaio : flatTree) {
            if(iaio.getInputObject() instanceof Output) {
                prints.add(HOPPER_MESSAGE_PREFIX + iaio.getInputObject().getText());
            } else if(iaio.getInputObject() instanceof Answer) {
                prints.add(USER_MESSAGE_PREFIX + iaio.getInputObject().getText());
            } else if(iaio.getInputObject() instanceof Conclusion) {
                prints.add(CONCLUSION_MESSAGE_PREFIX + iaio.getInputObject().getText());
            }
        }
        prints.stream().forEach(System.out::println);
    }

    public IndentationAndInputObject parseLine(String line) {
        // Modified to catch the line without indentation
        if(!line.startsWith(" ")) {
            return new IndentationAndInputObject(0, parseInputObject(line));
        }
        int indentation = line.indexOf(line.trim());
        return new IndentationAndInputObject(indentation, parseInputObject(line.substring(indentation)));
    }

    public InputObject parseInputObject(String line) {
        if (line.charAt(0) == '-') {
            return new Answer(line.substring(1));
        } else if (line.charAt(0) == '=') {
            return new Conclusion(line.substring(1));
        } else if (line.charAt(0) == '>') {
            return new Goto(Integer.parseInt(line.substring(1)));
        } else {
            return parseOutput(line);
        }
    }

    public Output parseOutput(String line) {
        boolean labelFound = false;
        int labelDelimiter = -1;
        for(int i = 0; i < line.length(); i++) {
            if(!Character.isDigit(line.charAt(i))) {
                if(line.charAt(i) == ':') {
                    labelFound = true;
                    labelDelimiter = i;
                }
                break;
            }
        }

        if(labelFound) {
            return new Output(line.substring(labelDelimiter+1), Integer.parseInt(line.substring(0, labelDelimiter)));
        } else {
            return new Output(line);
        }
    }

    public static void main(String args[] ) throws Exception {
        InputStream input = new ByteArrayInputStream(JSONTEXT4.getBytes(StandardCharsets.UTF_8));
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            Solution solution = new Solution();

            String line;
            boolean processingExamples = false;
            List<String> userAnswers = new LinkedList<>();
            List<IndentationAndInputObject> flatTree = new LinkedList<>();

            while((line = reader.readLine()) != null) {
                if(line.equals("---")) {
                    processingExamples = true;
                } else if(processingExamples) {
                    userAnswers.add(line);
                } else {
                    flatTree.add(solution.parseLine(line));
                }
            }

            solution.printConversation(flatTree, userAnswers);
        }
    }
}
