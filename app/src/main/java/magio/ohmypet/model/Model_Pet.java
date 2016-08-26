package magio.ohmypet.model;

import org.w3c.dom.Text;

/**
 * Created by mini on 2016-08-22.
 */
public class Model_Pet {
    Text category; // 카테고리
    Text kind; // 품종
    Text age; // 나이
    Text remarks; // remarks

    public Model_Pet() {

    }

    public Model_Pet(Text category, Text kind, Text age, Text remarks) {
        this.category = category;
        this.kind = kind;
        this.age = age;
        this.remarks = remarks;
    }

    public Text getCategory() {
        return category;
    }

    public void setCategory(Text category) {
        this.category = category;
    }

    public Text getKind() {
        return kind;
    }

    public void setKind(Text kind) {
        this.kind = kind;
    }

    public Text getAge() {
        return age;
    }

    public void setAge(Text age) {
        this.age = age;
    }

    public Text getRemarks() {
        return remarks;
    }

    public void setRemarks(Text remarks) {
        this.remarks = remarks;
    }
}
