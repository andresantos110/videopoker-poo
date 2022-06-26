package mode;

import java.util.ArrayList;

import cards.Card;
import game.Player;
import variant.DoubleBonus;

public class Advice {
	Player player;
	ArrayList<Card> hand;
	ArrayList<Card> remove;
	DoubleBonus db;
	
	public Advice(Player player){
		this.player = player;
		remove = new ArrayList<Card>();
		db = new DoubleBonus(player);
	}

	public ArrayList<Card> advicePlayer() {
		hand = player.getPlayerHand();
		Card auxCard;
		ArrayList<Card> arrayRemove = new ArrayList<Card>();

		if (checkStraightFlush() || checkRoyalFlush() || checkFour()) { 
			return null;
		}
		
		auxCard = oneCardForRoyalFlush();

		if (auxCard != null) {
			remove.add(auxCard);
			
			return remove;
		}

		if (db.countRankFrequencies('A') == 3) { //Three Aces
			for(Card card : hand) {
				if(card.rank != 'A') {
					remove.add(card);
				}
			}
			return remove;
		}

		if (db.isStraight() || db.isFlush() || (db.isPair() && db.isThreeOfKind())) { 
			return null;
		}

		if (db.isThreeOfKind()) { //Three of a kind (Except Aces)
			for(Card card : hand) {
				if(db.countRankFrequencies(card.rank) != 3) {
					remove.add(card);
				}
			}
			return remove;
		}
		
		auxCard = oneCardForStraightFlush();
		
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}

		if (db.isTwoPairs()) { //Two Pair
			for(Card card:hand) {
				if (db.countRankFrequencies(card.rank) == 1)
					remove.add(card);
			}
			
			return remove;
			
		}

		arrayRemove = checkHighPair();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			remove.add(arrayRemove.get(2));
			
			return remove;
		}
		
		auxCard = oneCardForFlush();
		
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}
		
		arrayRemove = twoCardsForRoyalFlush();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			
			return remove;
		}

		auxCard = outsideStraight();
		
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}
		
		arrayRemove = checkLowPair();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			remove.add(arrayRemove.get(2));
			
			return remove;
		}
			
		//AKQJ Unsuited
		if(db.countRankFrequencies('A') == 1 && db.countRankFrequencies('K') == 1 && db.countRankFrequencies('Q') == 1 && db.countRankFrequencies('J') == 1) { 
			for(Card card:hand) {
				if(card.rank != 'A' ||card.rank != 'K' ||card.rank != 'Q' ||card.rank != 'J') {
					remove.add(card);
				}
			}
			return remove;
		}
		
		arrayRemove = twoToStraightFlushTypeI();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			return remove;
		}
		
		// 4 to inside straight with 3 high cards
		auxCard = insideStraightThreeHighCards();
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}
		
		//QJ Suited
		if(db.countRankFrequencies('Q') >= 1 && db.countRankFrequencies('J') >= 1) { 
			char suit = ' ';
			
			for(Card card: hand) {
				if(card.rank_int == 12) {
					suit = card.suit;
				}
			}
			
			for(Card card: hand) {
				if(card.rank_int == 11) {
					if(card.suit != suit) {
						suit = ' ';
					}
				}
			}
			
			if(suit != ' ') {
				for(Card card: hand) {
					if(card.rank != 'Q' && card.rank != 'J') {
						remove.add(card);
					}
				}
				
				return remove;	
			}
			
		}
		
		arrayRemove = twoForFlushTwoHighCards();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			return remove;
		}
		
		
		arrayRemove = twoSuitedHighCards();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			remove.add(arrayRemove.get(2));
			return remove;
		}
		
		
		auxCard = insideStraightTwoHighCards();
		
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}
		
		
		arrayRemove = twoToStraightFlushTypeII();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			return remove;
		}
		
		auxCard = insideStraightOneHighCards();
				
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}
		
		//KQJ Unsuited
		if(db.countRankFrequencies('K') == 1 && db.countRankFrequencies('Q') == 1 && db.countRankFrequencies('J') == 1) {
			for(Card card : hand) {
				if(card.rank_int < 11)
					remove.add(card);
			}
			
			return remove;
		}
		
		//JT Suited
		if(db.countRankFrequencies('J') >= 1 && db.countRankFrequencies('T') >= 1) {
			char suit = ' ';
			
			for(int i = 0; i < 4; i++) {
				if(hand.get(i).rank_int == 10 && hand.get(i+1).rank_int == 11) {
					if(hand.get(i).suit == hand.get(i+1).suit) {
						suit = hand.get(i).suit;
					}
				}
			}
			
			
			if(suit != ' ') {
				for(Card card : hand) {
					if(card.rank_int != 10 && card.rank_int != 11)
						remove.add(card);
				}
				
				return remove;
			}	
			
		}
		
		//QJ Unsuited
		if(db.countRankFrequencies('Q') == 1 && db.countRankFrequencies('J') == 1) {
			for(Card card : hand) {
				if(card.rank_int != 12 && card.rank_int != 11)
					remove.add(card);
			}
			
			return remove;
		}
		
		
		arrayRemove = twoForFlushOneHighCards();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			return remove;
		}	
		
		
		//QT Suited
		if(db.countRankFrequencies('Q') == 1 && db.countRankFrequencies('T') == 1) {
			char suit = ' ';

			for(int i = 0; i < 4; i++) {
				if(hand.get(i).rank_int == 10 && hand.get(i+1).rank_int == 12) {
					if(hand.get(i).suit == hand.get(i+1).suit) {
						suit = hand.get(i).suit;
					}
				}
			}
			
			if(suit != ' ') {
				for(Card card : hand) {
					if(card.rank_int != 10 && card.rank_int != 12)
						remove.add(card);
				}
				
				return remove;
			}
		}
		
		
		arrayRemove = twoToStraightFlushTypeIII();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			return remove;
		}
		
		//KQ, KJ Unsuited
		if((db.countRankFrequencies('K') == 1 && db.countRankFrequencies('Q') == 1) || (db.countRankFrequencies('K') == 1 && db.countRankFrequencies('J') == 1)) {
			for(Card card:hand) {
				if(card.rank_int < 11 || card.rank_int == 14) {
					remove.add(card);
				}
			}
			return remove;
		}
		
		//Ace
		if(db.countRankFrequencies('A') == 1) {
			remove.add(hand.get(0));
			remove.add(hand.get(1));
			remove.add(hand.get(2));
			remove.add(hand.get(3));
			
			return remove;
		}
		
		//KT Suited
		if(db.countRankFrequencies('K') == 1 && db.countRankFrequencies('T') == 1) {
			char suit = ' ';

			for(int i = 0; i < 4; i++) {
				if(hand.get(i).rank_int == 10 && hand.get(i+1).rank_int == 13) {
					if(hand.get(i).suit == hand.get(i+1).suit) {
						suit = hand.get(i).suit;
					}
				}
			}
			
			if(suit != ' ') {
				for(Card card : hand) {
					if(card.rank_int != 10 && card.rank_int != 13)
						remove.add(card);
				}
				
				return remove;
			}
		}
		
		if(db.countRankFrequencies('J') == 1 || db.countRankFrequencies('Q') == 1 || db.countRankFrequencies('K') == 1) {
			remove.add(hand.get(0));
			remove.add(hand.get(1));
			remove.add(hand.get(2));
			remove.add(hand.get(3));
			
			return remove;
		}
		
		auxCard = insideStraightNoHighCards();
		
		if(auxCard != null) {
			remove.add(auxCard);
			return remove;
		}
		
		arrayRemove = twoForFlushNoHighCards();
		
		if(arrayRemove != null) {
			remove.add(arrayRemove.get(0));
			remove.add(arrayRemove.get(1));
			return remove;
		}
		
		for(Card card:hand)
			remove.add(card);
		
		return remove;

	}
	
	private ArrayList<Card> checkHighPair() {
		ArrayList<Card> toRemove = new ArrayList<Card>();
		int cardRank = 0;
		
		for(Card card:hand) {
			if(db.countRankFrequencies(card.rank) == 2 && card.rank_int > 10) {
				cardRank = card.rank_int;
			}
		}
		
		if(cardRank != 0) {
			for(Card card : hand) {
				if(card.rank_int != cardRank)
					toRemove.add(card);
			}
		
			
			return toRemove;
		}
			
		return null;
	}
	
	private ArrayList<Card> checkLowPair() {
		ArrayList<Card> toRemove = new ArrayList<Card>();
		int cardRank = 0;
		
		for(Card card:hand) {
			if(db.countRankFrequencies(card.rank) == 2)
				cardRank = card.rank_int;
		}
		
		if(cardRank != 0) {
			for(Card card : hand) {
				if(card.rank_int != cardRank)
					toRemove.add(card);
			}
		
			
			return toRemove;
		}
			
		return null;
	}
	
	private boolean checkStraightFlush() {
		if (db.isStraight() && db.isFlush())
			return true;
		else return false;
	}

	private boolean checkRoyalFlush() {
		if (checkStraightFlush() && hand.get(4).rank_int == 14)
			return true;
		else return false;
	}
	
	private boolean checkFour() {
		if (db.countRankFrequencies(hand.get(2).rank) == 4)
			return true;
		
		else return false;
	}

	private Card oneCardForRoyalFlush() {
		int count = 0;
		char suit = ' ';

		if (checkRoyalFlush()) {
			return null;
		}
		
		for (Card card : hand)
			if (card.rank == 'A' || card.rank == 'J' || card.rank == 'Q' || card.rank == 'K' || card.rank == 'T')
				count++;

		if (db.countSuitFrequencies('H') >= 4)
			suit = 'H';
		else if (db.countSuitFrequencies('S') >= 4)
			suit = 'S';
		else if (db.countSuitFrequencies('C') >= 4)
			suit = 'C';
		else if (db.countSuitFrequencies('D') >= 4)
			suit = 'D';
		
		if (count >= 4 && suit != ' ') {
			// if all the cards have the same suit, the missing card is the one different
			if (db.isFlush() == true) {

				for (Card card : hand) {
					if (card.rank != 'A' || card.rank != 'T' || card.rank != 'Q' || card.rank != 'K' || card.rank != 'T')
						return card;
				}
			}

			else { // if there's a card with the same rank, that's the one
				
				for(Card card:hand) {
					if(card.suit != suit)
						return card;
				}
			}
		}
		
		return null;
	}
	
	private Card oneCardForFlush() {
		int count = 0;
		Card auxCard = null;
		
		for(Card card: hand) {
			if(db.countSuitFrequencies(card.suit) != 4) {
				auxCard = card;
				count++;
			}
		}
		
		if(count == 1)
			return auxCard;
		
		return null;
	}

	private Card oneCardForStraightFlush() {
		char suit = ' ';
		
		for(Card auxCard:hand) {
			if(db.countSuitFrequencies(auxCard.suit) == 4) {
				suit = auxCard.suit;
			}
		}
		
		if(suit != ' ') {
			if(hand.get(0).suit != suit) {
				return hand.get(0);
			}
			if(hand.get(4).suit != suit) {
				return hand.get(4);
			}
	
			if(hand.get(1).rank_int == hand.get(4).rank_int - 3 ) {
				return hand.get(0);
			}
			else if(hand.get(0).rank_int == hand.get(3).rank_int - 3) {
				if(hand.get(2).rank_int == hand.get(4).rank_int - 3)
					return hand.get(0);
				else return hand.get(4);
			}
		}
	
		return null;
	
	}
	
	private ArrayList<Card> twoCardsForRoyalFlush() {
		int countRank = 0;
		char suit = ' ';
		ArrayList<Card> toRemove = new ArrayList<Card>();

		for(Card card : hand) {
			if(db.countSuitFrequencies(card.suit) >= 3 ) {
				suit = card.suit;
			}
		}
		
		if(suit != ' ') {
			for(Card card : hand) {
				if((card.rank == 'A' || card.rank == 'K' || card.rank == 'Q' || card.rank == 'T' || card.rank == 'J') && card.suit == suit) {
					countRank++;
				}
			}
			
			if(countRank >= 3) {
				for(Card card : hand) {
					if(card.rank != 'A' && card.rank != 'K' && card.rank != 'Q' && card.rank != 'T' && card.rank != 'J') {
						toRemove.add(card);
					}
					else if(card.suit != suit) {
						toRemove.add(card);
					}
				}
				return toRemove;
			}
		}
	
		return null;
	}
	
	private Card outsideStraight() {
		int count = 0;

		for(int i = 1; i < 4; i++) {
			if(hand.get(0).rank_int == hand.get(i).rank_int - i)
				count++;
			else
				break;
		}

		if(count == 3)
			return hand.get(4);
		
		count = 0;
		int j = 1;
		for(int i = 3; i >= 1; i--) {
			if(hand.get(4).rank_int == hand.get(i).rank_int + j) {
				count++;
			}
			else
				break;
			
			j++;
		}

		if(count == 3)
			return hand.get(0);
		
		
		return null;
	}
	
	private Card insideStraight() {		
		if(hand.get(1).rank_int == hand.get(4).rank_int - 4) {
			return hand.get(0);
		}
		else if(hand.get(0).rank_int == hand.get(3).rank_int - 4) {
			return hand.get(4);
		}
		else if(hand.get(0).rank_int == hand.get(1).rank_int - 1 && hand.get(2).rank_int == hand.get(3).rank_int -1 && hand.get(1).rank_int == hand.get(2).rank_int-2){ 
			if(hand.get(3).rank_int == hand.get(4).rank_int)
				return hand.get(0);
			else return hand.get(4);
		}
		else if(hand.get(1).rank_int == hand.get(2).rank_int - 1 && hand.get(3).rank_int == hand.get(4).rank_int -1 && hand.get(2).rank_int == hand.get(3).rank_int-2){ 
			return hand.get(0);
		}
		else if(hand.get(0).rank == 'J' && hand.get(1).rank == 'Q' && hand.get(2).rank == 'K' && hand.get(3).rank == 'A')
			return hand.get(0);
		else if(hand.get(0).rank == '2' && hand.get(1).rank == '3' && hand.get(2).rank == '4' && hand.get(4).rank == 'A')
			return hand.get(4);
		else
			return null;
	}
	
	private ArrayList<Card> twoToStraightFlushTypeI(){
		ArrayList<Card> toRemove = new ArrayList<Card>();
		ArrayList<Card> sameSuit = new ArrayList<Card>();
		int countNoGap = 0, countOneGap = 0, countTwoGap = 0; 
		int countHighCards = 0;
		
		for(Card card:hand) {
			if(db.countSuitFrequencies(card.suit) >= 3 && card.rank_int >= 6) {
				sameSuit.add(card);
			}
		}
		
		
		if(sameSuit.size() >= 3) {
			for(int i = 0; i < sameSuit.size()-1; i++) {
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-1) {
					countNoGap++;
				}
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-2) {
					countOneGap++;
				}
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-3) {
					countTwoGap++;
				}
			}
			
			if(countNoGap >= 3 && sameSuit.get(1).rank_int >= 8) { // 8 9 T 
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
						else if(card.rank_int < 8)
							toRemove.add(card);
					}
				}
				
				return toRemove;
			}
			
			if(countOneGap == 1 && sameSuit.get(1).rank_int >= 7) { // 7 9 T
				for(Card card:sameSuit) {
					if(card.rank_int > 10) {
						countHighCards++;
					}
				}
				
				if(countHighCards <= 1)
					return null;
				
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
						else if(card.rank_int < 7)
							toRemove.add(card);
					}
				}
				
				return toRemove;
			}
			
			if(countTwoGap == 1 && sameSuit.get(1).rank_int >= 6) { // 6 9 J
				for(Card card:sameSuit) {
					if(card.rank_int > 10) {
						countHighCards++;
					}
				}
				
				if(countHighCards < 2)
					return null;
				
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
						else if(card.rank_int < 6)
							toRemove.add(card);
					}
				}
				return toRemove;
			}
			
			if(hand.get(0).rank_int == 3 && hand.get(1).rank_int == 4 && hand.get(2).rank_int == 5) {
				toRemove.add(hand.get(3));
				toRemove.add(hand.get(4));
				
				return toRemove;
			}
			
		}
		return null;
	}
	
	private ArrayList<Card> twoToStraightFlushTypeII(){ // 1 gap or 2 gap with High Card
		ArrayList<Card> toRemove = new ArrayList<Card>();
		ArrayList<Card> sameSuit = new ArrayList<Card>();
		int countNoGap = 0, countOneGap = 0, countTwoGap = 0;
		
		for(Card card:hand) {
			if(db.countSuitFrequencies(card.suit) >= 3) {
				sameSuit.add(card);
			}
		}
		
		if(sameSuit.size() >= 3) {
			if(sameSuit.get(2).rank_int - sameSuit.get(0).rank_int >= 5)
				return null;
			
			for(int i = 0; i < sameSuit.size()-1; i++) {
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-1) {
					countNoGap++;
				}
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-2) {
					countOneGap++;
				}
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-3) {
					countTwoGap++;
				}
			}
			
			if(countNoGap >= 3) { // 4 5 6 
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
					}
				}
				
				return toRemove;
			}
			
			if(countOneGap == 1 && countTwoGap != 1) { // 7 9 T
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
					}
				}
				
				return toRemove;
			}
			
			if(countTwoGap == 1 && countOneGap != 1 && sameSuit.get(1).rank_int >= 6 && sameSuit.get(2).rank_int > 10) { // 6 9 J
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
						else if(card.rank_int < 6)
							toRemove.add(card);
					}
				}
				return toRemove;
			}
			if(hand.get(0).rank_int == 2 && hand.get(1).rank_int == 3 && hand.get(2).rank_int == 4) {
				toRemove.add(hand.get(3));
				toRemove.add(hand.get(4));
				
				return toRemove;
			}
			if(hand.get(4).rank_int == 14 && hand.get(0).rank_int == 2 && hand.get(1).rank_int == 3) {
				toRemove.add(hand.get(2));
				toRemove.add(hand.get(3));
				
				return toRemove;
			}
		}
		
		return null;
	}
	
	private ArrayList<Card> twoToStraightFlushTypeIII(){ // two gaps -> 2 or 3 High Cards
		ArrayList<Card> toRemove = new ArrayList<Card>();
		ArrayList<Card> sameSuit = new ArrayList<Card>();
		int countOneGap = 0, countTwoGap = 0;
		
		for(Card card:hand) {
			if(db.countSuitFrequencies(card.suit) >= 3) {
				sameSuit.add(card);
			}
		}
		
		if(sameSuit.size() >= 3) {
			if((sameSuit.get(2).rank_int - sameSuit.get(0).rank_int) >= 5)
				return null;
			
			for(int i = 0; i < sameSuit.size()-1; i++) {
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-2) {
					countOneGap++;
				}
				if(sameSuit.get(i).rank_int == sameSuit.get(i+1).rank_int-3) {
					countTwoGap++;
				}
			}
			

			
			if(countOneGap == 2) { // 4 6 8
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
					}
				}
				return toRemove;
			}
					
			if(countTwoGap == 1 && sameSuit.get(2).rank_int < 11) { // 3 6 7
				for(Card sameSuitCard:sameSuit) {
					for(Card card:hand) {
						if(card.rank != sameSuitCard.rank && card.suit != sameSuitCard.suit)
							toRemove.add(card);
					}
				}
				return toRemove;
			}
		}
		return null;
	}
	
	
	
	private ArrayList<Card> twoForFlushTwoHighCards(){
		ArrayList<Card> toRemove = new ArrayList<Card>();
		char suit = ' ';
		int count = 0;
		
		for(Card card:hand) {
			if(db.countSuitFrequencies(card.suit) >= 3 && card.rank_int > 10) {
				suit = card.suit;
				count++;
			}
		}
		
		
		if(count == 2 && suit != ' ') {
			for(Card card:hand) {
				if(card.suit != suit) {
					toRemove.add(card);
				}
			}
			return toRemove;
		}
		
		return null;
	}
	
	private ArrayList<Card> twoSuitedHighCards(){
		char suit = ' ';
		int count = 0;
		ArrayList<Card> toRemove = new ArrayList<Card>();
		
		if(hand.get(3).suit == hand.get(4).suit)
			suit = hand.get(3).suit;
		else if(hand.get(2).suit == hand.get(4).suit)
			suit = hand.get(4).suit;
		else if(hand.get(2).suit == hand.get(3).suit)
			suit = hand.get(2).suit;
		
		for(Card card:hand) {
			if(card.suit == suit && card.rank_int > 10) {
				count++;
			}
		}
		if(count == 2) {
			for(Card card:hand) {
				if(card.suit != suit || card.rank_int < 10)
					toRemove.add(card);
			}
			return toRemove;
		}
		return null;
	}
	
	private Card insideStraightThreeHighCards() {
		Card card = insideStraight();
		int count = 0;
		
		if(card != null) {
			for(Card auxCard : hand) {
				if(auxCard.rank_int > 10 && (auxCard.rank != card.rank || auxCard.suit != card.suit)) {
					count++;
				}
			}
			
			if(count == 3) {
				return card;
			}
		}
		
		return null;
	}
	
	private Card insideStraightTwoHighCards() {
		Card card = insideStraight();
		int count = 0;
		
		if(card != null) {
			for(Card auxCard : hand) {
				if(auxCard.rank_int > 10 && (auxCard.rank != card.rank || auxCard.suit != card.suit)) {
					count++;
				}
			}
			
			if(count == 2) {
				return card;
			}
		}
		
		return null;
	}
	
	private Card insideStraightOneHighCards() {
		Card card = insideStraight();
		int count = 0;

		if(card != null) {
			for(Card auxCard : hand) {
				if(auxCard.rank_int > 10 && (auxCard.rank != card.rank || auxCard.suit != card.suit))
					count++;
			}
			
			if(count == 1) {
				return card;
			}
		}
		
		return null;
	}
	
	private ArrayList<Card> twoForFlushOneHighCards(){
		ArrayList<Card> toRemove = new ArrayList<Card>();
		char suit = ' ';
		int count = 0;
		
		for(Card card:hand) {
			if(db.countSuitFrequencies(card.suit) == 3 && card.rank_int > 10) {
				suit = card.suit;
				count++;
			}
		}
		
		if(count == 1 && suit != ' ') {
			for(Card card:hand) {
				if(card.suit != suit) {
					toRemove.add(card);
				}
			}
			return toRemove;
		}
		
		return null;
	}
	
	private Card insideStraightNoHighCards() {
		Card card = insideStraight();
		int count = 0;
		
		if(card != null) {
			for(Card auxCard : hand) {
				if(auxCard.rank_int > 10 && db.countSuitFrequencies(card.suit) == 4 && (auxCard.rank != card.rank && auxCard.suit != card.suit))
					count++;
			}
			
			if(count == 0) {
				return card;
			}
		}
		
		return null;
	}
	
	private ArrayList<Card> twoForFlushNoHighCards(){
		ArrayList<Card> toRemove = new ArrayList<Card>();
		char suit = ' ';
		int count = 0;
		
		for(Card card:hand) {
			if(db.countSuitFrequencies(card.suit) == 3) {
				suit = card.suit;
			}
			if(card.rank_int > 10)
				count++;
		}
		
		if(count == 0 && suit != ' ') {
			for(Card card:hand) {
				if(card.suit != suit) {
					toRemove.add(card);
				}
			}
			return toRemove;
		}
		
		return null;
	}

}