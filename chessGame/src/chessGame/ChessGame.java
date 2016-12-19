package chessGame;

import chessGUI.WindowView;
import java.util.Scanner;
/*
 * Implemented by qzou3
 * Date: Sep. 2016
 * Reference: 
 * 		1. Game structure design reference
 * 			http://codereview.stackexchange.com/questions/71790/design-a-chess-game-using-object-oriented-principles
 * 			by Ramanathan Ganesan & Hosch250
 * 		2. Game Logic reference
 * 				a. https://en.wikipedia.org/wiki/Chess
 * 			 	b. https://www.youtube.com/playlist?list=PLQV5mozTHmaffB0rBsD6m9VN1azgo5wXl for ending conditions
 * 				c. https://www.youtube.com/user/amir650 for pieces moves logic design 
 */

//This is the main method for our chess game.
public class ChessGame {
	public static void main(String[] args) {
		// Setup the initial chess board and put all chess pieces at proper
		// positions
		Board board = Board.createInitialChessBoard();
//		Board board = Board.createInitialCrazyChessBoard();
		WindowView view = new WindowView(board);
		// Print out a message to tell players that the game has started
		System.out.println("Welcome, your game has started!");
		// Print out the game board let players to see
		System.out.println(board.printBoard());
		// Creates a new instance of scanner to take in inputs from user
		Scanner scan = new Scanner(System.in);

		// While the game over condition is not satisfied, the game will run
		while (true) {
			// Get all the necessary objects on a game board
			Player currentPlayer = board.getCurrentPlayer();
			Player opponent = currentPlayer.getOpponent();
			String currentPlayerColor = currentPlayer.getPlayerColor().toString();
			String opponentPlayerColor = opponent.getPlayerColor().toString();
			// this calls the setPlayNames() method in the view object
			// players are asked to type in their names to be displayed on the 
			// chess board
//			view.setPlayerNames();
			Move move = null;
			// Print out a message to tell which player should make a move.
			System.out.println("It's " + currentPlayerColor + " player's turn.");

			// Game ending condition. If the current player is check mated, then
			// its
			// opponent wins.
			if (currentPlayer.isInCheckMate()) {
				System.out.println(opponentPlayerColor + " wins!");
				
//				view.InCheckMate(board);
				break;
			}

			// Game ending condition. If the game is in a stall mate, then no one
			// wins
			if (currentPlayer.isInStaleMate()) {
				System.out.println("This is a draw!");
//				view.InStaleMate(board);
				break;
			}

			// If the opponent player puts current player in check, we check if
			// the
			// current player can find an escape move.
			if (currentPlayer.isInCheck()) {
				System.out.println(currentPlayerColor + " king is under attack!!");
//				view.InCheck(board);
				move = userBuildMove(view, scan, board);
				// If the current player didn't make an escape move,
				// then the game is over and its opponent wins.
				// Otherwise, the current player survives and opponent is to
				// make the
				// next move.
				if (!isValidEscape(board, move)) {
					System.out.println(currentPlayerColor + " king did not survive.");
					System.out.println(opponentPlayerColor + " wins!");
					break;
				}
			}
			// If the current player is not in check condition, then
			// the current player can make any legal moves.
			else {
				move = userBuildMove(view, scan, board);
			}

			// Executes the move and returns a new board to represent the
			// current game
			// condition.
			board = move.execute();
			// Print out the game board again to let players to see.
			System.out.println(board.printBoard());
			// Let JFrame to update the new board configuration
			view.updateGame(board);
		}

	}

	// This is a helper method to ask users to input a move.
	// This method outputs a move specified by the players.
	public static Move userBuildMove(WindowView view, Scanner scan, Board board) {
		
		System.out.println("Please enter the coordinate of the piece you want to move:");
		String tileLocation = extractString(scan.next());
		System.out.println("Please enter the destination coordinate:");
		String destTileLocation = extractString(scan.next());
		boolean validMove = false;
		Move result = null;
		// While the user has not made a valid move, keep asking for it!
		while (!validMove) {
			result = Move.MoveFactory.createCoordinateMove(board, Character.getNumericValue(tileLocation.charAt(0)),
					Character.getNumericValue(tileLocation.charAt(1)), Character.getNumericValue(destTileLocation.charAt(0)),
							Character.getNumericValue(destTileLocation.charAt(1)));
			if (Move.isNullMove(result)) {
				System.out.println("The move you have entered is not valid. Please try again.");
				System.out.println("Please reenter the location of the piece");
				tileLocation = extractString(scan.next());
				System.out.println("Please reenter the destination of the peice");
				destTileLocation = extractString(scan.next());
			} else {
				validMove = true;
			}
		}
//		Move result = view.userBuildMove(view, scan, board);
		return result;
	}

	// This method check the condition of whether or not the player has made
	// a valid escape move to prevent its king being killed.
	public static boolean isValidEscape(Board board, Move move) {
		Move escapeMove = Move.MoveFactory.createEscapeMove(board, move.getCurrentLocation(), move.getDestLocation());
		return !Move.isNullMove(escapeMove);
	}
	
	// A helper method to extract numbers from users input
	public static String extractString(String string) {
		return string.replaceAll("[^0-9]", "");
	}
}
