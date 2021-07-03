import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssignmentString {

    public Boolean testStringToken(String token) {
        if (token !=null && !token.startsWith("HY") && token.length() != 20) {
            return false;
        }
        return true;
    }

    public String reverseString(String words) {
        StringBuilder reversedWords = new StringBuilder();
        char[] charWords = words.toCharArray();
        for (int i = charWords.length - 1; i>=0 ;i--){
            reversedWords.append(charWords[i]);
        }
        return reversedWords.toString();
    }

    public String reverseStringCollection(String words) {
        List<String> listWords = new LinkedList<>();
        char[] charWords = words.toCharArray();
        for(int i= 0; i < charWords.length; i++) {
            listWords.add(Character.toString(charWords[i]));
        }
        Collections.reverse(listWords);
        return listWords.stream().reduce("", (partialString, element) -> partialString + element);
    }

    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> res = new ArrayList<List<String>>();
        HashMap<String, ArrayList<String>> nodeNeighbors = new HashMap<String, ArrayList<String>>();// Neighbors for every node
        HashMap<String, Integer> distance = new HashMap<String, Integer>();// Distance of every node from the start node
        ArrayList<String> solution = new ArrayList<String>();

        dict.add(end);
        bfs(start, end, dict, nodeNeighbors, distance);
        dfs(start, end, dict, nodeNeighbors, distance, solution, res);
        return res;
    }

    private void bfs(String start, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance) {
        for (String str : dict)
            nodeNeighbors.put(str, new ArrayList<String>());

        Queue<String> queue = new LinkedList<String>();
        queue.offer(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            int count = queue.size();
            boolean foundEnd = false;
            for (int i = 0; i < count; i++) {
                String cur = queue.poll();
                int curDistance = distance.get(cur);
                ArrayList<String> neighbors = getNeighbors(cur, dict);

                for (String neighbor : neighbors) {
                    nodeNeighbors.get(cur).add(neighbor);
                    if (!distance.containsKey(neighbor)) {// Check if visited
                        distance.put(neighbor, curDistance + 1);
                        if (end.equals(neighbor))// Found the shortest path
                            foundEnd = true;
                        else
                            queue.offer(neighbor);
                    }
                }
            }

            if (foundEnd)
                break;
        }
    }

    // Find all next level nodes.
    private ArrayList<String> getNeighbors(String node, Set<String> dict) {
        ArrayList<String> res = new ArrayList<String>();
        char chs[] = node.toCharArray();

        for (char ch ='a'; ch <= 'z'; ch++) {
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] == ch) continue;
                char old_ch = chs[i];
                chs[i] = ch;
                if (dict.contains(String.valueOf(chs))) {
                    res.add(String.valueOf(chs));
                }
                chs[i] = old_ch;
            }

        }
        return res;
    }

    // DFS: output all paths with the shortest distance.
    private void dfs(String cur, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance, ArrayList<String> solution, List<List<String>> res) {
        solution.add(cur);
        if (end.equals(cur)) {
            res.add(new ArrayList<String>(solution));
        }
        else {
            for (String next : nodeNeighbors.get(cur)) {
                if (distance.get(next) == distance.get(cur) + 1) {
                    dfs(next, end, dict, nodeNeighbors, distance, solution, res);
                }
            }
        }
        solution.remove(solution.size() - 1);
    }

    public static void combinations(ArrayList<Integer> numbers, Integer target, ArrayList<Integer> partial) {
        int s = 0;
        for (int x: partial) s += x;
        if (s == target || s >= target) {
            return;
        } else {
            for (int i = 0; i < numbers.size(); i++) {
                ArrayList<Integer> remaining = new ArrayList<Integer>();
                int n = numbers.get(i);
                for (int j = i + 1; j < numbers.size(); j++) remaining.add(numbers.get(j));
                ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
                partial_rec.add(n);
                combinations(remaining, target, partial_rec);
            }
        }
    }
    static Integer sum_up(ArrayList<Integer> numbers, int target) {
        Integer nCombinations = 0;
        ArrayList<Integer> partial = new ArrayList<>();
        combinations(numbers, target, partial);
        return nCombinations;
    }


    public List<String> findWords(char[][] board, String[] words) {
        ArrayList<String> result = new ArrayList<String>();

        int m = board.length;
        int n = board[0].length;

        for (String word : words) {
            boolean flag = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char[][] newBoard = new char[m][n];
                    for (int x = 0; x < m; x++)
                        for (int y = 0; y < n; y++)
                            newBoard[x][y] = board[x][y];

                    if (dfs(newBoard, word, i, j, 0)) {
                        flag = true;
                    }
                }
            }
            if (flag) {
                result.add(word);
            }
        }

        return result;
    }

    public boolean dfs(char[][] board, String word, int i, int j, int k) {
        int m = board.length;
        int n = board[0].length;

        if (i < 0 || j < 0 || i >= m || j >= n || k > word.length() - 1) {
            return false;
        }

        if (board[i][j] == word.charAt(k)) {
            char temp = board[i][j];
            board[i][j] = '#';

            if (k == word.length() - 1) {
                return true;
            } else if (dfs(board, word, i - 1, j, k + 1)
                    || dfs(board, word, i + 1, j, k + 1)
                    || dfs(board, word, i, j - 1, k + 1)
                    || dfs(board, word, i, j + 1, k + 1)) {
                board[i][j] = temp;
                return true;
            }

        } else {
            return false;
        }

        return false;
    }

    public static Map<String, Integer> findFrequency(String sentence, Integer frequency) {
        String[] splitedSentence = sentence.split(" ");
        Map<String, Integer> stringMap = new LinkedHashMap<>();
        for(String word : splitedSentence) {
            if (!stringMap.containsKey(word)) {
                stringMap.put(word, 1);
            } else {
                Integer n = stringMap.get(word) + 1;
                stringMap.put(word, n);
            }
        }

        for (Map.Entry<String, Integer> e : stringMap.entrySet()){
            if(e.getValue() == frequency){
                System.out.println("value: "+ e.getKey());
                break;
            }
        }
        Optional<String> result =  stringMap.entrySet().stream().filter(p -> p.getValue().equals(frequency)).map(Map.Entry::getKey).findFirst();
        Map<String, Integer> resultMap = new HashMap<>();
        if (result.isPresent()) {
            String key = result.get();
            resultMap.put(key, stringMap.get(key));
        }
        return resultMap;
    }

    public Integer sumArray(List<List<Integer>> arr) {
        List sum = new ArrayList();
        if (arr == null && (arr.size() == 0 || arr.get(0).size() == 0)) {
            return 0;
        }
        int height = arr.size();
        for(int i=0; i<height; i++){
            List<Integer> currLine = arr.get(i);
            sum.add(currLine.stream().mapToInt(n->n).sum());
        }
        //Integer n = (Integer) sum.stream().max(n -> n)
        //return n;
        return 0;
    }

    public String winnerCandidate(Map<List<String>, Integer> votes) {
        Map<String, Integer> votesMap = new LinkedHashMap<>();
        for(Map.Entry<List<String>, Integer> vote : votes.entrySet()) {
            List<String> candidates = vote.getKey();
            Integer currVote = vote.getValue();
            if (votesMap.containsKey(candidates.get(0))) {
                currVote = currVote + votesMap.get(candidates.get(0));
            }
            votesMap.put(candidates.get(0), currVote);
        }
        String winner = votesMap.entrySet().stream().max((v1, v2) -> v1.getValue() > v2.getValue() ? 1 : -1).get().getKey();
        return winner;
    }

    public List<String> getMostVoted(Map<List<String>, Integer> votes) {
        Map<String, Integer> votesMapFirst = new LinkedHashMap<>();
        Map<String, Integer> votesMapSecond = new LinkedHashMap<>();
        for (Map.Entry<List<String>, Integer> vote : votes.entrySet()) {
            List<String> candidates = vote.getKey();
            Integer currVote = vote.getValue();
            if (votesMapFirst.containsKey(candidates.get(0))) {
                currVote = currVote + votesMapFirst.get(candidates.get(0));
            }
            votesMapFirst.put(candidates.get(0), currVote);

            if (votesMapSecond.containsKey(candidates.get(1))) {
                currVote = currVote + votesMapSecond.get(candidates.get(1));
            }
            votesMapSecond.put(candidates.get(1), currVote);
        }
        List<String> votesMost = new LinkedList<>();
        votesMost.add(votesMapFirst.entrySet().stream().max((v1, v2) -> v1.getValue() > v2.getValue() ? 1 : -1).get().getKey());
        votesMost.add(votesMapSecond.entrySet().stream().max((v1, v2) -> v1.getValue() > v2.getValue() ? 1 : -1).get().getKey());
        return votesMost;
    }




}
