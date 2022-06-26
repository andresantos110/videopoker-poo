package cards;

public class Card {
	public char rank;
	public int rank_int;
	public char suit;
	/**
	 * Card
	 * The class card is used to represent individual cards, and it is composed by it's rank and suit,
	 * obtained directly from the file, and it's rank represented as an integer.
	 * @param r
	 * @param s
	 */
	public Card(char r, char s) {
		rank = r;
		suit = s;
		rank_int = convertToInt(r);
	}
	/**
	 * toString
	 * takes the rank and the suit of a card and places it into a string, for it to be printed.
	 */
	public String toString() {
		return rank + "" + suit + " ";
	}
	/**
	 * convertToInt
	 * Takes a card that is not represented by a number and converts it's value to an integer,
	 * in order to facilitate calculating hands.
	 * @param r
	 * @return
	 */
	private int convertToInt(char r) {
		if (r >= '2' && r <= '9') {
			return Character.getNumericValue(r);
		} else if (r == 'A')
			return 14;
		else if (r == 'K')
			return 13;
		else if (r == 'Q')
			return 12;
		else if (r == 'J')
			return 11;
		else if (r == 'T')
			return 10;

		return -1;
	}
}