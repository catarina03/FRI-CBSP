package cbsp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class GenerateParameters {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main (String[] args) {

        /* Insert your student ID here: */
        int studentID = 70088634;

        if (args.length == 1) {
            try {
                studentID = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println("First argument should be integer value of your student ID.");
            }
        }

        if (9999999 < studentID && studentID < 99999999) {
            Random random = new Random(studentID);
            System.out.println("Computer Based Sound Production: 4. Filters in frequency domain");
            System.out.println("Student ID: " + studentID);
            System.out.println("Task 1");
            System.out.println("    Generate three sine signals using JSyn API with the following parameters and sum them together:");
            System.out.println("    - Frequency 1: " + (random.nextInt(100, 1000)));
            BigDecimal bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            double value = bd.doubleValue();
            System.out.println("    - Amplitude 1: " + value);
            System.out.println("    - Frequency 2: " + (random.nextInt(1500, 4000)));
            bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            value = bd.doubleValue();
            System.out.println("    - Amplitude 2: " + value);
            System.out.println("    - Frequency 3: " + (random.nextInt(4500, 8000)));
            bd = BigDecimal.valueOf(0.3 + (1.0 - 0.3) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
            value = bd.doubleValue();
            System.out.println("    - Amplitude 3: " + value);
            System.out.println("    - Duration: " + (random.nextInt((10 - 2) + 1) + 2));
            System.out.println("    A: Filter the top two frequencies out of the signal with the low-pass filter.");
            System.out.println("    B: Filter the lowest two frequencies out of the signal with the high-pass filter.");
            System.out.println("    C: Filter the only the middle frequency out of the signal with the band-stop filter.");
            System.out.println("    D: Filter the lowest and the top frequencies out of the signal with the band-pass filter.");
            System.out.println("Task 2");
            System.out.println("    In file media/siney.wav you have a song which has a narrow-band interference. Find out which is frequency of the interference in the song and use a notch filter to remove the interference.");
            System.out.println("    You can check the frequency spectrum of media/siney.wav by running FrequencySpectrum class.");
            System.out.println("Task 3");
            System.out.println("    Make a simple equalizer with the cascade of at least 8 FilterPeakingEQ filters.");
            System.out.println("Submit the solution over the e-classroom!");
        }
        else {
            System.err.println("Student ID needs to have 8 values.");
        }
    }
}
