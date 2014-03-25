package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Files {
	public static BufferedReader OpenText(String filename) throws FileNotFoundException{
		return new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
	}
}
