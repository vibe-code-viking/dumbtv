package tv.dumbtv.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.File;

/** Optional local PNGs only. No downloading and no brand artwork bundled. */
final class LocalIcons {
    private LocalIcons() {}
    static Drawable load(Context context, String packageId) {
        if (!packageId.matches("[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)+")) return null;
        File file = new File(new File(Config.get(context).dir, "icons"), packageId + ".png");
        if (!file.isFile() || file.length() > 2 * 1024 * 1024) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getPath(), options);
            if (options.outWidth < 1 || options.outHeight < 1
                    || options.outWidth > 4096 || options.outHeight > 4096) return null;
            while (Math.max(options.outWidth, options.outHeight) / options.inSampleSize > 256) {
                options.inSampleSize = options.inSampleSize == 0 ? 2 : options.inSampleSize * 2;
            }
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
            return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
        } catch (RuntimeException error) {
            return null;
        }
    }
}
