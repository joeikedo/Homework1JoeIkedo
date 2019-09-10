package edu.miracosta.cs113;

/**
 * RandomClue.java : Your job is to ask your AssistantJack and get the correct
 * answer in <= 20 tries.  RandomClue is ONE solution to the problem,
 * where a set of random numbers is generated every attempt until all three
 * random numbers match the solution from the AssistantJack object.
 *
 * This is a sample solution, a driver using random number implementation.
 * You can use this file as a guide to create your own SEPARATE driver for
 * your implementation that can solve it in <= 20 times consistently.
 *
 * @author Nery Chapeton-Lamas (material from Kevin Lewis)
 * @version 1.0
 *
 */

import java.util.Random;
import java.util.Scanner;
import model.Theory;
import model.AssistantJack;
import java.util.ArrayList;

public class myClueSolver{

    /*
     * ALGORITHM:
     *
     * PROMPT "Which theory to test? (1, 2, 3[random]): "
     * READ answerSet
     * INSTANTIATE jack = new AssistantJack(answerSet)
     * DO
     *      weapon = random int between 1 and 6
     *      location = random int between 1 and 10
     *      murder = random int between 1 and 6
     *      solution = jack.checkAnswer(weapon, location, murder)
     * WHILE solution != 0
     *
     * OUTPUT "Total checks = " + jack.getTimesAsked()
     * IF jack.getTimesAsked() is greater than 20 THEN
     *      OUTPUT "FAILED"
     * ELSE
     *      OUTPUT "PASSED"
     * END IF
     */

    /**
     * Driver method for random guessing approach
     *
     * @param args not used for driver
     */
    public static void main(String[] args) {
        // DECLARATION + INITIALIZATION
        int answerSet, solution, murder, weapon, location;
        Theory answer;
        AssistantJack jack;
        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();

        // INPUT
        System.out.println("Which theory would like you like to test? (1, 2, 3[random]): ");
        answerSet = keyboard.nextInt();
        keyboard.close();

        // PROCESSING
        jack = new AssistantJack(answerSet);

        // Arrays
        ArrayList<Integer> wrongWeapons = new ArrayList<Integer>();
        ArrayList<Integer> wrongLocations = new ArrayList<Integer>();
        ArrayList<Integer> wrongPeople = new ArrayList<Integer>();

        do {
            // Here we initialize the weapon number to guess based off our wrongWeapons array
            if(wrongWeapons.size() == 0) {
                weapon = random.nextInt(6) + 1;
            }
            else if(wrongWeapons.size() > 0 && wrongWeapons.size() < 5){
                weapon = 1;

                for(int i = 0; i<wrongWeapons.size(); i++) {

                    if(wrongWeapons.contains(weapon)) {
                        weapon = weapon + 1;
                    }
                    else {
                        continue;
                    }

                }
            }
            else {
                int sum = 0;
                for(int i = 0; i < wrongWeapons.size(); i ++){
                    sum = wrongWeapons.get(i) + sum;
                }
                weapon = 21 - sum; //We subtract by 21 because 1+2+3+4+5+6 is 21, thus the difference must be
                //the remaining weapon
            }

            // Here we initialize the location number to guess based off our wrongLocations array
            if(wrongLocations.size() == 0){
                location = random.nextInt(10) + 1;
            }
            else if(wrongLocations.size() > 0 && wrongLocations.size() < 9){
                location= 1;

                for(int i = 0; i<wrongLocations.size(); i++) {

                    if(wrongLocations.contains(location)) {
                        location = location + 1;
                    }
                    else {
                        continue;
                    }

                }
            }
            else{
                int sum = 0;
                for(int i = 0; i < wrongLocations.size(); i ++){
                    sum = wrongLocations.get(i) + sum;
                }
                location= 55 - sum; //We subtract by 55 because 1+2+3+4+5+6+7+8+9+10 is 55, thus
                // the difference must be the remaining location.
            }

            // Here we initialize the person number to guess based off our wrongPeople array
            if(wrongPeople.size() == 0){
                murder = random.nextInt(6) + 1;
            }
            else if(wrongPeople.size() > 0 && wrongPeople.size() < 5){
                murder= 1;
                for(int i = 0; i<wrongPeople.size(); i++) {

                    if(wrongPeople.contains(murder)) {
                        murder = murder + 1;
                    }
                    else {
                        continue;
                    }

                }
            }
            else{
                int sum = 0;
                for(int i = 0; i < wrongPeople.size(); i ++){
                    sum = wrongPeople.get(i) + sum;
                }
                murder = 21 - sum; //We subtract by 21 because 1+2+3+4+5+6 is 21, thus the difference must be
                //the remeaining person.
            }


            solution = jack.checkAnswer(weapon, location, murder);

            //For adding incorrect numbers to the arrays
            if(solution == 1){
                wrongWeapons.add(weapon);
            }
            else if (solution == 2){
                wrongLocations.add(location);
            }
            else if(solution == 3){
                wrongPeople.add(murder);
            }

        } while (solution != 0);

        answer = new Theory(weapon, location, murder);

        // OUTPUT
        System.out.println("Total Checks = " + jack.getTimesAsked() + ", Solution " + answer);

        if (jack.getTimesAsked() > 20) {
            System.out.println("FAILED!! You're a horrible Detective...");
        } else {
            System.out.println("WOW! You might as well be called Batman!");
        }

    }

}