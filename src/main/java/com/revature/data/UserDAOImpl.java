package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.User;
import com.revature.beans.Department;
import com.revature.beans.User.Role;
import com.revature.data.factory.Log;
import com.revature.utils.CassandraUtil;
@Log
public class UserDAOImpl implements UserDAO {
	private CqlSession session;// = CassandraUtil.getInstance().getSession();
	/*
	 * gets user id sent in
	 * Creates a query for all fields where id = id 
	 * returns user if found, if not returns null
	 */
	public UserDAOImpl() {
		this.session=CassandraUtil.getInstance().getSession();
	}
	public UserDAOImpl(CqlSession sess) {
		this.session=sess;
	}
	
	@Override
	public User getUser(int id) throws Exception {
		User u;
		String query = "select * from trms.user where id = ?";
		BoundStatement bound = session.prepare(query).bind(id);
		ResultSet rs = session.execute(bound);
		Row data = rs.one();
		if(data != null) {
			u = new User();
			u.setName(data.getString("name"));
			u.setPassword(data.getString("password"));
			u.setSupervisorid(data.getInt("superID"));
			u.setRole(Role.valueOf(data.getString("role")));
			u.setDepartmentid(data.getInt("departmentID"));
			u.setId(data.getInt("id"));
			u.setPendingReim(data.getDouble("pendingReim"));
			u.setReimbursementTotalForYear(data.getDouble("reimbursementTotalForYear"));
			return u;
		}
		return null;
	}

	/*
	 * gets user info sent in from service layer
	 * creates prepared statement 
	 * bind statement with information of user
	 * executes query
	 */
	@Override
	public void addUser(User user) throws Exception{
		String query = "Insert into user (name, role, id, password,superid,"
				+ "departmentid, pendingReim,reimbursementTotalForYear ) values (?,?,?,?,?,?,?,?); ";
		SimpleStatement s = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(user.getName(), user.getRole().toString(), 
				user.getId(), user.getPassword(), user.getSupervisorid(), user.getDepartmentid(), 
				user.getPendingReim(), user.getReimbursementTotalForYear());
		session.execute(bound);

	}

	/*
	 * creates query
	 * calls database
	 * iterates through the result set to create a list
	 * send back list
	 */
	@Override
	public List<User> getUsers() throws Exception{
		List<User> users = new ArrayList<User>();
		String query ="select * from user";
		ResultSet rs = session.execute(query);
		
		rs.forEach(data ->{
			User u = new User();
			u = new User();
			u.setName(data.getString("name"));
			u.setPassword(data.getString("password"));
			u.setSupervisorid(data.getInt("superID"));
			u.setRole(Role.valueOf(data.getString("role")));
			u.setDepartmentid(data.getInt("departmentID"));
			u.setId(data.getInt("id"));
			u.setPendingReim(data.getDouble("pendingReim"));
			u.setReimbursementTotalForYear(data.getDouble("reimbursementTotalForYear"));
			
			users.add(u);
		});
		return users;
	}

	/*
	 * gets user information that was updated
	 * creates query with prepared statement
	 * binds information
	 * executes query
	 */
	@Override
	public void updateUser(User user) throws Exception{
		String query = "update user set name =?, password =?,departmentid = ?, pendingReim=?, "
				+ "reimbursementTotalForYear=? where superid =? and id =? and role = ?";
		SimpleStatement s = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(user.getName(),user.getPassword(),
				user.getDepartmentid(),user.getPendingReim(),user.getReimbursementTotalForYear(),user.getSupervisorid(),user.getId(),user.getRole().toString());
		session.execute(bound);
		
	}

	/*
	 * 
	 */
	@Override
	public void deleteUser(User user) throws Exception{
	//contemplating on this
//		String query = "delete from question where superid = ? and id = ? and role = ?";
//		SimpleStatement s = new SimpleStatementBuilder(query)
//				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
//		BoundStatement bound = session.prepare(s).bind(user.getSupervisorid(),user.getId(),user.getRole().toString());
//		session.execute(bound);
	}

	@Override
	public void createUserTable() throws Exception{
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("id int, name text, departmentid int, role text,")
				.append("password text, superid int, pendingReim double,")
				.append("reimbursementTotalForYear double, primary key(id, superid, role));");
		//maybe need primary key to be id, departmentid, superid,role
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
	}

}
