package magio.ohmypet.model;

import org.w3c.dom.Text;

/**
 * Created by mini on 2016-08-22.
 */
public class Model_User {
    Text nickname;
    Text email;
    Text phone;
    Text address;

    public Model_User() {

    }

    public Model_User(Text nickname, Text email, Text phone, Text address) {
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Text getNickname() {
        return nickname;
    }

    public void setNickname(Text nickname) {
        this.nickname = nickname;
    }

    public Text getEmail() {
        return email;
    }

    public void setEmail(Text email) {
        this.email = email;
    }

    public Text getPhone() {
        return phone;
    }

    public void setPhone(Text phone) {
        this.phone = phone;
    }

    public Text getAddress() {
        return address;
    }

    public void setAddress(Text address) {
        this.address = address;
    }
}
