package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Стас on 16.04.2016.
 */
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();


        for (final int currentСandidateOfset : CANDIDATE_MOVE_COORDINATE) {

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentСandidateOfset);

            if (!BoardUtils.isValidateCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentСandidateOfset == 8 && board.getTile(candidateDestinationCoordinate).isTileOccupied()) {

                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentСandidateOfset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behintCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() + 8);
                if (!board.getTile(behintCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }

            }else if(currentСandidateOfset==7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition]&& this.pieceAlliance.isWhite()||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition]&&this.pieceAlliance.isBlack())))){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final  Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()){
                      // TODO more to do here
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                    }
                }

            } else if(currentСandidateOfset==9&&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition]&& this.pieceAlliance.isWhite()||
                      (BoardUtils.EIGHTH_COLUMN[this.piecePosition]&&this.pieceAlliance.isBlack())))){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final  Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()){
                        // TODO more to do here
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
