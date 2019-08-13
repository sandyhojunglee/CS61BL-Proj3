import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPuzzles {
    public static List<String> urlRegex(String[] urls) {
        // Create a String pattern to fill return array
        List<String> result = new ArrayList<>();
        String pattern = "\\(.*?(https?://(\\w+\\.)+[a-z]{2,3}/(\\w+\\.hyml)).*/\\)";
        for (String s : urls) {
            if (s.matches(pattern)) {
                result.add(s);
            }
        }
        return result;
    }

    public static List<String> findStartupName(String[] names) {
        // Create a String pattern to fill return array
        List<String> result = new ArrayList<>();
        String pattern = "(on|un|my|Data|App)([A-Za-hj-z]+)(ly|\\.io|\\.fm|\\.tv|sy|ify)";
        for (String s : names) {
            if (s.matches(pattern)) {
                result.add(s);
            }
        }
        return result;
    }

    public static BufferedImage imageRegex(String filename, int width, int height) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file found: " + filename);
        }

        Pattern coordPattern = Pattern.compile("\\(([0-9]{0,3}), ([0-9]{0,3})\\)");
        Pattern pixelPattern = Pattern.compile("\\[([0-9]{0,3}), ([0-9]{0,3}), ([0-9]{0,3})\\]");

        int[][][] arr = new int[width][height][3];

        // Initialize both Patterns and 3-d array
        try {
            String line;
            while ((line = br.readLine()) != null) {
                // Initialize both Matchers and find() for each
                Matcher m1 = coordPattern.matcher(line);
                Matcher m2 = pixelPattern.matcher(line);
                m1.find();
                m2.find();
                int x = Integer.parseInt(m1.group(1));
                int y = Integer.parseInt(m1.group(2));
                int r = Integer.parseInt(m2.group(1));
                int g = Integer.parseInt(m2.group(2));
                int b = Integer.parseInt(m2.group(3));
                // Parse each group as an Integer
                arr[x][y][0] = r;
                arr[x][y][1] = g;
                arr[x][y][2] = b;
                // Store in array
            }
        } catch (IOException e) {
            System.err.printf("Input error: %s%n", e.getMessage());
            System.exit(1);
        }
        // Return the BufferedImage of the array
        return arrayToBufferedImage(arr);
    }

    public static BufferedImage arrayToBufferedImage(int[][][] arr) {
        BufferedImage img = new BufferedImage(arr.length,
                arr[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int pixel = 0;
                for (int k = 0; k < 3; k++) {
                    pixel += arr[i][j][k] << (16 - 8 * k);
                }
                img.setRGB(i, j, pixel);
            }
        }

        return img;
    }

    public static void main(String[] args) {
        /* For testing image regex */
        BufferedImage img = imageRegex("mystery.txt", 400, 400);

        File outputfile = new File("output_img.jpg");
        try {
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
