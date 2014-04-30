package com.vmware.vim25.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfProviderSummary;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;


public class Util extends Thread {

	private String vm;
	private static final int SELECTED_COUNTER_ID = 6; // Active (mem) in KB
	static Integer[] a = { 5, 23, 124, 142, 156 };
	static String[] aName = { "cpu", "mem", "disk", "net", "sys" };
	private static HashMap<String, String> infoList = new HashMap<String, String>();
	static int counter = 0;

	public void run() {
		while(true){
		try {
			String url_host1 = "https://130.65.132.13/sdk";
			URL url = new URL(url_host1);	
				
			try {
			ServiceInstance si = new ServiceInstance(url,"administrator","12!@qwQW", true);
			VirtualMachine host = (VirtualMachine) new InventoryNavigator(si.getRootFolder()).searchManagedEntity("VirtualMachine", "T14-vHost01-cum1-2GB-NFS2-lab3_base5 _.133.21");
			
			PerformanceManager perfMgr = si.getPerformanceManager();
			PerfProviderSummary summary = perfMgr.queryPerfProviderSummary(host);
					
			int perfInterval = summary.getRefreshRate();
			//System.out.println("summary"+summary.getRefreshRate());	
			
			PerfMetricId[] queryAvailablePerfMetric = perfMgr.queryAvailablePerfMetric(host, null, null,perfInterval);
			PerfCounterInfo[] pci = perfMgr.getPerfCounter();
			
			ArrayList<PerfMetricId> list = new ArrayList<PerfMetricId>();
			
		//	System.out.println("length"+queryAvailablePerfMetric.length); 
					
				for (int i = 0; i < queryAvailablePerfMetric.length; i++) {
						PerfMetricId perfMetricId = queryAvailablePerfMetric[i];
					
					//	System.out.println("id"+perfMetricId.getCounterId());
						
						if (SELECTED_COUNTER_ID == perfMetricId.getCounterId()) {
							list.add(perfMetricId);
						}
					}
					
			PerfMetricId[] pmis = list.toArray(new PerfMetricId[list.size()]);
			
			
					PerfQuerySpec qSpec = new PerfQuerySpec();
					qSpec.setEntity(host.getMOR());
					qSpec.setMetricId(pmis);
					qSpec.intervalId = perfInterval;
					
					PerfEntityMetricBase[] pembs = perfMgr.queryPerf(new PerfQuerySpec[] { qSpec });

					for (int i = 0; pembs != null && i < pembs.length; i++) {
						PerfEntityMetricBase val = pembs[i];
						PerfEntityMetric pem = (PerfEntityMetric) val;
						PerfMetricSeries[] vals = pem.getValue();
						
						PerfSampleInfo[] infos = pem.getSampleInfo();
						

						for (int j = 0; vals != null && j < vals.length; ++j) {
							PerfMetricIntSeries val1 = (PerfMetricIntSeries) vals[j];
							long[] longs = val1.getValue();
							
							for (int k : a) {
								infoList.put(aName[counter],String.valueOf(longs[k]));
								System.out.println(aName[counter]+"-"+String.valueOf(longs[k]));
								counter++;
							}
							counter = 0;
						}
					}
					si.getServerConnection().logout();
				} catch (InvalidProperty e) {
					e.printStackTrace();
				} catch (RuntimeFault e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				infoList.put("vmName","T14-vHost01-cum1-2GB-NFS2-lab3_base5 _.133.21");
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				infoList.put("datetime",sd.format(new Date()));
				counter = 0;
				
				try {
					System.out.println("Writing to gile: Hoststats");
					String path = "C:\\Users\\Gaurav\\Gaurav_DATA\\CmpE-283\\Project#2\\Hosttats.txt";
					
					File file = new File("Hoststats.log");
					 
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		 
					FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
					BufferedWriter bw = new BufferedWriter(fw);
//					bw.append(basicdbObject.toString());
					for(String key : infoList.keySet()){
						
						if(key.equals("vmName")){
							bw.append(key +":"+ infoList.get(key)+";");
						}
						else{
							bw.append(key +":"+ infoList.get(key)+",");
						}
					}
					bw.newLine();
					bw.flush();
					bw.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}

		} catch (Exception e) {
			e.printStackTrace();
		 }
	  try {
		Thread.sleep(5*1000);
	} catch (InterruptedException e) {
	System.out.println("failed to sleep");
		e.printStackTrace();
	} 
	  }
		
	}
	
	public static void main(String args[]){
		new Util().start(); 
		 
	}

}