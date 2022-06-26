package mode;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import game.Player;
import cards.Card;
import cards.Deck;

import variant.DoubleBonus;

public abstract class Mode {
	int credit;
	Player player;
	ArrayList<Card> hand;
	Deck deck;
	
	public Mode(Player newPlayer) {
		player = newPlayer;
		hand = new ArrayList<Card>();
		deck = new Deck();
		credit = 5;
	}


	public Mode(Player newPlayer, File cardsFile) {
		player = newPlayer;
		hand = new ArrayList<Card>();
		deck = new Deck(cardsFile);
		credit = 5;
	}

	public void bet() {
		System.out.println("\nBet: " + credit);
	}

	public void bet(int b) {
		if(b <= 5) {
		
		credit = b;
		
		System.out.println("\nBet: " + credit);
		}
		else System.out.println("\nb: Illegal ammount");
	}

	public void deal() {
		
		while(!hand.isEmpty()) {
				hand.remove(0);
		}
		
		for (int i = 0; i < 5; i++)
			hand.add(deck.getCardFromDeck());
		
		if(hand.size() != 5) {
			System.out.println("\nNot enough cards to make a full hand");
			System.exit(0);
		}
			
		player.playerHand.sort(hand);
		player.updatePlayerHand(hand);

		player.playerHand.printHand();
	}

	public void hold(ArrayList<Integer> remove) {
		int countCardsRemoved = 0;
		int removeCard = 0;
		
		while(!remove.isEmpty()) {
			removeCard = remove.get(remove.size()-1);
			remove.remove(remove.size()-1);
			
			hand.remove(removeCard);

			countCardsRemoved++;			
		}
		
		System.out.println("Removed " + countCardsRemoved + " cards");
		
		for(int i = 0; i < countCardsRemoved; i++) {
			hand.add(deck.getCardFromDeck());
		}
	
		player.playerHand.sort(hand);
	}

	public void credit() {
		System.out.println("\n" + "Player has " + player.getPlayerCredit() + " credits");
	}

	public void advice() {
		
	}

	public void statistics() {
		showStatistics();
	}
	
	public void getScore() {
		DoubleBonus doubleBonus = new DoubleBonus(player);
		
		int score = doubleBonus.calculateScore();
		
		if(score == 0) {
			player.updatePlayersCredit(-credit);
			
			System.out.println("\n" + "Player loses and their credit is " + player.getPlayerCredit());
			
			player.updateScoresStatistics(score, credit);

		}
		else {
			int winnings = doubleBonus.calculateRoundCredit(score, credit); 
			
			player.updatePlayersCredit(winnings - credit);
			
			System.out.println("\n" + "Player wins with " + player.getLastScore(score) + " and their credit is " + player.getPlayerCredit());
			
			player.updateScoresStatistics(score, winnings, credit);
		}
		
	}
	
	public void showStatistics(){
    	player.previousScores[11] = player.getRoundsPlayed();
    	player.previousScores[12] = player.getPlayerCredit();
    	
    	double percentagem =  (double) (player.winningBets) / (double)(player.getTotalBets());
    	percentagem *= 100;
    	DecimalFormat df = new DecimalFormat("#.#####");
    	
		System.out.println("\nHand" + "                    " + "Nb");
		System.out.println("------------------------------------");
		System.out.println("Jacks or Better" + "         " + player.previousScores[1]);
		System.out.println("Two Pair" + "                " + player.previousScores[2]);
		System.out.println("Three of a Kind" + "         " + player.previousScores[3]);
		System.out.println("Straight" + "                " + player.previousScores[4]);
		System.out.println("Flush" + "                   " + player.previousScores[5]);
		System.out.println("Full House" + "              " + player.previousScores[6]);
		System.out.println("Four of a Kind" + "          " + player.previousScores[7]);
		System.out.println("Straight Flush" + "          " + player.previousScores[8]);
		System.out.println("Royal Flush" + "             " + player.previousScores[9]);
		System.out.println("Other" + "                   " + player.previousScores[10]);
		System.out.println("------------------------------------");
		System.out.println("Total" + "                   " + player.previousScores[11]);
		System.out.println("------------------------------------");
		System.out.println("Credit" + "                " + player.previousScores[12] + " (" + df.format(percentagem)+ "%)\n" );
		 
	}

}
