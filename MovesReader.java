package checkers;

import java.io.File;
import java.util.ArrayList;
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
		int fromRow = fromCoords / 4;
		int fromCol = fromCoords % 2;
		int toRow = toCoords / 4;
		int toCol = toCoords % 2;
		return new Move(fromRow, fromCol, toRow, toCol);
	}

}
