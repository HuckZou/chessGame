package chessGame;

import java.util.List;

//This is the black pieces player of the chess game
public class BlackPlayer extends Player {
	public BlackPlayer(Board board, List<Move> whitePieceLegalMoves, List<Move> blackPieceLegalMoves) {
		super(board, blackPieceLegalMoves, whitePieceLegalMoves);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Piece> getActivePiece() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces();
	}

	@Override
	public PieceColor getPlayerColor() {
		// TODO Auto-generated method stub
		return PieceColor.BLACK;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.getWhitePlayer();
	}
}
