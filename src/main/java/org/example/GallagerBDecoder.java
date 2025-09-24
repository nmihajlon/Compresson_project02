package org.example;

import org.example.models.LDPCMatrix;
import org.example.models.VectorError;

import java.util.Arrays;

public class GallagerBDecoder {

    private final LDPCMatrix H;
    private final double th0;
    private final double th1;
    private final int maxIterations;

    public GallagerBDecoder(LDPCMatrix H, double th0, double th1, int maxIterations) {
        this.H = H;
        this.th0 = th0;
        this.th1 = th1;
        this.maxIterations = maxIterations;
    }

    public DecodeResult decode(int[] received) {
        int[] x = received.clone();
        int[] original = received.clone();

        for (int iter = 0; iter < maxIterations; iter++) {

            int[] syndrome = H.multiplyVector(x);
            if (isZero(syndrome)) return new DecodeResult(x, true, iter + 1);

            boolean changed = false;

            for (int j = 0; j < H.n; j++) {

                int neighborCount = 0;
                for (int i = 0; i < H.m; i++) if (H.H[i][j] == 1) neighborCount++;
                if (neighborCount == 0) continue;

                int d0 = 0, d1 = 0;
                for (int i = 0; i < H.m; i++) {
                    if (H.H[i][j] != 1) continue;
                    int sum = 0;
                    for (int l = 0; l < H.n; l++) if (l != j && H.H[i][l] == 1) sum ^= x[l];
                    int recVal = (syndrome[i] - sum + 2) % 2;
                    if (recVal == 0) d0++; else d1++;
                }

                int old = x[j];
                if (d0 > th0 * neighborCount) x[j] = 0;
                else if (d1 > th1 * neighborCount) x[j] = 1;
                else x[j] = original[j];

                if (x[j] != old) changed = true;
            }

            if (!changed) break;
        }

        return new DecodeResult(x, false, maxIterations);
    }

    private boolean isZero(int[] vec) {
        for (int b : vec) if (b != 0) return false;
        return true;
    }

    public boolean canCorrect(VectorError error) {
        int[] codeword = new int[H.n];
        int[] received = error.applyTo(codeword);
        DecodeResult result = decode(received);
        for (int bit : result.decodedWord) if (bit != 0) return false;
        return result.success;
    }

    public static class DecodeResult {
        public final int[] decodedWord;
        public final boolean success;
        public final int iterations;

        public DecodeResult(int[] decodedWord, boolean success, int iterations) {
            this.decodedWord = decodedWord.clone();
            this.success = success;
            this.iterations = iterations;
        }

        @Override
        public String toString() {
            return "Decoded: " + Arrays.toString(decodedWord) +
                    ", Success: " + success + ", Iterations: " + iterations;
        }
    }
}
