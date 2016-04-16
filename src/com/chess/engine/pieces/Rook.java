package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

/**
 * Created by Стас on 16.04.2016.
 */
public class Rook extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOfSet : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidateCoordinate(candidateDestinationCoordinate)) {
                if(isFirstColumnExlusion(candidateDestinationCoordinate, candidateCoordinateOfSet)||
                        isEightColumnExlusion(candidateDestinationCoordinate,candidateCoordinateOfSet)){
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOfSet;
                if (BoardUtils.isValidateCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExlusion(final int currentPosition, final int candidateOfSet ){
        return BoardUtils.FIRST_COLUMN[currentPosition]&&(candidateOfSet==-1);
    }
    private static boolean isEightColumnExlusion(final int currentPosition, final int candidateOfSet ){
        return BoardUtils.EIGHTH_COLUMN[currentPosition]&&(candidateOfSet==1);
    }
}
