/*
 * Copyright (c) 2015 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

package com.trustonic.tsdk.sample;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


/**
 * Class to ease data conversion
 * @author vinbon01
 * 
 */
public class Utils {

/**
 * Check All Containers availability
 * @return status
 */
	public static boolean checkContainers(){
		return checkContainerRot13() && checkContainerAes() && checkContainerRsa() && checkContainerSha256();
	}

/**
 * Check Container Sha256 availability
 * @return status
 */
	public static boolean checkContainerSha256(){
		return checkFileAvailability(Constants.MC_SYSTEM_REGISTRY_PATH+"/"+Constants.SHA256_CONT_FILE)
			|| checkFileAvailability(Constants.MC_REGISTRY_PATH+"/"+Constants.SHA256_CONT_FILE);
	}

/**
 * Check Container Rsa availability
 * @return status
 */
	public static boolean checkContainerRsa(){
		return checkFileAvailability(Constants.MC_SYSTEM_REGISTRY_PATH+"/"+Constants.RSA_CONT_FILE)
			|| checkFileAvailability(Constants.MC_REGISTRY_PATH+"/"+Constants.RSA_CONT_FILE);
	}

/**
 * Check Container Aes availability
 * @return status
 */
	public static boolean checkContainerAes(){
		return checkFileAvailability(Constants.MC_SYSTEM_REGISTRY_PATH+"/"+Constants.AES_CONT_FILE)
			|| checkFileAvailability(Constants.MC_REGISTRY_PATH+"/"+Constants.AES_CONT_FILE);
	}

/**
 * Check Container Rot13 availability
 * @return status
 */
	public static boolean checkContainerRot13(){
		return checkFileAvailability(Constants.MC_SYSTEM_REGISTRY_PATH+"/"+Constants.ROT13_CONT_FILE)
			|| checkFileAvailability(Constants.MC_REGISTRY_PATH+"/"+Constants.ROT13_CONT_FILE);
	}
	/**
	 * Check file availability
	 * @param fileName file name
	 * @return status
	 */
	public static boolean checkFileAvailability(String fileName){
		if (fileName != null){
			String out = executeProcess(null, "ls","-l",fileName);
			if (out != null){
				try{
				    return out.contains(fileName.substring(fileName.lastIndexOf("/")+1,fileName.length()));
				} catch (Exception e){
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Execute a process
	 * @param inDirectory target directory
	 * @param commands process parameters
	 * @return status
	 */
	public static String executeProcess(String inDirectory, String... commands){
		StringBuffer sb = new StringBuffer();
		ProcessBuilder processBuilder = null;

		if (inDirectory!=null){
			//Execute process in directory
			processBuilder = new ProcessBuilder(commands).directory(new File(inDirectory));
		} else {
			//Execute process on cwd
			processBuilder = new ProcessBuilder(commands);
		}

		Process process;
		try {
			process = processBuilder.start();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			out.flush();
			String resultLine = null;
			do {
				resultLine = in.readLine();
				if (resultLine != null) sb.append(resultLine);
			} while (resultLine != null);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

}
