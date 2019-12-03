import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Scanner;

/**
 * CommonMisspellings.java: This sets up the TreeMap of the common misspellings of words. It reads in data
 * from the Misspellings.txt file
 *
 * @author Joe Ikedo <joeikedo@gmail.com>
 * @version 1.0
 */
public class CommonMisspellings {
    public static void main(String[] args){
        // Setting up the Tree map that will store all the key-value misspellings and proper spellings
        TreeMap<String, String> misspellingTreeMap = new TreeMap<String, String>();

        // Keyboard setup, to allow for typing in own words.
        Scanner keyboard = new Scanner(System.in);

        // Scanning in the proper spellings and misspellings from the text file.
        try {
            // Try to open text file
            BufferedReader inputStream = new BufferedReader(new FileReader(
                    "C:\\Users\\Joe\\IdeaProjects\\FinalProject\\src\\Misspellings.txt"));


            // Read in the next line in the text file.
            String nextLine = inputStream.readLine();

            while (nextLine != null) {
                //trieDict.insert(newWord);
                String[] wordsArray = nextLine.split("[–,] "); // Use either - or , to split the strings.

                // This for loop will make every misspelled word in the line be the key in a key-value pairing
                // (The correct spelling is the value, and is always the first word in the array)
                for(int i = 1; i < wordsArray.length; i++){
                    misspellingTreeMap.put(wordsArray[i], wordsArray[0]);
                }

                nextLine = inputStream.readLine();
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


        System.out.println(misspellingTreeMap);

        // Check to see if the word typed is among the common misspellings
        System.out.println("Enter a word:");
        String message = keyboard.nextLine();

        boolean potentialMispelling = misspellingTreeMap.containsKey(message);
        if(potentialMispelling == true){
            System.out.println("Did you mean: " + misspellingTreeMap.get(message) +"?");
        }
        else{
            System.out.println("The word you typed is not among the common misspellings list.");
        }




    }

}
