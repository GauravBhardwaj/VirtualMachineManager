package com.vmware.vim25.mo.runtime;
import com.vmware.vim25.mo.config.PingHost;
import com.vmware.vim25.mo.connect.Connect;

/**
 * This class should be used for Demo. 
 * @author Gaurav
 *
 */
public class Runtime {
	
public static void main(String args[]) throws Exception{
	String hostnameSystemName = "130.65.130.25"; // host ip
	String machineIp="130.65.133.218"; // ip of machine to be checked for  
	String machineName ="TEAM14_Ubuntu10";  // name of vm
	
	//VmPowerOps vmops = new VmPowerOps();
	Connect c1 = new Connect();
	DisplayInventory di = new DisplayInventory();
	ManageHost mHost = new ManageHost();
	
	PrintStatistics ps = new PrintStatistics();
//	ps.printStats();
//	ps.PrintHostStats();
	
	
	//create alarm- created once so commenting now. 
	//Alarm a = new Alarm(machineName);
   // a.createAlarm();	
	
	//starting the hearbeatManager
	PingHost ph = new  PingHost(hostnameSystemName,machineIp);
	ph.start();
	
	
	
	// starting the snapshot -manager for this VM
	Snapshot snap1 = new Snapshot("TEAM14_Ubuntu10",null);
	//snap1.start();

	
	 //create one thread to create a snapsot for its vHost.
	HostSnapshot snap2 = new HostSnapshot("T14-vHost04-lab2 _.133.25",null);
	//snap2.start();
	
	
	
   //Thread.sleep(70000);
	
	//now when you have snapshot better migrate it to another vhost. 
	
	//ClonedBackUp bk = new ClonedBackUp();
	//bk.migrateClone("TEAM14_Ubuntu04");
	
	//trying to clone and then reverting with the snapshot we have
	//snap.revertToCurrentSnapshot("130.65.133.21", "TEAM14_Ubuntu04");
	
	
	 //just for testing
//	di.retrieveVM();
//	di.retrieveVhosts();
//	di.retrieveDC();
//	di.getResourcePool();
   
   
	//  vmops.StartVM(); Pass the VM name you want to start
	
	//PingHost.pingTrial(" 10.189.15.63");
	//PingHost.pingHost();
	//mHost.addHost("130.65.133.21");
	
	//snap1.createSnapshot("TEAM14_Ubuntu07");
	//Thread.sleep(50000);
	//bk.migrateClone("TEAM14_Ubuntu07");
	
	//snap1.revertToCurrentSnapshot(hostnameSystemName, "TEAM14_Ubuntu03");
	//di.getVMforHost("130.65.133.26");
}
}
