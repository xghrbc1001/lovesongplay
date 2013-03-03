package models;

import javax.persistence.*;

import play.db.jpa.*;

import java.sql.*;

import play.*;
import java.util.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Send extends GenericModel {

	// uuid 替换自动生成的id
	@Id
		@GeneratedValue(generator = "system-uuid")
		@GenericGenerator(name = "system-uuid", strategy = "uuid")
		@Column(length = 32)
		public String id;

    @ManyToOne
    public User user;

    public String toEmail;

    public String content;

    public Boolean isShow=true;

    // 机会,默认次数为3
    public Integer chance=3;

    // 状态，0 未查看 1 失败 2 成功
    public Integer status=0;

   // base
    public Timestamp createTime;;
    public Timestamp updateTime;    
    public String createUser;
    public String updateUser;
    public Boolean deleted=false;

}
