import org.code.theater.Scene;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DataScene extends Scene {
    private double[] data1;
    private double[] data2;
    private int highlightIndex;

    public DataScene(String file1, String file2) {
        // Load the datasets from text files
        data1 = readData(file1);
        data2 = readData(file2);

        // Safety check for empty files
        if (data1.length == 0 || data2.length == 0) {
            System.out.println("Error: One or both data files are empty or missing.");
            highlightIndex = -1; // no highlight
            return;
        }

        // Find the index of the largest sum (data1 + data2)
        highlightIndex = 0;
        double maxSum = data1[0] + data2[0];
        for (int i = 1; i < data1.length; i++) {
            if (data1[i] + data2[i] > maxSum) {
                maxSum = data1[i] + data2[i];
                highlightIndex = i;
            }
        } //we dont have any datasets so I had to make my own via random number generator

        drawScene();
    }

    // Reads numbers from a text file and returns them as an array
    private double[] readData(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            java.util.List<Double> numbers = new java.util.ArrayList<>();
            while (scanner.hasNextDouble()) {
                numbers.add(scanner.nextDouble());
            }
            scanner.close();
            // Convert List<Double> to double[]
            double[] arr = new double[numbers.size()];
            for (int i = 0; i < numbers.size(); i++) {
                arr[i] = numbers.get(i);
            }
            return arr;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return new double[0];
        }
    }

    // Draw the visualization
    private void drawScene() {
        clear("white"); // background

        if (data1.length == 0 || data2.length == 0) return;

        int numPoints = Math.min(data1.length, data2.length);
        int width = getWidth();
        int height = getHeight();
        int margin = 50;

        for (int i = 0; i < numPoints; i++) {
            int x = margin + i * (width - 2 * margin) / (numPoints - 1);

            int y1 = height - margin - (int) data1[i]; // invert y-axis
            int y2 = height - margin - (int) data2[i];

            int size1 = 20;
            int size2 = 20;

            // Highlight max sum
            if (i == highlightIndex) {
                setFillColor("gold");
                drawEllipse(x - size1 / 2, y1 - size1 / 2, size1, size1);
                drawEllipse(x - size2 / 2, y2 - size2 / 2, size2, size2);
            } else {
                setFillColor("red");
                drawEllipse(x - size1 / 2, y1 - size1 / 2, size1, size1);

                setFillColor("blue");
                drawEllipse(x - size2 / 2, y2 - size2 / 2, size2, size2);
            }

            // Draw value labels
            setTextColor("black");
            drawText("" + Math.round(data1[i]), x - 10, y1 - size1 - 10);
            drawText("" + Math.round(data2[i]), x - 10, y2 - size2 - 10);
        }

        // Optional: display highlight index
        setTextColor("black");
        drawText("Highlight Index: " + highlightIndex, 50, 50);
    }
}
