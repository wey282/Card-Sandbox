import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BackgroundGenerator {
    private static final long SEED = 10;
    private static final float FREQUENCY = 0.01f;
    
    public static Image generate(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        double[][] layerH = generateNoise(width, height);
        double[][] layerS = generateNoise(width, height);
        double[][] layerV = generateNoise(width, height);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, Color.HSBtoRGB((117+(float)layerH[i][j]*30)/360, (65 + (float)layerS[i][j]*35)/100, (30 + (float)layerV[i][j]*20)/100));
            }
        }
        
        return image;
    }
            
    private static double[][] generateNoise(int width, int height) {
        double[][] arr = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                arr[y][x] = OpenSimplex2S.noise3_ImproveXY((int)(Math.random()*SEED), x * FREQUENCY, y * FREQUENCY, 0.0);
            }
        }
        return arr;
    }
}
