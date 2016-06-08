package game_pacman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JDialog;
//import javax.swing.JLabel;

//import javax.swing.JDialog;


public class StatisticWindow extends JDialog{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){	
		
		ArrayList<String> dest = new ArrayList<String>();
		int right = 0, left = 0, up = 0, down = 0;
		int pcRight = 0, pcLeft = 0, pcUp = 0, pcDown = 0;
		
		ArrayList<Integer> ghStat = new ArrayList<Integer>();
		ArrayList<Integer> pcStat = new ArrayList<Integer>();

		File directory = new File("//home//nikita//pacman//MyGames//Move/");
		for(File file:directory.listFiles())
		{
			if(!file.isFile())
				break;
			if(file.getAbsolutePath().contains("ghost"))
			{
				try {
					dest = NotationWrite.readFile(file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				ghStat = Statistics.getStatistics(dest);
				right+=ghStat.get(0);
				left+=ghStat.get(1);
				up+=ghStat.get(2);
				down+=ghStat.get(3);
			}
			else 
			{
				try {
					dest = NotationWrite.readFile(file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				pcStat = Statistics.getStatistics(dest);
				pcRight += pcStat.get(0);
				pcLeft += pcStat.get(1);
				pcUp += pcStat.get(2);
				pcDown += pcStat.get(3);
			}
		}
			
		System.out.println("Ghost right = " + right);
		System.out.println("Ghost left = " + left);
		System.out.println("Ghost up = " + up);
		System.out.println("Ghost down = " + down);
		
		System.out.println("Ghost average right = " + right/999);
		System.out.println("Ghost average left = " + left/999);
		System.out.println("Ghost average up = " + up/999);
		System.out.println("Ghost average down = " + down/999);
		
		System.out.println("Pacman right = " + pcRight);
		System.out.println("Pacman left = " + pcLeft);
		System.out.println("Pacman up = " + pcUp);
		System.out.println("Pacman down = " + pcDown);
		
		System.out.println("Pacman average right = " + pcRight/999);
		System.out.println("Pacman average left = " + pcLeft/999);
		System.out.println("Pacman average up = " + pcUp/999);
		System.out.println("Pacman average down = " + pcDown/999);
	}
}
