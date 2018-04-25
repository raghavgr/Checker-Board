package checkers;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		File file = new File("/Users/grandhis/eclipse-workspace/Assignment-1/src/checkers/moves.txt");
		Scanner sc = new Scanner(file);
		//CheckersModel test = new CheckersModel();
		ArrayList<String> moves = new ArrayList<String>();
	    while (sc.hasNextLine()) {
	    	String move = sc.nextLine();
	    	//System.out.println(move);
	    	//test.translateMove(move);
	    	moves.add(move);
	    }

	    MovesReader test = new MovesReader(moves);

	}
}
