package models;

import javax.persistence.Entity;

import play.db.jpa.Model;
import java.sql.*;

@Entity
public class BaseModel extends Model {
    public Timestamp createTime;
    public Timestamp updateTime;    
    public String createUser;
    public String updateUser;
    public Boolean deleted;

}
