package com.erchpito.crunchtime;

import java.util.*;

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
                System.out.println(myName + ":\t" + amount
                        + (toCalorie ? " " + myType : " cal") + " -> "
                        + result
                        + (toCalorie ? " cal" : " " + myType));
            }
            return result;
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

    // to test the functions
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Polling for an exercise
        System.out.println("Enter an exercise: ");
        int myExercise = in.nextInt();

        // Polling for calorie or amount measure
        System.out.println("Choose a mode -- 0: calories to reps / minutes, "
                + "1: reps / minutes to calories: ");
        boolean wantCalorie = (in.nextInt() == 1);

        if (wantCalorie) {
            System.out.println("Enter reps / minutes: ");
            int amount = in.nextInt();
            System.out.println("");
            for (int i = 0; i < 12; i++) {
                if (i != myExercise) {
                    System.out.println("");
                }
            }
        }
    }

}
