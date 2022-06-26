package mode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cards.Card;
import game.Player;

public class Debug extends Mode {
	Player player;

	public Debug(Player newPlayer, File cardsFile) {
		super(newPlayer, cardsFile);
		player = newPlayer;
	}
	
	public void debugMode(File cmdFile) {
		Scanner cmdReader;
		String next;
		ArrayList<Integer> remove = new ArrayList<Integer>();
		
		try {
			cmdReader = new Scanner(cmdFile);
			
			while(cmdReader.hasNext()) {
				
				next = cmdReader.next();
	
				if (next.equals("b")) {
					if(cmdReader.hasNextInt()) {
						bet(Integer.parseInt(cmdReader.next()));
					}
					else {
						bet();
					}
				}
				
				if (next.equals("d")) {
					deal();
					
					next = cmdReader.next();
					
					if (next.equals("a")) {
						advice();
					}
					
					if (next.equals("h")) {
						while(cmdReader.hasNextInt()) {
							int removeNb = Integer.parseInt(cmdReader.next());
							
							if(removeNb > 5 || removeNb <= 0)
								System.out.println("h: invalid position");
							else {
								remove.add(removeNb);
							}
						}
						if(remove.isEmpty()) {
							remove.add(1);
							remove.add(2);
							remove.add(3);
							remove.add(4);
							remove.add(5);
						}
						
						hold(remove);
						player.playerHand.printHand();
					}
									
					getScore();
				}
				
				if (next.equals("$")) {
					credit();
				}
				
				if( next.equals("s")) {
					statistics();
				}
			}
			
			cmdReader.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void advice() {
		Advice adviceDebug = new Advice(player);
		ArrayList<Card> hand = player.getPlayerHand();
		ArrayList<Card> toRemove = new ArrayList<Card>();
		Card remove;
		
		toRemove = adviceDebug.advicePlayer();
		
		if(toRemove != null) {
			System.out.print("Remove: ");
		
			while(!toRemove.isEmpty()) {
				remove = toRemove.get(0);
				toRemove.remove(0);
				
				for(Card card:hand) {
					if(card.rank == remove.rank && card.suit == remove.suit) {
						System.out.print(" " + card.rank + "" + card.suit);
					}
				}
			}
			System.out.println();
		}
		else {
			System.out.println("\nKeep all");
		}
	}

}
