package com.vmware.vim25.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;










import com.vmware.vim25.AlarmState;
import com.vmware.vim25.ComputeResourceConfigSpec;
import com.vmware.vim25.Event;
import com.vmware.vim25.EventFilterSpec;
import com.vmware.vim25.EventFilterSpecByTime;
import com.vmware.vim25.GroupAlarmAction;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.MethodAction;
import com.vmware.vim25.MethodActionArgument;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.StateAlarmExpression;
import com.vmware.vim25.StateAlarmOperator;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Alarm;
import com.vmware.vim25.mo.AlarmManager;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.EventManager;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.config.Config;
import com.vmware.vim25.mo.connect.Connect;


public class Util {
	
	Util(){
		
	}

	public static void main(String args[]){

		
			
			Connect c = new Connect();
			ServiceInstance si = c.getServiceInstance();
		
				//ServiceInstance si = Connect.getServiceInstance();

				Folder rootFolder = si.getRootFolder();
				Datacenter dc;
				
				HostConnectSpec hSpec = new HostConnectSpec();
				hSpec.setHostName("130.65.133.22");
				hSpec.setUserName("root");
				hSpec.setPassword("12!@qwQW");
				//hSpec.isForce();
				
				hSpec.setSslThumbprint("FA:73:EA:20:D1:C7:AB:A3:CD:F4:72:79:3E:E7:F3:A6:34:F0:DE:04");
				//hSpec.force=true;
				hSpec.setForce(true);
				
				try {
					dc = (Datacenter) new InventoryNavigator(rootFolder).searchManagedEntity("Datacenter", "TEAM14_DC");
					ComputeResourceConfigSpec compResSpec = new ComputeResourceConfigSpec();    
				    	
				   Task tk = dc.getHostFolder().addStandaloneHost_Task(hSpec, compResSpec, true);
				   if(tk.waitForTask()==Task.SUCCESS){
					   System.out.println("==New vHost is aded to your vCentre==");
				     
				    }
				} catch (Exception e) {
						
					e.printStackTrace();
					
				}
				
	}
}
