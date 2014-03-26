package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Files {
	public static BufferedReader OpenText(String filename) throws IOException{
		BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(filename),"utf-8"));
//		char c = (char)br.read();
		return br;
	}
}
