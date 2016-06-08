package game_pacman;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class PseudoNotation {
	static String filePath;
	static String pacmanFilePath;
	static String ghostFilePath;
	
	static String fileName;
	static String ghostFileName;
	static String mainFileName;
	
	static ArrayList<String> fileNames;
	static ArrayList<String> dest = new ArrayList<String>();
	static ArrayList<String> dest2 = new ArrayList<String>();
	
	public PseudoNotation() throws FileNotFoundException {
		
		fileName = "//home//nikita//pacman//Notation//Move//Move_"+getTimeAndDate()+".txt";
		ghostFileName = "//home//nikita//pacman//Notation//Move//ghostMove_"+getTimeAndDate()+".txt";
		mainFileName = "//home//nikita//pacman//Notation//Main_"+getTimeAndDate()+".txt";
		
		NotationWrite.write(mainFileName, fileName);
		NotationWrite.update(mainFileName, ghostFileName);
		
		NotationWrite.create(ghostFileName);
		NotationWrite.create(fileName);
		JFileChooser fileopen = new JFileChooser("//home//nikita//pacman//Notation/");             
		fileopen.showOpenDialog(fileopen);
	    fileopen.setVisible(true);
	    /*if(fileopen.getSelectedFile().isFile())
	    {
	    	filePath = fileopen.getSelectedFile().getAbsolutePath();
	    	setFileNames();
	    }*/
	    filePath = fileopen.getSelectedFile().getAbsolutePath();
	    printPseudoNotation();
	}
	
	static String getTimeAndDate(){
		long curTime = System.currentTimeMillis(); 
		String curStringDate = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(curTime); 
		return curStringDate;
	}
	
	static void setFileNames() throws FileNotFoundException
	{
		fileNames = NotationWrite.readFile(filePath);
		pacmanFilePath = fileNames.get(0);//+' ' +fileNames.get(1);
		ghostFilePath = fileNames.get(1);//+' '+fileNames.get(3);

		System.out.println(pacmanFilePath);
		System.out.println(ghostFilePath);
	}
	
	static void printPseudoNotation() throws FileNotFoundException{
		
		dest = NotationWrite.readFile(pacmanFilePath);
		PseudoNotationScala.writePseudoNotation(dest, fileName, "Pacman");
		
		dest2 = NotationWrite.readFile(ghostFilePath);
		PseudoNotationScala.writePseudoNotation(dest2, ghostFileName, "Ghost");
		
		System.out.println("Готово");
	}
}
