package th.ac.kmitl.a59070182;

import android.content.ContentValues;

public class Users {
    private String userId;
    private String name;
    private int age;
    private String password;

    private ContentValues contentValues = new ContentValues();

    public Users(String userId, String name, int age, String password) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public void setContentValues() {
        this.contentValues.put("_userId", getUserId());
        this.contentValues.put("_name", getName());
        this.contentValues.put("_age", getAge());
        this.contentValues.put("_password", getPassword());

    }
}
