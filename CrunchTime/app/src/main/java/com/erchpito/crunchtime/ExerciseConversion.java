package com.erchpito.crunchtime;

import java.util.Scanner;

//Todo: Provide improved visuals and aesthetic iconography.
//Todo: More accurate metrics: the numbers we gave on the table are a rough approximate for someone who weighs 150 pounds. Allow users to input their weight, and factor that into calculations. (You may have to do some Googling to get a conversion formula).
//Todo: For the experienced Android programmer: a Personal Fitness App, which, after the user has input how many calories they want to burn (see bullet #2), pushes notifications of encouragement, or calories remaining.
//Todo: Enable orientation (app should be viewable in portrait and landscape mode).

/**
 * Created by erchpito on 30/1/2016.
 */
public class ExerciseConversion {

    class Exercise {
        private double myAmount;
        private double myCalorie;
        private double myWeight;

        private String myName;
        private String myType;

        public Exercise(String name, String type, double amount) {
            myAmount = amount;
            myCalorie = 100;
            myName = name;
            myType = type;
            myWeight = 150;
        }

        public int convert(int amount, boolean toCalorie, boolean debug) {
            int result = (int) (amount * (toCalorie ? myCalorie / myAmount : myAmount / myCalorie));
            if (debug) {
                System.out.println(amount
                        + (toCalorie ? " " + myType + " of " + myName.toLowerCase() : " cal") + " -> "
                        + result
                        + (toCalorie ? " cal" : " " + myType + " of " + myName.toLowerCase()));
            }
            return result;
        }

        public String getMyName() {
            return myName;
        }

        public String getMyType() {
            return myType;
        }
    }

    private Exercise[] exercises;

    final int NUM_EXERCISE = 12;
    final int EXERCISE_PUSHUP = 0;
    final int EXERCISE_SITUP = 1;
    final int EXERCISE_SQUATS = 2;
    final int EXERCISE_LEG_LIFT = 3;
    final int EXERCISE_PLANK = 4;
    final int EXERCISE_JUMPING_JACKS = 5;
    final int EXERCISE_PULLUP = 6;
    final int EXERCISE_CYCLING = 7;
    final int EXERCISE_WALKING = 8;
    final int EXERCISE_JOGGING = 9;
    final int EXERCISE_SWIMMING = 10;
    final int EXERCISE_STAIR_CLIMBING = 11;

    public ExerciseConversion() {
        exercises = new Exercise[NUM_EXERCISE];
        exercises[EXERCISE_PUSHUP] = new Exercise("Pushup", "reps", 350);
        exercises[EXERCISE_SITUP] = new Exercise("Situp", "reps", 200);
        exercises[EXERCISE_SQUATS] = new Exercise("Squats", "reps", 255);
        exercises[EXERCISE_LEG_LIFT] = new Exercise("Leg-lift", "mins", 25);
        exercises[EXERCISE_PLANK] = new Exercise("Plank", "mins", 25);
        exercises[EXERCISE_JUMPING_JACKS] = new Exercise("Jumping Jacks", "mins", 10);
        exercises[EXERCISE_PULLUP] = new Exercise("Pullup", "reps", 100);
        exercises[EXERCISE_CYCLING] = new Exercise("Cycling", "mins", 12);
        exercises[EXERCISE_WALKING] = new Exercise("Walking", "mins", 20);
        exercises[EXERCISE_JOGGING] = new Exercise("Jogging", "mins", 12);
        exercises[EXERCISE_SWIMMING] = new Exercise("Swimming", "mins", 13);
        exercises[EXERCISE_STAIR_CLIMBING] = new Exercise("Stair-climbing", "mins", 15);
    }

    public int convert(int exercise, int amount, boolean toCalorie, boolean debug) {
        return exercises[exercise].convert(amount, toCalorie, debug);
    }

    public int translate(int exercise1, int exercise2, int amount, boolean debug) {
        int result = convert(exercise2, convert(exercise1, amount, true, false), false, false);
        if (debug) {
            System.out.println(amount + " " + exercises[exercise1].getMyType() + " of "
                    + exercises[exercise1].getMyName().toLowerCase() + " -> " + result + " "
                    + exercises[exercise2].getMyType() + " of "
                    + exercises[exercise2].getMyName().toLowerCase());
        }
        return result;
    }

    // to test the functions
    public static void main(String[] args) {
        ExerciseConversion converter = new ExerciseConversion();
        Scanner in = new Scanner(System.in);

        boolean run = true;
        while (run) {
            // Polling for an exercise
            System.out.print("Enter an exercise: ");
            //Todo: consider if users will want to input exercise first for finding calorie conversion
            int myExercise = in.nextInt();

            if (myExercise == -1) {
                run = false;
            } else {
                // Polling for calorie or amount measure
                System.out.print("Choose a mode -- 0: calories to reps / minutes, "
                        + "1: reps / minutes to calories: ");
                boolean toCalorie = (in.nextInt() == 1);
                System.out.print((toCalorie ? "Enter reps / minutes: " : "Enter calories: "));
                int amount = in.nextInt();
                converter.convert(myExercise, amount, toCalorie, true);
                System.out.println("--------------------");
                for (int i = 0; i < converter.NUM_EXERCISE; i++) {
                    if (i != myExercise) {
                        if (toCalorie) {
                            converter.translate(myExercise, i, amount, true);
                        } else {
                            converter.convert(i, amount, toCalorie, true);
                        }
                    }
                }
            }
        }
    }
}
