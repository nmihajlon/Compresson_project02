package org.example;

import org.example.models.LDPCMatrix;
import org.example.models.Syndrome;
import org.example.models.VectorError;

import java.util.*;

public class SyndromeDecoder {
    private final LDPCMatrix H;
    private final Map<String, Syndrome> syndromeMap;
    private int codeDistance;

    public SyndromeDecoder(LDPCMatrix H){
        this.H = H;
        this.syndromeMap = new HashMap<>();
        this.codeDistance = Integer.MAX_VALUE;
        generateSyndromMap();
    }

    private void generateSyndromMap(){
        int n = H.getN();

        for(int weight = 1; weight <= n; weight++){
            List<int[]> combinations = generateCombinations(n, weight);
            for(int[] error: combinations){
                VectorError vectorError = new VectorError(error);
                int[] syndromeBits = H.multiplyVector(vectorError.errorBits);
                String key = Arrays.toString(syndromeBits);

                if(!syndromeMap.containsKey(key)){
                    syndromeMap.put(key, new Syndrome(syndromeBits, error));
                }

                if(isZero(syndromeBits)){
                    codeDistance = Math.min(codeDistance, vectorError.weight);
                }
            }
        }

        if(codeDistance == Integer.MAX_VALUE){
            codeDistance = -1;
        }
    }

    public Syndrome decode(int[] received){
        int[] syndromeBits = H.multiplyVector(received);
        String key = Arrays.toString(syndromeBits);
        return syndromeMap.getOrDefault(key, new Syndrome(syndromeBits));
    }

    private boolean isZero(int[] vector){
        for(int bit: vector){
            if(bit != 0) return false;
        }
        return true;
    }

    private List<int[]> generateCombinations(int n, int weight){
        List<int[]> results = new ArrayList<>();
        int[] comb = new int[weight];
        for(int i = 0; i < weight; i++){
            comb[i] = i;
        }

        while(comb[0] <= n - weight){
            int[] vec = new int[n];
            for(int pos: comb){
                vec[pos] = 1;
            }
            results.add(vec.clone());

            int t = weight - 1;
            while(t >= 0 && comb[t] == n - weight + t){
                t--;
            }
            if(t < 0) break;
            comb[t] ++;
            for(int i = t + 1; i < weight; i++){
                comb[i] = comb[i - 1] + 1;
            }
        }
        return results;
    }

    public int getCodeDistance() {
        return codeDistance;
    }

    public Map<String, Syndrome> getSyndromeMap() {
        return syndromeMap;
    }
}
