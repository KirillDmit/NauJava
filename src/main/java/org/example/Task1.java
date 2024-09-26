package org.example;

import java.util.OptionalDouble;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Task1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] arr = new int[n];
        Random rnd = new Random();
        for(int i = 0; i < arr.length; i++){
            arr[i] = rnd.nextInt(500);
            System.out.println(arr[i]);
        }
        OptionalDouble avg = IntStream.of(arr).average();
        System.out.println(avg.getAsDouble());
    }
}
