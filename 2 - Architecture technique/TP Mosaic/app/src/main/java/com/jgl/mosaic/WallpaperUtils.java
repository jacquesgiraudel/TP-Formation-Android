/**
 * Created by Jacques Giraudel
 */
package com.jgl.mosaic;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by Jacques Giraudel on 19/11/2015.
 */
public class WallpaperUtils {

    private static final int MOSAIC_IMAGE_SIDE = 400;

    public static Bitmap createMosaic(Context context){
        Bitmap mosaic = null;

        String imagePathes[] = new String[4];

        String sortClause = MyContentProvider.WallpaperImagesColumns.COLUMN_POSITION + " asc";
        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI, new String[]{MyContentProvider.WallpaperImagesColumns.COLUMN_IMAGE_LOCATION}, null, null, sortClause);
        cursor.moveToFirst();
        int i = 0;
        do {
            imagePathes[i] = cursor.getString(0);
            i++;
        }
        while (cursor.moveToNext());
        cursor.close();

        Bitmap image0 = getThumbnailFromPath(imagePathes[0]);
        Bitmap image1 = getThumbnailFromPath(imagePathes[1]);
        Bitmap image2 = getThumbnailFromPath(imagePathes[2]);
        Bitmap image3 = getThumbnailFromPath(imagePathes[3]);

        mosaic = Bitmap.createBitmap(MOSAIC_IMAGE_SIDE * 2, MOSAIC_IMAGE_SIDE * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mosaic);
        Paint paint = new Paint();
        canvas.drawBitmap(image0, 0, 0, paint);
        canvas.drawBitmap(image1, MOSAIC_IMAGE_SIDE, 0, paint);
        canvas.drawBitmap(image2, 0, MOSAIC_IMAGE_SIDE, paint);
        canvas.drawBitmap(image3, MOSAIC_IMAGE_SIDE, MOSAIC_IMAGE_SIDE, paint);

        return mosaic;
    }

    private static Bitmap getThumbnailFromPath(String imagePath){
        Bitmap bitmap = null;

        bitmap = BitmapFactory.decodeFile(imagePath);
        if (bitmap == null){
            bitmap = Bitmap.createBitmap(MOSAIC_IMAGE_SIDE,MOSAIC_IMAGE_SIDE,Bitmap.Config.ARGB_8888);
        }
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, MOSAIC_IMAGE_SIDE, MOSAIC_IMAGE_SIDE);

        return bitmap;
    }
}
