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

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addHexagon(1, 1, 3, Tileset.FLOWER, world);
        addHexagon(7, 4, 2, Tileset.FLOWER, world);
        addHexagon(11, 1, 4, Tileset.FLOWER, world);
        ter.renderFrame(world);
    }

    public static void addHexagon1(int xPos, int yPos, int length, TETile tile, TETile[][] world) {
        int xLen = (length - 2) * 3 + 4; //3l-2
        int yLen = 2 * length;
        // int empty = length - 1;
        for (int j = yPos; j < yPos + length; j++) {
            for (int i = xPos + length - 1 - (j - yPos); i < xPos + xLen - (length - 1 - (j - yPos)); i++) {
                world[i][j] = tile;
            }
        }
        for (int j = yPos + length; j < yPos + yLen; j++) {
            for (int i = xPos + (j - yPos - length); i < xPos + xLen - (j - yPos - length); i++) {
                world[i][j] = tile;
            }
        }
    }

    public static void addHexagon(int xPos, int yPos, int length, TETile tile, TETile[][] world) {
        int xLen = (length - 2) * 3 + 4; //3l-2 矩形宽x
        int curLen = length;
        for (int j = yPos; j < yPos + length; j++) {
            for (int i = 0; i < curLen; i++) {
                world[i + xPos + (xLen - curLen) / 2][j] = tile;
            }
            curLen += 2;
        }
        curLen = xLen;
        for (int j = yPos + length; j < yPos + length * 2; j++) {
            for (int i = 0; i < curLen; i++) {
                world[i + xPos + (xLen - curLen) / 2][j] = tile;
            }
            curLen -= 2;
        }
    }
}
