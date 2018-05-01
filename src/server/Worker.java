package server;

import java.io.IOException;
import java.net.Socket;

import chatsocket.bean.AccountInfo;
import chatsocket.bean.ChatRequest;
import chatsocket.bean.ChatResult;
import chatsocket.bo.ITransmission;
import chatsocket.bo.ObjectAdapter;
import chatsocket.bo.Protocol;
import chatsocket.bo.SocketTransmission;
import chatsocket.utils.StreamUtilities;

public class Worker {
	private AccountInfo myAccount = null;
	private ITransmission transmission;
	private ObjectAdapter objectAdapter;
	private Protocol protocol;
	private OnRequestReceivedListener mOnRequestReceivedListener = null;
	private OnAuthenticatedListener mOnAuthenticatedListener = null;

	public void setOnReceivedDataListener(OnRequestReceivedListener listener) {
		mOnRequestReceivedListener = listener;
	}
	
	public void setOnAuthenticatedListener(OnAuthenticatedListener listener) {
		mOnAuthenticatedListener = listener;
	}

	public void response(ChatResult result) throws IOException {
		if(result == null)
			return;
		protocol.sendObject(result);
	}

	public void startBridge() throws IOException {
		while (true) {
			Object receivedObject = protocol.receiveObject();
			if (receivedObject == null)
				break;
			if (receivedObject instanceof ChatRequest) {
				if (mOnRequestReceivedListener != null)
					response(mOnRequestReceivedListener.onRequestReceived(this, (ChatRequest) receivedObject));
			}
		}
	}

	public void release() {
		StreamUtilities.tryCloseStream(transmission);
	}

	public Worker(Socket socket) throws IOException {
		transmission = new SocketTransmission(socket);
		objectAdapter = new ObjectAdapter();
		protocol = new Protocol(objectAdapter, transmission);
	}
	
	public void setAccount(AccountInfo accountInfo) {
		this.myAccount = accountInfo;
		if (accountInfo != null && mOnAuthenticatedListener != null)
			mOnAuthenticatedListener.onAuthenticated(this);
	}

	public AccountInfo getAccount() {
		return myAccount;
	}

	public interface OnAuthenticatedListener {
		void onAuthenticated(Worker worker);
	}
	
	public interface OnRequestReceivedListener {
		ChatResult onRequestReceived(Worker sender, ChatRequest request);
	}
}
