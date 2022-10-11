package cbsp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class GenerateParameters {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main (String[] args) {

        /* Insert your student ID here: */
        int studentID = 11111111;

        if (args.length == 1) {
            try {
                studentID = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println("First argument should be integer value of your student ID.");
            }
        }

        if (9999999 < studentID && studentID < 99999999) {
            Random random = new Random(studentID);
            System.out.println("Computer Based Sound Production: 2. Sound generation tasks");
            System.out.println("Student ID: " + 92629);
            System.out.println("Task 1");
            System.out.println("A: Generate a signal with sine wave equation with the following parameters:");
            System.out.println("    - Frequency: " + (random.nextInt((4000 - 100) + 1) + 100));
            System.out.println("    - Amplitude: " + (random.nextInt((127 - 50) + 1) + 50));
            System.out.println("    - Sample rate per second: " + (random.nextInt((44000 - 8000) + 1) + 8000));
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            System.out.println("B: Generate a signal with cosine wave equation with the following parameters:");
            System.out.println("    - Frequency: " + (random.nextInt((4000 - 100) + 1) + 100));
            System.out.println("    - Amplitude: " + (random.nextInt((127 - 50) + 1) + 50));
            System.out.println("    - Sample rate per second: " + (random.nextInt((44000 - 8000) + 1) + 8000));
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            System.out.println("C: Generate a signal with sine wave equation with the following parameters and then add its higher harmonics:");
            System.out.println("    - Fundamental frequency: " + (random.nextInt((400 - 100) + 1) + 100));
            System.out.println("    - Fundamental amplitude: " + (random.nextInt((127 - 10) + 1) + 10));
            System.out.println("    - Sample rate per second: " + (random.nextInt((44000 - 8000) + 1) + 8000));
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            int nOfHigherHarmonics = random.nextInt((10 - 2) + 1) + 2;
            System.out.println("    - Number of higher harmonics: " + nOfHigherHarmonics);
            for (int i = 1; i < nOfHigherHarmonics + 1; i++) {
                BigDecimal bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
                double value = bd.doubleValue();
                System.out.println("    - Amplitude for " + i + ". harmonic: " + value + " of the maximum amplitude.");
            }
            System.out.println("D: Generate a signal with cosine wave equation with the following parameters and then add its higher harmonics:");
            System.out.println("    - Fundamental frequency: " + (random.nextInt((400 - 100) + 1) + 100));
            System.out.println("    - Fundamental amplitude: " + (random.nextInt((127 - 10) + 1) + 10));
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            nOfHigherHarmonics = random.nextInt((10 - 2) + 1) + 2;
            System.out.println("    - Number of higher harmonics: " + nOfHigherHarmonics);
            for (int i = 1; i < nOfHigherHarmonics + 1; i++) {
                BigDecimal bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
                double value = bd.doubleValue();
                System.out.println("    - Amplitude for " + i + ". harmonic: " + value + " of the maximum amplitude.");
            }
            System.out.println("Task 2");
            System.out.println("A: Generate a sine signal using JSyn API with the following parameters:");
            System.out.println("    - Frequency: " + (random.nextInt((4000 - 100) + 1) + 100));
            BigDecimal bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            double value = bd.doubleValue();
            System.out.println("    - Amplitude: " + value);
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            System.out.println("B: Generate two sine signals using JSyn API with the following parameters and sum them together:");
            System.out.println("    - Frequency 1: " + (random.nextInt((4000 - 100) + 1) + 100));
            bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            value = bd.doubleValue();
            System.out.println("    - Amplitude 1: " + value);
            System.out.println("    - Frequency 2: " + (random.nextInt((4000 - 100) + 1) + 100));
            bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            value = bd.doubleValue();
            System.out.println("    - Amplitude 2: " + value);
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            System.out.println("C: Generate a sine signal using JSyn API with the following parameters and then add its higher harmonics:");
            System.out.println("    - Fundamental frequency: " + (random.nextInt((400 - 100) + 1) + 100));
            bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            value = bd.doubleValue();
            System.out.println("    - Fundamental amplitude: " + value);
            System.out.println("    - Sample rate per second: " + (random.nextInt((44000 - 8000) + 1) + 8000));
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            nOfHigherHarmonics = random.nextInt((10 - 2) + 1) + 2;
            System.out.println("    - Number of higher harmonics: " + nOfHigherHarmonics);
            for (int i = 1; i < nOfHigherHarmonics + 1; i++) {
                bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
                value = bd.doubleValue();
                System.out.println("    - Amplitude for " + i + ". harmonic: " + value + " of the maximum amplitude.");
            }
            System.out.println("Submit the solution over the e-classroom!");
        }
        else {
            System.err.println("Student ID needs to have 8 values.");
        }
    }
}
