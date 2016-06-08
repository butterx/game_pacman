package game_pacman;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NotationWrite {
    public static File file;
    
	public static void create(String fileName) {
	    //Определяем файл
		File file = new File(fileName);
	 
	    try {
	        //проверяем, если файл не существует то создаем его
	        /*if(!file.exists()){
	            file.createNewFile();
	        }*/
	        file.createNewFile();
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	public static void write(String fileName, String text) {
	    //Определяем файл
		File file = new File(fileName);
	 
	    try {
	        //проверяем, если файл не существует то создаем его
	        if(!file.exists()){
	            file.createNewFile();
	        }
	 
	        //PrintWriter обеспечит возможности записи в файл
	        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
	 
	        try {
	            //Записываем текст в файл
	            out.print(text);
	            System.out.println(text);
	        } finally {
	            //После чего мы должны закрыть файл
	            //Иначе файл не запишется
	            out.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static void update(String nameFile, String newText) throws FileNotFoundException {
	    exists(nameFile);
	    StringBuilder sb = new StringBuilder();
	    String oldFile = read(nameFile);
	    sb.append(oldFile);
	    sb.append(newText);
	    System.out.println(newText);
	    write(nameFile, sb.toString());
	}
	
	public static ArrayList<String> readFile(String fileName) throws FileNotFoundException {
	 
		//exists(fileName);
	    file = new File(fileName);
	    ArrayList<String> list = new ArrayList<String>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) 
            list.add(scanner.next());
        scanner.close();
    
	    //Возвращаем полученный текст с файла
	    return list;
	}
	
	
	public static String read(String fileName) throws FileNotFoundException {
	    //Этот спец. объект для построения строки
	    StringBuilder sb = new StringBuilder();
	 
	    exists(fileName);
	 
	    try {
	        //Объект для чтения файла в буфер
	        BufferedReader in = new BufferedReader(new FileReader(fileName));
	        try {
	            //В цикле построчно считываем файл
	            String s;
	            while ((s = in.readLine()) != null) {
	                sb.append(s);
	                sb.append("\n");
	            }
	        } finally {
	            //Также не забываем закрыть файл
	            in.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	 
	    //Возвращаем полученный текст с файла
	    return sb.toString();
	}
	
	private static void exists(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
	    //проверяем, если файл не существует то создаем его
		if(!file.exists()){
            try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}

