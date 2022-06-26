package mode;

import game.Player;

import java.io.File;
import java.util.ArrayList;
import cards.Card;

public class Simulation extends Mode {
	Player player;
	File cardsFile;

	public Simulation(Player newPlayer) {
		super(newPlayer);
		player = newPlayer;
		
	}
	
	public void simulationMode(int nbDeals,int betValue) {
		for (int i = 0; i < nbDeals; i++) {
			deck.sortDeck();
			
			bet(betValue);
			deal();
			advice();
			getScore();

			updateDeck();
		}
	}
	
	public void updateDeck() {
		ArrayList<Card> newDeck = deck.getDeck();
		while(!newDeck.isEmpty()) {
			newDeck.remove(0);
		}
		
		deck.createDeck();
			
			
	}
	
	@Override
	public void advice() {
		ArrayList<Card> toRemove = new ArrayList<Card>();
		Advice adviceSim= new Advice(player);
		Card remove;
		ArrayList<Integer> holdRemove = new ArrayList<Integer>();
		
		toRemove = adviceSim.advicePlayer();
		
		if(toRemove != null) {
			while(!toRemove.isEmpty()) { 
				remove = toRemove.get(0);
				toRemove.remove(0);
				
				for(int i = 0; i < 5; i++) {
					if(hand.get(i).rank == remove.rank && hand.get(i).suit == remove.suit) {
						holdRemove.add(i);
						break;
					}
				}
			}
			hold(holdRemove);
		}
	}	
}
