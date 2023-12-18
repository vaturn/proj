package com.kms.smartsiren;

public class ReliabilityModel {
    final static public int USER_RELIABILITY_DEFAULT = 400;
    final static public int TRUSTWORTHY_RANK_DEFAULT = 10000;
    final public int CRITICALSIZE = 3;

    public int getUserReliability(int G, int N){
        if(N == 0)
            return USER_RELIABILITY_DEFAULT;

        int B = N - G;
        double prob = (double) (G + 1) / (B + 1) * (G - B + N) / N;

        return (int)(prob * 100) + 50;
    }
    public int getCaseReliability(int current, int additional, int size){
        double prob = (double) (current * (size - 1) + additional) / size;

        if(size >= CRITICALSIZE){
            return (int)(prob * 1.9);
        }

        return (int)prob;
    }
}
