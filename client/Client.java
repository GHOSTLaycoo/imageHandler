/**
 * 
 */
package com.b510.image.client;

import java.io.File;

import com.b510.image.common.Common;
import com.b510.image.util.ImageUtil;
import com.b510.image.util.ScaledImageUtil;
import com.b510.image.util.TextUtil;

/**
 * This project is a tool for processing the image. <br>Including TWO functions :
 * <li>Color image to converted black and white picture.
 * <li>Imitating the original image to paint into the TXT file with alphabets.
 * You can change the code according to your requirement.
 * 
 * @author Hongten
 * @mail hongtenzone@foxmail.com
 * @created Jul 23, 2014
 */
public class Client {
	
	public static String SCALED_IMAGE = Common.SCALED + Common.FULL_STOP + ScaledImageUtil.getPostfix(Common.ORIGINAL);

	public static void main(String[] args) {
		//colorImage2BWPic();
		painting(Common.ORIGINAL, SCALED_IMAGE, 1);
//		remoteImageHandler("E:/images/ipad/photo");
	}

	/**
	 * Continuously review images
	 * @param remotePath Other folder path(Including pictures)
	 */
	private static void remoteImageHandler(String remotePath) {
		File file = new File(remotePath);
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if(fileList[i].isFile()){
				String originalImagePath = fileList[i].getAbsolutePath();
				System.out.println("Processing ... " + originalImagePath);
				SCALED_IMAGE =  Common.SCALED + Common.FULL_STOP + ScaledImageUtil.getPostfix(originalImagePath);
				painting(originalImagePath, SCALED_IMAGE, 1);
			}
			try {
				Thread.sleep(4000); // every four seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void painting(String originalImagePath, String scalImagepath, int scaled) {
		ScaledImageUtil.scaledImage(originalImagePath, scaled, scalImagepath);
		double[][] result = ImageUtil.getImageGRB(scalImagepath);
		StringBuffer stringBuffer = ImageUtil.getCanvas(result);
		TextUtil.write2File(stringBuffer);
	}

	/**
	 * Color image to converted black and white picture.
	 */
	private static void colorImage2BWPic() {
		File input = new File(Common.ORIGINAL_IMAGE);
		File out = new File(Common.PROCESSED_IMAGE);
		ImageUtil.colorImage2BlackAndWhitePicture(input, out);
	}
}
