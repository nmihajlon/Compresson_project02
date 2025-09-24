package org.example.models;

import java.util.Arrays;

public class Syndrome {
    private final int[] bits;
    private final int[] correction;

    public Syndrome(int[] syndromeBits, int[] correctionVector){
        this.bits = Arrays.copyOf(syndromeBits, syndromeBits.length);
        if(correctionVector != null){
            this.correction = Arrays.copyOf(correctionVector, correctionVector.length);
        }
        else{
            this.correction = null;
        }
    }

    public Syndrome(int[] syndromeBits){
        this(syndromeBits, null);
    }

    public boolean isCorrectable(){
        return correction != null;
    }

    public int[] getBits(){
        return Arrays.copyOf(bits, bits.length);
    }

    public int[] getCorrection(){
        if(correction != null){
            return Arrays.copyOf(correction, correction.length);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(!(other instanceof Syndrome)) return false;
        Syndrome that = (Syndrome) other;
        return Arrays.equals(this.bits, that.bits);
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(bits);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("Sindrome: ");
        for(int b: bits) s.append(b);

        if(isCorrectable()){
            s.append(" -> Korektor: ");
            for(int b: correction) s.append(b);
        }
        else{
            s.append(" -> ne moze da se ispravi");
        }

        return  s.toString();
    }
}
