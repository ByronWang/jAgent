package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Files {
	public static BufferedReader OpenText(String filename) throws FileNotFoundException, UnsupportedEncodingException{
		return new BufferedReader(new InputStreamReader(new FileInputStream(filename),"utf-8"));
	}
}
