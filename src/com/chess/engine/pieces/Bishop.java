package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Стас on 03.04.2016.
 */
public class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

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
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExlusion(final int currentPosition, final int candidateOfSet ){
        return BoardUtils.FIRST_COLUMN[currentPosition]&&(candidateOfSet==-9||candidateOfSet==7);
    }
    private static boolean isEightColumnExlusion(final int currentPosition, final int candidateOfSet ){
        return BoardUtils.EIGHTH_COLUMN[currentPosition]&&(candidateOfSet==-7||candidateOfSet==9);
    }
}
