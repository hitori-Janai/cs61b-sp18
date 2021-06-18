package byog.Core;

import java.util.Random;
import java.util.TreeSet;

import org.junit.Test;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapGenerator {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static final TETile wall = Tileset.WALL;
    private static final TETile nothing = Tileset.NOTHING;
    private static final TETile roomspace = Tileset.FLOOR;

    private TreeSet<Room> rooms = new TreeSet<>();
    private TETile[][] world = null;
    private int mapWidth;
    private int mapHight;

    public void setTETile(Position p, TETile t) {
        world[p.x][p.y] = t;
    }

    public MapGenerator(int mapWidth, int mapHight) {
        this.mapWidth = mapWidth;
        this.mapHight = mapHight;
        world = new TETile[mapWidth][mapHight];
        fillNothing(world, mapWidth, mapHight);
    }

    class Room implements Comparable<Room> {
        int xPos;
        int yPos;
        int width;
        int height;

        boolean overlap() {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (!checkOnePosition(i + xPos, j + yPos)) {
                        return true;
                    }
                }
            }
            return false;
        }

        void updateWorld() {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    world[i + xPos][j + yPos] = roomspace;
                }
            }
            rooms.add(this);
            addWall();
        }

        void addWall() {
            for (int i = -1; i < width + 1; i++) {
                for (int j = -1; j < height + 1; j++) {
                    if (nothing.equals(world[i + xPos][j + yPos])) {
                        world[i + xPos][j + yPos] = wall;
                    }
                }
            }
        }

        void addWall(int xStart, int yStart, int xEnd, int yEnd) {
            if (xStart == xEnd) {
                for (int i = yStart; i <= yEnd; i++) {
                    world[xStart - 1][i] = wall;
                    world[xStart + 1][i] = wall;
                }
                for (int i = yEnd; i <= yStart; i++) {
                    world[xStart - 1][i] = wall;
                    world[xStart + 1][i] = wall;
                }
            }
        }

        public Room(int xPos, int yPos, int width, int height) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.width = width;
            this.height = height;
        }

        /**
         * 该点在地图范围-1. 没有重合
         * @param x
         * @param y
         * @return
         */
        boolean checkOnePosition(int x, int y) {
            if (x < 1 || x >= mapWidth - 1) {
                return false;
            }
            if (y < 1 || y >= mapHight - 1) {
                return false;
            }
            return nothing.equals(world[x][y]);
        }

        @Override
        public int compareTo(Room o) {
            int rs = this.xPos - o.xPos;
            if (rs == 0) {
                rs = this.yPos - o.yPos;
            }
            return rs;
        }

        void connect(Room r) {
            Position np = new Position(r.xPos, r.yPos);
            while (this.xPos + this.width - 1 < np.x) {
                // addWall(r.xPos,r.yPos,r.xPos,this.yPos);
                np = leftDraw(np, roomspace);
            }
            drawWalls(new Position(np.x - 1, np.y));//转弯处加方块
            drawWalls(new Position(np.x - 1, np.y - 1));//转弯处加方块
            drawWalls(new Position(np.x - 1, np.y + 1));//转弯处加方块
            while (this.yPos + this.height < np.y) {
                np = downDraw(np, roomspace);
            }

            while (this.yPos > np.y) {
                np = upDraw(np, roomspace);
            }
        }

        boolean isInRoom(Position p) {
            return xPos <= p.x && p.x < xPos + width && yPos <= p.y && p.y < yPos + height;
        }
    }

    class Position {
        int x;
        int y;

        /**
         * 当前点是否在地图范围内
         * @return
         */
        boolean checkBound() {
            if (x < 0 || x >= mapWidth) {
                return false;
            }
            if (y < 0 || y >= mapHight) {
                return false;
            }
            return true;
        }

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(Position p) {
            this.x = p.x;
            this.y = p.y;
        }

        /**
         * 当前点+1范围是否在地图内
         * @return
         */
        boolean checkBoundA1() {
            if (x < 1 || x >= mapWidth - 1) {
                return false;
            }
            if (y < 1 || y >= mapHight - 1) {
                return false;
            }
            return true;
        }
    }

    void randRooms(int chance) {
        for (int i = 0; i < chance; i++) {
            Room room = new Room(nextPos(mapWidth), nextPos(mapHight), RANDOM.nextInt(4) + 2, RANDOM.nextInt(4) + 2);
            if (!room.overlap()) {
                room.updateWorld();
            }
        }
    }

    /**
     * 中间位置概率高
     * 细节有问题
     * @return
     */
    public static int nextPos(int range) {
        int chance = (1 + range / 2) * range / 2 / 2 + ((range + 1) / 2 + 1) * ((range + 1) / 2) / 2;
        int nextNum = RANDOM.nextInt(chance) + 1;
        int rs = 0;
        for (int i = 1; i < range / 2 + 1; i++) {
            nextNum -= i;
            if (nextNum <= 0) {
                break;
            }
            rs++;
        }
        for (int i = (range + 1) / 2; i > 0; i--) {
            nextNum -= i;
            if (nextNum <= 0) {
                break;
            }
            rs++;
        }
        return rs;
    }

    @Test
    public void testNextPos() {
        System.out.println("helo");
    }

    TETile[][] generate() {
        // randomFill(world, mapWidth, mapHight);
        randRooms(30);
        Room lastRoom = rooms.pollFirst();
        for (Room room : rooms) {
            lastRoom.connect(room);
            lastRoom = room;
        }

        return world;
    }

    static void fillNothing(TETile[][] world, int WIDTH, int HEIGHT) {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                world[i][j] = nothing;
            }
        }

    }

    static void randomFill(TETile[][] world, int WIDTH, int HEIGHT) {

        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                switch (RANDOM.nextInt(3)) {
                case 0:
                    world[i][j] = wall;
                    break;
                case 1:
                    world[i][j] = nothing;
                    break;
                default:
                    world[i][j] = roomspace;
                    break;
                }
            }
        }
    }

    /**
     * 描绘当前坐标的下一块砖.
     * @param p 当前坐标
     * @param next 下一块砖样式
     * @return 下块砖坐标
     */
    Position upDraw(Position p, TETile next) {
        Position np = new Position(p.x, p.y + 1);
        setTETile(np, next);
        drawWalls(new Position(np.x - 1, np.y));
        drawWalls(new Position(np.x + 1, np.y));
        return np;
    }

    Position downDraw(Position p, TETile next) {
        Position np = new Position(p.x, p.y - 1);
        setTETile(np, next);
        drawWalls(new Position(np.x - 1, np.y));
        drawWalls(new Position(np.x + 1, np.y));
        return np;
    }

    Position leftDraw(Position p, TETile next) {
        Position np = new Position(p.x - 1, p.y);
        setTETile(np, next);
        drawWalls(new Position(np.x, np.y + 1));
        drawWalls(new Position(np.x, np.y - 1));
        return np;
    }

    Position rightDraw(Position p, TETile next) {
        Position np = new Position(p.x + 1, p.y);
        setTETile(np, next);
        drawWalls(new Position(np.x, np.y + 1));
        drawWalls(new Position(np.x, np.y - 1));
        return np;
    }

    void drawWalls(Position p) {
        if (nothing.equals(world[p.x][p.y])) {
            setTETile(p, wall);
        }
    }
}
