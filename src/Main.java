import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static ArrayList<Point> trainingPoints = new ArrayList<>();
    private static ArrayList<Point> testingPoints = new ArrayList<>();

    private static int k;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * TRAINING_DATA_FILES merupakan lokasi data training
     */
    private static final String TRAINING_DATA_FILES = "G:\\Materi Semester 5\\Machine Learning\\NN\\K-Example-1\\data\\TrainingData.txt";

    /**
     * TESTING_DATA_FILES merupakan lokasi data testing
     */
    private static final String TESTING_DATA_FILES = "G:\\Materi Semester 5\\Machine Learning\\NN\\K-Example-1\\data\\TestingData.txt";

    public static void main(String[] args) {
        trainingPoints = setData(TRAINING_DATA_FILES);
        testingPoints = setData(TESTING_DATA_FILES);

        System.out.print("Masukkan k : ");
        k = scanner.nextInt();

        System.out.println("");

        searchHypotesis();
    }

    /**
     * Merupakan fungsi yang digunakan untuk membaca data dari dalam file dan mengconvertnya menjadi bentuk array of string dua dimensi
     *
     * @param filePath merupakan lokasi file yang ingin dibaca isinya, setiap kata pada file haruslah dipisahkan dengan spasi (" ")
     * @return merupakan isi file dalam bentuk array of string dua dimensi
     */
    private static ArrayList<Point> setData(String filePath) {
        ArrayList<String[]> resultList = new ArrayList<>(); // List untuk menampung sementara isi file
        ArrayList<Point> dataPoints = new ArrayList<>();

        try {
            File file = new File(filePath); // Membuat object file dari file yang diinginkan
            BufferedReader br = new BufferedReader(new FileReader(file)); // Membuat object untuk membaca file
            String str = "";

            /*
             * Looping untuk membaca keseluruhan isi file
             */
            while (str != null) {
                str = br.readLine(); // Mengambil setiap baris file

                if (str != null) // Jika baris ada
                    resultList.add(str.split(" ")); // Memisahkan setiap kata menurut spasi (" ")
            }
        } catch (FileNotFoundException e) { // Jika file tidak ditemukan
            System.out.println("File tidak ditemukan");
        } catch (IOException e) { // Jika terjadi kesalahan saat membaca file
            System.out.println("Kesalahan saat membaca file");
        } finally {
            if (resultList.size() > 0) { // Jika file tidak kosong
                for (String[] strings : resultList) {
                    Point point;

                    double age = Double.parseDouble(strings[0]);
                    double weight = Double.parseDouble(strings[1]);

                    try {
                        int answer = Integer.parseInt(strings[2]);

                        point = new Point(age, weight, answer);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        point = new Point(age, weight);
                    }

                    dataPoints.add(point);
                }
            }
        }

        return dataPoints; // Mengembalikan data
    }

    private static void searchHypotesis() {
        ArrayList<Double> distanceList = new ArrayList<>();
        ArrayList<Point> nearestPoints = new ArrayList<>();

        int yesCount, noCount;

        for (Point testingPoint : testingPoints) {
            double[] testingVector = {testingPoint.getAge(), testingPoint.getWeight()};

            distanceList.clear();
            nearestPoints.clear();

            yesCount = 0;
            noCount = 0;

            for (Point trainingPoint : trainingPoints) {
                double[] trainingVector = {trainingPoint.getAge(), trainingPoint.getWeight()};
                double[] t = {testingVector[0] - trainingVector[0],
                        testingVector[1] - trainingVector[1]};

                double d = Math.pow(t[0], 2) + Math.pow(t[1], 2);
                double distance = Math.sqrt(d);

                trainingPoint.setDistance(distance);
                distanceList.add(distance);
            }

            Collections.sort(distanceList);

            System.out.println("");

            for (int i = 0; i < k; i++) {
                Point point = new Point();
                double distance = distanceList.get(i);

                for (Point trainingPoint : trainingPoints) {
                    if (trainingPoint.searchDistance(distance)) {
                        point = trainingPoint;
                        break;
                    }
                }

                System.out.println(point.getAnswer() + ", " + distance);

                nearestPoints.add(point);
            }

            System.out.println("");

            for (Point nearestPoint : nearestPoints) {
                switch (nearestPoint.getAnswer()) {
                    case 0 :
                        noCount++;
                        break;
                    case 1 :
                        yesCount++;
                        break;
                }
            }

            System.out.print("Umur " + testingPoint.getAge() + ", Berat " + testingPoint.getWeight() + ", ");

            if (yesCount >= noCount)
                System.out.println("Hipertensi");
            else
                System.out.println("Tidak Hipertensi");

            System.out.println("");
        }
    }
}
