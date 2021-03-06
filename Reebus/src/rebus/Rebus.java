package rebus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Class for game logic
 * 
 * @author marsh_000
 *
 */
public class Rebus {

	Rebus() {
		Config.entireCollection = new BigWordCollection();
		// Set Config.gameCollection to the appropriate settings
	}

	// Sets Config.gameCollection to the appropriate settings
	public void generateWordBank() {
		BigWordCollection temp = Config.entireCollection
				.getBigWordCollectionByTopic(Config.wordBankTopic);
		BigWordCollection teluguSolutions = new BigWordCollection();
		teluguSolutions.getAllBigWords().clear();
		WordProcessor word;
		// 0 Represents any solution length
		if (Config.wordBankMaxLength != 0 && Config.LANGUAGE.equals("En")) {
			temp = temp.getBigWordCollectionByWordLength(0,
					Config.wordBankMaxLength);

		} else if (Config.wordBankMaxLength != 0) {
			for (BigWord bigWord : temp.getAllBigWords()) {
				word = new WordProcessor(bigWord.getTelugu());
				if (word.getLogicalChars().size() <= Config.wordBankMaxLength) {
					teluguSolutions.addBigWord(bigWord);
					// System.out.println("Logical Size during selection: "+word.getLogicalLength());
				}
			}
			System.out.println("Telugu List Size: " + teluguSolutions.size());
			temp = teluguSolutions;
		}
		temp = temp.getBigWordCollectionByWordStrength(
				Config.wordBankWordStrengthMin, Config.wordBankWordStrengthMax);
		Config.gameCollectionWordBank = temp;
	}

	public void generateSolutionBank() {
		BigWordCollection temp = Config.entireCollection
				.getBigWordCollectionByTopic(Config.solutionBankTopic);
		BigWordCollection teluguSolutions = new BigWordCollection();
		teluguSolutions.getAllBigWords().clear();
		WordProcessor word;
		// 0 Represents any solution length
		if (Config.solutionLength != 0 && Config.LANGUAGE.equals("En")) {
			temp = temp.getBigWordCollectionByWordLength(Config.solutionLength);

		} else if (Config.solutionLength != 0) {
			for (BigWord bigWord : temp.getAllBigWords()) {
				word = new WordProcessor(bigWord.getTelugu());
				if (word.getLogicalChars().size() == Config.solutionLength) {
					teluguSolutions.addBigWord(bigWord);
					// System.out.println("Logical Size during selection: "+word.getLogicalLength());
				}
			}
			System.out.println("Telugu List Size: " + teluguSolutions.size());
			temp = teluguSolutions;
		}
		temp = temp.getBigWordCollectionByWordStrength(
				Config.solutionBankWordStrengthMin,
				Config.solutionBankWordStrengthMax);
		Config.gameCollectionSolutionBank = temp;
	}

	public void findGameWords() {
		Config.gameBigWords.clear();
		BigWordCollection possibleWords = Config.gameCollectionWordBank;
		BigWordCollection trimmedWords;
		if (Config.rebusX != 6) {
			trimmedWords = possibleWords.getBigWordCollectionByWordLength(
					Config.rebusX, Config.MAX_WORD_LENGTH);
		} else {
			trimmedWords = possibleWords.getBigWordCollectionByWordLength(1,
					Config.MAX_WORD_LENGTH);
		}
		ArrayList<BigWord> bigWords = trimmedWords.getAllBigWords();
		ArrayList<ArrayList<String>> readableWords = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> randomIndexes = new ArrayList<Integer>();
		for (int i = 0; i < bigWords.size(); i++) {
			randomIndexes.add(i);
		}
		WordProcessor word;
		// generate all the parsed words
		for (BigWord bigWord : bigWords) {
			if (Config.LANGUAGE.equals("En")) {
				word = new WordProcessor(bigWord.getEnglish());
				readableWords.add(word.getLogicalChars());
				bigWord.setProcessedEnglish(word.getLogicalChars());
			} else {
				word = new WordProcessor(bigWord.getTelugu());
				readableWords.add(word.getLogicalChars());
				bigWord.setProcessedTelegu(word.getLogicalChars());
			}
		}
		Config.potentialGameWords = bigWords;

		ArrayList<String> urls = new ArrayList<String>();
		for (int i = 0; i < Config.solutionWord.size(); i++) {
			// create some randomness for different boards to be made from same
			// solution word
			Collections.shuffle(randomIndexes);

			ArrayList<String> temp = new ArrayList<String>();
			boolean noWordFound = true;
			String testForBadCharacters = Config.solutionWord.get(i);
			if (testForBadCharacters.equals("")
					|| testForBadCharacters.equals("-")
					|| testForBadCharacters.equals(" ")) {
				noWordFound = false;
			}
			for (int j = 0; j < readableWords.size(); j++) {

				int index = 0;
				temp = readableWords.get(randomIndexes.get(j));
				if (Config.rebusX == 6) {
					if (temp.size() > 1) {
						index = new Random().nextInt(temp.size() - 1);
					}else{
						index = 0;
					}
				} else {
					index = Config.rebusX - 1;
				}
				if (temp.get(index).equals(Config.solutionWord.get(i))) {
					word = new WordProcessor(temp);
					bigWords.get(randomIndexes.get(j)).setProcessedWord(
							word.getWord());
					Config.gameBigWords.add(bigWords.get(randomIndexes.get(j)));
					noWordFound = false;
					break;
				}
			}
			if (noWordFound) {
				BigWord emptyWord = new BigWord();
				emptyWord.setProcessedWord("*No Word Found*");
				Config.gameBigWords.add(emptyWord);
			}

		}
		for (int i = 0; i < Config.gameBigWords.size(); i++) {
			System.out.println("Word "
					+ Config.gameBigWords.get(i).getEnglish());
		}
		Config.urls = urls;
	}

	public void pickSolutionWord() {
		BigWordCollection possibleSolutions = Config.gameCollectionSolutionBank;
		Random rand = new Random();
		BigWord solution;
		WordProcessor word;

		if (possibleSolutions.size() > 1) {
			solution = possibleSolutions.getBigWord(rand
					.nextInt(possibleSolutions.size() - 1) + 1);
		} else {
			solution = possibleSolutions.getBigWord(0);
		}

		if (Config.LANGUAGE.equals("En")) {
			word = new WordProcessor(solution.getEnglish());
			Config.solutionWord = word.getLogicalChars();
			Config.solutionBigWord = solution;
		} else {
			word = new WordProcessor(solution.getTelugu());
			System.out.println("Logical Length: " + word.getLogicalLength());
			Config.solutionWord = word.getLogicalChars();
			Config.solutionBigWord = solution;
			System.out.println("Size: " + Config.solutionWord.size());
		}
		System.out.println("Solution Word: " + word.getWord());
	}
}
