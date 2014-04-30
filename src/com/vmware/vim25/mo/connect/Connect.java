package com.vmware.vim25.mo.connect;
import com.vmware.vim25.mo.EventManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.config.Config;

public class Connect {
 static ServiceInstance serviceInstance;
 static ServiceInstance host_si;
   
 public Connect() {

	 try {
		 init();
		 initHost();

	 }catch(Exception e){
		 System.out.println("Connection error!!!");
	 }	   
 } 

	public void initHost() throws Exception {
	
	System.out.println("Trying to connect to vcentre for host:"+Config.getUrl_class_vcentre());		
	ServiceInstance host_si = new ServiceInstance(Config.getUrl_class_vcentre(),Config.getVmwareLoginId(),Config.getVmwarePassword(), true);
	this.host_si=host_si;
	System.out.println("Connected at :"+host_si.currentTime().getTime());
   }
	
	public static ServiceInstance getHostServiceInstance(){
		return host_si;
		
	}

	/**
	 * Just a getter method to retrieve serviceInstance in other classes
	 * @return
	 */
	public static ServiceInstance getServiceInstance(){
		
		return serviceInstance;
	}
	public void init() throws Exception{
		System.out.println("Trying to connect to:"+Config.getVmwareHostURL());		
		ServiceInstance si = new ServiceInstance(Config.getVmwareHostURL(),Config.getVmwareLoginId(),Config.getVmwarePassword(), true);	
		this.serviceInstance = si;		  
		System.out.println("Connected at :"+si.currentTime().getTime());
	}
}
