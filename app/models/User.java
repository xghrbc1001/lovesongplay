package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends BaseModel {

    public String email;
    public String password;
    public Boolean sex;
    public Boolean active;
       
}
