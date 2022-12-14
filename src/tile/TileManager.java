package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;

    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/maps/map_default_backup.txt");
    }

    public void loadMap(String txtMapPath){
        try {
            InputStream stream = getClass().getResourceAsStream(txtMapPath); // import map
            assert stream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); // read content of the stream

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow){
                String line = reader.readLine(); // read line and store it in variable

                while(col < gamePanel.maxWorldColumn){
                    String[] numbers = line.split(" "); // split the string at a space

                    int number = Integer.parseInt(numbers[col]); // parsing string to int

                    mapTileNum[col][row] = number;
                    col++;
                }
                if(col == gamePanel.maxWorldColumn){
                    col = 0;
                    row++;
                }
            }
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tile_base.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall_base_right.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/corner.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall_base_up.png")));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall_base_left.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall_base_down.png")));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/floor.png")));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/box.png")));
            tile[7].collision = true;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D graphics2D){

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldColumn && worldRow < gamePanel.maxWorldRow){
            // draw tile at x=0 y=0 position
            int tileNumber = mapTileNum[worldCol][worldRow]; // extract a tile number which is stored in mapTileNumber[0][0]

            int worldX = worldCol * gamePanel.tileSize; // check the world X starts from 0 up to 48 etc
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX; // its off set needs to + screenX
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

                graphics2D.drawImage(tile[tileNumber].image,screenX,screenY,gamePanel.tileSize,gamePanel.tileSize,null);
            }
            worldCol++;

            if(worldCol == gamePanel.maxWorldColumn){
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
