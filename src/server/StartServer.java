package server;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.ui.Homechat;

public final class StartServer {
	private static void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
	}

	public static void main(String[] args) {
		setSystemLookAndFeel();
		new Homechat().setVisible(true);
	}
}
