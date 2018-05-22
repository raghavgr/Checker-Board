package checkers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class CheckersView extends JPanel implements ActionListener {

	JButton nextButton;
	JButton prevButton;
	JLabel notification;
	CheckersModel boardState;
	ArrayList<Move> requestedMoves;
	boolean gameIsHappening;
	int currPlayer;
	Move[] possibleMoves;
	int currRow, currCol;
	int currMove = 0; //start the iteratedMoves for going over requestedMoves at 0

	public CheckersView(ArrayList<Move> requestedMoves) {
		repaint();
		JFrame test = new JFrame("Checkers");
		test.getContentPane().setBackground(Color.decode("#049304"));
		//test.setLayout(null);
		test.setTitle("Checkers");
		test.setSize(600, 400);
		test.add(this);

		nextButton = new JButton(" Next ");
		nextButton.addActionListener(this);
		prevButton = new JButton(" Previous ");
		prevButton.addActionListener(this);

		this.setBounds(20, 20, 244, 244);
		//this.setPreferredSize(new Dimension(246, 246));
		
		//test.setLayout(new BoxLayout(test.getContentPane(), BoxLayout.X_AXIS));
		/**
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
		buttonPane.add(nextButton);
		buttonPane.add(prevButton);
		buttonPane.setAlignmentX(RIGHT_ALIGNMENT);
		test.add(buttonPane);
		//test.add(prevButton, BorderLayout.EAST);
		**/
		test.add(nextButton);
		test.add(prevButton);
		nextButton.setBounds(310, 120, 100, 30);
		prevButton.setBounds(310, 60, 100, 30);
		
		notification = new JLabel("First move is by RED", JLabel.CENTER);
		test.add(notification);
		notification.setBounds(0, 310, 330, 30);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setVisible(true);
		this.requestedMoves = requestedMoves;
		boardState = new CheckersModel();
		System.out.println("in checkers view constructor: " + requestedMoves.size());
		startGame();
	}

	/**
	 * Starts a new game of checkers and sets up
	 * the board for checkers.
	 */
	public void startGame() {
		boardState.createBoard();
		gameIsHappening = true;
		currPlayer = CheckersModel.RED;
		possibleMoves = boardState.currentPossibleMoves(CheckersModel.RED);
		System.out.println("possibleMoves count is: " + possibleMoves.length);
		currRow = -1;
		nextButton.setEnabled(true);
		prevButton.setEnabled(false);
		repaint();
	}

	/**
	 * Checks if the move requested is one of the legal moves or not
	 */
	public void checkRequestedMove() {
		boolean isMoveAllowed = false;
		Move move = null;
		if(currMove < requestedMoves.size()) {
			for (int i = 0; i < possibleMoves.length; i++) {
				if(possibleMoves[i].fromRow == requestedMoves.get(currMove).fromRow && possibleMoves[i].fromCol == requestedMoves.get(currMove).fromCol
						&& possibleMoves[i].toRow == requestedMoves.get(currMove).toRow && possibleMoves[i].toCol == requestedMoves.get(currMove).toCol)
				{
					isMoveAllowed = true;
					move = possibleMoves[i];
				}

			}

			if(isMoveAllowed) {
				processMove(move);
			} else {
				notification.setText("Requested Move: " + requestedMoves.get(currMove).toString() + " is invalid");
			}

			currMove++;

		} else {
			notification.setText("Reached the limit of requested moves");
		}
	}

	/**
	 * Carry out the move once it is verified that it is a legal move.
	 * @param move
	 */
	public void processMove(Move move) {
		boardState.updateBoardWith(move);
		
		if(move.isJumpMove()) {
			possibleMoves = boardState.currentPossibleJumps(currPlayer, move.toRow, move.toCol);
			if(possibleMoves != null) {
				if(currPlayer == CheckersModel.RED) {
					System.out.println("another jump present");
					notification.setText("It is still RED player's turn");
				} else {
					notification.setText("It is still BLACK player's turn");
				}
				currRow = move.toRow;
				currCol = move.toCol;
				repaint();
				return;
			}
		}

		if(currPlayer == CheckersModel.RED) {
			currPlayer = CheckersModel.BLACK;
			possibleMoves = boardState.currentPossibleMoves(currPlayer);
			if(possibleMoves == null) {
				notification.setText("BLACK player has no more moves. RED wins");
				gameIsHappening = false;
			} else if(possibleMoves[0].isJumpMove()) {
				notification.setText("BLACK player has to make the jump available");
			} else {
				notification.setText("BLACK player's turn");
			}
		} else {
			currPlayer = CheckersModel.RED;
			possibleMoves = boardState.currentPossibleMoves(currPlayer);
			if(possibleMoves == null) {
				notification.setText("RED player has no more moves. BLACK wins");
				gameIsHappening = false;
			} else if(possibleMoves[0].isJumpMove()) {
				notification.setText("RED player has to make the jump available");
			} else {
				notification.setText("RED player's turn");
			}
		}

		currRow = -1;


		if(possibleMoves != null) {
			boolean stillCurrLocation = true;
			for(int i = 1;  i < possibleMoves.length; i++) {
				if(possibleMoves[i].fromCol != possibleMoves[0].fromCol
					|| possibleMoves[i].fromRow != possibleMoves[0].fromRow) {

					stillCurrLocation = false;
					break;
				}
			}
			if(stillCurrLocation) {
				currRow = possibleMoves[0].fromRow;
				currCol = possibleMoves[0].fromCol;
			}
		}

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object src = event.getSource();
		if (src == nextButton) {
			checkRequestedMove();
		}
	}

	/**
	 * Draws the checker board in the panel
	 * @param g Graphics object
	 */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

        g.setColor(Color.black);
        g.drawRect(0,0,getSize().width-1,getSize().height-1);
        g.drawRect(1,1,getSize().width-3,getSize().height-3);


        for (int row = 0; row < 8; row++) {
           for (int col = 0; col < 8; col++) {
              if ( row % 2 == col % 2 )
                 g.setColor(Color.LIGHT_GRAY);
              else
                 g.setColor(Color.GRAY);
              g.fillRect(2 + col*30, 2 + row*30, 30, 30);
              switch (boardState.currPieceAt(row,col)) {
	              case CheckersModel.RED:
	                 g.setColor(Color.RED);
	                 g.fillOval(4 + col*30, 4 + row*30, 25, 25);
	                 break;
	              case CheckersModel.BLACK:
	                 g.setColor(Color.BLACK);
	                 g.fillOval(4 + col*30, 4 + row*30, 25, 25);
	                 break;
	              case CheckersModel.RED_KING:
	                 g.setColor(Color.RED);
	                 g.fillOval(4 + col*30, 4 + row*30, 25, 25);
	                 g.setColor(Color.WHITE);
	                 g.drawString("K", 7 + col*30, 16 + row*30);
	                 break;
	              case CheckersModel.BLACK_KING:
	                 g.setColor(Color.BLACK);
	                 g.fillOval(4 + col*30, 4 + row*30, 25, 25);
	                 g.setColor(Color.WHITE);
	                 g.drawString("K", 7 + col*30, 16 + row*30);
	                 break;
              }
           }
        }
        
        if(gameIsHappening) {
        	g.setColor(Color.CYAN);
        	for(int i = 0; i < possibleMoves.length; i++) {
        		g.drawRect(2 + possibleMoves[i].fromCol*30, 2 + possibleMoves[i].fromRow*30, 29, 29);
        		g.drawRect(3 + possibleMoves[i].fromCol*30,	3 + possibleMoves[i].fromRow*30, 27, 27);
        	}

        	if(currRow >= 0) {
        		g.setColor(Color.white);
        		g.drawRect(2 + currCol*30, 2 + currRow*30, 29, 29);
        		g.drawRect(3 + currCol*30, 3 + currRow*30, 27, 27);
        		g.setColor(Color.green);
        		for(int i = 0; i < possibleMoves.length; i++) {
        			if(possibleMoves[i].fromRow == currRow && possibleMoves[i].fromCol == currCol) {
        				g.drawRect(2 + possibleMoves[i].toCol*30, 2 + possibleMoves[i].toRow*30, 29, 29);
        				g.drawRect(3 + possibleMoves[i].toCol*30, 3 + possibleMoves[i].toRow*30, 27, 27);
        			}
        		}
        	}
        }   
	}
}
