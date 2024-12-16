import java.awt.Color;
public class Editor4 {
    public static void main (String[] args) {
        String sourceFile = args[0];
        int steps = Integer.parseInt(args[1]);
        Color[][] originalImage = Runigram.read(sourceFile);
        Color[][] greyscaleImage = Runigram.grayScaled(originalImage);
        Runigram.setCanvas(originalImage);
        Runigram.morph(originalImage, greyscaleImage, steps);
    }
}
