package com.vmware.vim25.recovery;
import java.io.IOException;
import java.rmi.RemoteException;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.connect.Connect;
import com.vmware.vim25.mo.runtime.Alarm;
import com.vmware.vim25.mo.runtime.ClonedBackUp;
import com.vmware.vim25.mo.runtime.EventObserver;
import com.vmware.vim25.mo.runtime.HostSnapshot;
import com.vmware.vim25.mo.runtime.ManageHost;
import com.vmware.vim25.mo.runtime.Snapshot;
import com.vmware.vim25.mo.runtime.VmPowerOps;

public class RecoveryManager extends Thread {
	
	private String machineIP;
	VmPowerOps vops;
	private String hostIp;
	
	public RecoveryManager(String machineIP,String hostIp){
		this.machineIP = machineIP;
		this.hostIp = hostIp;
	}
	
	public String getVMState() throws Exception
	{   
		
		Folder rootFolder = Connect.getServiceInstance().getRootFolder();
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", "VM1");
		
		VirtualMachineRuntimeInfo vmri = vm.getRuntime();
		String state=vmri.getPowerState().toString();
		return state;
	}
	
	
	public String CheckHostState(String hostIp){

		String strCommand = "ping "+hostIp;		
		Process myProcess;
		String message="";
		try {
			myProcess = Runtime.getRuntime().exec(strCommand);
			myProcess.waitFor();
			if(myProcess.exitValue() == 0) {
				//System.out.println("============ ping success ============");
				message="alive";
			} else {
				//System.out.println("============ ping failure ============");
				message="dead";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	/**
	 * Check if a user has turned machine off
	 * @return
	 */
	public String checkVirtualMachineState(){
		
		EventObserver evtObs = new EventObserver();
		return evtObs.getLatestEvent();
	}

	public void run(){
		String Expectedmessage="Triggered"; // hardcoded not good 
		//String hostIp; //get the pi of host for this VM from machineIP
		
		
		//step1. check if user has turned it off
		try {
			if(Alarm.getAlarmStatus().equals(Expectedmessage)){
				System.out.println("============Machine has been switched off by user ============");
				
			}
			else{				
				//case2. try to restart the machine if possible, best way could be using attempts..but lets be simple 
				System.out.println("============seems some issues with this machine============");
				 try {
					//VmPowerOps.StartVM(machineIP,hostIp);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(CheckHostState(hostIp).equalsIgnoreCase("alive")){//case3. check if the host machine is dead (vHost )-null should be ip
			
			System.out.println("============vHost is alive for this vm ============");

			try {
				if(getVMState()=="poweredOff" | getVMState()=="suspended"){//since vHost is alive check if user has suspended or stopped it, so restart it. 	
					// VmPowerOps.StartVM("vm_1",null);
					System.out.println("============machine seems to be suspended or powered off ============");
				}
				else{ // revert to most current snapshot  
					
					Snapshot snapVM = new Snapshot("VM1",null);
					snapVM.revertToCurrentSnapshot();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
		}else{ //case4. vHost is dead so you should now add a vhost to your vCentre.
			System.out.println("============Vhost is dead for this vm !!! ============");
			try {
				System.out.println("=======Trying to make this host alive by reverting to its most recent snapshot========");
				
				HostSnapshot snapHost = new HostSnapshot("t14-vHost01-cum3-lab1 _.133.21",null);
				snapHost.revertToHostSnapshot();
				
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
			if(ManageHost.addHost("130.65.133.23")){//case5. vHost is added successfully, migrate machine on this vHost 
				 try {
					
					ClonedBackUp.migrateClone("vm_1", "130.65.133.23");

				} catch ( Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 System.out.println("============Vhost is added successfully,fail over to this host started ============"); 
				   try {
					ClonedBackUp.migrateClone("vm_1", "130.65.133.23");
				} catch (InvalidProperty e) {
					System.out.println("Error while moving vMachine");
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("Error while moving vMachine");
					e.printStackTrace();
				}
				 
			 }else{//case6. Error adding vHost
				 System.out.println("Error while adding vHost to your vCentre");
			 }
		}
		
	}

}