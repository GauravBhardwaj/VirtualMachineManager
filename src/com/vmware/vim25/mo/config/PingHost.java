package com.vmware.vim25.mo.config;

import java.io.IOException;
import java.net.InetAddress;

import com.vmware.vim25.recovery.RecoveryManager;

/*
 * This class should keep pinging host in intervals of one minute and should create a alert if Host/Machine doesn't respond to
 * ping for 1 minute.
 */
public class PingHost extends Thread {
	final  String machineIP;
	private String hostName;
	//static final String hostip="130.65.133.236";
/*	static final String VM_1 = "130.65.133.160";
	static final String VM_2 = "130.65.133.154";
	static final String VM_3 = "130.65.133.161";
		*/
	//static final String VM_4 = "130.65.133.215"; 

	public PingHost(String hostName,String machineip){
		this.hostName = hostName;
		this.machineIP = machineip;
	 }
	
	
	/**
	 * Remove this method...
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public boolean pingHost() {
		 
		  String strCommand = "ping "+machineIP;
		  Process myProcess;
		try {
			myProcess = Runtime.getRuntime().exec(strCommand);
			myProcess.waitFor();
	        if(myProcess.exitValue() == 0) {
	        	//System.out.println("============ ping success ============");
	        	return true;
	        } else {
	        	//System.out.println("============ ping failure ============");
	        	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return false;	  
	}	

	
	public void run(){
		
		while(true){
			int attempt = 0;
			int deadCount=0;
    	     
			while(attempt<9){
    			attempt++;
    			System.out.println("Attempt:"+attempt+" Checking for heartbeat from: "+machineIP);
    			if(pingHost()){
    	    	System.out.println("alive");

    			}else {
    				deadCount++;
    				System.out.println("seems dead");
    			}
    		 
    			if(deadCount==5){
    				
        			System.out.println("machine is dead, Starting Recovery Manager");
        			RecoveryManager rm = new RecoveryManager(machineIP,hostName);
        			 rm.start();
        			// you gotta now sleep this HB manager 
        			 try {
        	    			Thread.sleep(5*60*1000);
        	        		} catch (InterruptedException e) {
        	    			// TODO Auto-generated catch block
        	    			e.printStackTrace();
        	        		}	
        		}
    			
    			
			}
			
		}
    	}	
}
