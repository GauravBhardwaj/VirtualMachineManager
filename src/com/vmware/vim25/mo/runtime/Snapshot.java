package com.vmware.vim25.mo.runtime;

import java.rmi.RemoteException;
import java.util.Date;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;
import com.vmware.vim25.mo.config.Config;
import com.vmware.vim25.mo.connect.Connect;

/*
 * Provides the functionality to create a snapshot of VM every 10 minutes.
 */
public class Snapshot extends Thread {
          
	String vmname;
	String snapshotname;
			
	
	public Snapshot(String vmname,String snapshot){
		this.vmname=vmname;
		this.snapshotname=snapshotname;
	}
	
	ServiceInstance si = Connect.getServiceInstance();
	int snapshotnumber=0;
	
	
	
	//Folder rootFolder = si.getRootFolder();
	
	/**
	 * This method will revert the machine to current OR the last snapshot taken.
	 * @param vmname
	 */
public void revertToCurrentSnapshot() throws Exception{
	
	try {
	//	ServiceInstance si = Connect.getServiceInstance();
		Folder rootFolder = si.getRootFolder();
		
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmname);
		//HostSystem vhost = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem", hostname); 
		Task task = vm.revertToCurrentSnapshot_Task(null);

		if(task.waitForTask()==Task.SUCCESS)
		{
			System.out.println("Snapshot was reverted on Host: ");
		}

	} catch ( RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}
	
	/**
	 * MEthod to create a snapshot for this VM	
	 * @param vmname
	 * @throws Exception 
	 */

public void createSnapshot(String vmname) throws Exception{
	snapshotnumber++;
	String snapshotname=vmname+"_app_snapshot"+new Date().toString().substring(0, 11)+"_"+snapshotnumber;
	String desc="Snapshot created for VM: "+vmname;

		
	ServiceInstance host_si = new ServiceInstance(Config.getVmwareHostURL(),Config.getVmwareLoginId(),Config.getVmwarePassword(), true);
	Folder rootFolder = host_si.getRootFolder();
	try {
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", "VM1");
		System.out.println("===Starting SnapShot creation===");
		Task task = vm.createSnapshot_Task(snapshotname, desc, true, false); 
		
		if(task.waitForTask()==Task.SUCCESS)
		{
			System.out.println("Snapshot was created.");
						
		}

	} catch ( RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public void run(){
	System.out.println("Starting snapshot manager for "+vmname);
	
	while(true){
	try {
		createSnapshot(vmname);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}     
	try {
		Thread.sleep(1000000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}


}



