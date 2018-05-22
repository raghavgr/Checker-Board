package checkers;

import java.util.ArrayList;

public class CheckersModel {

	// Constants representing each player
	public static final int
			EMPTY = 0,
			BLACK = 1,
			BLACK_KING = 2,
			RED = 3,
			RED_KING = 4,
			BLOCKED = 5;
	
	// String constants
	public static final String[] players = 
			{"EMPTY", "BLACK", "BLACK_KING", "RED", "RED_KING", "BLOCKED"};

	public int[][] board; // Using a 2-dimensional board as model
		// board[row][column] will give the contents of that row and column


	/**
	 * Constructor for checkers model
	 */
	public CheckersModel() {
		board = new int[8][8];
		createBoard();
	}

	/**
	 * Setup the board
	 */
	public void createBoard() {

		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if(row % 2 == col % 2) {
					if (row < 3)
						board[row][col] = BLACK;
					else if(row >  4)
						board[row][col] = RED;
					else
						board[row][col] = EMPTY;

				} else {
					board[row][col] = EMPTY;
				}
			}
		}
	}

	/**
	 * State of board is updated with the move requested
	 * @param requestedMove - move read from input file
	 */
	public void updateBoardWith(Move requestedMove) {
		System.out.println("board is getting updated");
		board[requestedMove.toRow][requestedMove.toCol] = board[requestedMove.fromRow][requestedMove.fromCol];
		board[requestedMove.fromRow][requestedMove.fromCol] = EMPTY;
		System.out.println("Math.abs value of the move is: "+ Math.abs(requestedMove.toRow - requestedMove.fromRow));
		if(Math.abs(requestedMove.toRow - requestedMove.fromRow) == 2) {
			System.out.println("2 Row Cleanup in process");
			int removePieceRow = (requestedMove.fromRow + requestedMove.toRow) / 2;
			int removePieceCol = (requestedMove.fromCol + requestedMove.toCol) / 2;
			board[removePieceRow][removePieceCol] = EMPTY;
		}
		if(Math.abs(requestedMove.toRow - requestedMove.fromRow) == 4) {
			System.out.println("4 row cleanup in process");
			if(requestedMove.toRow > requestedMove.fromRow && requestedMove.toCol > requestedMove.fromCol) {
				board[requestedMove.fromRow + 3][requestedMove.fromCol + 3] = EMPTY;
				board[requestedMove.fromRow + 1][requestedMove.fromCol + 1] = EMPTY;
			}

			if(requestedMove.toRow < requestedMove.fromRow && requestedMove.toCol > requestedMove.fromRow) {
				board[requestedMove.fromRow - 3][requestedMove.fromCol + 3] = EMPTY;
				board[requestedMove.fromRow - 1][requestedMove.fromCol + 1] = EMPTY;
			}

			if(requestedMove.toRow > requestedMove.fromRow && requestedMove.toCol < requestedMove.fromCol) {
				board[requestedMove.fromRow + 3][requestedMove.fromCol - 3] = EMPTY;
				board[requestedMove.fromRow + 1][requestedMove.fromCol - 1] = EMPTY;
			}

			if(requestedMove.toRow < requestedMove.fromRow && requestedMove.toCol < requestedMove.fromCol) {
				board[requestedMove.fromRow - 3][requestedMove.fromCol - 3] = EMPTY;
				board[requestedMove.fromRow - 1][requestedMove.fromCol - 1] = EMPTY;
			}
		}
		if(Math.abs(requestedMove.toRow - requestedMove.fromRow) == 6) {
			System.out.println("6 row cleanup in process");

			if(requestedMove.toRow > requestedMove.fromRow && requestedMove.toCol > requestedMove.fromCol) {
				board[requestedMove.fromRow + 5][requestedMove.fromCol + 5] = EMPTY;
				board[requestedMove.fromRow + 1][requestedMove.fromCol + 1] = EMPTY;
			}

			if(requestedMove.toRow < requestedMove.fromRow && requestedMove.toCol > requestedMove.fromRow) {
				board[requestedMove.fromRow - 5][requestedMove.fromCol + 5] = EMPTY;
				board[requestedMove.fromRow - 1][requestedMove.fromCol + 1] = EMPTY;
			}

			if(requestedMove.toRow > requestedMove.fromRow && requestedMove.toCol < requestedMove.fromCol) {
				board[requestedMove.fromRow + 5][requestedMove.fromCol - 5] = EMPTY;
				board[requestedMove.fromRow + 1][requestedMove.fromCol - 1] = EMPTY;
			}

			if(requestedMove.toRow < requestedMove.fromRow && requestedMove.toCol < requestedMove.fromCol) {
				board[requestedMove.fromRow - 5][requestedMove.fromCol - 5] = EMPTY;
				board[requestedMove.fromRow - 1][requestedMove.fromCol - 1] = EMPTY;
			}
		}

		/**
		int removePieceRow = (requestedMove.fromRow + requestedMove.toRow) / 2;
		int removePieceCol = (requestedMove.fromCol + requestedMove.toCol) / 2;
		board[removePieceRow][removePieceCol] = EMPTY;
		 */
		if(requestedMove.toRow == 7 && board[requestedMove.toRow][requestedMove.toCol] == BLACK)
			board[requestedMove.toRow][requestedMove.toCol] = BLACK_KING;
		if(requestedMove.toRow == 0 && board[requestedMove.toRow][requestedMove.toCol] == RED)
			board[requestedMove.toRow][requestedMove.toCol] = RED_KING;
	}

	/**
	 * Helper method. Check if the requested move is possible for the Player.
	 * @param player: RED or BLACK
	 * @param requestedMove: Move instance
	 * @return boolean
	 */
	private boolean isLegalMove(int player, Move requestedMove) {

		if (requestedMove.toCol < 0 || requestedMove.toCol >= 8 || requestedMove.toRow >= 8 || requestedMove.toRow < 0) {
			return false;
		}

		if(board[requestedMove.toRow][requestedMove.toCol] != EMPTY) {
			//System.out.println("(" + requestedMove.toRow + ", " + requestedMove.toCol + ") is not empty" );
			return false;
		} else {
			if(player == RED) {
				if(board[requestedMove.fromRow][requestedMove.fromCol] == RED && requestedMove.toRow > requestedMove.fromRow) {

					return false;
				}
				return true;
			} else {
				if (board[requestedMove.fromRow][requestedMove.fromCol] == BLACK && requestedMove.toRow < requestedMove.fromRow)
					return false;
				return true;
			}
		}
	}

	/**
	 * Helper method. Check if the jump move requested is possible according to game rules.
	 * @param player: RED or BLACK
	 * @param move: the requested move
	 * @param inBetweenRow: the row		containing the opponent player over whom the jump is being made
	 * @param inBetweenCol: the column 		"		"		" 	  "		" 	  "	  "	   "   "  "	    "
	 * @return
	 */
	private boolean isLegalJump(int player, Move move, int inBetweenRow, int inBetweenCol) {
		if (move.toCol < 0 || move.toCol >= 8 || move.toRow >= 8 || move.toRow < 0) {
			return false;
		}

		if(board[move.toRow][move.toCol] != EMPTY ) {
			return false;
		}

		if(player == RED) {

			if(board[move.fromRow][move.fromCol] == RED && move.toRow > move.toCol)
				return false;

			if(board[inBetweenRow][inBetweenCol] != BLACK && board[inBetweenRow][inBetweenCol] != BLACK_KING)
				return false;

			return true;
		} else {

			if(board[move.fromRow][move.fromCol] == BLACK && move.toRow < move.fromRow)
				return false;

			if(board[inBetweenRow][inBetweenCol] != RED && board[inBetweenRow][inBetweenCol] != RED_KING)
				return false;

			return true;
		}
	}

	/**
	 * Get current possible moves of this player and return them as an array.
	 * @param currPlayer
	 * @return
	 */
	public Move[] currentPossibleMoves(int currPlayer) {

		if (currPlayer != RED && currPlayer != BLACK)
			return null;

		int currPlayerKing;
		if(currPlayer == RED)
			currPlayerKing = RED_KING;
		else
			currPlayerKing = BLACK_KING;
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if(board[row][col] == currPlayer || board[row][col] == currPlayerKing) {
					if(isLegalJump(currPlayer, new Move(row, col, row + 2, col + 2), row + 1, col + 1)) {
						if(isLegalJump(currPlayer, new Move(row + 2, col + 2, row + 4, col + 4), row + 3, col + 3)) {
							if(isLegalJump(currPlayer, new Move(row + 4, col + 4, row + 6, col + 6), row + 5, col + 5)) {
								availableMoves.add(new Move(row, col, row + 6, col + 6));
							} else {
								availableMoves.add(new Move(row, col, row + 4, col + 4));
							}
						} else {
							availableMoves.add(new Move(row, col, row + 2, col + 2));
						}
					}
					if(isLegalJump(currPlayer, new Move(row, col, row - 2, col + 2), row - 1, col + 1)) {
						if(isLegalJump(currPlayer, new Move(row - 2, col + 2, row - 4, col + 4), row - 3, col + 3)) {
							if(isLegalJump(currPlayer, new Move(row - 4, col + 4, row - 6, col + 6), row - 5, col + 5)) {
								availableMoves.add(new Move(row, col, row - 6, col + 6));
							} else {
								availableMoves.add(new Move(row, col, row - 4, col + 4));
							}
						} else {
							availableMoves.add(new Move(row, col, row - 2, col + 2));
						}
					}
					if(isLegalJump(currPlayer, new Move(row, col, row + 2, col - 2), row + 1, col - 1)) {
						if(isLegalJump(currPlayer, new Move(row + 2, col - 2, row + 4, col - 4), row + 3, col - 3)) {
							if(isLegalJump(currPlayer, new Move(row + 4, col - 4, row + 6, col - 6), row + 5, col - 5)) {
								availableMoves.add(new Move(row, col, row + 6, col - 6));
							} else {
								availableMoves.add(new Move(row, col, row + 4, col - 4));
							}
						} else {
							availableMoves.add(new Move(row, col, row + 2, col - 2));
						}
					}
					if(isLegalJump(currPlayer, new Move(row, col, row - 2, col - 2), row - 1, col - 1)) {
						if(isLegalJump(currPlayer, new Move(row - 2, col - 2, row - 4, col - 4), row - 3, col - 3)) {
							if(isLegalJump(currPlayer, new Move(row - 4, col - 4, row - 6, col - 6), row - 5, col - 5)) {
								availableMoves.add(new Move(row, col, row - 6, col - 6));
							} else {
								availableMoves.add(new Move(row, col, row - 4, col - 4));
							}
						} else {
							availableMoves.add(new Move(row, col, row - 2, col - 2));
						}
					}
				}
			}
		}


		if(availableMoves.size() == 0) {
			for(int row = 0; row < 8; row++) {
				for(int col = 0; col < 8; col++) {
					//System.out.println(availableMoves.size());

					if(board[row][col] == currPlayer || board[row][col] == currPlayerKing) {
						if(isLegalMove(currPlayer, new Move(row, col, row+1, col+1))) {
							availableMoves.add(new Move(row, col, row+1, col+1));
						}
						if(isLegalMove(currPlayer, new Move(row, col, row-1, col+1))) {
							availableMoves.add(new Move(row, col, row-1, col+1));
						}
						if(isLegalMove(currPlayer, new Move(row, col, row+1, col-1))) {
							availableMoves.add(new Move(row, col, row+1, col-1));
						}
						if(isLegalMove(currPlayer, new Move(row, col, row-1, col-1))) {
							availableMoves.add(new Move(row, col, row-1, col-1));
						}
					}
				}
			}
		}


		if(availableMoves.size() == 0)
			return null;
		else {
			Move[] moves = new Move[availableMoves.size()];
			System.out.println("Current player is: " + players[currPlayer]);
			for(int i = 0; i < availableMoves.size(); i++) {
				moves[i] = availableMoves.get(i);
				
				System.out.println("Possible Moves: " + moves[i].toString());
			}
			return moves;
		}
	}

	/**
	 * Get current possible jumps and return it as an array of Move.
	 * @param currPlayer
	 * @param row
	 * @param col
	 * @return
	 */
	public Move[] currentPossibleJumps(int currPlayer, int row, int col) {
		if (currPlayer != RED && currPlayer != BLACK)
			return null;

		int currPlayerKing;
		if(currPlayer == RED)
			currPlayerKing = RED_KING;
		else
			currPlayerKing = BLACK_KING;

		ArrayList<Move> availableJumps = new ArrayList<Move>();
		if(board[row][col] == currPlayer || board[row][col] == currPlayerKing) {
			if(isLegalJump(currPlayer, new Move(row, col, row + 2, col + 2), row + 1, col + 1)) {
				if(isLegalJump(currPlayer, new Move(row + 2, col + 2, row + 4, col + 4), row + 3, col + 3)) {
					if(isLegalJump(currPlayer, new Move(row + 4, col + 4, row + 6, col + 6), row + 5, col + 5)) {
						availableJumps.add(new Move(row, col, row + 6, col + 6));
					} else {
						availableJumps.add(new Move(row, col, row + 4, col + 4));
					}
				} else {
					availableJumps.add(new Move(row, col, row + 2, col + 2));
				}
			}
			if(isLegalJump(currPlayer, new Move(row, col, row - 2, col + 2), row - 1, col + 1)) {
				if(isLegalJump(currPlayer, new Move(row - 2, col + 2, row - 4, col + 4), row - 3, col + 3)) {
					if(isLegalJump(currPlayer, new Move(row - 4, col + 4, row - 6, col + 6), row - 5, col + 5)) {
						availableJumps.add(new Move(row, col, row - 6, col + 6));
					} else {
						availableJumps.add(new Move(row, col, row - 4, col + 4));
					}
				} else {
					availableJumps.add(new Move(row, col, row - 2, col + 2));
				}
			}
			if(isLegalJump(currPlayer, new Move(row, col, row + 2, col - 2), row + 1, col - 1)) {
				if(isLegalJump(currPlayer, new Move(row + 2, col - 2, row + 4, col - 4), row + 3, col - 3)) {
					if(isLegalJump(currPlayer, new Move(row + 4, col - 4, row + 6, col - 6), row + 5, col - 5)) {
						availableJumps.add(new Move(row, col, row + 6, col - 6));
					} else {
						availableJumps.add(new Move(row, col, row + 4, col - 4));
					}
				} else {
					availableJumps.add(new Move(row, col, row + 2, col - 2));
				}
			}
			if(isLegalJump(currPlayer, new Move(row, col, row - 2, col - 2), row - 1, col - 1)) {
				if(isLegalJump(currPlayer, new Move(row - 2, col - 2, row - 4, col - 4), row - 3, col - 3)) {
					if(isLegalJump(currPlayer, new Move(row - 4, col - 4, row - 6, col - 6), row - 5, col - 5)) {
						availableJumps.add(new Move(row, col, row - 6, col - 6));
					} else {
						availableJumps.add(new Move(row, col, row - 4, col - 4));
					}
				} else {
					availableJumps.add(new Move(row, col, row - 2, col - 2));
				}
			}
		}
		if(availableJumps.size() == 0) {
			return null;
		} else {
			Move[] jumps = new Move[availableJumps.size()];
			for(int i = 0; i < availableJumps.size(); i++) {
				jumps[i] = availableJumps.get(i);
			}
			return jumps;
		}
	}

	public int currPieceAt(int row, int col) {
		return board[row][col];
	}



}
