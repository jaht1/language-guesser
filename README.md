# language-guesser

This program guesses the language of the user input. The languages which the programs can guess is English, Finnish, Swedish, Spanish, French, Italian, Norwegian and German.

The guessing is done by analysing the input in three different ways and then comparing the analysis to the text files in the program.

## Analysis

Analysis 1 calculates the offurence of **all** characters in the input and compares it to all the language text files.

Analysis 2 splits the text to **every three** characters, and calculates the occurence of those three characters in the input and text files.

Analysis 3 calculates the occurence of every **first** character in a the words of the user input, and compares the amount of occurence to the text files.


The difference is then calculated. The language with the **least** difference in occurence is the language the program guesses.
