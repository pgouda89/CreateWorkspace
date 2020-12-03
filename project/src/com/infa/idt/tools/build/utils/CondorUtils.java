package com.infa.idt.tools.build.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.infa.idt.tools.build.common.Constansts;
import com.infa.idt.tools.build.common.OsType;

public class CondorUtils {

	public static Set<String> getP4SyncCommands(String release, String buildNo) throws UnsupportedEncodingException, IOException {
		
		String strUrl =  String.format("http://condor/10.0UI/FileDownload?release=%s&buildnum=%s&buildtype=PROMOTED&productName=TRAILBLAZER", release.replaceAll(" ", Constansts.EMPTY), buildNo);
		System.out.println("Connecting to [" + strUrl +"] to download the p4 sync commands");
		URL url = new URL(strUrl);
		Set<String> commandSet = new HashSet<String>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    	commandSet.add(line);
		    }
		}
		return commandSet;
	}

	public static String getLatestBuildNo(String release, OsType osType) {
		// TODO Auto-generated method stub
		return null;
	}
}
