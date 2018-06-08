/**
 * 
 */
package com.b510.image.util;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.b510.image.common.Common;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Hongten
 * @created Jul 23, 2014
 */
public class ImageUtil {

	private static int height = 0;
	private static int width = 0;

	/**
	 * Color image is converted to black and white picture.
	 * @param input
	 * @param out
	 */
	public static void colorImage2BlackAndWhitePicture(File input, File out) {
		try {
			Image image = ImageIO.read(input);
			int srcH = image.getHeight(null);
			int srcW = image.getWidth(null);
			BufferedImage bufferedImage = new BufferedImage(srcW, srcH, BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics().drawImage(image, 0, 0, srcW, srcH, null);
			bufferedImage = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(bufferedImage, null);
			FileOutputStream fos = new FileOutputStream(out);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufferedImage);
			fos.close();
			System.out.println(Common.PROCESSED_SUCCESS);
		} catch (Exception e) {
			throw new IllegalStateException(Common.PROCESS_ERROR, e);
		}
	}

	/**
	 * Get the image(*.jpg, *.png.etc) GRB value
	 * @param filePath the original image path
	 * @return
	 */
	public static double[][] getImageGRB(String filePath) {
		File file = new File(filePath);
		double[][] result = null;
		if (!file.exists()) {
			return result;
		}
		try {
			BufferedImage bufImg = readImage(file);
			result = new double[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					double temp = Double.valueOf(bufImg.getRGB(x, y) & 0xFFFFFF);
					result[y][x] = Math.floor(Math.cbrt(temp));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Read the original image to convert BufferedImage
	 * @param file Original image path
	 * @return
	 * @see BufferedImage
	 * @throws IOException
	 */
	private static BufferedImage readImage(File file) throws IOException {
		BufferedImage bufImg = ImageIO.read(file);
		height = bufImg.getHeight();
		width = bufImg.getWidth();
		return bufImg;
	}
	
	/**
	 * Get the canvas, which is made up of alphabets.
	 * @param result
	 * @return
	 */
	public static StringBuffer getCanvas(double[][] result) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				fullBlank(result, stringBuffer, y, x);
			}
			stringBuffer.append(Common.NEW_LINE);
		}
		return new StringBuffer(stringBuffer.substring(0, stringBuffer.length() - 1));
	}

	/**
	 * Full blank
	 * @param result
	 * @param stringBuffer
	 * @param y
	 * @param x
	 */
	private static void fullBlank(double[][] result, StringBuffer stringBuffer, int y, int x) {
		if (result[y][x] > 0.0 && result[y][x] < 168.0) {
			fullBlackColor(result, stringBuffer, y, x);
		} else if (result[y][x] >= 168.0 && result[y][x] < 212.0) {
			fullGreyColor(result, stringBuffer, y, x);
		} else {
			fullWhiteColor(result, stringBuffer, y, x);
		}
	}

	/**
	 * Full black color, and you can change the alphabet if you need.
	 * @param result
	 * @param stringBuffer
	 * @param y
	 * @param x
	 */
	private static void fullBlackColor(double[][] result, StringBuffer stringBuffer, int y, int x) {
		if (result[y][x] > 0.0 && result[y][x] < 25.0) {
			stringBuffer.append(Common.R);
		} else if (result[y][x] >= 25.0 && result[y][x] < 50.0) {
			stringBuffer.append(Common.R);
		} else if (result[y][x] >= 50.0 && result[y][x] < 75.0) {
			stringBuffer.append(Common.A);
		} else if (result[y][x] >= 75.0 && result[y][x] < 100.0) {
			stringBuffer.append(Common.X);
		} else if (result[y][x] >= 100.0 && result[y][x] < 125.0) {
			stringBuffer.append(Common.R);
		} else if (result[y][x] >= 125.0 && result[y][x] < 150.0) {
			stringBuffer.append(Common.A);
		} else if (result[y][x] >= 150.0 && result[y][x] < 154.0) {
			stringBuffer.append(Common.X);
		} else if (result[y][x] >= 154.0 && result[y][x] < 158.0) {
			stringBuffer.append(Common.M);
		} else if (result[y][x] >= 158.0 && result[y][x] < 162.0) {
			stringBuffer.append(Common.W);
		} else if (result[y][x] >= 162.0 && result[y][x] < 168.0) {
			stringBuffer.append(Common.M);
		}
	}

	/**
	 * Full grey color
	 * @param result
	 * @param stringBuffer
	 * @param y
	 * @param x
	 */
	private static void fullGreyColor(double[][] result, StringBuffer stringBuffer, int y, int x) {
		if (result[y][x] >= 168.0 && result[y][x] < 172.0) {
			stringBuffer.append(Common.H);
		} else if (result[y][x] >= 172.0 && result[y][x] < 176.0) {
			stringBuffer.append(Common.E);
		} else if (result[y][x] >= 176.0 && result[y][x] < 180.0) {
			stringBuffer.append(Common.H);
		} else if (result[y][x] >= 180.0 && result[y][x] < 184.0) {
			stringBuffer.append(Common.H);
		} else if (result[y][x] >= 184.0 && result[y][x] < 188.0) {
			stringBuffer.append(Common.J);
		} else if (result[y][x] >= 188.0 && result[y][x] < 192.0) {
			stringBuffer.append(Common.L);
		} else if (result[y][x] >= 192.0 && result[y][x] < 196.0) {
			stringBuffer.append(Common.C);
		} else if (result[y][x] >= 196.0 && result[y][x] < 200.0) {
			stringBuffer.append(Common.V);
		} else if (result[y][x] >= 200.0 && result[y][x] < 204.0) {
			stringBuffer.append(Common.Z);
		} else if (result[y][x] >= 204.0 && result[y][x] < 208.0) {
			stringBuffer.append(Common.Q);
		} else if (result[y][x] >= 208.0 && result[y][x] < 212.0) {
			stringBuffer.append(Common.T);
		}
	}

	/**
	 * Full white color
	 * @param result
	 * @param stringBuffer
	 * @param y
	 * @param x
	 */
	private static void fullWhiteColor(double[][] result, StringBuffer stringBuffer, int y, int x) {
		if (result[y][x] >= 212.0 && result[y][x] < 216.0) {
			stringBuffer.append(Common.r);
		} else if (result[y][x] >= 216.0 && result[y][x] < 220.0) {
			stringBuffer.append(Common.s);
		} else if (result[y][x] >= 220.0 && result[y][x] < 224.0) {
			stringBuffer.append(Common.w);
		} else if (result[y][x] >= 224.0 && result[y][x] < 228.0) {
			stringBuffer.append(Common.u);
		} else if (result[y][x] >= 228.0 && result[y][x] < 232.0) {
			stringBuffer.append(Common.l);
		} else if (result[y][x] >= 232.0 && result[y][x] < 236.0) {
			stringBuffer.append(Common.i);
		} else if (result[y][x] >= 236.0 && result[y][x] < 240.0) {
			stringBuffer.append(Common.e);
		} else if (result[y][x] >= 240.0 && result[y][x] < 244.0) {
			stringBuffer.append(Common.COMMA);
		} else if (result[y][x] >= 244.0 && result[y][x] < 248.0) {
			stringBuffer.append(Common.m);
		} else if (result[y][x] >= 248.0 && result[y][x] < 252.0) {
			stringBuffer.append(Common.a);
		} else if (result[y][x] >= 252.0 && result[y][x] < 257.0) {
			stringBuffer.append(Common.BLANK);
		}
	}
}
