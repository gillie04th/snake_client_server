package view;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.AbstractController;
import controller.ControllerSnakeGame;
import model.Game;
import model.SnakeGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;
import java.util.Observable;

public class ViewLogin implements Observer {

	JFrame frame;

	AbstractController controller;
	JButton submit;
	JLabel lPassword, lUsername;
	JTextField tUsername;
	JPasswordField tPassword;
	JButton button;

	public ViewLogin (AbstractController controller, Observable obs)  {

		obs.addObserver(this);
		this.controller = controller;
		// Panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		// Frame
		frame= new JFrame();
		frame.setTitle("Login");
		frame.setLocation(new Point(500, 300));
		frame.add(panel);
		frame.setSize(new Dimension(400, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Label Nom
		lUsername = new JLabel("Nom");
		lUsername.setBounds(100, 8, 70, 20);
		panel.add(lUsername);
		// TextField Nom
		tUsername = new JTextField();
		tUsername.setBounds(100, 27, 193, 28);
		panel.add(tUsername);
		// Label Password
		lPassword = new JLabel("Mot de passe");
		lPassword.setBounds(100, 55, 70, 20);
		panel.add(lPassword);
		// TextField Password
		tPassword = new JPasswordField();
		tPassword.setBounds(100, 75, 193, 28);
		panel.add(tPassword);
		// Bouton Submit
		submit = new JButton("Submit");
		submit.setBounds(100, 110, 90, 25);
		submit.setForeground(Color.WHITE);
		submit.setBackground(Color.BLACK);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement){
				((ControllerSnakeGame)controller).login(tUsername.getText(), tPassword.getText());
				update(obs, null);
			}
		});

		frame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game)o;
		if(((SnakeGame)game).isUserLogged()){
			//frame.setVisible(false);
			frame.dispose();
		}
	}


}