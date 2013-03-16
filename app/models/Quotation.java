package models;

import javax.persistence.*;

import play.db.jpa.*;

import java.sql.*;

import play.*;
import java.util.*;
import play.data.validation.*;

@Entity
public class Quotation extends Model {

	public String content;

	// base
	public Timestamp createTime;;
	public Timestamp updateTime;
	public String createUser;
	public String updateUser;
	public Boolean deleted = false;

}
