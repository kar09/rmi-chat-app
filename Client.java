import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.lang.Thread;
import javax.swing.*;


public class Client extends Thread {
	
	public Client(){
		ac=new actionControl();
		drawWelcomeFrame();
	}
	//welcome gui///////////////////////////////////////
	private JLabel welcomeMessage,welcomeMessage1;
	private JButton wSignin,wSignup,wConnectButton;
	private JFrame welcomeFrame;
	private JPanel wPanel1,wPanel2,wPanel3;
	private JTextField wServerAddress;	
	private void drawWelcomeFrame(){
		welcomeFrame=new JFrame("Welcome");
		welcomeFrame.setSize(320,290);
		wPanel1=new JPanel(new GridLayout(2,1));
		welcomeMessage=new JLabel("                                  Welcome to RMI");
		welcomeMessage1=new JLabel( "                                Intranet Chat App");
		wSignin=new JButton("Sign in");
		wSignup=new JButton("Sign up");
		welcomeFrame.add(wPanel1);
		welcomeFrame.getContentPane().add(wPanel1);
		wPanel2=new JPanel(new GridLayout(5,1));
		wPanel2.add(new JLabel(" "));
		wPanel2.add(welcomeMessage);
		wPanel2.add(welcomeMessage1);
		wPanel2.add(new JLabel(" "));
		wPanel3=new JPanel(new GridLayout(4,1,10,10));
		wPanel3.add(wSignin);
		wPanel3.add(wSignup);
		wPanel3.add(new JLabel("Server IP Address: "));
		wServerAddress=new JTextField(20);
		wPanel3.add(wServerAddress);
		wPanel3.add(new JLabel(" "));
		wConnectButton=new JButton("Connect");
		wPanel3.add(wConnectButton);
		wPanel1.add(wPanel2);
		wPanel1.add(wPanel3);
		welcomeFrame.setLocation(350,150);
		welcomeFrame.setVisible(true);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wSignin.addActionListener(ac);
		wSignup.addActionListener(ac);
		wConnectButton.addActionListener(ac);
		wSignin.setEnabled(false);
		wSignup.setEnabled(false);
	}
	///// signin gui
	private JTextField sInUserName;
	private JPasswordField sInPassword;
	private JButton Signin,siBack;
	private JFrame signinFrame;
	private JPanel siPanel1,siPanel2,siPanel3;
	
	private void drawSigninFrame(){
		signinFrame=new JFrame();
		signinFrame.setSize(250,200);
		signinFrame.setLocation(350, 200);
		signinFrame.setVisible(true);
		signinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		siPanel1=new JPanel(new GridLayout(3,1));
		siPanel2=new JPanel(new GridLayout(2,2,5,5));
		siPanel2.add(new JLabel("UserName:"));
		sInUserName=new JTextField(20);
		siPanel2.add(sInUserName);
		siPanel2.add(new JLabel("Password:"));
		sInPassword=new JPasswordField(20); 
		siPanel2.add(sInPassword);
		siPanel1.add(siPanel2);
		siPanel3=new JPanel(new GridLayout(2,2,5,5));
		siPanel3.add(new JLabel(" "));siPanel3.add(new JLabel(" "));
		siBack=new JButton("Back");
		siPanel3.add(siBack);
		Signin=new JButton("Sign in");
		siPanel3.add(Signin);
		siPanel1.add(siPanel3);
		signinFrame.getContentPane().add(siPanel1);
		Signin.addActionListener(ac);
		siBack.addActionListener(ac);
	}
	private JTextField sUpUserName,sUpName,sUpAge;
	private JPasswordField sUpPassword,sUpConfPassword;
	private JButton Signup,suBack;
	private JFrame signupFrame;
	private JPanel suPanel1;
	//signup gui
	private void drawSignupFrame(){
		signupFrame=new JFrame();
		signupFrame.setSize(350,200);
		signupFrame.setLocation(350, 200);
		signupFrame.setVisible(true);
		signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		suPanel1=new JPanel(new GridLayout(7,2));
		sUpUserName=new JTextField(20);
		sUpPassword=new JPasswordField(20);
		sUpConfPassword=new JPasswordField(20);
		sUpName=new JTextField(20);
		sUpAge=new JTextField(20);
		suPanel1.add(new JLabel("UserName: "));
		suPanel1.add(sUpUserName);
		suPanel1.add(new JLabel("Name: "));
		suPanel1.add(sUpName);
		suPanel1.add(new JLabel("Password: "));
		suPanel1.add(sUpPassword);
		suPanel1.add(new JLabel("Confirm Password: "));
		suPanel1.add(sUpConfPassword);
		suPanel1.add(new JLabel("Age: "));
		suPanel1.add(sUpAge);
		suBack=new JButton("Back");
		suPanel1.add(suBack);
		Signup=new JButton("Sign up");
		suPanel1.add(Signup);
		signupFrame.getContentPane().add(suPanel1);
		suBack.addActionListener(ac);
		Signup.addActionListener(ac);
	}
	
	//chat gui
	private JButton send,signout;
	public static JButton recieveMsgButton;
	private JFrame chatFrame;
	private JPanel panel1,panel2,panel3,panel4,panel5,panel6;
	private JTextField message,username;
	private JTextArea chatBox;
	private JScrollPane chatScroll;
	private JLabel messagebar;
	@SuppressWarnings("static-access")
	private void drawChatApp(){
		chatBox=new JTextArea(8,40);
		chatBox.setEditable(false);
		chatBox.setFocusable(false);
		chatScroll=new JScrollPane(chatBox);
		panel1=new JPanel(new BorderLayout());
		panel1.add(new JLabel("Chat:",SwingConstants.LEFT),BorderLayout.PAGE_START);
		panel1.add(chatScroll);
		message=new JTextField(40);
		send=new JButton("Send");
		panel2=new JPanel();
		panel2.setLayout(new BoxLayout(panel2,BoxLayout.LINE_AXIS));
		panel2.add(message);
		panel2.add(send);
		signout=new JButton("Sign out");
		panel2.add(signout);
		panel3=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		panel3.add(new JLabel("You:"));
		panel4=new JPanel();
		panel4.setLayout(new BoxLayout(panel4,BoxLayout.PAGE_AXIS));
		panel4.add(panel1);
		panel4.add(Box.createVerticalStrut(10));
		panel4.add(panel3);
		panel4.add(panel2);
		panel6=new JPanel(new GridLayout(2,1));
		panel6.add(panel4);
		
		recieveMsgButton=new JButton("Receive Message");
		panel5=new JPanel(new GridLayout(6,2,10,10));
		username=new JTextField(20);
		//panel5.add(signout);
		//panel5.add(recieveMsgButton);
		panel5.add(new JLabel(" "));panel5.add(new JLabel(" "));
		panel5.add(new JLabel("                                Username: "));
		panel5.add(username);
		panel6.add(panel5);
		panel5.add(new JLabel(" "));panel5.add(new JLabel(" "));
		panel5.add(new JLabel(" "));panel5.add(new JLabel(" "));
		messagebar=new JLabel();
		panel5.add(messagebar);
		chatFrame=new JFrame("RMI Chat App, UserName: "+user);
		//chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatFrame.add(panel6);
		chatFrame.pack();
		chatFrame.setLocationRelativeTo(null);
		chatFrame.setVisible(true);
		signout.addActionListener(ac);
		recieveMsgButton.addActionListener(ac);
		send.addActionListener(ac);
		chatFrame.getRootPane().setDefaultButton(send);
		Runnable r=new recieveMsg();
		Thread t=new Thread(r);
		t.start();
		try {
			t.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class actionControl implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			Object obj=ae.getSource();
			if(obj==wSignin){
				welcomeFrame.setVisible(false);
				drawSigninFrame();
			}
			if(obj==wSignup){
				welcomeFrame.setVisible(false);
				drawSignupFrame();
			}
			if(obj==suBack){
				signupFrame.dispose();
				welcomeFrame.setVisible(true);
			}
			if(obj==siBack){
				signinFrame.dispose();
				welcomeFrame.setVisible(true);
			}
			if(obj==Signin){
				user=sInUserName.getText();
				if(isValidUser()){
					signinFrame.setVisible(false);
					drawChatApp();
				}
				else{
					JOptionPane.showMessageDialog(new Frame(), "You have entered wrong username or password, or the "
							+ "user is already online form some different computer, try again.");
				}
			}
			if(obj==Signup){
				try {
					signupRequest();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(obj==signout){
				removeFromOnlineList();
				chatFrame.dispose();
				signinFrame.dispose();
				welcomeFrame.setVisible(true);
				
			}
			//////// chat app control events ////////////////
			if(obj==send){
				String m=message.getText();
				if(m.length()>0){
					message.setText("");
					if(user.equals(username.getText())){
						JOptionPane.showMessageDialog(new Frame(),"You can't send messages to yourself.");
					}
					else{
						if(sendMessage(m)){
							if(!m.equals("You:- "))
								chatBox.append("You (to: "+username.getText()+"):- "+m+"\n");
							chatBox.setCaretPosition(chatBox.getDocument().getLength());
						}
					}
				}
			}
			if(obj==recieveMsgButton){
				String s=recieveMessage();
				if(s!=null)
					chatBox.append(s);
				chatBox.setCaretPosition(chatBox.getDocument().getLength());
			}
			if(obj==wConnectButton){
				String ipAddress;
				try {
					ipAddress=wServerAddress.getText();
					serverInt=(serverInterface)Naming.lookup("rmi://"+ipAddress+"/serverInterface");
					JOptionPane.showMessageDialog(new Frame(),"Connection Established");
					wSignin.setEnabled(true);
					wSignup.setEnabled(true);
				} catch (MalformedURLException | RemoteException | NotBoundException e) {
					JOptionPane.showMessageDialog(new Frame(),"Server not found.");
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(new Frame(),"Server not found.");
				}
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	private boolean isValidUser(){
		//return true;
		user=sInUserName.getText();
		String p=String.valueOf(sInPassword.getPassword());
		try {
			return serverInt.isValidUser(user,p);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	private boolean sendMessage(String m){
		String to=username.getText();
		try {
			if(!serverInt.sendMessage(to, user, m)){
				JOptionPane.showMessageDialog(new Frame(), "user with whom you want to chat does not exist.");
				return false;
			}
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	private void signupRequest() throws RemoteException{
		String username,name,pass,cpass;
		int age;
		username=sUpUserName.getText();
		name=sUpName.getText();
		pass=String.valueOf(sUpPassword.getPassword());
		cpass=String.valueOf(sUpConfPassword.getPassword());
		if((username.length()<1) || (name.length()<1) || (pass.length()<1) || ((sUpAge.getText()).length())<1){
			JOptionPane.showMessageDialog(new Frame(),"Empty Fields not allowed.");
		}
		else{
			try{
				age=Integer.parseInt(sUpAge.getText());
				if(!pass.equals(cpass)){
					JOptionPane.showMessageDialog(new Frame(),"Password confirmation not correct,try again.");
				}
				else if(serverInt.insertDetails(username, pass, name, age)){
					JOptionPane.showMessageDialog(new Frame(),"You are successfully registred with username: "+username);
				}
				else{
					JOptionPane.showMessageDialog(new Frame(),"Username already taken. Try another one.");
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(new Frame(),"Age must be an integral value.");
			}	
		}
	}
	private String recieveMessage(){
		try {
			return serverInt.recieveMessage(user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		new Client();
	}
	private void removeFromOnlineList(){
		try {
			serverInt.removeFromOnlineList(user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	private actionControl ac;
	public serverInterface serverInt;
	private String user;
}

class recieveMsg extends Thread{
	public void run(){
		while(true){
			Client.recieveMsgButton.doClick();
			}		
	}
}