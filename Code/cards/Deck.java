package cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Deck {
	private ArrayList<Card> deck;

	public Deck(File cardsFile) {
		deck = new ArrayList<Card>();
		createDeck(cardsFile);
	}
	
	public Deck() {
		deck = new ArrayList<Card>();
		createDeck();
	}


	public ArrayList<Card> getDeck() {
		return deck;
	}
	/**
	 * createDeck
	 * Reads the file given by the user as a parameter and reads it,
	 * storing the cards on an ArrayList, thus creating a deck to be used in the game.
	 * @param cardsFile
	 * @return
	 */
	public ArrayList<Card> createDeck(File cardsFile) {
		String data;
		Card card;
		try {
			File file = cardsFile;

			Scanner fileReader = new Scanner(file);

			while (fileReader.hasNextLine()) {
				data = fileReader.nextLine();

				for (int i = 0; i < data.length(); i++) {
					if (data.charAt(i) != ' ' && data.charAt(i) != '\n') {
						card = new Card(data.charAt(i), data.charAt(i + 1));
						deck.add(card);
						i++;
					}
				}
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file with cards.");
			e.printStackTrace();
		}

		return deck;

	}
	
	/**
	 * createDeck
	 * Reads the file given by the user as a parameter and reads it,
	 * storing the cards on an ArrayList, thus creating a deck to be used in the game.
	 * @return
	 */
	public ArrayList<Card> createDeck() {
		String data;
		Card card;
		
		String cards = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH\n"
				+ "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS\n"
				+ "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD\n"
				+ "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC\n";

		Scanner fileReader = new Scanner(cards);

		while (fileReader.hasNextLine()) {
			data = fileReader.nextLine();

			for (int i = 0; i < data.length(); i++) {
				if (data.charAt(i) != ' ' && data.charAt(i) != '\n') {
					card = new Card(data.charAt(i), data.charAt(i + 1));
					deck.add(card);
					i++;
				}
			}
		}

		fileReader.close();
	
	
		return deck;

	}
	
	public void sortDeck() {
		Collections.shuffle(deck);
	}
	
	public Card getCardFromDeck() {
		if(deck.isEmpty()) {
			System.out.println("\nNo more cards in deck");
			System.exit(0);
		}
		
		Card card = deck.get(0);
		deck.remove(0);

		return card;
	}
	

}
