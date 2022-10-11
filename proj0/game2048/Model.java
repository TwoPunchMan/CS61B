package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        
        /*
            3
            2
            1
            0 1 2 3
         */
        
        board.setViewingPerspective(side);
        int size = board.size();
        for (int col = 0; col < size; col++) {
            /* 1. move all number tiles one direction
            [x,2,2,x] -> [2,2,x,x]
             */
            for (int row = size-1; row >= 0; row--) {
                Tile tile = board.tile(col,row);
                //don't bother with empty tiles
                if (tile == null) {
                    continue;
                }
                
                // find out where tile is supposed to move
                int tile_next_pos = 3;
                while (tile_next_pos >= row) {
                    if (board.tile(col, tile_next_pos) == null) {
                        break;
                    }
                    tile_next_pos--;
                }
                
                // move tile if needed
                if (tile_next_pos >= row) {
                    board.move(col, tile_next_pos, tile);
                    changed = true;
                }
            }
            
            /* 2. handle merge logic
            [2,2,x,x] -> [4,x,x,x]
             */
            for (int row = size-1; row >= 0; row--) {
                Tile current_tile = board.tile(col, row);
                int next_row_down = row - 1;
                if (next_row_down < 0) {
                    break;
                }
                Tile tile_adj = board.tile(col, next_row_down);
                if (current_tile == null || tile_adj == null) {
                    break;
                }
                
                int current_tile_value = current_tile.value();
                int tile_adj_value = tile_adj.value();
                if (current_tile_value == tile_adj_value) {
                    board.move(col, row, tile_adj);
                    this.score += current_tile_value * 2;
                    for (int p = next_row_down-1; p >= 0; p--) {
                        Tile tile_third = board.tile(col, p);
                        if (tile_third == null) {
                            break;
                        }
                        
                        if (p < size) {
                            board.move(col, p+1, tile_third);
                        }
                    }
                    changed = true;
                }
            }
        }
        /*
        for (int i = 0; i < board.size(); i++) {            // col
            boolean has_merged = false;
            for (int j = 3; j >= 0; j--) {                  // row
                // Starting from top -> down
                Tile current_tile = board.tile(i, j);
                
                // Don't bother with blank tiles
                if (current_tile != null) {
                    // Get blank movable spaces without hitting a border/other tile
                    // [x,2,x,x] -> 1
                    // [x,x,2,x] -> 2
                    int moves = get_num_blanks_before_tile(current_tile);
    
                    // Consider merge operation if adjacent cell has same value
                    boolean is_merge_possible = can_handle_merge(current_tile, moves);
    
                    // handle merge logic for additional move+
                    if (is_merge_possible && has_merged == false) {
                        moves++;
                        has_merged = true;
                        // don't merge if already merged
                    } else if (is_merge_possible && has_merged == true) {
                        has_merged = false;
                    }
    
                    // change if moved
                    // add points if merged
                    if (moves > 0) {
                        boolean is_merge = board.move(i, j + moves, current_tile);
                        if (is_merge) {
                            this.score += current_tile.value() * 2;
                        }
                        changed = true;
                    }
                }
            }
        }
        */
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        
        return changed;
    }
    
    public int get_num_blanks_before_tile(Tile tile) {
        int blank_moves = 0;
        int tile_col = tile.col();
        int tile_row = tile.row();

        int j = tile_row + 1;
        while (j <= 3) {
            Tile tile_pointer_adj = board.tile(tile_col, j);
            if (tile_pointer_adj == null) {
                blank_moves++;
            }
            j++;
        }
        
        return blank_moves;
    }
    
    public boolean can_handle_merge(Tile tile, int blank_moves) {
        int tile_col = tile.col();
        int tile_row = tile.row();
        int tile_adj_row = tile_row + blank_moves + 1;
        
        if (tile_adj_row > 3) {
            return false;
        }
        
        Tile tile_adj = board.tile(tile_col, tile_adj_row);
        if (tile_adj == null) {
            return false;
        }
        
        if (tile_adj.value() == tile.value()) {
            return true;
        }
        
        return false;
    }
    
    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int board_size = b.size();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int board_size = b.size();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (b.tile(i,j) == null) {
                    // skip null tile
                }
                else if (b.tile(i, j).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i,j) == null) {
                    return true;
                }
                
                int current_tile_value = b.tile(i,j).value();
                int north = i - 1;
                int east = j + 1;
                int south = i + 1;
                int west = j - 1;
                
                // adjacent tiles in bounds
                if (north < b.size() && north >= 0) {
                    if (b.tile(i,north) == null || current_tile_value == b.tile(north,j).value()) {
                        return true;
                    }
                }
                if (east < b.size() && east >= 0) {
                    if (b.tile(i,east) == null || current_tile_value == b.tile(i,east).value()) {
                        return true;
                    }
                }
                if (south < b.size() && south >= 0) {
                    if (b.tile(i,south) == null || current_tile_value == b.tile(south,j).value()) {
                        return true;
                    }
                }
                if (west < b.size() && west >= 0) {
                    if (b.tile(i,west) == null || current_tile_value == b.tile(i, west).value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
