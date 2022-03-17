import java.lang.reflect.Array;

public class Main {

	public static void main(String[] args) {

		UserInterface ui = new UserInterface();
		LanguageStats langStats = new LanguageStats();
		String text = ui.UserInterface();
		Language user = langStats.checkLanguageStats(text);

		ui.printText(langStats.calculateCharByThree());
		ui.printText(langStats.firstChar());
		ui.printText(langStats.guessLanguage(user));
		// Utils.textIntoArray(text);
		langStats.firstChar();

		// langStats.averageOfThree();
		// langStats.averageOfFirst();

	}
}
