package models;

import javax.persistence.*;
import play.db.jpa.Model;
import java.util.*;
import java.sql.*;
import play.data.validation.*;
@Entity
public class User extends Model {

  	@Required @Email	
	public String email;

	@Required 
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
