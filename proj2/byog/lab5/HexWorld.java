package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static class Position {
        private int x;
        private int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Adds a hexagon to the world
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int y = 0; y < 2 * s; ++y) {
            int curY = p.y + y;
            int curXStart = p.x + offset(s, y);
            int width = rowWidth(s, y);
            Position rowStartP = new Position(curXStart, curY);
            addRow(world, rowStartP, width, t);
        }
    }

    public static int offset(int s, int y) {
        int offsetX = 0;
        if (y < s) {
            offsetX = -y;
        }
        else {
            offsetX = -(2 * s - 1 - y);
        }
        return offsetX;
    }

    public static int rowWidth(int s, int y) {
        int width = 0;
        if (y < s) {
            width = s + 2 * y;
        }
        else {
            width = 5 * s - 2 - 2 * y;
        }
        return width;
    }

    /**
     * @param p the leftmost position of cur row
     * @param width the number of tiles to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int i = 0; i < width; ++i) {
            int x = p.x + i;
            int y = p.y;
            world[x][y] = t.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * @param s the size of a single hexagon
     */
    public static void addWorld(TETile[][] world, int s) {

    }

}
