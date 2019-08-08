package bearmaps.utils.ps;

import bearmaps.utils.ps.TrieSet61BL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61BL {

    public Node root;
    private int n;

    public MyTrieSet() {
        this.root = new Node('\u0000', false);
    }

    private static class Node {
        public HashMap<Character, Node> map;
        private char c;
        private boolean isKey;

        Node(char ch, boolean isKey1) {
            this.c = ch;
            this.isKey = isKey1;
            this.map = new HashMap<>();
        }
    }


    /**
     * Clears all items out of Trie
     */

    public void clear() {
        this.root.map = new HashMap<>();
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     */
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        Node temp = root;
        for (int i = 0; i < key.length(); i++) {
            if (!temp.map.containsKey(key.charAt(i))) {
                return false;
            } else {
                temp = temp.map.get(key.charAt(i));
            }
        }
        return temp.isKey;
    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }


    /**
     * Returns a list of all words that start with PREFIX
     */
    public List<String> keysWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();

        Node curr = root;

        for (int i = 0; i < prefix.length(); i++) {
            curr = curr.map.get(prefix.charAt(i));
            if (curr == null) {
                return result;
            }
        }

        return prefixHelper(curr, result, prefix);
    }

    public List<String> prefixHelper(Node current,
                                     List<String> wordList, String prefixSoFar) {
        if (current.isKey) {
            wordList.add(prefixSoFar);
        }
        for (Character key : current.map.keySet()) {
            Node children = current.map.get(key);
            prefixHelper(children, wordList, prefixSoFar + key);
        }
        return wordList;


    }
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 18. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        return null;
    }


}
