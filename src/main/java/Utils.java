import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static String readtextFile(String text) {
        String content = "";
        text = text.replaceAll("[^\\p{L}\\s]", "");
        text = text.replaceAll("\\s+", " ");
        text = text.toLowerCase();

        String[] words = text.split(" ");
        for (String word : words) {
            content += word;
        }
        return content;

    }

    public static String[] textIntoArray(String text) {
        text = text.replaceAll("[^\\p{L}\\s]", "");
        String[] words = text.split("\\s+");
        return words;

    }

    public static String readLanguageFile(LangLabel lang) {

        String fileName = "assets/lang-samples/" + lang + ".txt";
        StringBuilder content = new StringBuilder();

        try {
            FileInputStream inStream = new FileInputStream(fileName);
            InputStreamReader reader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);

            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }

        return content.toString();
    }
}
