package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chatsocket.bean.AccountInfo;
import chatsocket.utils.Security;
import oracle.database.JDBCConnection;

public final class AccountManager {
	private static AccountManager instance = null;
	private static final String INIT_STATUS = "Hi everyone!";

	private AccountManager() {

	}

	public static void createInstance() {
		instance = new AccountManager();
	}

	public static AccountManager getInstance() {
		return instance;
	}

	private synchronized void addAccountToDatabase(HashMap<String, String> account) {
		try (PreparedStatement ps = JDBCConnection.getInstance().getDatabaseConnection()
				.prepareStatement("INSERT INTO CONTURIMDS VALUES(?, ?, ?, ?, ?)");) {
			ps.setInt(1, Integer.parseInt(account.get("USER_ID")));
			ps.setString(2, account.get("USERNAME"));
			ps.setString(3, account.get("PASSWORD"));
			ps.setString(4, account.get("DISPLAY_NAME"));
			ps.setString(5, account.get("STATUS"));
			ps.executeUpdate();
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private synchronized void changeAccountInfo(int id, String key, String value) {
		if (key == null || value == null)
			return;
		try (PreparedStatement ps = JDBCConnection.getInstance().getDatabaseConnection()
				.prepareStatement("UPDATE CONTURIMDS SET " + key + "= ? WHERE USER_ID = ?")) {
			ps.setString(1, value);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private boolean checkExistsAccount(String username) {
		try (PreparedStatement ps = JDBCConnection.getInstance().getDatabaseConnection()
				.prepareStatement("SELECT * FROM CONTURIMDS WHERE USERNAME = ?")) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery();) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void addAccount(String username, String passhash, String dispname) {
		if (username == null || passhash == null || dispname == null)
			return;
		if (checkExistsAccount(username) == true)
			return;
		HashMap<String, String> newAccount = new HashMap<String, String>();
		newAccount.put("USER_ID", Integer.toString(Security.getRandomInteger()));
		newAccount.put("USERNAME", username);
		newAccount.put("PASSWORD", passhash);
		newAccount.put("DISPLAY_NAME", dispname);
		newAccount.put("STATUS", INIT_STATUS);

		addAccountToDatabase(newAccount);
	}

	public void changeDisplayName(int id, String newDisplayName) {
		if (newDisplayName == null)
			return;
		changeAccountInfo(id, "DISPLAY_NAME", newDisplayName);
	}

	public void changeStatus(int id, String status) {
		if (status == null)
			return;
		changeAccountInfo(id, "STATUS", status);
	}

	public void changePasswordHash(int id, String passhash) {
		if (passhash == null)
			return;
		changeAccountInfo(id, "PASSWORD", passhash);
	}

	public AccountInfo getAccountInfo(String username, String passhash) {
		if (username == null || passhash == null)
			return null;

		try (PreparedStatement ps = JDBCConnection.getInstance().getDatabaseConnection()
				.prepareStatement("SELECT * FROM CONTURIMDS WHERE USERNAME = ? AND PASSWORD = ?")) {
			ps.setString(1, username);
			ps.setString(2, passhash);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next() == true) {
					AccountInfo accountInfo = new AccountInfo();
					accountInfo.setAccountId(rs.getInt("USER_ID"));
					accountInfo.setDisplayName(rs.getString("DISPLAY_NAME"));
					accountInfo.setStatus(rs.getString("STATUS"));
					accountInfo.setState(AccountInfo.STATE_ONLINE);
					return accountInfo;
				} else
					return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<AccountInfo> getAllAccountInfos() {
		List<AccountInfo> allFriends = new ArrayList<>();
		try (PreparedStatement ps = JDBCConnection.getInstance().getDatabaseConnection()
				.prepareStatement("SELECT * FROM CONTURIMDS")) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next() == true) {
					AccountInfo accountInfo = new AccountInfo();
					accountInfo.setAccountId(rs.getInt("USER_ID"));
					accountInfo.setDisplayName(rs.getString("DISPLAY_NAME"));
					accountInfo.setStatus(rs.getString("STATUS"));
					accountInfo.setState(AccountInfo.STATE_OFFLINE);
					allFriends.add(accountInfo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allFriends;
	}
}
