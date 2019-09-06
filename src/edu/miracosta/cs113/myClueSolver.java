package edu.miracosta.cs113;

import model.AssistantJack;
import model.Theory;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class myClueSolver {

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

        ArrayList<Integer> wrongWeapons = new ArrayList();
        ArrayList<Integer> wrongLocations = new ArrayList();
        ArrayList<Integer> wrongPeople = new ArrayList();

        do {
            weapon = random.nextInt(6) + 1;
            //Need to check to see if the random weapon we rolled up for this iteration is on our wrongWeapons list!!
            //If so, we need to roll up a new weapon.//change testing


            location = random.nextInt(10) + 1;
            murder = random.nextInt(6) + 1;     //As in, 'the murderer'
            solution = jack.checkAnswer(weapon, location, murder);

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

