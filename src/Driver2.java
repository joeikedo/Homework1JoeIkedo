
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver2.java: This is the driver program to use the searchWithSuggestions and searchSubtreeSuggestions methods.
 *
 * @author Joe Ikedo <joeikedo@gmail.com>
 * @version 1.0
 *
 */
public class Driver2 {
    public static void main(String[] args){

        AVLTree<String> dictTree = new AVLTree<>(); // The tree containing all the Dictionary words.

        try {
            // Try to open text file
            BufferedReader inputStream = new BufferedReader(new FileReader(
                    "C:\\Users\\Joe\\IdeaProjects\\FinalProject\\src\\dictionary.txt"));


            // Read in every word line by line and add the word to the tree
            String newWord = inputStream.readLine();

            while (newWord != null) {
                //dictionary.add(newWord);
                dictTree.add(newWord);
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

        // Keyboard setup, to allow for typing in own words.
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a word:");
        String message = keyboard.nextLine();

        System.out.println("Tree Height:" + dictTree.getHeight(dictTree.getRoot()));

        Stack<BinaryTree.Node> testStack = new Stack<BinaryTree.Node>();
        dictTree.searchWithSuggestions(message, testStack); //The searchWithSuggestions puts all the parent
        // node that were passed when searching for the word into the input Stack passed as parameter.

        System.out.println("Stack size:" + testStack.size());

        ArrayList<String> testArrayList = new ArrayList<String>(); // The arrayList to use for the
        // searchSubtreeSuggestions method, which will instead of just giving the parent nodes as suggestions will
        // give the subtree starting from three parent levels up as suggested words.


        for(int i = 0; i < 3; i++){
            System.out.println(testStack.pop()); // Removing the top 3 to get to the fourth level up in the method call below.
        }
        System.out.println("-------The above are the parent Nodes-------");
        dictTree.searchSubtreeSuggestions(testStack.pop(), testArrayList); // popping off the stack to use the
        // node four levels above the bottom level in the parameter of the method call.

        System.out.println("Array List size:" + testArrayList.size());
        System.out.println("The suggestions:");

        for(int i = 0; i < testArrayList.size(); i ++){ // Print out all the Strings from the subtree that were stored
                                                        // in the ArrayList
            System.out.println(testArrayList.get(i));
        }








    }
}
