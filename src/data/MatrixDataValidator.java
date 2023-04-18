package data;

import exceptions.WrongDataException;

public class MatrixDataValidator {
    private static MatrixDataValidator instance;

    private static final int MAX_SIZE = 21;
    private static final int MIN_SIZE = 0;


    private MatrixDataValidator() {
    }

    public static MatrixDataValidator getInstance() {
        if (instance == null) {
            instance = new MatrixDataValidator();
        }
        return instance;
    }

    public Integer validateSize(int size) throws WrongDataException {
        int sizeValue = size;
        if (!( sizeValue > MIN_SIZE && sizeValue < MAX_SIZE)) {
            throw new WrongDataException("Dimension should be between: " + MIN_SIZE + " and " + MAX_SIZE);
        }
        return sizeValue;
    }

}
