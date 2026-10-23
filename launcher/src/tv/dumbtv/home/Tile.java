package tv.dumbtv.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.tv.TvInputManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

/** A card drawn in one pass: banner, or icon plus name, or an input name; focus ring on top. */
final class Tile extends View {
    private static final float FOCUS_SCALE = 1.08f;

    final Item item;
    final String kind;
    private final CharSequence title;
    private final int cardW, cardH, labelH;
    private final boolean labelAlways, labelOnFocus;
    private final float radius, stroke;
    private final RectF card = new RectF();
    private final Path clip = new Path();
    private final Path arrow = new Path();
    private final Paint brand = new Paint();
    private final Paint fade = new Paint();
    private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint ring = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint edge = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint outerEdge = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dot = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint inner = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint under = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private boolean moving;
    private final boolean textArtwork;
    private final boolean cornerIcons;
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    Tile(Context c, Item item, String kind, CharSequence title, int cardW, int cardH,
            String labels, int accent) {
        super(c);
        this.item = item;
        this.kind = kind;
        this.title = title;
        textArtwork = "text".equals(Config.get(c).get("artwork", "original"));
        cornerIcons = "on".equals(Config.get(c).get(Config.CORNER_ICONS));
        android.graphics.ColorMatrix mono = new android.graphics.ColorMatrix();
        mono.setSaturation(0);
        iconPaint.setColorFilter(new android.graphics.ColorMatrixColorFilter(mono));
        iconPaint.setAlpha(255);
        this.cardW = cardW;
        this.cardH = cardH;
        boolean app = Catalog.APPS.equals(kind);
        labelH = app && !textArtwork ? Ui.dp(c, 26) : 0;
        labelAlways = "always".equals(labels);
        labelOnFocus = "focus".equals(labels);
        radius = Ui.dp(c, 10);
        stroke = Math.max(1, Ui.dp(c, 1f));

        fill.setColor(textArtwork ? 0xFF101010 : app ? Ui.SURFACE : 0xFF17181B);
        outerEdge.setColor(android.graphics.Color.BLACK);
        outerEdge.setStyle(Paint.Style.STROKE);
        outerEdge.setStrokeWidth(4f); // Two physical pixels outside the tile.
        edge.setColor(android.graphics.Color.BLACK);
        edge.setStyle(Paint.Style.STROKE);
        edge.setStrokeWidth(Ui.dp(c, 2f));
        ring.setStyle(Paint.Style.STROKE);
        ring.setStrokeWidth(stroke);
        ring.setColor(accent);
        dot.setColor(0xFF34C759);
        inner.setColor(Ui.TEXT);
        inner.setTypeface(Ui.MEDIUM);
        inner.setTextAlign(Paint.Align.CENTER);
        inner.setTextSize(Ui.dp(c, app ? 14 : 17));
        under.setColor(0xFFA3A3A3);
        under.setTypeface(Ui.MEDIUM);
        under.setTextAlign(Paint.Align.CENTER);
        under.setTextSize(Ui.dp(c, 13));

        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
        setContentDescription(title);
    }

    void setMoving(boolean on) {
        moving = on;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(cardW, cardH + labelH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        card.set(0, 0, cardW, cardH);
        clip.reset();
        clip.addRoundRect(card, radius, radius, Path.Direction.CW);
        int[] colors = BrandColors.forPackage(item.id);
        if (textArtwork && Catalog.APPS.equals(kind) && colors != null
                && "brand".equals(Config.get(getContext()).get(Config.TILE_STYLE))) {
            brand.setShader(new android.graphics.LinearGradient(0, 0, cardW, 0,
                    colors, null, android.graphics.Shader.TileMode.CLAMP));
            fade.setShader(new android.graphics.LinearGradient(0, 0, 0, cardH,
                    new int[] {0xFF101010, 0xFF101010, 0xD6101010, 0x87101010, 0x00101010},
                    new float[] {0f, .25f, .5f, .75f, 1f}, android.graphics.Shader.TileMode.CLAMP));
        }
        setPivotX(cardW / 2f);
        setPivotY(cardH / 2f);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previous) {
        super.onFocusChanged(focused, direction, previous);
        float s = focused ? FOCUS_SCALE : 1f;
        animate().scaleX(s).scaleY(s).setDuration(110).start();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable banner = item.banner, icon = item.icon;
        canvas.save();
        canvas.clipPath(clip);
        canvas.drawRect(card, fill);
        if (brand.getShader() != null) {
            canvas.drawRect(card, brand);
            canvas.drawRect(card, fade);
        }
        if (textArtwork && Catalog.APPS.equals(kind)) {
            inner.setTypeface(android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.BOLD));
            String text = title.toString().toUpperCase(java.util.Locale.ROOT);
            float size = Ui.dp(getContext(), 17);
            inner.setTextSize(size);
            while (inner.measureText(text) > cardW - Ui.dp(getContext(), 24) && size > Ui.dp(getContext(), 10)) inner.setTextSize(--size);
            canvas.drawText(text, cardW / 2f, cardH / 2f - (inner.ascent() + inner.descent()) / 2, inner);
            if (cornerIcons && icon != null) {
                int maxWidth = Ui.dp(getContext(), 28), maxHeight = Ui.dp(getContext(), 18);
                float aspect = (float) Math.max(1, icon.getIntrinsicWidth()) / Math.max(1, icon.getIntrinsicHeight());
                int width = Math.min(maxWidth, Math.round(maxHeight * aspect));
                int height = Math.min(maxHeight, Math.round(width / aspect));
                int right = cardW - Ui.dp(getContext(), 9), top = Ui.dp(getContext(), 9);
                int layer = canvas.saveLayer(right-width, top, right, top+height, iconPaint);
                icon.setBounds(right-width, top, right, top+height);
                icon.draw(canvas);
                canvas.restoreToCount(layer);
            }
        } else if (banner != null) {
            banner.setBounds(0, 0, cardW, cardH);
            banner.draw(canvas);
        } else if (icon != null) {
            int size = cardH * 46 / 100;
            int left = (cardW - size) / 2, top = cardH * 12 / 100;
            icon.setBounds(left, top, left + size, top + size);
            icon.draw(canvas);
            drawLine(canvas, inner, cardW / 2f, cardH - cardH * 13 / 100f);
        } else if (!Catalog.APPS.equals(kind)) {
            drawLine(canvas, inner, cardW / 2f, cardH / 2f - (inner.ascent() + inner.descent()) / 2);
        }
        // Cover the clipped gradient boundary before drawing the focus ring.
        float edgeHalf = edge.getStrokeWidth() / 2f;
        canvas.drawRoundRect(edgeHalf, edgeHalf, cardW - edgeHalf, cardH - edgeHalf,
                radius - edgeHalf, radius - edgeHalf, edge);
        if (moving) canvas.drawColor(0x99000000);
        canvas.restore();

        canvas.drawRoundRect(card, radius, radius, outerEdge);

        if (item.state == TvInputManager.INPUT_STATE_CONNECTED) {
            float r = Ui.dp(getContext(), 3.5f);
            canvas.drawCircle(cardW - r * 3, r * 3, r, dot);
        }
        if (isFocused() || moving) {
            float half = stroke / 2;
            ring.setPathEffect(moving ? new DashPathEffect(new float[] {stroke * 3, stroke * 2}, 0) : null);
            canvas.drawRoundRect(half, half, cardW - half, cardH - half, radius - half, radius - half, ring);
        }
        if (moving) drawArrows(canvas);
        boolean label = (textArtwork || banner != null) && (labelAlways || labelOnFocus && isFocused());
        if (labelH > 0 && label) {
            drawLine(canvas, under, cardW / 2f, cardH + labelH - Ui.dp(getContext(), 6));
        }
    }

    private void drawLine(Canvas canvas, TextPaint paint, float x, float baseline) {
        float room = cardW - Ui.dp(getContext(), 16);
        CharSequence s = TextUtils.ellipsize(title, paint, room, TextUtils.TruncateAt.END);
        canvas.drawText(s, 0, s.length(), x, baseline, paint);
    }

    private void drawArrows(Canvas canvas) {
        float h = cardH / 6f, w = h * 0.7f, mid = cardH / 2f, pad = h * 0.8f;
        inner.setStyle(Paint.Style.FILL);
        arrow.reset();
        arrow.moveTo(pad, mid);
        arrow.lineTo(pad + w, mid - h / 2);
        arrow.lineTo(pad + w, mid + h / 2);
        arrow.close();
        arrow.moveTo(cardW - pad, mid);
        arrow.lineTo(cardW - pad - w, mid - h / 2);
        arrow.lineTo(cardW - pad - w, mid + h / 2);
        arrow.close();
        canvas.drawPath(arrow, inner);
    }
}
