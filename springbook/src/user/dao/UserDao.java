package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.domain.User;

public abstract class UserDao {
//	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
	private ConnectionMaker connectionMaker;
	
	public UserDao() {
		connectionMaker = new DConnectionMaker();
	}
	
//		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/lotto_test", "ryu", "1234");
//		return c;
//	public class NUserDao extends UserDao{
//		public Connection getConnection() throws ClassNotFoundException, SQLException{
//			Connection c = DriverManager.getConnection("jdbc:mysql://localhost/lotto_test", "ryu", "1234");
//			return c;
//		}
//	}
//	
//	public class DUserDao extends UserDao{
//		public Connection getConnection() throws ClassNotFoundException, SQLException{
//			Connection c = DriverManager.getConnection("jdbc:mysql://localhost/lotto_test", "ryu", "1234");
//			return c;			
//		}
//	}
//	
	public class DConnectionMaker implements ConnectionMaker {
		public Connection makeConnection() throws ClassNotFoundException, SQLException{
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost/lotto_test", "ryu", "1234");
			return c;
		}
	}
	
	public void add(User user ) throws ClassNotFoundException, SQLException{
//		Class.forName("com.mysql.jdbc.Driver");
		//���̻� ������ �ʴ� ����̹�, SPI�� ���� �ڵ����� ��ϵ�
//		Connection c = getConnection();
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection c = getConnection();
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();
		
		return user;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		UserDao dao = new UserDao();
		
		User user = new User();
		user.setId("whiteShip");
		user.setName("��⼱");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " ��� ����");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId()+" ��ȸ ����");
	}
}
