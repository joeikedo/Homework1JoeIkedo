/* TestDriver - PURPOSE
    Author:    Kevin Webb
    Module:
    Project:

    Problem Statement: XXX

    Plan:
        1.  XXX
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TestDriver {

    public static void main(String[] args) {
        //AVLTree<String> dictionary = new AVLTree<>(); // Tree to hold the dictionary
        Trie trieDict = new Trie(); // Trie for the dictionary
        try {
            // Try to open text file
            BufferedReader inputStream = new BufferedReader(new FileReader(
                    "C:\\Users\\Joe\\IdeaProjects\\FinalProject\\src\\dictionary.txt"));


            // Read in every word line by line and add the word to the tree
            String newWord = inputStream.readLine();

            while (newWord != null) {
                //dictionary.add(newWord);
                trieDict.insert(newWord);
                newWord = inputStream.readLine();
            }

            inputStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't open file");
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("Can't read from file");
            System.exit(0);
        }
        System.out.println("trie size: " + trieDict.getSize());
        //new scanner instance
        Scanner keyboard = new Scanner(System.in);

        String sentence; // Initialize to hold

        System.out.print("Enter a sentence to check: ");
        sentence = keyboard.nextLine();
        StringTokenizer tokenizer; // Breaks sentence into words

        do {
            // Tokenizer here has to be changed to get rid of punctuation or we need to do
            // a .replace() on the sentence first to do it
            tokenizer = new StringTokenizer(sentence);
            while (tokenizer.hasMoreTokens()){
                String searchWord = tokenizer.nextToken();

                // Decided to have a dictionary with proper nouns, so removed the
                // .toLowerCase() on the word. But I guess we should do some kind of
                // ignoring case on first word? Or if the first word isn't capitalized,
                // suggest that it should be?
                //System.out.println(searchWord + " found: " +
                // dictionary.contains(searchWord));
                System.out.println(searchWord + " found in trie: " +
                        trieDict.search(searchWord));
            }

            // Ideas here: We could have separate trees for user dictionary and ignore
            // list. Check all 3 (priority?) for each word until it's found in 1. Offer to
            // add to user dictionary or ignore this session when word not found at all.
            // When program exits normally, save user dictionary AVL to file, but do not
            // save ignore list. We could also implement a menu choice to list all words
            // in the user dictionary and delete them (since I have the .remove() function
            // working. Last thought: Possible to implement serializable and import the
            // entire dictionary AVL as a single object from a .dat file instead of
            // remaking the tree each run?

            // So we need to figure out a way (new method in the AVL Tree, or new method
            // in a new dictionary tree child class?) to return the previous children.
            // Could pass a Stack into the contains method that stores the whole search,
            // then if the search is false, you can pop 3 words off the stack?

            // I just did a small test of some words and what the previous nodes were.
            // test had tessiture, testablility, testacies. aple (misspelled apple) was
            // aplectrum, aplenty, aplastic. dunplings (misspelled dumplings) had
            // dunpickle, duns, dunny. I'm wondering if I should actually look into
            // implementing a Trie like I talked about in class that searches down letter
            // by letter to build a valid word to come up with better suggestions for
            // misspelled words. Thoughts?

            System.out.print("Enter next sentence or \\q to quit: ");
            sentence = keyboard.next();
        } while (!sentence.equals("\\q"));

    } //End of main

} //end of TestDriver Program