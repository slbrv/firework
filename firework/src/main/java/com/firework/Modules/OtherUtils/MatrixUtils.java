package com.firework.Modules.OtherUtils;

/**
 * Created by Slava on 11.03.2017.
 */

public class MatrixUtils {

    public synchronized static void sumMM(float[] toMatrix, int offset, float[] matrix1, int offset1, float[] matrix2, int offset2, int colmRow)
    {
        for (int y = 0; y < colmRow; y++) {
            for (int x = 0; x < colmRow; x++) {
                toMatrix[y * colmRow + x + offset] = matrix1[y*colmRow+x+offset1] + matrix2[y*colmRow+x+offset2];
            }
        }
    }
    public synchronized static void setMM(float[] toMatrix, int offset, float[] matrix1, int offset1, int colms, int rows)
    {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < colms; x++) {
                toMatrix[y * rows + x + offset] = matrix1[y*rows+x+offset1];
            }
        }
    }
}
