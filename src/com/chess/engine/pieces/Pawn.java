package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Стас on 16.04.2016.
 */
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {8};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();


        for (final int currentcandidateOfset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentcandidateOfset;

            if (!BoardUtils.isValidateCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentcandidateOfset == 8 && board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentcandidateOfset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behintCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() + 8);
                if (!board.getTile(behintCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));


                }

            }
        }
        return legalMoves;
    }
}
