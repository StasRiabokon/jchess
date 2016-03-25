package com.chess.engine.board;

/**
 * Created by Стас on 25.03.2016.
 */
public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = null ;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGHTH_COLUMN = null ;

    private BoardUtils(){
        throw new RuntimeException("You cannot instantiate me!");
    }

    public static boolean isValidateCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }


}
