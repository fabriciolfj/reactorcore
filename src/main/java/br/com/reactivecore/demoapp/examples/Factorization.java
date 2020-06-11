package br.com.reactivecore.demoapp.examples;

import java.util.ArrayList;
import java.util.Collection;

public class Factorization {

    public Collection<Integer> findFactor(int number) {
        ArrayList<Integer> factors = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if(number % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}
