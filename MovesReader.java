package checkers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MovesReader {

	ArrayList<String> requestedMoves;


	public MovesReader(ArrayList<String> requestedMoves) {
		this.requestedMoves = requestedMoves;
		System.out.println("number of requested moves: " + this.requestedMoves.size());
		openCheckers();
	}
	
	public void openCheckers() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for(String move: requestedMoves) {
			moves.add(this.translateMove(move));
		}
		CheckersView checkersPanel = new CheckersView(moves);
	}
	private Move translateMove(String move) {
		String[] parts = move.split("-");
		int fromCoords = Integer.parseInt(parts[0]);
		int toCoords = Integer.parseInt(parts[1]);
		HashMap<Integer, String> coordMap = getCoords();
		String[] fromMove = coordMap.get(fromCoords).split("-");
		String[] toMove = coordMap.get(toCoords).split("-");
		int fromRow = Integer.parseInt(fromMove[0]);
		int fromCol = Integer.parseInt(fromMove[1]);
		int toRow = Integer.parseInt(toMove[0]);
		int toCol = Integer.parseInt(toMove[1]);
		return new Move(fromRow, fromCol, toRow, toCol);
	}
	
	private HashMap<Integer, String> getCoords() {
		int counter = 1;
		HashMap<Integer, String>  coordMap = new HashMap<>();
		for (int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(i % 2 == 0) {
					if(j % 2 == 0) {
						coordMap.put(counter, i + "-" + j);
						counter++;
					} else {
						coordMap.put(-1, i + "-" + j);
					}
				} else {
					if(j % 2 == 0) {
						coordMap.put(-1, i + "-" + j);
					} else {
						coordMap.put(counter, i +"-" + j);
						counter++;
					}
				}
			}
		}
		return coordMap;
	}

}
