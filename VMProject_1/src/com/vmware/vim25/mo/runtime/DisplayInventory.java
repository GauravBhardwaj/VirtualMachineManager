package com.vmware.vim25.mo.runtime;
import java.net.MalformedURLException;
import java.rmi.RemoteException;



import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.config.Config;
import com.vmware.vim25.mo.connect.*;;

public class DisplayInventory {

	
	ServiceInstance si; // static reference
	
	public void retrieveVM(){
			
			
			try {
				
			//	si = new ServiceInstance(Config.getVmwareHostURL(),Config.getVmwareLogin(),Config.getVmwarePassword(), true);
				Folder rootFolder =  Connect.getServiceInstance().getRootFolder();
				ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities(
						new String[][] { {"VirtualMachine", "name" }, }, true);

				System.out.println("============ virtual Machines ============");
				for(int i=0; i<vms.length; i++)
				{
					System.out.println("vm["+i+"]=" + vms[i].getName());
					
				}		
				
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	
		}
	
	public void retrieveDC(){
				System.out.println("============ Data Centers ============");
				try{				
				Folder rootFolder = Connect.getServiceInstance().getRootFolder();
				ManagedEntity[] dcs = new InventoryNavigator(rootFolder).searchManagedEntities(
							new String[][] { {"Datacenter", "name" }, }, true);
				
				
				for(int i=0; i<dcs.length; i++)
				{
					System.out.println("DC["+i+"]=" + dcs[i].getParent());
					
				}	

				}
				 catch(Exception e){
					 
				 }
				
	}
	
	public void retrieveVhosts(){
		System.out.println("============ Hosts ============");
		try{
		
		Folder rootFolder = Connect.getServiceInstance().getRootFolder();
		
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] {{"HostSystem", "name" }, }, true);
		
		for(int i=0; i<hosts.length; i++)
		{
			System.out.println("host["+i+"]=" + hosts[i].getName());
		}	
	
		 }
		 catch(Exception e){
			 
		 }
		
	}
	
	public void getResourcePool(){
		System.out.println("============ Pool ============");
		try{
		
		Folder rootFolder = Connect.getServiceInstance().getRootFolder();
		
		ManagedEntity[] rps = new InventoryNavigator(rootFolder).searchManagedEntities(new String[][] {{"ResourcePool", "name" }, }, true);
		
		
		for(int i=0; i<rps.length; i++)
		{
			ResourcePool rp = (ResourcePool) rps[i];
		    
		}	
		
		 }
		 catch(Exception e){
			 
		 }
		
			
		
		
	}
	public void getVMforHost(String Host) throws InvalidProperty, RuntimeFault, RemoteException{
		Folder rootFolder =  Connect.getServiceInstance().getRootFolder();
		//Folder rootFolder = si.getRootFolder();
		HostSystem hs = (HostSystem) new InventoryNavigator(rootFolder).searchManagedEntity("HostSystem", Host); 
		
		com.vmware.vim25.mo.VirtualMachine[] VMarray= hs.getVms();
		System.out.println("=========="+VMarray[0].getName());
         
	}
	
}
