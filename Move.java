package checkers;

public class Move {
	
	int fromRow, fromCol;
	int toRow, toCol;
	
	public Move(int frow, int fcol, int trow, int tcol) {
		this.fromRow = frow;
		this.fromCol = fcol;
		this.toRow = trow;
		this.toCol = tcol;
		//System.out.println(this.toString());
	}
	
	public String toString() {
		return "(" + this.fromRow + ", " + this.fromCol + ") -> (" + this.toRow + ", " + this.toCol + ")";
	}
	
	public boolean isJumpMove() {
		if (fromRow - toRow == -2 || fromRow - toRow == 2) {
			return true;
		}
		return false;
	}
}
