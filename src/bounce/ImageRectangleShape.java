package bounce;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

/**
 * 
 * Class to represent an image.
 */
public class ImageRectangleShape extends RectangleShape {
    private Image image;
    protected static int sw;
    protected static int sh;

    public ImageRectangleShape(int deltaX, int deltaY, Image image) {
        super(DEFAULT_X_POS, DEFAULT_Y_POS, deltaX, deltaY);
        this.image = image;
    }

    public static Image makeImage(String imageFileName, int shapeWidth) {
        sw = shapeWidth;
        File f; // instance of File
        BufferedImage inputImage, outputImage; // instance of BufferedImage
        int w; // width of loaded image
        int h; // height of loaded image
        double sf; // scale factor
        Graphics2D g; // instnace of Graphics2D

        f = new File(imageFileName);

        try {
            inputImage = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Image not found");
            return null;
        }

        w = inputImage.getWidth();
        h = inputImage.getHeight();

        sf = w > sw ? (double) sw / w : 1;
        sh = (int) (sf * h);

        outputImage = new BufferedImage(sw, sh, inputImage.getType());
        g = outputImage.createGraphics();
        g.drawImage(inputImage, 0, 0, sw, sh, null);

        return outputImage;
    }

    @Override
    public void paint(Painter painter){
        painter.drawImage(this.image, x(), y(), sw, sh);
    }

}