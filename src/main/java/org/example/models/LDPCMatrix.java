package org.example.models;

import java.util.Arrays;

public class LDPCMatrix {
    public final int n;
    public final int k;
    public final int wr;
    public final int wc;
    public final int m;
    public final int[][] H;

    public LDPCMatrix(int n, int k, int wr, int wc, int[][] H){
        this.n = n;
        this.k = k;
        this.wr = wr;
        this.wc = wc;
        this.m = n - k;
        this.H = new int[m][n];

        for(int i = 0; i < m; i++){
            this.H[i] = Arrays.copyOf(H[i], n);
        }
    }

    public int[] multiplyVector(int[] vector){
        if(vector.length != n){
            throw new IllegalArgumentException("Dimenzija vektora mora da bude " + n);
        }

        int[] result = new int[m];
        for(int i = 0; i < m; i++){
            int sum = 0;
            for(int j = 0; j < n; j++){
                sum ^= (H[i][j] & vector[j]);
            }
            result[i] = sum;
        }
        return result;
    }

    public void printMatrix() {
        System.out.println("LDPC matrica H (" + m + " x " + n + "):");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(H[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
