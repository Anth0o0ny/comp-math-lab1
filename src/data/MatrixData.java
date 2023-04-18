package data;

import exceptions.WrongDataException;

import java.util.ArrayList;

public class MatrixData {

    private final MatrixDataValidator VALIDATOR = MatrixDataValidator.getInstance();
    private final int SIZE;
    private double[][] matrix;
    private double[][] mtxCopy;
    private double[][] triangleMtx;
    private ArrayList<Double> arrayList;
    public ArrayList<Double> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Double> arrayList) {
        this.arrayList = arrayList;
    }



    public MatrixData(int size) throws WrongDataException {
        SIZE = VALIDATOR.validateSize(size);
    }

    public MatrixDataValidator getVALIDATOR() {
        return VALIDATOR;
    }

    public int getSIZE() {
        return SIZE;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMtxCopy() {
        return mtxCopy;
    }

    public void setMtxCopy(double[][] mtxCopy) {
        this.mtxCopy = mtxCopy;
    }

    public double[][] getTriangleMtx() {
        return triangleMtx;
    }

    public void setTriangleMtx(double[][] triangleMtx) {
        this.triangleMtx = triangleMtx;
    }
}
