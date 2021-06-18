package byog.Core;

import java.util.HashMap;

import org.junit.Test;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class MapVisualTest {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new MapGenerator(WIDTH, HEIGHT).generate();
        ter.renderFrame(world);
    }

    @Test
    public void testNextPos() {
        HashMap<Integer, Integer> map = new HashMap<>();
        double chance = 100_0000;
        for (int i = 0; i < chance; i++) {
            int nextPos = MapGenerator.nextPos(WIDTH);
            // int nextPos = new Random().nextInt(HEIGHT);

            map.put(nextPos, map.get(nextPos) == null ? 1 : map.get(nextPos) + 1);
        }

        for (int i : map.keySet()) {
            System.out.println(i + "\t" + map.get(i) / chance);
        }
    }
}
