import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Set;

public class Language {

    double averageFirst = 0.0;
    int firstCounter = 0;
    double averageThree = 0.0;
    int threeCounter = 0;
    String content;
    LangLabel label;

    HashMap<String, Double> charDistribution = new HashMap<>();
    HashMap<String, Integer> charCount = new HashMap<>();

    HashMap<String, Integer> charByThree = new HashMap<>();
    HashMap<String, Double> charDistByThree = new HashMap<>();

    HashMap<Character, Integer> firstChar = new HashMap<>();
    HashMap<Character, Double> firstCharPercent = new HashMap<>();

    String letters = "";
    boolean endOfText = false;
    int labelLength = LangLabel.values().length;

    public Language(String content, LangLabel label) {
        this.content = content;
        this.label = label;
        calculateCharDistribution();
        // calculateCharDistributionByThree();

    }

    public Language() {

    }

    public Double getPercentageValue(String s) {

        return charDistribution.get(s);
    }

    public String getContent() {
        return content;
    }

    public void setAverageOfThree(Double averageThree) {
        this.averageThree = averageThree;
    }

    public Double getAverageOfThree() {
        return averageThree;
    }

    public void setAverageOfFirst(Double averageFirst) {
        this.averageFirst = averageFirst;
    }

    public Double getAverageOfFirst() {
        return averageFirst;
    }

    public HashMap<String, Double> calculateCharDistribution() {
        // För att undvika att programmen tolkar stora bokstäver och små bokstäver
        // skillt
        // content = user input utan mellanslag osv.
        content = content.toLowerCase();

        double amount = content.length();

        Set<String> keys = charCount.keySet();

        // Loopa igenom textinputen
        for (int i = 0; i < content.length(); i++) {
            // Konvertera varje char i user input till String
            String letter = Character.toString(content.charAt(i));

            // Checka charCount content ifall bokstaven redan finns i hashmapen
            if (!charCount.containsKey(letter)) {
                // 0 = procentfördelningen av bokstaven som anges senare
                charCount.put(letter, 0);
            }
            // Ifall bokstaven finns redan, +1 antal av bokstaven
            charCount.put(letter, charCount.get(letter) + 1);

        }

        for (String key : keys) {
            // Räkna procentuella andelen av bokstaven i texten
            double d = (charCount.get(key) / amount) * 100;
            d = Math.round(d * 100.0) / 100.0;
            charDistribution.put(key, d);
        }
        return charDistribution;

    }

    public HashMap<String, Double> calculateCharDistributionByThree() {

        String s = getContent();
        Set<String> keys = charByThree.keySet();
        String letters = "";
        int amount = 0;
        if (endOfText == false) {
            for (int i = 0; i < s.length(); i++) {
                // checkar ifall vi är vid slutet av texten, breakar for-loopen isåfall
                if (s.length() <= i + 3) {
                    letters = s.substring(i, s.length());
                    amount++;
                    endOfText = true;

                } else {

                    // Var tredje karaktär
                    letters = s.substring(i, i + 3);
                    amount++;
                }

                if (!charByThree.containsKey(letters)) {
                    // 0 = procentfördelningen av bokstaven som anges senare
                    charByThree.put(letters, 0);
                }
                // Ifall bokstaven finns redan, +1 antal av bokstaven
                charByThree.put(letters, charByThree.get(letters) + 1);

                if (endOfText == true) {
                    break;
                }

            }
        }

        for (String key : keys) {
            // Räkna procentuella andelen av bokstaven i texten

            double d = ((double) charByThree.get(key) / (double) amount) * 100.0;

            // försökte avrunda talen så tabellen sku se bättre ut
            BigDecimal bd = BigDecimal.valueOf(d);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            d = bd.doubleValue();
            charDistByThree.put(key, d);
            threeCounter++;

            averageThree += d;

        }
        averageThree = averageThree / threeCounter;
        return charDistByThree;
    }

    // Funkar men vi fick inte den att fungera vid uppgift 4
    public HashMap<Character, Double> calculateFirstLetter(String text) {
        // String s = getContent();
        String[] words = Utils.textIntoArray(text);
        int amount = 0;

        for (int i = 0; i < words.length; i++) {
            amount++;
            // Konvertera varje char i user input till String
            char letter = words[i].charAt(0);

            // Checka charCount content ifall bokstaven redan finns i hashmapen
            if (!firstChar.containsKey(letter)) {
                // 0 = procentfördelningen av bokstaven som anges senare
                firstChar.put(letter, 0);

            }
            // Ifall bokstaven finns redan, +1 antal av bokstaven
            firstChar.put(letter, firstChar.get(letter) + 1);

        }

        Set<Character> keys = firstChar.keySet();
        for (Character key : keys) {
            // Räkna procentuella andelen av bokstaven i texten

            double d = ((double) firstChar.get(key) / (double) content.length()) * 100.0;
            d = (d * 100.0) / 100.0;
            firstCharPercent.put(key, d);
            firstCounter++;
            averageFirst += d;
        }
        setAverageOfFirst(averageFirst / firstCounter);
        return firstCharPercent;

    }

    // Räknar procentuella medelvärdet för tre teckenkombinationerna
    public Double calcAverageForThree() {
        Double t = 0.0;
        Set<String> keys = charByThree.keySet();

        for (String key : keys) {
            t += charDistByThree.get(key);
        }
        return t / (double) charDistByThree.size();
    }

    // Räknar procentuella medelvärdet för första tecknet
    public Double calcAverageForFirst() {
        Double t = 0.0;
        Set<Character> keys = firstCharPercent.keySet();

        for (Character key : keys) {
            t += firstCharPercent.get(key);
        }
        return t / (double) firstCharPercent.size();
    }

}