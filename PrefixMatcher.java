import java.util.ArrayList;
import java.util.List;

class PrefixMatcher {
    public static void main(String[] args) {
        // test for trie
        String[] lsOfWords = {"there", "their", "answer", "any", "bye", "an", "a"};
        Trie tree = new Trie();
        for (String word : lsOfWords) {
            tree.insert(word);
        }

        List<String> treeToArr = traverseTrie(tree.root, new ArrayList<String>(), "");
        System.out.println(treeToArr);

        List<String> matches = getListOfMatchedWords(tree, "an");
        System.out.println(matches);
    }

    public static List<String> getListOfMatchedWords(Trie tree, String prefix) {
        List<String> prefixMatches = new ArrayList<>();

        // find node that ends in prefix
        TrieNode currNode = tree.root;
        int currChar;
        for (int i=0; i<prefix.length(); i++) {
            currChar = prefix.charAt(i) - 'a';
            if (currNode.children[currChar] == null) return prefixMatches;
            currNode = currNode.children[currChar];
        }

        traverseTrie(currNode, prefixMatches, prefix);

        return prefixMatches;
    }

    public static List<String> traverseTrie(TrieNode node, List<String> leaves, String parent) {
        if (node.children == null) {
            leaves.add(parent);
            return leaves;
        }
        if (node.numOfChildren < node.frequency) {
            leaves.add(parent);
        }
        for (int i=0; i<node.children.length; i++) {
            if (node.children[i] != null) {
                char letter = (char) (i + 'a');
                traverseTrie(node.children[i], leaves, parent + letter);
            }
        }
        return leaves;
    }
}

class Trie {

    static TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    static void insert(String key) {
        TrieNode currNode = root;
        for (int i=0; i<key.length(); i++) {
            int currChar = key.charAt(i) - 'a';

            if (currNode.children == null)  {
                currNode.initChildren();
            }
            if (currNode.children[currChar] == null) {
                currNode.children[currChar] = new TrieNode();
            }

            currNode.numOfChildren+=1;
            currNode.children[currChar].frequency+=1;
            currNode = currNode.children[currChar];
        }
    }

    static boolean search(String key) {
        TrieNode currNode = root;
        for (int i=0; i<key.length(); i++) {
            int currChar = key.charAt(i) - 'a';
            if (currNode.children == null) return false;
            if (currNode.children[currChar] == null) return false;
            currNode = currNode.children[currChar];
        }
        return true;
    }
}

class TrieNode {
    static final int ALPHABET_SIZE = 26;

    TrieNode[] children;

    int frequency;
    int numOfChildren;

    void initChildren() {
        this.children = new TrieNode[ALPHABET_SIZE];
    }
}