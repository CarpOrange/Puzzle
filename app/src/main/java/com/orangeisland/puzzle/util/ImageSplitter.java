package com.orangeisland.puzzle.util;
 
import java.util.ArrayList;
import java.util.List;
 
import android.graphics.Bitmap;
 
public class ImageSplitter {
 
	public static List<Bitmap> split(Bitmap bitmap, int xPiece, int yPiece) {
 
		List<Bitmap> pieces = new ArrayList<Bitmap>(xPiece * yPiece);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pieceWidth = width / xPiece;
		int pieceHeight = height / yPiece;
		for (int i = 0; i < yPiece; i++) {
			for (int j = 0; j < xPiece; j++) {
				int xValue = j * pieceWidth;
				int yValue = i * pieceHeight;
				Bitmap bitmap1 = Bitmap.createBitmap(bitmap, xValue, yValue,
						pieceWidth, pieceHeight);
				pieces.add(bitmap1);
			}
		}
 
		return pieces;
	}
 
}