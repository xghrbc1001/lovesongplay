package models;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Send extends BaseModel {

    @ManyToOne
    public User user;

    public String toEmail;

    public String content;

    public Boolean isShow;
}
