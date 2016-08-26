package magio.ohmypet.model;

import android.graphics.drawable.Drawable;

/**
 * Created by mini on 2016-08-01.
 */
public class ItemData {
    private Drawable image;
    private String title;
    private String description;
    private int testImage;

    public void setImage(Drawable icon) {
        this.image = icon;
    }
    public void setTestImage(int imageNum) {
        this.testImage = imageNum;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDesc(String desc) {
        this.description = desc;
    }

    public Drawable getImage() {
        return this.image;
    }
    public int getTestImage() {
        return this.testImage;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDesc() {
        return this.description;
    }
}
