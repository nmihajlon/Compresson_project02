package org.example;

import org.example.models.LDPCMatrix;

import java.util.Random;

public class LDPC {
    public static LDPCMatrix generateMatrix(int n, int k, int wr, int wc, long seed){
        int m = n - k;
        int[][] H = new int[m][n];

        if(m * wr != n * wc){
            throw new IllegalArgumentException("Mora da vazi: M * WR = N * WC");
        }

        Random rand = new Random(seed);

        for(int i = 0; i < wc; i++){
            int startCol = (i * wr) % n;
            for(int j = 0; j < wr; j++){
                H[i][(startCol + j) % n] = 1;
            }
        }

        for(int i = wc; i < 2 * wc && i < m; i++){
            int baseRow = i - wc;
            int shift = rand.nextInt();
            for(int j = 0; j < n; j++){
                if(H[baseRow][j] == 1){
                    H[i][(j + shift) % n] = 1;
                }
            }
        }

        for(int i = 2 * wc; i < m; i++){
            int baseRow = i % (2 * wc);
            int shift = rand.nextInt(n);
            for (int j = 0; j < n; j++) {
                if (H[baseRow][j] == 1) {
                    H[i][(j + shift) % n] = 1;
                }
            }
        }

        return new LDPCMatrix(n, k, wr, wc, H);
    }
}
