package magio.ohmypet.model;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

import magio.ohmypet.util.Constants;

/**
 * Created by mini on 2016-08-22.
 */
public class Model_adopt implements Serializable {
    String title;
    String price;

    Model_User user;
    Model_Pet pet;

    ImageView picture;

    String position; // 분양 위치
    Date date; // 분양 가능 날짜
    String way; // 분양 방법

    String id;

    public Model_adopt() {

    }

    public Model_adopt(Model_User user, Model_Pet pet, String title) {
        this.user = user;
        this.pet = pet;
        this.title = title;
    }

    public Model_adopt(JSONObject jsonObject) {

        try{
//            event = new Event();
//            contents = new Contents();

            this.id = jsonObject.getString("_id");
//            this.user = (Model_User)jsonObject.getString("user");
//            this.pet = (Model_Pet)jsonObject.getString("pet");
            this.title = jsonObject.getString("title");
            this.price = jsonObject.getString("price");

        } catch (Exception e) {
            Log.d("Lettle","JSONException - " + e.toString());
        }
    }


    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", this.user);
            jsonObject.put("pet", this.pet);
            jsonObject.put("title", this.title);
            jsonObject.put("price", this.price);

            return jsonObject.toString();

        } catch (JSONException e) {
            Log.d(Constants.TAG,"JSONException - " + e.toString());
        }
        return null;
    }
}
