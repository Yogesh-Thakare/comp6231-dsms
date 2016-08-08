package com.front;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import FrontEnd.FrontEndInterfacePOA;

public class FrontEndInterfaceImpl extends FrontEndInterfacePOA {

	private static Long sequence = 1L;
	private Object lock = new Object();

	/**
	 * synchronized method which receives method name and arguments from client
	 */
	@Override
	public String executeOperation(String method, String badgeId,
			String firstName, String lastName, String description,
			String status, String address, String lastDate,
			String lastLocation, String recordId, String remoteServerName) {
		String leaderInfo = "", result = "";
		DatagramSocket socket = null;
		try {
			synchronized (lock) {
				do {
					leaderInfo = getLeaderInfo();
				} while (null == leaderInfo && "".equalsIgnoreCase(leaderInfo));

				Message msg = constructRequest(method, badgeId, firstName,
						lastName, description, status, address, lastDate,
						lastLocation, recordId, remoteServerName);
				String[] split = leaderInfo.split(":");
				
				//serialize the message object
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutput oo = new ObjectOutputStream(bos);
				oo.writeObject(msg);
				oo.close();
				socket = new DatagramSocket();
				InetAddress host = InetAddress.getByName(split[0]);
				byte[] serializedMsg = bos.toByteArray();
				DatagramPacket request = new DatagramPacket(serializedMsg,
						serializedMsg.length, host, Integer.parseInt(split[1]));
				socket.send(request);

				//send reply back to client
				byte[] buffer = new byte[100];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				socket.receive(reply);
				result = new String(reply.getData());
			}

		} catch (SocketException s) {
			System.out.println("Socket: " + s.getMessage());
		} catch (Exception e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		return result;
	}

	/**
	 * read the group leader info from file
	 * @return
	 */
	private String getLeaderInfo() {
		String info = "";
		String property = System.getProperty("user.dir");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(property + File.separator
					+ "leader.txt"));
			info = br.readLine();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return info;
	}

	/**
	 * construct Message object from method parameters 
	 * @param method
	 * @param badgeId
	 * @param firstName
	 * @param lastName
	 * @param description
	 * @param status
	 * @param address
	 * @param lastDate
	 * @param lastLocation
	 * @param recordId
	 * @param remoteServerName
	 * @return
	 */
	private synchronized Message constructRequest(String method,
			String badgeId, String firstName, String lastName,
			String description, String status, String address, String lastDate,
			String lastLocation, String recordId, String remoteServerName) {
		Message msg = new Message();
		msg.setMessageID(sequence);
		msg.setMethod(method);
		msg.setFirstName(firstName);
		msg.setLastName(lastName);
		msg.setBadgeId(badgeId);
		msg.setStatus(status);
		
		if (null != address || !"".equalsIgnoreCase(address))
			msg.setAddress(address);
		if (null != description || !"".equalsIgnoreCase(description))
			msg.setDescription(description);
		if (null != lastDate || !"".equalsIgnoreCase(lastDate))
			msg.setLastDate(lastDate);
		if (null != lastLocation || !"".equalsIgnoreCase(lastLocation))
			msg.setLastLocation(lastLocation);
		if (null != recordId || !"".equalsIgnoreCase(recordId))
			msg.setRecordId(recordId);
		if (null != remoteServerName || !"".equalsIgnoreCase(remoteServerName))
			msg.setRemoteServerName(remoteServerName);
		msg.setMessageType("operation");
		try {
			msg.setMessage(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		sequence++;

		return msg;
	}

}