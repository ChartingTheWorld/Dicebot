package com.chamelaeon.dicebot.rollers;

import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.chamelaeon.dicebot.InputException;
import com.chamelaeon.dicebot.dice.Statistics;
import com.chamelaeon.dicebot.personality.BasicPersonality;
import com.chamelaeon.dicebot.personality.Personality;
import com.chamelaeon.dicebot.rollers.Roller.L5RRoller;
import com.google.common.io.Closeables;

/**
 * Driver program for testing L5R rolls and analyzing their statistics.
 * @author Chamelaeon
 */
public class L5RTester {

	/**
	 * Main.
	 * @param args The arguments.
	 */
	public static void main(String[] args) throws Exception {
	    Personality personality = new BasicPersonality("");
	    
		final Statistics statistics = new Statistics();
		final AtomicBoolean running = new AtomicBoolean(true);
		final L5RRoller roller = new L5RRoller(personality);
		final int rolled = 10;
		final int kept = 9;

		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (running.get()) {
						String[] parts = new String[] {"", "1", "" + rolled, "" + kept, null, null, null};
						roller.assembleRoll(parts, "", statistics);
					}
				} catch (InputException ie) {
					throw new RuntimeException(ie);
				}
			}
		});
		
		Scanner scanner= null;
		try {
			scanner = new Scanner(System.in);
			while(true) {
				String line = scanner.nextLine();
				
				if ("stop".equals(line)) {
					running.set(false);
					long diceCount = statistics.getDice();
					System.out.println("Rolled " + diceCount + " " + rolled + "k" + kept + " for a mean average of " 
							+ statistics.getAverage(rolled + "-" + kept) + ".");
					System.exit(0);
				}
				if ("stats".equals(line)) {
					long diceCount = statistics.getDice();
					System.out.println("Rolled " + diceCount + " " + rolled + "k" + kept + " for a mean average of " 
							+ statistics.getAverage(rolled + "-" + kept) + ".");
				}
			}
		} finally {
			Closeables.closeQuietly(scanner);
		}
	}

}