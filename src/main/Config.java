package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Fullscreen
            if (gp.fullScreenOn) {
                bw.write("On");
            }
            if (!gp.fullScreenOn) {
                bw.write("Off");
            }

            bw.newLine();

            // Music Volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // Sound Effects Volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            // Fullscreen
            if (s.equals("On")) {
                gp.fullScreenOn = true;
            }
            if (s.equals("Off")) {
                gp.fullScreenOn = false;
            }

            // Music Volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // Sound Effects Volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
