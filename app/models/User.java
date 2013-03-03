package models;

import javax.persistence.*;
import play.db.jpa.Model;
import java.util.*;
import java.sql.*;

@Entity
public class User extends Model {

	public String email;
	public String password;
	public Boolean sex;
	public Boolean activated = false;
	public String uuid;

	@OneToMany
	public List<Send> sends;

	// base
	public Timestamp createTime;
	public Timestamp updateTime;
	public String createUser;
	public String updateUser;
	public Boolean deleted = false;
}
