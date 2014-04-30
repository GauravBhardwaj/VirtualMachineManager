package com.vmware.vim25.mo.runtime;

import java.rmi.RemoteException;

import com.vmware.vim25.ComputeResourceConfigSpec;
import com.vmware.vim25.HostConnectFault;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.InvalidLogin;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.connect.Connect;

public class ManageHost {

	/**
	 * Method to add a new StandAlone Vhost to the inverntory.
	 * @param ip
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws HostConnectFault 
	 * @throws InvalidLogin 
	 */
	public static boolean addHost(String ip){
		ServiceInstance si = Connect.getServiceInstance();

		Folder rootFolder = si.getRootFolder();
		Datacenter dc;
		
		HostConnectSpec hSpec = new HostConnectSpec();
		hSpec.setHostName(ip);
		hSpec.setUserName("root");
		hSpec.setPassword("12!@qwQW");
		//hSpec.isForce();
		
		hSpec.setSslThumbprint("7F:5A:3E:C4:E1:74:ED:4F:7A:55:99:BE:25:F2:A0:64:26:27:DE:1C");
		//hSpec.force=true;
		hSpec.setForce(true);
		
		try {
			dc = (Datacenter) new InventoryNavigator(rootFolder).searchManagedEntity("Datacenter", "TEAM14_DC");
			ComputeResourceConfigSpec compResSpec = new ComputeResourceConfigSpec();    
		    	
		   Task tk = dc.getHostFolder().addStandaloneHost_Task(hSpec, compResSpec, true);
		   if(tk.waitForTask()==Task.SUCCESS){
		       return true;
		    }
		} catch (Exception e) {
				
			e.printStackTrace();
			
		}return false; 
	}
}
