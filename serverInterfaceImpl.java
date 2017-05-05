import java.net.Inet4Address;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
public class serverInterfaceImpl extends UnicastRemoteObject implements serverInterface {

	private static final long serialVersionUID = 1L;
	PreparedStatement stat;
	Connection con;
	protected serverInterfaceImpl() throws RemoteException {
		super();
		onlineUsers=new HashSet<String>();
		message=new ConcurrentHashMap<String,Message>();
	}

	@Override
	public boolean insertDetails(String uname, String password, String name, int age) throws RemoteException {
		//////////database
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi_chat_database","root","");
			stat=con.prepareStatement("insert into users(username,password,name,age) values(?,?,?,?)");
			stat.setString(1,uname);
			stat.setString(2,password);
			stat.setString(3,name);
			stat.setInt(4,age);
			int rowsAffected=stat.executeUpdate();
			if(rowsAffected>0){
				return true;
			}
		}
		catch(Exception v)
		{
			System.out.println(v);
		}
		return false;
	}

	@Override
	public boolean sendMessage(String to, String from, String msg)throws RemoteException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection co=DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi_chat_database","root","");
			Statement st=co.createStatement();
			ResultSet rs=st.executeQuery("select * from users where username='"+to+"'");
			rs.next();
			if(rs.getString(1)==null || rs.getString(1).length()==0){
				return false;
			}
			System.out.println(to+" "+from+" "+msg+"\n");
			Message m=new Message();
			if(message.containsKey(to)){
				m=message.get(to);
				String t1,t2;
				t1=m.getMessage();
				t2=t1+"\n"+from+":-"+msg;
				m.setMessage(t2);
				message.replace(to, m);
			}
			else{
				m.setFrom(from);
				m.setMessage(msg);
				message.put(to,m);
			}
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean isValidUser(String username, String password)throws RemoteException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection co=DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi_chat_database","root","");
			Statement st=co.createStatement();
			ResultSet rs=st.executeQuery("select * from users where username='"+username+"'");
			rs.next();
			String u,p;
			u=rs.getString(1);
			p=rs.getString(2);
			if(p.equals(password) && u.equals(username)){
				if(onlineUsers.contains(username)){
					return false;
				}
				onlineUsers.add(username);
				return true;
			}	
			else{
				return false;
			}
				
		}
		catch(Exception e){
			return false;
		}
	}
	
	@Override
	public String recieveMessage(String uname) throws RemoteException {
		if(message.containsKey(uname)){
			Message m=new Message();
			m=message.get(uname);
			String f,mm;
			f=m.getFrom();
			mm=m.getMessage();
			message.remove(uname);
			return (f+":- "+mm+"\n");
		}
		return null;
	}
	
	public static void main(String[] args){
		System.setSecurityManager(new RMISecurityManager());
		try
		{
			serverInterfaceImpl instance=new serverInterfaceImpl();
			Naming.rebind("serverInterface",instance);
			System.out.println("Server Registered at: "+Inet4Address.getLocalHost().getHostAddress());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	private HashSet<String> onlineUsers;
	private ConcurrentHashMap<String,Message> message;
	class Message{
		private String from,msg;	
		public String getFrom(){
			return from;
		}
		public String getMessage(){
			return msg;
		}
		public void setMessage(String m){
			msg=m;
		}
		public void setFrom(String f){
			from=f;
		}
	}
	@Override
	public void removeFromOnlineList(String uname) throws RemoteException {
		onlineUsers.remove(uname);
	}
}
