package game;

import java.util.ArrayList;

import cards.Card;
import cards.Hand;

public class Player {
	static int initialCredit;
	int credit;
	int nbRounds;
	public Hand playerHand;
	public int[] previousScores;
	int totalBets;
	public int winningBets;

	public Player() {
		playerHand = new Hand();
		nbRounds = 0;
		previousScores = new int[13];
	}
	/**
	 * updatePlayersCredit
	 * Adds the winnings or subtracts the losses, given as an entry parameter, to or for the player's credit.
	 * @param newCredit
	 */
	public void updatePlayersCredit(int newCredit) {
		credit += newCredit;
	}
	
	public void updatePlayerHand(ArrayList<Card> newHand) {
		playerHand.updateHand(newHand);
	}

	public int getPlayerCredit() {
		return credit;
	}
	
	public int getStartingCredit() {
		return initialCredit;
	}
	
	public int getTotalBets() {
		return totalBets;
	}

	public ArrayList<Card> getPlayerHand() {
		return playerHand.getPlayerHand();
	}

	public String getLastScore(int score) {
		if(score == 1)
			return "JACKS OR BETTER";
		else if(score == 2)
			return "TWO PAIR";
		else if(score == 3)
			return "THREE OF A KIND";
		else if(score == 4)
			return "STRAIGHT";
		else if(score == 5)
			return "FLUSH";
		else if(score == 6)
			return "FULL HOUSE";
		else if(score == 7 || score == 8 || score == 9)
			return "FOUR OF A KIND";
		else if(score == 10)
			return "STRAIGHT FLUSH";
		else if(score == 11)
			return "ROYAL FLUSH";
			
		return null;
	}
	
	public int getRoundsPlayed() {
		return nbRounds;
	}
	
	
	public void updateScoresStatistics(int score, int creditBetted) {
		previousScores[10]++;
		
		totalBets += creditBetted;
		nbRounds++;
	}
	
	/**
	 * updateScoresStatistics
	 * Takes score 8 and 9 and groups it with score 7. Score 11 and 10 become score 9 and 8 respectively. 
	 * After that updates the number of rounds, the total bets and the array with the sum of each group of scores
	 * @param score
	 * @param winningBet - has the round winning credits 
	 * @param creditbetted - has the round betted credits
	 */
	
	public void updateScoresStatistics(int score, int winningBet, int creditBetted) {
		int prevScore = score;
		
		if(score == 8 || score == 9)
			prevScore = 7;
		
		if(score > 9)
			prevScore -= 2; 
		
		winningBets += winningBet;
		previousScores[prevScore]++;
		
		
		totalBets += creditBetted;
		nbRounds++;
	}
}

