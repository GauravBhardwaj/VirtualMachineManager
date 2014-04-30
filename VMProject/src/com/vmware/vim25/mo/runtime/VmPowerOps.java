package com.vmware.vim25.mo.runtime;

import java.rmi.RemoteException;

import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.connect.Connect;

/**
 * This class should provide methods to restart VM in atleast 3 attempts.
 * @author Gaurav
 *
 */
public class VmPowerOps{

	Connect connectInfo;
	String status="OFF";
	
	public static void StartVM(String vmname,String hostip) throws RuntimeFault, RemoteException, Exception{
		
		ServiceInstance si = Connect.getServiceInstance();
		Folder rootFolder = si.getRootFolder();
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmname);
		HostSystem hostsystem = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem", hostip);
		
		System.out.println("============ ============");

		Task task = vm.powerOnVM_Task(null); // hostSystem should be added.
		if(task.waitForTask()==Task.SUCCESS){
			System.out.println(vm + "Switched-On");	 
			String status="ON";
		}

	}

	public static boolean ShutdownVM(VirtualMachine vm){
		
		String status="OFF";

	return false;
	}
	
	public static boolean SuspendVM(VirtualMachine vm){
		
		return false;
}
	
		
}
