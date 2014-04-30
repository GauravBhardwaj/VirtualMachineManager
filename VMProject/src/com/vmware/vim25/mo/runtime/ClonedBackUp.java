package com.vmware.vim25.mo.runtime;

import java.rmi.RemoteException;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.connect.Connect;


public class ClonedBackUp {


	
	/**
	 * Method to keep a clone of current machine being pinged
	 * @throws Exception 
	 */
	public static void migrateClone(String vmname,String alternateHost) throws InvalidProperty, Exception{

		try {
			Folder rootFolder = Connect.getServiceInstance().getRootFolder();
			String cloneName=vmname+"_clone";
		//	String AlternateHostName="130.65.133.21";

			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmname);
		//	ResourcePool rp = (ResourcePool) new InventoryNavigator(rootFolder).searchManagedEntity("ResourcePool", "Resources");
			HostSystem targetHost = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem",alternateHost); 

			//	HostSystem targetHost = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem",A);
			//	ResourcePool rp = new ResourcePool(Connect.getServiceInstance().getServerConnection(),);
            
			ManagedEntity[] rps = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] {{"ResourcePool", "name" }, }, true);  
            
			//needs a datastore
			Datastore[] dc = vm.getDatastores();
			//    System.out.println("DataStore"+dc[0].getName());

			//configuration setup to relocate clone file on another vHost
			VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
			relocateSpec.setDatastore(dc[0].getMOR());
			relocateSpec.setHost(targetHost.getMOR());
			relocateSpec.setPool(rps[1].getMOR());
			

			//config setup to clone this VM using relocation specification above.
			VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
			cloneSpec.setSnapshot(vm.getCurrentSnapShot().getMOR()); // should be working issue here
			cloneSpec.setPowerOn(true);			
			cloneSpec.setLocation(relocateSpec);
		
			//cloneSpec.setMemory(true);


			Task task = vm.cloneVM_Task((Folder) vm.getParent(),cloneName, cloneSpec);
			System.out.println("Launching the VM clone task. " +"Please wait ...");

			String status = task.waitForTask();  // shall be waitfortask ??
			if(status==Task.SUCCESS)
			{
				System.out.println("VM got cloned successfully.");
			}
			else
			{
				System.out.println("Failure -: VM cannot be cloned");
			}


		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}