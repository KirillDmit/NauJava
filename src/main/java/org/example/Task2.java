package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        ArrayList<Double> arr = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            arr.add(rnd.nextDouble(500));
        }
        for(var e : arr)
            System.out.print(e + " ");
        System.out.println();
        bubbleSort(arr);
        for(var e : arr)
            System.out.print(e + " ");
    }

    public static void bubbleSort(ArrayList<Double> sortArr){
        for (int i = 0; i < sortArr.size() - 1; i++) {
            for(int j = 0; j < sortArr.size() - i - 1; j++) {
                if(sortArr.get(j + 1) < sortArr.get(j)) {
                    double swap = sortArr.get(j);
                    sortArr.set(j, sortArr.get(j + 1));
                    sortArr.set(j + 1, swap);
                }
            }
        }
    }
}
