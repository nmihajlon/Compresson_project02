package org.example;

import org.example.models.LDPCMatrix;
import org.example.models.VectorError;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int n = 15;
        int k = 6;
        int wr = 5;
        int wc = 3;
        long seed = 42;

        LDPCMatrix H = LDPC.generateMatrix(n, k, wr, wc, seed);
        System.out.println("LDPC matrica H:");
        H.printMatrix();

        SyndromeDecoder syndromeDecoder = new SyndromeDecoder(H);
        int codeDistance = syndromeDecoder.getCodeDistance();
        System.out.println("Kodno rastojanje: " + codeDistance);

        GallagerBDecoder gallager = new GallagerBDecoder(H, 0.5, 0.5, 20);

        VectorError minimalFailure = findMinimalGallagerFailure(H, gallager);

        if (minimalFailure != null) {
            System.out.println("Najmanja greska koju Gallager B ne moze da ispravi:");
            System.out.println(minimalFailure);

            System.out.println("Kodno rastojanje: " + codeDistance);
            System.out.println("Tezina greske: " + minimalFailure.weight);

            if (minimalFailure.weight < codeDistance) {
                System.out.println("Gallager B ne moze ispraviti greske manje od kodnog rastojanja.");
            } else if (minimalFailure.weight == codeDistance) {
                System.out.println("Gallager B ne moze ispraviti greske jednake kodnom rastojanju.");
            } else {
                System.out.println("Gallager B moze ispraviti greske do kodnog rastojanja.");
            }
        } else {
            System.out.println("Gallager B moze ispraviti sve greske.");
        }
    }

    private static VectorError findMinimalGallagerFailure(LDPCMatrix H, GallagerBDecoder gallager) {
        int n = H.n;

        for (int weight = 1; weight <= n; weight++) {
            List<int[]> combinations = SyndromeDecoder.generateCombinations(n, weight);

            for (int[] errorBits : combinations) {
                VectorError error = new VectorError(errorBits);
                if (!gallager.canCorrect(error)) {
                    return error;
                }
            }
        }
        return null;
    }
}