package game;

import java.io.File;
import mode.Debug;
import mode.Simulation;

public class Game {

	public static void main(String[] args) {
		Player player = new Player();
		
		System.out.println("Welcome to Video Poker");
		System.out.println("Variant in this machine: Double Bonus 10/7");

		String gameMode = args[0];
		String credit_string = args[1];

		try {
			player.credit = Integer.parseInt(credit_string);
			Player.initialCredit = Integer.parseInt(credit_string);
		}

		catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		if (gameMode.equals("-s")) {
			int betValue = 0;
			int nbDeals = 0;
			
			String betValue_string = args[2];
			String nbdeals_string = args[3];
			
			try {
				betValue = Integer.parseInt(betValue_string);
				nbDeals = Integer.parseInt(nbdeals_string);
			}

			catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
			
			Simulation simMode = new Simulation(player);

			simMode.simulationMode(nbDeals, betValue);
			
			simMode.showStatistics();

		}

		else if (gameMode.equals("-d")) {
			String cmdFile_s = args[2];
			String cardFile_s = args[3];

			File cmdFile = new File(cmdFile_s);
			File cardsFile = new File(cardFile_s);

			Debug debugMode = new Debug(player, cardsFile);
			
			debugMode.debugMode(cmdFile);
		}
		
		else {
			System.out.println("Invalid command");
		}
	}

}
