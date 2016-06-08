package game_pacman;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


public class StatisticWind extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> dest = new ArrayList<String>();
	int right = 0, left = 0, up = 0, down = 0;
	int pcRight = 0, pcLeft = 0, pcUp = 0, pcDown = 0;
	
	ArrayList<Integer> ghStat = new ArrayList<Integer>();
	ArrayList<Integer> pcStat = new ArrayList<Integer>();
	
	private final JPanel contentPanel = new JPanel();
	private final int numOfGames = 5;

	/**
	 * Create the dialog.
	 */
	public StatisticWind() {
		getStatistics();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 439);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label_1 = new JLabel("Вправо: " + right);
		label_1.setBounds(44, 36, 254, 14);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("Влево: " + left);
		label_2.setBounds(44, 54, 254, 14);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("Вверх: " + up);
		label_3.setBounds(263, 36, 246, 14);
		contentPanel.add(label_3);
		
		JLabel label_4 = new JLabel("Вниз: " + down);
		label_4.setBounds(263, 54, 246, 14);
		contentPanel.add(label_4);
		
		JLabel lblNewLabel = new JLabel("Среднее количество шагов за одну игру в направлении: ");
		lblNewLabel.setBounds(44, 79, 439, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Вправо: " + right/numOfGames);
		lblNewLabel_1.setBounds(44, 104, 171, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Влево: " + left/numOfGames);
		lblNewLabel_2.setBounds(125, 104, 173, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Вверх: " + up/numOfGames);
		lblNewLabel_3.setBounds(217, 104, 167, 14);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Вниз: " + down/numOfGames);
		lblNewLabel_4.setBounds(307, 104, 171, 14);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Статистика Привидения");
		lblNewLabel_5.setBounds(154, 11, 262, 14);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Статистика Пакмана");
		lblNewLabel_6.setBounds(154, 154, 258, 14);
		contentPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Вправо: " + pcRight);
		lblNewLabel_7.setBounds(44, 178, 254, 14);
		contentPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Влево: " + pcLeft);
		lblNewLabel_8.setBounds(44, 203, 254, 14);
		contentPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Вверх: " + pcUp);
		lblNewLabel_9.setBounds(263, 179, 246, 14);
		contentPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Вниз: " + pcDown);
		lblNewLabel_10.setBounds(263, 203, 246, 14);
		contentPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Среднее количество шагов за одну игру в направлении: ");
		lblNewLabel_11.setBounds(44, 228, 439, 14);
		contentPanel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("Вправо: " + pcRight/numOfGames);
		lblNewLabel_12.setBounds(44, 253, 254, 14);
		contentPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Вверх: " + pcUp/numOfGames);
		lblNewLabel_13.setBounds(217, 253, 246, 14);
		contentPanel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("Влево: " + pcLeft/numOfGames);
		lblNewLabel_14.setBounds(125, 253, 254, 14);
		contentPanel.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("Вниз: " + pcDown/numOfGames);
		lblNewLabel_15.setBounds(307, 253, 246, 14);
		contentPanel.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("Самое популярное направление движения: " + getGhostPopDir());
		lblNewLabel_16.setBounds(44, 133, 465, 14);
		contentPanel.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Самое популярное направление движения: " + getPacmanPopDir());
		lblNewLabel_17.setBounds(44, 282, 419, 14);
		contentPanel.add(lblNewLabel_17);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {	//обработка нажатия
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();					
						Game.map.pause = false;
					}
		        });
				
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void getStatistics(){

		File directory = new File("//home//nikita//pacman//Test/");
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
	}
	
	public String getGhostPopDir()
	{
		String dir;
		int temp;
		ArrayList<Integer> directs = new ArrayList<Integer>();
		directs.add(right);
		directs.add(left);
		directs.add(up);
		directs.add(down);
		
		Collections.sort(directs, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return s1 < s2 ? 1 : -1;
			}
		});
		
		Iterator<Integer> i = directs.iterator();
		temp = i.next();
		if(temp == right)
			dir = "Вправо";
		else if(temp == left)
			dir = "Влево";
		else if(temp == up)
			dir = "Вверх";
		else if(temp == down)
			dir = "Вниз";
		else dir = "Не найдено";

		return dir;
	}
	
	public String getPacmanPopDir()
	{
		String dir;
		int temp;
		ArrayList<Integer> directs = new ArrayList<Integer>();
		directs.add(pcRight);
		directs.add(pcLeft);
		directs.add(pcUp);
		directs.add(pcDown);
		
		Collections.sort(directs, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return s1 < s2 ? 1 : -1;
			}
		});
		
		Iterator<Integer> i = directs.iterator();
		temp = i.next();
		if(temp == pcRight)
			dir = "Вправо";
		else if(temp == pcLeft)
			dir = "Влево";
		else if(temp == pcUp)
			dir = "Вверх";
		else if(temp == pcDown)
			dir = "Вниз";
		else dir = "Не найдено";

		return dir;
	}
}

