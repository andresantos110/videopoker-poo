package variant;

import java.util.ArrayList;

import cards.Card;
import game.Player;

public class DoubleBonus implements Variant {
	ArrayList<Card> hand;
	Player player;

	public DoubleBonus(Player player) {
		this.player = player;
		hand = player.getPlayerHand();
	}
	/**
	 * isFlush
	 * checks if the player's hand is a flush. this is done by checking if there are 5 cards with the same suit.
	 * @return
	 */
	public boolean isFlush() {
		if (countSuitFrequencies('H') == 5 || countSuitFrequencies('S') == 5 || countSuitFrequencies('C') == 5 || countSuitFrequencies('D') == 5)
			return true;
		else
			return false;
	}
	
	/**
	 * isStraight
	 * checks if the player's hand is a straight. does this by going through the hand
	 * and checking if every card is next in order to the one before it. if this happens, there is a straight.
	 * @return
	 */
	public boolean isStraight() {
		int count_sequence = 0;
		
		for (int i = 1; i < 5; i++) {
			if (hand.get(0).rank_int == hand.get(i).rank_int - i)
				count_sequence++;
		}
		if (count_sequence == 4 || (hand.get(0).rank == '2' && hand.get(1).rank == '3' && hand.get(2).rank == '4' && hand.get(3).rank == '5' && hand.get(4).rank == 'A'))
			return true;
		else
			return false;
	}
	/**
	 * countRankFrequencies
	 * checks how many cards are the rank s, given as entry parameter.
	 * @param s
	 * @return
	 */
	public int countRankFrequencies(char s) {
		int rank_freq = 0;

		for (Card card : hand) {
			if (card.rank == s) {
				rank_freq++;
			}
		}

		return rank_freq;
	}
	
	/**
	 * countSuitFrequencies
	 * checks how many cards are the suit s, given as entry parameter.
	 * @param s
	 * @return
	 */
	public int countSuitFrequencies(char s) {
		int suit_freq = 0;

		for (Card card : hand) {
			if (card.suit == s)
				suit_freq++;
		}

		return suit_freq;
	}
	
	/**
	 * isPair
	 * checks if the player's hand is a pair. does this by going through the cards and checking if there are 2 of the same rank.
	 * @return
	 */
	public boolean isPair() {
		for(Card card:hand) {
			if(countRankFrequencies(card.rank) == 2) {
				return true;
			}
		}

		return false;
		
		/*
		
		if(hand.get(0).rank_int == hand.get(1).rank_int && hand.get(2).rank_int != hand.get(0).rank_int) {
			return true;
		}

		for(int i = 1; i < 3; i++) {
			if(hand.get(i).rank_int == hand.get(i+1).rank_int && hand.get(i).rank_int != hand.get(i+2).rank_int && hand.get(i-1).rank_int != hand.get(i).rank_int) {
				return true;
			}
		}
		
		if(hand.get(3).rank_int == hand.get(4).rank_int && hand.get(2).rank_int != hand.get(3).rank_int) {
			return true;
		}
		
		else return false;
		*/
		
	}
	/**
	 * isThreeOfKind
	 * checks if the player's hand is a three of a kind. does this by going through
	 * the cards and checking if there are 3 of the same rank.
	 * @return
	 */
	public boolean isThreeOfKind() {
		
		for(Card card:hand) {
			if(countRankFrequencies(card.rank) == 3) {
				return true;
			}
		}
		
		return false;
		
		
		/*
		for(int i = 0; i < 2; i++) {
			if(hand.get(i).rank_int == hand.get(i+1).rank_int && hand.get(i).rank_int == hand.get(i+2).rank_int && hand.get(i).rank_int != hand.get(i+3).rank_int ) {
				return true;
			}
		}
		
		if(hand.get(2).rank_int == hand.get(3).rank_int && hand.get(2).rank_int == hand.get(4).rank_int && hand.get(1).rank_int != hand.get(2).rank_int) {
			return true;
		}
		else return false;
		*/
	}
	/**
	 * isTwoPairss
	 * checks if the player's hand has two pair. if it does, the second and fourth positions always
	 * have a card that is part of it, so it is only necessary to check if there is another card of the same
	 * rank found in these positions.
	 * @return
	 */
	public boolean isTwoPairs() {
		if(countRankFrequencies(hand.get(1).rank) == 2 && countRankFrequencies(hand.get(3).rank) == 2) {
			return true;
		}
		else return false;
	}
	
	/**
	 * calculateScore
	 * checks the players hand and gives a score based on what it has. higher value hands are checked first.
	 */
	public int calculateScore() {
		int result = 0;

		if (isStraight() && isFlush() && hand.get(0).rank == 'T') { // royal flush
			result = 11;
		}

		else if (isFlush() && isStraight()) { // straight flush
			result = 10;
		}

		else if (countRankFrequencies('A') == 4) { //four A
			result = 9;
		}

		else if (countRankFrequencies('2') == 4 || countRankFrequencies('3') == 4 || countRankFrequencies('4') == 4) {
			// four 24
			result = 8;
		}

		else if (countRankFrequencies('5') == 4 || countRankFrequencies('6') == 4 || countRankFrequencies('7') == 4
				|| countRankFrequencies('8') == 4 || countRankFrequencies('9') == 4 || countRankFrequencies('T') == 4
				|| countRankFrequencies('J') == 4 || countRankFrequencies('Q') == 4 || countRankFrequencies('K') == 4) {
			// four 5k
			result = 7;
		}

		else if (isPair() && isThreeOfKind()) { //full house
			result = 6;
		}

		else if (isFlush() && !isStraight()) { // flush
			result = 5;
		}

		else if (!isFlush() && isStraight()) { // straight
			result = 4;
		}

		else if (isThreeOfKind()) { // three of a kind
			result = 3;
		}

		else if (isTwoPairs()) { // two pairs
			result = 2;
		}

		else if (isPair() && (countRankFrequencies('J') == 2 || countRankFrequencies('Q') == 2 || countRankFrequencies('K') == 2 || countRankFrequencies('A') == 2)) { // pair jacks or better
			result = 1;
		}
		else result = 0;
		
		return result;
	}

	/**
	 * calculateRoundCredit
	 * takes the score of the hand and the bet and calculates the return.
	 * @param handScore
	 * @param roundCredit
	 * @return
	 */
	public int calculateRoundCredit(int handScore, int roundCredit) {

		switch (handScore) {

		case 1: // N1 - Jacks or Better
			return roundCredit;
		case 2: // N2 - Two Pair
			return roundCredit;
		case 3: // N3 - Three of a kind
			return roundCredit * 3;
		case 4: // N4 - Straight
			return roundCredit * 5;
		case 5: // N5 - Flush
			return roundCredit * 7;
		case 6: // N6 - Full House
			return roundCredit * 10;
		case 7: // N7 - Four 5-K
			return roundCredit * 50;
		case 8: // N7 - Four 2-4
			return roundCredit * 80;
		case 9: // N7 - Four Aces
			return roundCredit * 160;
		case 10: // N8 - Straight Flush
			return roundCredit * 50;
		case 11: // N9 - Royal Flush
			if (roundCredit == 5) {
				return roundCredit * 800;
			} else {
				return roundCredit * 250;
			}

		}
       return 0; //N10 - Other

	}

}
