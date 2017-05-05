import java.rmi.*;

public interface serverInterface extends Remote {
	public boolean insertDetails(String uname,String password,String name,int age)throws RemoteException;
	public boolean sendMessage(String to,String from,String message)throws RemoteException;
	public boolean isValidUser(String username,String password)throws RemoteException;
	public String recieveMessage(String uname)throws RemoteException;
	public void removeFromOnlineList(String uname) throws RemoteException;
}
