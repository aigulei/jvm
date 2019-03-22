package com.ai.jvm.classload;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义classLoader
 * @author Administrator
 *
 */
public class MyClassLoader extends ClassLoader{
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
		InputStream ins = getClass().getResourceAsStream(fileName);
		if(ins ==null) {
			return super.loadClass(name);
		}
		try {
			byte[] buffer = new byte[ins.available()];
			ins.read(buffer);
			return defineClass(name,buffer,0, buffer.length);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
		
	}
	
	public static void main(String[] args) {
		
	}
}
