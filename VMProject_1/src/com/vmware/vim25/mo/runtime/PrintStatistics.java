package com.vmware.vim25.mo.runtime;
import java.rmi.RemoteException;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.connect.Connect;

public class PrintStatistics{
 
	public static void printStats(){
	Folder rootFolder = Connect.getServiceInstance().getRootFolder();
	System.out.println("root:" +rootFolder.getName());
	
	
	try {
		ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
		
		for (int i = 0; i < mes.length; i++) {
			VirtualMachine vm = (VirtualMachine) mes[i];
			VirtualMachineConfigInfo vminfo = vm.getConfig();
			VirtualMachineCapability vmc = vm.getCapability();
			 
			System.out.println("Resource pool: " +vm.getResourcePool());
			System.out.println("Owner: " +vm.getResourcePool().getOwner().getName());
			System.out.println("Parent: " +vm.getResourcePool().getParent().getName());
			System.out.println("Values: " +vm.getResourcePool().getValues());
			System.out.println("");
			
			System.out.println("*************************************************");
			System.out.println("This is virtual machine " + vm.getName());
			System.out.println("Config: " +vm.getConfig().toString());
			System.out.println("Guest: " +vm.getGuest().getIpAddress());
			
			System.out.println("GuestOS: " + vminfo.getGuestFullName());
			System.out.println("GuestID: " + vminfo.getGuestId());
			System.out.println("GuestName: " + vminfo.getName());
			
			System.out.println("");
			System.out.println("Multiple snapshot supported: "
					+ vmc.isMultipleSnapshotsSupported());

	        System.out.println("Hello " + vm.getName());
	        System.out.println("GuestOS: " + vminfo.getGuestFullName());
	        System.out.println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());
	       
	        //CPU Statistics
	        VirtualMachineRuntimeInfo vmri = vm.getRuntime();
	        System.out.println("*************************************************");
	        System.out.println("CPU Statistics are as follows:");
	     
	        System.out.println("Connection State: " + vmri.getConnectionState());
	        System.out.println("Power State: " + vmri.getPowerState());
	        System.out.println("Max CPU Usage: " + vmri.getMaxCpuUsage());
	        System.out.println("Max Memory Usage: " + vmri.getMaxMemoryUsage());
		
		}
	} catch (InvalidProperty e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RuntimeFault e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}		

	public static void PrintHostStats(){
		
		Folder rootFolder = Connect.getHostServiceInstance().getRootFolder();
		
		try {
			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", "T14-vHost04-lab2 _.133.25");
			VirtualMachineConfigInfo vminfo = vm.getConfig();
			VirtualMachineCapability vmc = vm.getCapability();
			 
			System.out.println("Resource pool: " +vm.getResourcePool());
			System.out.println("Owner: " +vm.getResourcePool().getOwner().getName());
			System.out.println("Parent: " +vm.getResourcePool().getParent().getName());
			System.out.println("Values: " +vm.getResourcePool().getValues());
			System.out.println("");
			
			System.out.println("*************************************************");
			System.out.println("This is virtual machine " + vm.getName());
			System.out.println("Config: " +vm.getConfig().toString());
			System.out.println("Guest: " +vm.getGuest().getIpAddress());
			
			System.out.println("GuestOS: " + vminfo.getGuestFullName());
			System.out.println("GuestID: " + vminfo.getGuestId());
			System.out.println("GuestName: " + vminfo.getName());
			
			System.out.println("");
			System.out.println("Multiple snapshot supported: "
					+ vmc.isMultipleSnapshotsSupported());

	        System.out.println("Hello " + vm.getName());
	        System.out.println("GuestOS: " + vminfo.getGuestFullName());
	        System.out.println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());
	       
	        //CPU Statistics
	        VirtualMachineRuntimeInfo vmri = vm.getRuntime();
	        System.out.println("*************************************************");
	        System.out.println("CPU Statistics are as follows:");
	     
	        System.out.println("Connection State: " + vmri.getConnectionState());
	        System.out.println("Power State: " + vmri.getPowerState());
	        System.out.println("Max CPU Usage: " + vmri.getMaxCpuUsage());
	        System.out.println("Max Memory Usage: " + vmri.getMaxMemoryUsage());
		
		
		
		
		} catch (InvalidProperty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	} 
	
	
}
