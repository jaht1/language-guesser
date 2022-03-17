import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Set;

public class LanguageStats {

    // Nyckeln ska vara beteckningen på språket, t.ex. LangLabel.SV
    // Innehållet är ett Language objekt
    HashMap<LangLabel, Language> languages = new HashMap<>();
    Language userInput = new Language();
    Language txtFileContent = new Language();
    String c;
    String content;

    public void addLanguage() {

    }

    // Jämför teckenfördelningen med userinput (lang) med alla språk i languages
    public String guessLanguage(Language input) {
        // Inre loop userinput
        Double p = 0.0;
        Double t = 0.0;
        Double dif = 0.0;
        Double sum = 0.0;
        Double totSum = 0.0;
        Double th = 0.0;
        Double first = 0.0;
        LangLabel leastDif = LangLabel.OK;

        String b = "";
        b += "Språk      Analys1      Analys2        Analys 3          Kombinerat medeltal\n";
        // Loopa igenom språken
        for (LangLabel v : LangLabel.values()) {
            if (v != LangLabel.OK) {
                sum = 0.0;

                Language l = languages.get(v);
                // Loopa igenom inputen
                for (String s : input.charDistribution.keySet()) {
                    for (String c : l.charDistribution.keySet()) {
                        // Checka för samma bokstav i textfilerna och user input
                        if (s.equals(c)) {

                            // uträkningen
                            p = input.getPercentageValue(s);
                            t = l.getPercentageValue(c);
                            dif = Math.abs(p - t);
                            sum += dif;

                        }
                    }
                }
                th = Math.abs(l.getAverageOfThree() - input.getAverageOfThree());
                first = Math.abs(l.getAverageOfFirst() - input.getAverageOfFirst());
                double combo = (dif + th + first) / 3;
                dif = Math.round(dif * 100.0) / 100.0;
                th = Math.round(th * 100.0) / 100.0;
                first = Math.round(first * 100.0) / 100.0;
                
                b += v + "         " + dif + "        " + th + "            " + first + "               " +  Math.round(combo * 100.0) / 100.0 /*Math.round((combo * 100.0) / 100.0)*/
                        + "\n\n";
                // Körs en gång i början av programmet
                if (totSum == 0.0) {
                    totSum = sum;
                    leastDif = v;
                }
                // Ifall den nuvarande textfilen har mindre skillnad än den föregående ersätts
                // totSum med den nya skillnadsvärden
                else if (totSum > sum) {
                    totSum = sum;
                    leastDif = v;

                }

            }
        }
        // Returnera beteckningen på språket i languages som ger _minsta_ skillnaden

        return b + "\nJag gissar på att du skrev på " + leastDif.getName();
    }

    // Räknar skillnaden mellan input och textfil
    public Double averageOfThree(LangLabel v) {

        Double a = 0.0;

        Language l = languages.get(v);
        a = Math.abs(userInput.calcAverageForThree() - l.calcAverageForThree());

        return a;
    }

    // Borde räkna skillnaden mellan första bokstaven
    public void averageOfFirst() {

        Double a = 0.0;
        for (LangLabel v : LangLabel.values()) {
            Language l = languages.get(v);
            a = Math.abs(userInput.calcAverageForFirst() - l.calcAverageForThree());
        }
    }

    public String calculateCharByThree() {

        HashMap<String, Double> m = new HashMap<>();

        String h = "";

        int counter = 0;
        for (LangLabel v : LangLabel.values()) {
            counter++;

            Language l = languages.get(v);
            if (v != LangLabel.OK) {
                h += v;

                h += "\n3-Tecken kombination & " + "Procentuell fördelning\n";

                if (v != LangLabel.OK) {

                    Set<String> keys = l.calculateCharDistributionByThree().keySet();

                    for (String key : keys) {
                        h += "\n" + key + ": " + l.charDistByThree.get(key) + "%\n";
                    }

                }
            }
        }
        return h;
    }

    /*
     * public Double calculateCharByThree() {
     * 
     * Double h = 0.0; Double t = 0.0; for (LangLabel v : LangLabel.values()) {
     * 
     * Language l = languages.get(v); if (v != LangLabel.OK) { //h += v;
     * 
     * //h += "\n3-Tecken kombination & " + "Procentuell fördelning\n";
     * 
     * if (v != LangLabel.OK) {
     * 
     * Set<String> keys = l.calculateCharDistributionByThree().keySet();
     * 
     * //Spara key i en string för printande for (String key : keys) { //h += "\n" +
     * key + ": " + l.charDistByThree.get(key) + "%\n"; h +=
     * l.charDistByThree.get(key); }
     * 
     * }
     * 
     * 
     * } t = h/(double) l.charByThree.size();
     * 
     * 
     * } return t;
     * 
     * }
     */

    public Language checkLanguageStats(String text) {

        for (LangLabel v : LangLabel.values()) {

            content = Utils.readLanguageFile(v);
            c = Utils.readtextFile(content);
            txtFileContent = new Language(c, v);

            userInput = new Language(text, v);

            // Tillägger alla språkbeteckningar som key och textfilens content som värdet
            languages.put(v, txtFileContent);

        }

        return userInput;
    }

    // Räkna procentuella andelen av första bokstaven i orden

    public String firstChar() {

        String h = "";
        for (LangLabel v : LangLabel.values()) {
            content = Utils.readLanguageFile(v);
            content = content.toLowerCase();

            Language l = languages.get(v);
            if (v != LangLabel.OK) {
                h += v;

                h += "\nFörsta tecknet i varje ord & " + "Procentuell fördelning\n";

                if (v != LangLabel.OK) {

                    Set<Character> keys = l.calculateFirstLetter(content).keySet();

                    // Spara key i en string för printande
                    for (Character key : keys) {
                        h += "\n" + key + ": " + l.firstCharPercent.get(key) + "%\n";
                    }

                }

            }

        }
        return h;
    }

    /*
     * public Double firstChar(){
     * 
     * Double h = 0.0; Double t = 0.0; for (LangLabel v : LangLabel.values()) {
     * content = Utils.readLanguageFile(v); //String[] c =
     * Utils.textIntoArray(content);
     * 
     * Language l = languages.get(v); if (v != LangLabel.OK) { // h += v;
     * 
     * // h += "\nFörsta tecknet i varje ord & " + "Procentuell fördelning\n";
     * 
     * if (v != LangLabel.OK) {
     * 
     * Set<Character> keys = l.calculateFirstLetter(content).keySet();
     * 
     * //Spara key i en string för printande for (Character key : keys) { // h +=
     * "\n" + key + ": " + l.firstCharPercent.get(key) + "%\n";
     * h+=l.firstCharPercent.get(key); }
     * 
     * }
     * 
     * 
     * } t = h/(double) l.firstCharPercent.size();
     * 
     * 
     * } return t; }
     */
}