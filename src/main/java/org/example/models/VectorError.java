package org.example.models;

public class VectorError {
    public final int[] errorBits;
    public final int weight;

    public VectorError(int[] errorBits){
        this.errorBits = errorBits.clone();
        this.weight = calculateWeight();
    }

    private int calculateWeight(){
        int w = 0;
        for(int bit: errorBits){
            if(bit == 1) w++;
        }
        return w;
    }

    public int[] applyTo(int[] codeword){
        int[] result = new int[codeword.length];
        for(int i = 0; i < codeword.length; i++){
            result[i] = (codeword[i] + errorBits[i]) % 2;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int bit: errorBits){
            sb.append(bit);
        }

        return "Greska (tezina " + weight + "): " + sb;
    }
}
