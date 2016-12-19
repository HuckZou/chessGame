package chessGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import chessGame.*;
import chessGame.Move.MoveFactory;

import java.util.ArrayList;
import java.util.List;

//Chess pieces obtained from following source:
//https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent

public class WindowView {
	//This variable stores the current JFrame of the TableView
	private JFrame chessWindow;
	//This variable stores the window size of the chess game
	private Dimension windowSize = new Dimension(800,800);
	//This variable stores the chess board size of the chess game
	private Dimension boardSize = new Dimension(720,720);
	//This variable stores the tile size of the chess game
	private Dimension tileSize = new Dimension(90,90);
	//This variable stores the size of information panel
	private Dimension infoSize = new Dimension(40, 40);
	//This variable stores the current game board configuration
	protected Board board;
	//This variable stores the last board configuration
	protected Board lastBoard;
	//This variable stores the current BoardPanel
	private BoardPanel boardPanel;
	//This variable stores the current InfoPanel
	private InfoPanel infoPanel;
	//This variable holds the tile that current player is choosing
	private Tile startTile;
	//This variable holds the tile that current player is placing the piece to
	private Tile destTile;
	//This variable indicates whether or not the game is over
	private boolean gameOver = false;
	
	//Constructor for initializing the TableView
	public WindowView(Board board) {
		this.chessWindow = new JFrame("Chess Game");
		this.setUpMenu(chessWindow);
		this.addMenuItemsListener(chessWindow);
		this.chessWindow.setSize(windowSize);
//		this.board = Board.createInitialChessBoard();
		this.board = board;
//		this.board = Board.createInitialCrazyChessBoard();
		this.boardPanel = new BoardPanel();
		this.infoPanel = new InfoPanel();
		this.chessWindow.add(this.boardPanel, BorderLayout.CENTER);
		this.chessWindow.add(this.infoPanel, BorderLayout.NORTH);
		this.chessWindow.setVisible(true);
		this.chessWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//private helper method to setup the menubar
	//it takes in a parameter for the GUI window and
	//add menus to that window.
	private void setUpMenu(JFrame window) {
		JMenuBar menuBar = new JMenuBar();
		//Build a string for all menus
		String[] menus = "Game,Moves,Window,Help".split(",");
		//Build an array of strings for options in each menu
		String[] menuItems = {"New,Close,Save,Duplicate",
								"Undo,Redo,Restart,Forfeit",
								"Minimize,Zoom,Full",
								"Wiki"};
		//Loop through the strings to initialize all menus
		for(int i = 0; i < menus.length; i++) {
			menuBar = this.setUpMenuItems(menuBar, menus[i], menuItems[i]);
		}
		window.setJMenuBar(menuBar);
	}
	
	//private helper method to setup event listeners for
	//options in the menus
	private void addMenuItemsListener(JFrame window) {
		//setup a listener for the forfeit menu option
		//if a player choose to forfeit, then the other
		//player wins the game automatically.
		JMenuItem forfeit = window.getJMenuBar().getMenu(1).getItem(3);
		forfeit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String forfeitPlayer = board.getCurrentPlayer().getPlayerName();
				String message = forfeitPlayer + " has forfeited the game.\n";
				message += board.getCurrentPlayer().getOpponent().getPlayerName() + 
						" has won the game!";
				JOptionPane.showMessageDialog(null, message);
				incrementScore(board.getCurrentPlayer().getOpponent());
				setGameOver(true);
				}
			});
		
		//setup a listener for the restart menu option
		//if a player choose to restart, then the system will ask 
		//its opponent whether or not to have another game
		//if the opponent chooses not to have another game
		//then the game will leave the way it is
		JMenuItem restart = window.getJMenuBar().getMenu(1).getItem(2);
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String restartPlayer = board.getCurrentPlayer().getPlayerName();
				String message = restartPlayer + " wants to restart the game\nDo you agree?";
				final int answer = JOptionPane.showConfirmDialog(null, message, "Restart The Game",
						JOptionPane.YES_NO_OPTION);
				if(answer == JOptionPane.YES_OPTION) {
					restartGame();
				}
			}
			
		});
		
		//undo the last move that this player made.
		JMenuItem undo = window.getJMenuBar().getMenu(1).getItem(0);
		undo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				if(lastBoard != null) {
					board = lastBoard;
					lastBoard = null;
					updateGame(board);
				}
			}});
	}
	
	// this private method starts a new chess game without resetting the scores
	// for each of the players
	private void restartGame() {
		//TODO change the board so that it restart with another type of board
		board = Board.createInitialChessBoard();
//		board = Board.createInitialCrazyChessBoard();
		board.getBlackPlayer().setPlayerName(infoPanel.getBlackPlayerName());
		board.getWhitePlayer().setPlayerName(infoPanel.getWhitePlayerName());
		updateGame(board);
	}
	
	// public method to start the game. This is the game loop
	public void startGame(WindowView view) {
		view.setGameOver(false);
		Player lastCurrentPlayer = view.getGameBoard().getCurrentPlayer();
		// While the game is not over. keep looping
		while(!view.isGameOver()) {
			Player currentPlayer = view.getGameBoard().getCurrentPlayer();
			if(currentPlayer.getPlayerColor().toString()!= lastCurrentPlayer.getPlayerColor().toString()) {
				if(currentPlayer.getOpponent().isInCheck()) {
					String message = currentPlayer.getOpponent().getPlayerName() + " is in checkmate\n";
					message += currentPlayer.getPlayerName() + " wins!";
					JOptionPane.showMessageDialog(null, message);
					incrementScore(currentPlayer);
					askForAnotherGame();
					continue;
				}
				if(currentPlayer.isInCheckMate()) {
					view.inCheckMate();
				}
				if(currentPlayer.isInStaleMate()) {
					view.inStaleMate();
				}
				if(currentPlayer.isInCheck()) {
					view.inCheck();
				}
				lastCurrentPlayer = currentPlayer;
			}
			// put the thread to sleep for 200 ms, so that my CPU doesn't blow up.
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
	
	// private helper method to set up the GUI behavior when there is checkmate condition
	private void inCheckMate() {
		if(board.getCurrentPlayer().isInCheckMate()) {
			Player currentPlayer = board.getCurrentPlayer();
			Player opponentPlayer = currentPlayer.getOpponent();
			String message = currentPlayer.getPlayerName() + " is in checkmate\n";
			message += opponentPlayer.getPlayerName() + " wins!";
			incrementScore(opponentPlayer);
			JOptionPane.showMessageDialog(null, message);
			askForAnotherGame();
		}
	}

	// private helper method to set up the GUI behavior when there is statemate condition
	private void inStaleMate() {
		if(board.getCurrentPlayer().isInStaleMate()) {
			JOptionPane.showMessageDialog(null, "This is a stalemate!");
			askForAnotherGame();
		}
	}

	// private helper method to set up the GUI behavior when there is incheck condition
	private void inCheck() {
		if(board.getCurrentPlayer().isInCheck()) {
			Player currentPlayer = board.getCurrentPlayer();
			JOptionPane.showMessageDialog(null, currentPlayer.getPlayerName()+" is incheck!");
		}
	}
	
	// private helper method to let GUI ask players whether or not to have another game
	private void askForAnotherGame() {
		final int answer = JOptionPane.showConfirmDialog(null, "Play Another Game?", "Another Game",
				JOptionPane.YES_NO_OPTION);
		if(answer == JOptionPane.YES_OPTION) {
			restartGame();
		}
		else {
			announceWinner();
		}
	}
	
	// private helper method to increment the current player score by one
	// it calls the infoPanel.updateInfo() function to update the newest 
	// changes to the information panel
	private void incrementScore(Player player) {
		if(player.getPlayerColor().toString() == "white") {
			infoPanel.setWhiteScore(infoPanel.getWhiteScore()+1);
		} else {
			infoPanel.setBlackScore(infoPanel.getBlackScore()+1);
		}
		infoPanel.updateInfo();
	}
	// this private method ends the game and announce the winner of the game based
	// on the player who has a higher score.
	private void announceWinner() {
		String message;
		if(infoPanel.getWhiteScore()== infoPanel.getBlackScore()) {
			message = "This is a draw!";
		} else if (infoPanel.getWhiteScore() > infoPanel.getBlackScore()) {
			message = infoPanel.getWhitePlayerName() + " wins!";
		} else {
			message = infoPanel.getBlackPlayerName() + " wins!";
		}
		JOptionPane.showMessageDialog(null, message);
		setGameOver(true);
	}
	
	// this private method sets the variable gameOver 
	public void setGameOver(boolean status) {
		this.gameOver = status;
	}
	// this public method gets the variable gameOver 
	public boolean isGameOver() {
		return this.gameOver;
	}

	//private help method for setUpMenu(), this method takes in 
	//the menu name and a list of menu options and add all of them
	//to the menuBar. It returns a modified menuBar object.
	private JMenuBar setUpMenuItems(JMenuBar menuBar, String menuString, String menuItemsString) {
		String[] menuItems = menuItemsString.split(",");
		JMenu menu = new JMenu(menuString);
		for(int i = 0; i < menuItems.length; i++) {
			JMenuItem item = new JMenuItem(menuItems[i]);
			menu.add(item);
		}
		menuBar.add(menu);
		return menuBar;
	}
	
	// A class to represent the score board and store current
	// players' names. This panel will be placed on the top of 
	// the game board
	private class InfoPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		//This variable holds the name of the white player
		private String whitePlayerName;
		//This variable holds the name of the black player
		private String blackPlayerName;
		//This variable holds the score of the white player
		private int whiteScore;
		//This variable holds the score of the black player
		private int blackScore;
		
		InfoPanel() {
			super(new FlowLayout());
			whiteScore = 0;
			blackScore = 0;
			//asking users to input two different names
			boolean equal = true;
			while(equal) {
				whitePlayerName = JOptionPane.showInputDialog("Please enter a name for the white player.");
				blackPlayerName = JOptionPane.showInputDialog("Please enter a name for the black player.");
				equal = whitePlayerName.equals(blackPlayerName);
			}
			board.getBlackPlayer().setPlayerName(blackPlayerName);
			board.getWhitePlayer().setPlayerName(whitePlayerName);
			this.setupDisplay();
			this.setPreferredSize(infoSize);
		}
		
		// Helper function to setup the display of the information panel
		// This will format the font color and position of the players information
		private void setupDisplay(){
			JLabel whiteLabel = new JLabel("White player "+whitePlayerName+" ");
			JLabel scoreLabel = new JLabel(String.valueOf(whiteScore)+" : "+ 
					String.valueOf(blackScore)+" ");
			JLabel blackLabel = new JLabel(blackPlayerName+ " Black player");
					
			scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			scoreLabel.setFont(new Font("Serif", Font.BOLD, 24));
			whiteLabel.setFont(new Font("Serif", Font.PLAIN, 24));
			blackLabel.setFont(new Font("Serif", Font.PLAIN, 24));
			whiteLabel.setForeground(Color.decode("#B69D70"));
			blackLabel.setForeground(Color.decode("#7F1518"));
			
			this.add(whiteLabel);
			this.add(scoreLabel);
			this.add(blackLabel);
		}
		
		// This method is called to update the JFrame to reflect the newest updates for
		// player information
		public void updateInfo() {
			this.removeAll();
			this.setupDisplay();
			this.validate();
			this.repaint();
		}
		
		// Set method for setting white player scores
		public void setWhiteScore(int newscore) {
			this.whiteScore = newscore;
		}
		
		// Set method for setting black player scores		
		public void setBlackScore(int newscore) {
			this.blackScore = newscore;
		}
		
		// Get method for getting white player score
		public int getWhiteScore() {
			return this.whiteScore;
		}
		
		// Get method for getting black player score		
		public int getBlackScore() {
			return this.blackScore;
		}
		
		// Get method for getting white player name
		public String getWhitePlayerName() {
			return this.whitePlayerName;
		}

		// Get method for getting black player name
		public String getBlackPlayerName() {
			return this.blackPlayerName;
		}
	}
	
	
	// A class to represent the game board view
	// The game board has 64 tiles and every time 
	// when the configuration of the game board changes
	// this class will get updated to reflect the 
	// current view of the board
	private class BoardPanel extends JPanel {
		// Not sure what this is for, but
		// if I don't add it, there will be warnings
		private static final long serialVersionUID = 1L;
		// A private variable to store all the tile panels
		// that will be shown on the chess board tiles
		private List<TilePanel> tiles;
		// A constructor to construct the board panel
		// this will create 64 tile panels and store them
		BoardPanel() {
			super(new GridLayout(8,8));
			this.tiles = new ArrayList<>();
			for(int i = 0; i < 64; i++) {
				// For each tile on the board, we construct a new 
				// tile panel.
				TilePanel tilePanel = new TilePanel(this,i);
				this.tiles.add(tilePanel);
				this.add(tilePanel);
			}
			//Set the size of the game board
			this.setPreferredSize(boardSize);
		}
		
		// This method will be used by WindowView 
		// to update the view shown
		public void updateBoard(Board board) {
			this.removeAll();
			for(TilePanel tile: tiles) {
				tile.updateTile(board);
				this.add(tile);
			}
			// after we change the tile panels store in board panel
			// we need to let JPanel know that all changes have been made
			// and it should refresh the GUI
			this.validate();
			this.repaint();
		}
	}
	
	// A class to represent all the tiles on the game board
	// each tile panel will have its own board location stored
	// so that it knows where it belongs to on the board.
	// Each tile panel also has a boolean value to track whether
	// this tile is painted light color or dark color.
	private class TilePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		// This variable stores the location of the tile on the game board
		private int tileLocation;
		// This variable stores what color should this tile be painted into
		// either light color or dark color
		private boolean isLight;
		// This variable store the location of the images folder for this project
		private String filePath = "images/";
		// Constructor for TilePanel
		TilePanel(BoardPanel boardPanel, int tileLocation) {
			super(new GridBagLayout());
			this.tileLocation = tileLocation;
			this.isLight = this.isLight(tileLocation);
			this.setPreferredSize(tileSize);
			this.setTileColor();
			// Set the piece image that this tile should be displaying
			// for a given board configuration.
			this.setTilePieceImage(board);
			this.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					//If the player clicks right mouse button,
					//then the player is deselecting pieces
					if(SwingUtilities.isRightMouseButton(e)) {
						startTile = null;
						destTile = null;
						//If the player clicks left mouse button,
						//we would like to check if the player has selected
						//a piece or not. If it has, then the player performs
						//a move. If not, we get the piece at the location 
						//where the user has clicked.
					} else if (SwingUtilities.isLeftMouseButton(e)) {
						if(startTile == null) {
							startTile = board.getTile(tileLocation); 
							if(startTile.getPieceOnTile() == null) {
								startTile = null;
							}
						} else {
							destTile = board.getTile(tileLocation);
							Move move = MoveFactory.createMove(board, 
									startTile.getTileLocation(), 
									destTile.getTileLocation());
							//if it's illegal move
							if(Move.isNullMove(move)) {
								int currentLocation = startTile.getTileLocation();
								int destLocation = destTile.getTileLocation(); 
								String startCoordinate = "("+String.valueOf(Board.getRowNum(currentLocation))+","+
										String.valueOf(Board.getColumnNum(currentLocation))+")";
								String endCoordinate = "("+String.valueOf(Board.getRowNum(destLocation))+","+
										String.valueOf(Board.getColumnNum(destLocation))+")";
								String message = "Moving from " + startCoordinate + " to " + endCoordinate + " is illegal!\n";
								String reason = null;
								//The following coding determins the reason for which the move is illegal. 
								//Reason zero, it's not this player's turn
								String color = startTile.getPieceOnTile().getPieceColor().toString();
								if (board.getCurrentPlayer().getPlayerColor().toString() != color){
									reason = "It's not "+ color + "'s turn.";
								}
								//Reason one, the destination tile is occupied with another piece that has the same color
								else if(destTile.isTileOccupied() && startTile.getPieceOnTile().getPieceColor().toString() ==
										destTile.getPieceOnTile().getPieceColor().toString()) {
									reason = "Tile is occupied.";
								}
								//Reason two, the piece cannot attack the enemy piece on the destination tile
								else if(destTile.isTileOccupied() && startTile.getPieceOnTile().getPieceColor().toString() !=
										destTile.getPieceOnTile().getPieceColor().toString()) {
									reason = startTile.getPieceOnTile().getName() + " can't attack that position";
								}
								//Reason three, the piece is not legal to have this kind of move
								else{
									reason = "Not legal move for " + startTile.getPieceOnTile().getName();
								}
								JOptionPane.showMessageDialog(null, message+reason);
							} else {
								lastBoard = board;
								board = move.execute();
								board.getBlackPlayer().setPlayerName(infoPanel.getBlackPlayerName());
								board.getWhitePlayer().setPlayerName(infoPanel.getWhitePlayerName());
							}
							startTile = null;
							destTile = null;
						}
					}
					//wait until the other threads have finished what they are doing
					//we then update our board.
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {

							boardPanel.updateBoard(board);
						}});
				}

				@Override
				public void mousePressed(MouseEvent e) {

					
				}

				@Override
				public void mouseReleased(MouseEvent e) {

					
				}

				@Override
				public void mouseEntered(MouseEvent e) {

					
				}

				@Override
				public void mouseExited(MouseEvent e) {

					
				}});
		}
		
		// Helper function for determining which color should a tile be
		// painted into given its location on the board.
		private boolean isLight(int tileLocation) {
			int[] rowColIndex = Board.getRowColIndex(tileLocation);
			return ((rowColIndex[0] + rowColIndex[1]) & 1) == 0;
		}
		
		// This helper method sets the alternating color of the chess board
		private void setTileColor() {
			if(this.isLight) {
				this.setBackground(Color.decode("#B69D70"));
			} else {
				this.setBackground(Color.decode("#7F1518"));
			}
		}
		
		// This helper method sets the piece image on the corresponding tile
		private void setTilePieceImage(Board board) {
			// Remove the existing panel image
			this.removeAll();
			// If the tile isn't empty, then we want to add
			// the piece image to the tile panel
			Piece tempPiece;
			if(board.getTile(this.tileLocation).isTileOccupied()) {
				try {
					tempPiece = board.getTile(this.tileLocation).getPieceOnTile(); 
					// Build up the path name for the image we need
					String fileName = filePath +
							tempPiece.getPieceColor().toString() +
							"_" + tempPiece.toString() + ".png";
					BufferedImage image = ImageIO.read(new File(fileName));
					// Add this image to the JPanel
					this.add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// This helper method will be called when the configuration for the board
		// has been change (a player makes a move).
		public void updateTile(Board board) {
			this.setTilePieceImage(board);
		}
	}
	
	//Get method for get the current game board
	public Board getGameBoard() {
		return this.board;
	}
	
	//Get method for get the current BoardPanel
	public BoardPanel getBoardPanel() {
		return this.boardPanel;
	}
	
	//Get method for getting the current infoPanel
	public InfoPanel getInfoPanel() {
		return this.infoPanel;
	}
	//Get method for getting the current chessWindow
	public JFrame getChessWindow() {
		return this.chessWindow;
	}
	
	//Update method for updating the game board
	public void updateGameBoard(Board newBoard) {
		this.board = newBoard;
	}
	
	//Redraw the board after pieces have been moved
	public void updateGame(Board newBoard) {
		boardPanel.updateBoard(newBoard);
		infoPanel.updateInfo();
		board.getBlackPlayer().setPlayerName(infoPanel.getBlackPlayerName());
		board.getWhitePlayer().setPlayerName(infoPanel.getWhitePlayerName());
	}
}
