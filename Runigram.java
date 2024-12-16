import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
		Color[][] testImage = {
			{ new Color(0, 0, 0), new Color(255, 0, 0) },
			{ new Color(0, 255, 0), new Color(0, 0, 255) }
		};
	
		// Print the test image
		System.out.println("Test Image:");
		print(testImage);
	
		// Test read and print together
		System.out.println("\nReading and printing 'tinypic.ppm':");
		Color[][] tinyPic = read("tinypic.ppm"); // Assuming 'read' is implemented
		print(tinyPic);

		System.out.println("horizontally flipped Image:");
		Color[][] flippedImage = flippedHorizontally(testImage);
		print(flippedImage);

		System.out.println("vertically flipped Image:");
		Color[][] verticallyFlipped = flippedVertically(testImage);
		print(verticallyFlipped);

		System.out.println("greyscaled image:");
		Color[][] greyImage = grayScaled(testImage);
		print(greyImage);

		Color[][] scaledImage = scaled(testImage, 3, 5);
		print(scaledImage);
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int r = in.readInt(); 
				int g = in.readInt(); 
				int b = in.readInt(); 
				image[i][j] = new Color(r, g, b);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				Color pixel = image[i][j];
				System.out.print("(" + pixel.getRed() + ", " + pixel.getGreen() + ", " + pixel.getBlue() + ") ");
			}
			System.out.println(); // Move to the next row
		}
	}

	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int numRows = image.length;    // n of rows
		int numCols = image[0].length; // n of columns
		Color[][] flippedImage = new Color[numRows][numCols];
	
		// Flip each row 
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				flippedImage[i][numCols - 1 - j] = image[i][j];
			}
		}
		return flippedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int numRows = image.length;
		int numCols = image[0].length; 
		Color[][] flippedImage = new Color[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				flippedImage[numRows - 1 - i][j] = image[i][j];
			}
		}
		return flippedImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int lumNum = (int)(0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		return new Color(lumNum, lumNum, lumNum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int numCols = image[0].length;
		int numRows = image.length;    
		Color[][] greyImage = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				greyImage[i][j] = luminance(image[i][j]);
			}
		}
		return greyImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int originalHeight = image.length;   
		int originalWidth = image[0].length;  
		Color[][] scaledImage = new Color[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int originalI = ((i * originalHeight) / height);
				int originalJ = ((j * originalWidth) / width);
				scaledImage[i][j] = image[originalI][originalJ];
			}
		}
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
    	int gren = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
    	int blue = (int) (alpha *c1.getBlue() + (1 - alpha) * c2.getBlue());
    	return new Color(red, gren, blue);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int numRows = image1.length;
		int numCols = image1[0].length;
		Color[][] blendedImage = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				blendedImage[i][j] = blend(image1[i][j], image2[i][j],  alpha);
			}
		}
		return blendedImage;	
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int numRows = source.length;
		int numCols = source[0].length;

		if (target.length != numRows || target[0].length != numCols) {
			target = scaled(target, numCols, numRows);
		}
		for (int step = 0; step <= n; step++) {
			double alpha = (double) (n - step) / n;
			Color[][] blendedImage = blend(source, target, alpha);
			display(blendedImage);
			StdDraw.pause(550);
		}	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

