package magio.ohmypet.model;

import android.widget.ImageView;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by mini on 2016-08-22.
 */
public class Model_adopt {
    ImageView picture;
    Text title;
    int price;

    Model_User user;
    Model_Pet pet;

    Text position; // 분양 위치
    Date date; // 분양 가능 날짜
    Text way; // 분양 방법

    public Model_adopt() {

    }

    public Model_adopt(Model_User user, Model_Pet pet, Text title) {
        this.user = user;
        this.pet = pet;
        this.title = title;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public Text getTitle() {
        return title;
    }

    public void setTitle(Text title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Model_User getUser() {
        return user;
    }

    public void setUser(Model_User user) {
        this.user = user;
    }

    public Model_Pet getPet() {
        return pet;
    }

    public void setPet(Model_Pet pet) {
        this.pet = pet;
    }

    public Text getPosition() {
        return position;
    }

    public void setPosition(Text position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Text getWay() {
        return way;
    }

    public void setWay(Text way) {
        this.way = way;
    }
}
