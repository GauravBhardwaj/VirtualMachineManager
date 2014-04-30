package com.vmware.vim25.mo.runtime;

import com.vmware.vim25.Event;
import com.vmware.vim25.EventFilterSpec;
import com.vmware.vim25.mo.EventManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.connect.Connect;

public class EventObserver {
	
	public String getLatestEvent(){
		ServiceInstance si = Connect.getServiceInstance();
		
		EventManager evtMgr = si.getEventManager();
		Event latestEvent = evtMgr.getLatestEvent(); // it gives event for complete vCentre, so not useful
		EventFilterSpec fspec = new EventFilterSpec();
				
		
		System.out.println("Event on=="+latestEvent.getFullFormattedMessage());
		
		return latestEvent.getFullFormattedMessage();
	}
	
		
}
