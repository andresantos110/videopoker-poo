package cards;

import java.util.ArrayList;

public class Hand {
	ArrayList<Card> hand;

	public Hand() {
		hand = new ArrayList<Card>();
	}

	public void updateHand(ArrayList<Card> playersHand) {
		hand = playersHand;
	}
	
	public ArrayList<Card> getPlayerHand() {
		return hand;
	}

	/*
	 * public String toString() { return hand.get(0) + "" + hand.get(1) + " " +
	 * hand.get(2) + " " + hand.get(3) + " " + hand.get(4) + ""; }
	 */
	
	public void printHand() {
		System.out.println("Player Hand: " + hand.get(0) + "" + hand.get(1) + "" + hand.get(2) + "" + hand.get(3) + "" + hand.get(4));
	}
	
	/**
	 * sort
	 * Used to sort the player's hand. Takes the value of every card, places it in an array,
	 * sorts said array, then reinserts the cards into the ArrayList given.
	 * @param newHand
	 * @return
	 */
	public ArrayList<Card> sort(ArrayList<Card> newHand) {
		Card card1 = newHand.get(0);
		newHand.remove(0);
		Card card2 = newHand.get(0);
		newHand.remove(0);
		Card card3 = newHand.get(0);
		newHand.remove(0);
		Card card4 = newHand.get(0);
		newHand.remove(0);
		Card card5 = newHand.get(0);
		newHand.remove(0);

		int[] cardsInt = { card1.rank_int, card2.rank_int, card3.rank_int, card4.rank_int, card5.rank_int};

		java.util.Arrays.sort(cardsInt);

		for (int i = 0; i < 4; i++) {
			if (cardsInt[i] == cardsInt[i + 1]) {
				cardsInt[i + 1] = 0;
			}
		}
		
		boolean addedCard1 = false, addedCard2 = false, addedCard3 = false, addedCard4 = false, addedCard5 = false;

		for (int i = 0; i < 5; i++) {
			if (card1.rank_int == cardsInt[i] && !addedCard1) {
				newHand.add(card1);
				addedCard1 = true;
			}
			if (card2.rank_int == cardsInt[i] && !addedCard2) {
				newHand.add(card2);
				addedCard2 = true;
			}
			if (card3.rank_int == cardsInt[i] && !addedCard3) {
				newHand.add(card3);
				addedCard3 = true;
			}
			if (card4.rank_int == cardsInt[i] && !addedCard4) {
				newHand.add(card4);
				addedCard4 = true;
			}
			if (card5.rank_int == cardsInt[i] && !addedCard5) {
				newHand.add(card5);
				addedCard5 = true;
			}
		}

		return newHand;

	}

}
