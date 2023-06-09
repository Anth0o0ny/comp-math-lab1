package client;

import data.MatrixData;
import data.MatrixSolver;
import exceptions.WrongDataException;

import java.io.*;
import java.util.*;

public class Terminal {

    private Scanner scanner = new Scanner(System.in);

    private boolean isFromFile = false;
    private boolean isAutoGenerated = false;
    private int matrixSize;
    MatrixData matrixData = null;
    public void start () {
        System.out.println("Выберите форат ввода (keyboard or file) k/f:");

        isFromFile = isFileFormat();

        if (!isFromFile){
            if (!isFromFile)
                System.out.println("Размер матрицы:");
            matrixData = getMatrixSize();
            if (!isFromFile) {
                System.out.println("\nХодите спользовать автогенератор? (y/n)");
                isAutoGenerated = isAutoGenerated();
            }
            if (!isAutoGenerated) {
                if (!isFromFile){
//                    System.out.println("\nInput your factors by line by semicolon.\nEx: {a_11} {a_22} ... {a_1n} {b_1}");
                    while(getFactors(matrixData)!=0){
                        scanner.next();
                    };

                }
            } else {
                if (!isFromFile) {
                    System.out.println("\nГенерация коэффициентов...");
                    getRandomFactors(matrixData);
                }
            }
        }

        getNormalMatrix(matrixData);

        System.out.println("Изначальная матрица:");
        MatrixSolver.printMtx(matrixData.getMtxCopy());
        MatrixSolver.setIndexMass(matrixData.getSIZE());
        double[][] triangleMtx = MatrixSolver.getTriangleNew(matrixData.getMatrix());
        if(triangleMtx != null) {
            System.out.println("Получена треугольная матрица: ");
            MatrixSolver.printMtx(triangleMtx);

            System.out.println("Определитель матрицы равен: ");
            double det = MatrixSolver.getDeterminant(triangleMtx);
            System.out.println(det);
            System.out.println();

            if (det != 0) {
                double[] x = MatrixSolver.getRootsNew(triangleMtx);
                System.out.println("Найдены корни СЛАУ:");
                for (double v : x) System.out.printf("%.2f\t", v);
                System.out.println();
                System.out.println();

                System.out.println("Вектор невязки: ");
                double[] dis = MatrixSolver.getDiscrepancyNew(matrixData.getMtxCopy(), x);
                for (double di : dis) System.out.printf("%.16f\t", di);
                System.out.println();

            }
            else
                System.out.println("Система имеет бесконечное множество решений!");
        }
        else System.out.println("Ошибка в подсчете матрицы или система не имеет решений!");

        }


    private void getNormalMatrix(MatrixData matrixData) {
        double[][] mtx = new double[matrixData.getSIZE()][matrixData.getSIZE()+1];
        double[][] mtxCopy = new double[matrixData.getSIZE()][matrixData.getSIZE()+1];
        int index = 0;
        for(int i = 0; i< matrixData.getSIZE(); i++)
            for(int j = 0; j <matrixData.getSIZE()+1;j++)
            {
                 mtx[i][j] = mtxCopy[i][j] = matrixData.getArrayList().get(index);
                 index++;
            }
        matrixData.setMatrix(mtx);
        matrixData.setMtxCopy(mtxCopy);
    }
    public int getFactors(MatrixData matrixData) {
        System.out.println("\nВведите коэфициенты построчно с пробелом.\nEx: {a_11} {a_22} ... {a_1n} {b_1}");
        ArrayList<Double> arrayList = new ArrayList<>();

        boolean point = false;
            try {
                for (int i = 0; i < (matrixData.getSIZE() * matrixData.getSIZE() + matrixData.getSIZE()); i++) {
                        arrayList.add(scanner.nextDouble());
                }
            } catch (InputMismatchException e) {
                point = true;
                System.out.println("Ошибка ввода!  Проверьте, что дробные числа записаны через запятую");
                return 1;
            }
            matrixData.setArrayList(arrayList);
            return 0;
    }
    private void getRandomFactors(MatrixData matrixData) {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < (matrixData.getSIZE() * matrixData.getSIZE() + matrixData.getSIZE()); i++){
            arrayList.add(Math.round((new Random().nextDouble() - 0.3) * 1000d) / 100d);
        }
        matrixData.setArrayList(arrayList);
    }
    public void refresh() {
        isFromFile = false;
        isAutoGenerated = false;
        scanner = new Scanner(System.in);
        System.clearProperty("fileName");
    }

    public String continueUse () {
        System.out.print("\nПродолжить? (y/n)\n>");
        while (true) {
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y")){
                return "cont";
            } else if (answer.equals("n")){
                return "end";
            }
            System.out.println("Type y/n");
        }
    }



    private boolean isFileFormat() {
        System.out.print(">");
        String format = scanner.nextLine();
        if (Objects.equals(format, "f")) {
              matrixData =  getScannerFromFile();
            return true;
        } else if (Objects.equals(format, "k")) {
            return false;
        } else {
            System.out.println("k/f");
            return isFileFormat();
        }
    }

    private MatrixData getMatrixSize() {
        System.out.print(">");
        int size = 0;
        boolean valid = false;
        while(! valid ) {
            try {
                size = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Неверный формат");
                scanner.next();
            }
        }
        try {
            return new MatrixData(size);
        } catch (WrongDataException exception) {
            System.out.println(exception.getMessage());
            if (isFromFile) System.exit(-1);
        }
        return getMatrixSize();

    }


    private MatrixData getScannerFromFile() {
        String fileName = System.getProperty("fileName");
        ArrayList<Double> arrayList = new ArrayList<>();
        MatrixData matrixData;


        if (fileName == null) {
            System.out.println("Введите название файла:");
            System.out.print(">fileName = ");
            fileName = scanner.nextLine();
            System.setProperty("fileName", fileName);
        }

        try {
            int size = 0;
            FileInputStream path = new FileInputStream(fileName);
            DataInputStream inFile = new DataInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(inFile));
            String data;

            while ((data = br.readLine()) != null) {
                String[] tmp = data.split(" ");    //Split space
                for (String s : tmp)
                    try {
                        arrayList.add(Double.parseDouble(s.replace(",", ".")));
                    } catch (NumberFormatException e){
                        System.out.println("Исправтье содержимое файла и запустите программу сначала");
                        System.exit(0);
                    }

                size++;
            }
            matrixData = new MatrixData(size);
            matrixData.setArrayList(arrayList);
            System.out.println("Размерность матрицы: ");
            System.out.println(size);
            System.out.println();
            return matrixData;
        } catch (FileNotFoundException e) {
            System.out.println("Файл с названием '" + fileName + "' не найден. Попробуйте еще раз.");
            System.clearProperty("fileName");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WrongDataException e) {
            throw new RuntimeException(e);
        }

        return getScannerFromFile();
    }


    private boolean isAutoGenerated() {
        if (!isFromFile) System.out.print(">");
        String answer = scanner.nextLine().trim();
        if (answer.equals("y")) {
            return true;
        } else if (answer.equals("n")) {
            return false;
        } else {
            System.out.println("y/n");
            return isAutoGenerated();
        }
    }




}
