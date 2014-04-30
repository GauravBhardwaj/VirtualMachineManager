package com.vmware.vim25.mo.runtime;

import java.rmi.RemoteException;
import java.util.Date;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.config.Config;
import com.vmware.vim25.mo.connect.Connect;

public class HostSnapshot extends Thread {

	String vmname;
	String snapshotname;
	ServiceInstance si_host = Connect.getHostServiceInstance();

	public HostSnapshot(String vmname, String snapshotname) {
		this.vmname=vmname;
		this.snapshotname=snapshotname;

	}
	public void run(){
		System.out.println("Starting snapshot manager for "+vmname);

		while(true){
			try {
				createHostSnapshot(vmname);
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


	/**
	 * Connect to the another vCentre and get the host vMachine MOR and take its snapshot
	 */
	public void createHostSnapshot(String vmname) throws Exception{

		String snapshotname=vmname+"_app_snapshot"+new Date().toString().substring(0, 11);
		String desc="Snapshot created for VM: ";

		Folder rootFolder = si_host.getRootFolder();
		try {
			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmname);
			Task task = vm.createSnapshot_Task(snapshotname, desc, true, false); 

			if(task.waitForTask()==Task.SUCCESS)
			{
				System.out.println("Snapshot for host was created.");

			}

		} catch ( RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void revertToHostSnapshot() throws Exception {

		try {

			Folder rootFolder = si_host.getRootFolder();

			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmname);
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

}
