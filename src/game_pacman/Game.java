package game_pacman;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.DirectoryStream;
//import java.nio.file.Path;
import java.text.SimpleDateFormat;
//import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;


/**
 * Основной класс Game
 * наследуется от класса JFrame
 * реализует ActionListener
 * Создаёт главное окно и объединяет всю логику программы
 */
public class Game extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L; 
	static Map map;
	Pacman pacman;
	public final static int WIDTH = 655;
	public final static int HEIGHT = 730;
	Ghost[] ghost;
	JMenuBar menuBar;
	JMenu menu, styleMenu, levelMenu, newGame, mode;
	JMenuItem style, level, exit, pause, resume, playerMode, demoMode, replay, generateGames, Readfiles,
				SortFiles, SortFiles2, Statistics, pseudoNot;
	JRadioButtonMenuItem darkStyle, lightStyle;
	JMenuItem easyLevel, normalLevel, hardLevel, forReplayLevel;
	static String mainFileName;
	static String fileName;
	static String ghostFileName;
	NotationWrite notation;
	ArrayList<String> fileNames;
	MoveThread thread;
	ArrayList<String> dest;
	ArrayList<Integer> scoresVal;
	HashMap<Integer, String> valuesMap = new HashMap<Integer, String>();
	/**
	 * Конструктор класса
	 * @throws FileNotFoundException 
	 */
	public Game() throws FileNotFoundException{
		scoresVal = new ArrayList<Integer>();
		if(Map.level == 0)
		{
			fileName = "//home//nikita//pacman//MyGames//Move//Move_"+getTimeAndDate()+".txt";
			ghostFileName = "//home//nikita//pacman//MyGames//Move//ghostMove_"+getTimeAndDate()+".txt";
			mainFileName = "//home//nikita//pacman//MyGames//Main_"+getTimeAndDate()+".txt";
			NotationWrite.write(mainFileName, fileName);
			NotationWrite.update(mainFileName, ghostFileName);
		}
		initUI();
		thread = new MoveThread(map);
        thread.start();
	}
	
	String getTimeAndDate(){
		long curTime = System.currentTimeMillis(); 
		String curStringDate = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(curTime); 
		return curStringDate;
	}
	
	void setFileNames() throws FileNotFoundException
	{
		fileNames = NotationWrite.readFile(mainFileName);
		fileName = fileNames.get(0);//+' ' +fileNames.get(1);
		ghostFileName = fileNames.get(1);//+' '+fileNames.get(3);

		System.out.println(fileName);
	}
	/**
	 * Метод инициализации переменных и создания главного окна.
	 * <p>
	 * Устанавливает парметры окна, добавляет на него панель игры и меню действий.
	 */
	private void initUI(){
			map = new Map();									//создаём объект Map
			map.setBackground(Color.BLACK);						//цвет фона 
			map.setVisible(true);								//делаем видимым
			//map.setLevel(1);
	        add(map);											//добавляем в окно
	        setTitle("Pacman 1.0");								//заголовок окна
	        setDefaultCloseOperation(EXIT_ON_CLOSE);			//операция закрытия
	        setSize(WIDTH, HEIGHT);								//размер окна
	        setResizable(false);								//недеформируемое
	        setLocationRelativeTo(null);						//расположение по центру
	        setVisible(true);									//делаем видимым
	        menuBar = new JMenuBar();							//создание меню-бара
	        menu = new JMenu("Game");							//создание меню
	        menuBar.add(menu);									//добавление в меню-бара
	        
	        easyLevel = new JMenuItem("Easy");					//пункт меню
	        easyLevel.addActionListener(new ActionListener() {	//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//закрываем текущее окно
					map.setLevel(1);							//1 уровень
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//новое окно
				}
	        });				
	        normalLevel = new JMenuItem("Normal");				//пункт меню
	        normalLevel.addActionListener(new ActionListener() {//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//закрываем текущее окно
					map.setLevel(2);							//2 уровень
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//новое окно
			      
				}
	        });
	        hardLevel = new JMenuItem("Hard");					//пункт меню
	        hardLevel.addActionListener(new ActionListener() {  //обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//закрываем текущее окно
					map.setLevel(3);							//3 уровень
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//новое окно
				}
	        });
	        
	        forReplayLevel = new JMenuItem("For replay");					//пункт меню
	        forReplayLevel.addActionListener(new ActionListener() {  //обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();									//закрываем текущее окно
					map.setLevel(0);							
					Game game;
					try {
						game = new Game();
						game.setVisible(true);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						//новое окно
				}
	        });
	        
	        newGame = new JMenu("New Game");					//подменю
	        newGame.add(easyLevel);								//добавление в подменю
	        newGame.add(normalLevel);
	        newGame.add(hardLevel);
	        newGame.add(forReplayLevel);
	        mode = new JMenu("Mode");							//подменю
	        playerMode = new JMenuItem("Player");				//пункт меню
	        playerMode.addActionListener(new ActionListener() {	//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					thread.setPause(true);
					thread.setInGame(false);
					thread.setDemo(false);
					thread.refreshMap();							//обновление карты
				}
	        });
	        demoMode = new JMenuItem("Demo");					//пункт меню
	        demoMode.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { //обработка нажатия
					long start = System.currentTimeMillis();
					System.out.println(System.currentTimeMillis() - start);
					thread.setPause(false);
					thread.setInGame(true);
					thread.setDemo(true);
					System.out.println(System.currentTimeMillis() - start);
					thread.refreshMap();							//обновление карты
				}
	        });
	        replay = new JMenuItem("Replay");					//пункт меню
	        replay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { //обработка нажатия
					
					JFileChooser fileopen = new JFileChooser("//home//nikita//pacman//MyGames/");             
					fileopen.showOpenDialog(fileopen);
			        fileopen.setVisible(true);
			        if(fileopen.getSelectedFile().isFile())
			        {
			        	ReplayGame(fileopen.getSelectedFile().getName());
			        }
				}
	        });
	        mode.add(playerMode);
	        mode.add(demoMode);
	        mode.add(replay);


	        pause = new JMenuItem("Pause");						//пункт меню
	        pause.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					thread.setPause(true);							//пауза игры
				}
	        });
	        resume = new JMenuItem("Resume");					//пункт меню
	        resume.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					thread.setPause(false);							//продолжить игру
				}
	        });
	        
	        generateGames = new JMenuItem("Generate");						//пункт меню
	        generateGames.addActionListener(new ActionListener() {		//обработка нажатия
				@SuppressWarnings("static-access")
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int i = 0;
					map.setLevel(0);
					thread.setPause(false);
					thread.setInGame(true);
					thread.setDemo(true);
				
					do{
					if(map.finish)
					{
						try {
							thread.sleep(5);
							Thread.sleep(5);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(Map.level == 0)
						{
							fileName = "//home//nikita//pacman//MyGames//Move//Move_"+getTimeAndDate()+".txt";
							ghostFileName = "//home//nikita//pacman//MyGames//Move//ghostMove_"+getTimeAndDate()+".txt";
							mainFileName = "//home//nikita//pacman//MyGames//Main_"+getTimeAndDate()+".txt";
							NotationWrite.write(mainFileName, fileName);
							try {
								NotationWrite.update(mainFileName, ghostFileName);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						map.finish = false;
						map.refresh();
						map.pause = false;
						map.inGame = true;
						map.demo = true;
						i++;
						}
					}
					}while(i!=5);
					dispose();
				}
	        });
	        
	        exit = new JMenuItem("Exit");						//пункт меню
	        exit.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//dispose();									//закрытие окна
					System.exit(0);
				}
	        });
	        menu.add(newGame);									//добавление пунктов в меню
	        menu.add(mode);
	        menu.addSeparator();
	        menu.add(pause);
	        menu.add(resume);
	        menu.addSeparator();
	        
	        Readfiles = new JMenuItem("ReadFiles");						//пункт меню
	        Readfiles.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ReadFiles();
				}
	        });
	        
	        SortFiles = new JMenuItem("Sort");						//пункт меню
	        SortFiles.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ReadFiles();
					String filename = new String();
					try {
						filename = sortJava();
						ReplayGame(filename);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
	        });
	        
	        
	        SortFiles2 = new JMenuItem("Sort Scala");						//пункт меню
	        SortFiles2.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ReadFiles();
					String filename = new String();
					try {
						filename = sortScala();
						ReplayGame(filename);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	        });
	        
	        Statistics = new JMenuItem("Statistics");						//пункт меню
	        Statistics.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					map.pause = true;
					StatisticWind wind = new StatisticWind();
					wind.setLocationRelativeTo(null);
					wind.setVisible(true);
				}
	        });
	        
	        styleMenu = new JMenu("Style");						//подменю
	        darkStyle = new JRadioButtonMenuItem("Dark");		//кнопка
	        darkStyle.setSelected(true);
	        darkStyle.addActionListener(this);
	        lightStyle = new JRadioButtonMenuItem("Light");		//кнопка
	        darkStyle.addActionListener(new ActionListener() {	//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					map.box = new ImageIcon("images/Box.png").getImage(); //установка нового изображения
					darkStyle.setSelected(true);
					lightStyle.setSelected(false);
					map.repaint();								//перерисовка
				}
	        });
	        lightStyle.addActionListener(new ActionListener() { //обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					map.box = new ImageIcon("images/Box2.png").getImage();//новое изображение
					darkStyle.setSelected(false);
					lightStyle.setSelected(true);
					map.repaint();								//перерисовка
				}
	        });
	        styleMenu.add(darkStyle);							//добавление в меню
	        styleMenu.add(lightStyle);
	        menu.add(styleMenu);
	        
	        pseudoNot = new JMenuItem("Pseudo Notation");						//пункт меню
	        pseudoNot.addActionListener(new ActionListener() {		//обработка нажатия
				@Override
				public void actionPerformed(ActionEvent arg0) {
					map.pause = true;
					try {
						@SuppressWarnings("unused")
						PseudoNotation not = new PseudoNotation();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        });
	        
	        menu.add(Readfiles);
	        menu.add(generateGames);
	        menu.add(SortFiles);
	        menu.add(SortFiles2);
	        menu.addSeparator();
	        menu.add(Statistics);
	        menu.addSeparator();
	        menu.add(pseudoNot);
	        menu.addSeparator();
	        menu.add(exit);
	        setJMenuBar(menuBar);								//установка меню в окне
	    }

	private void ReplayGame(String filename)
	{
		mainFileName = "//home//nikita//pacman//MyGames/"+filename;
        try {
			setFileNames();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        map.setLevel(4);
        dispose();									//закрываем текущее окно							
		Game game;
		try {
			game = new Game();
			game.setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}						//новое окно

		map.pause = false;
		map.inGame = true;
		map.replay = true;
		map.refresh();
	}
	public void ReadFiles(){
		int x;
		File directory = new File("//home//nikita//pacman//MyGames/");
		for(File file:directory.listFiles())
		{
			if(!file.isFile())
				break;
			try {
				dest = NotationWrite.readFile(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			x = Integer.parseInt(dest.get(2));
			scoresVal.add(x);
			valuesMap.put(x, file.getName());
			System.out.println(x);
			System.out.println(file.getName());
		}
	}
	public String sortScala() throws FileNotFoundException, InterruptedException {
		
		String filename = new String();
		int temp;
		Sorter obj = null;
		long start;
		long end;
		start = System.currentTimeMillis();
		@SuppressWarnings("static-access")
		ArrayList<Integer> sortedList = obj.sortScala(scoresVal);

		end = System.currentTimeMillis();
		System.out.println("time = " + (end-start));
		
		Iterator<Integer> i = sortedList.iterator();
		temp = i.next();
		
		System.out.println(temp);
		filename = valuesMap.get(temp);
		System.out.println(filename);
		return filename;
	}
	/**
	 * @throws FileNotFoundException
	 * @throws InterruptedException 
	 * 
	 */
	public String sortJava() throws FileNotFoundException, InterruptedException {
		String filename = new String();
		int temp;
		long start;
		long end;
		start = System.currentTimeMillis();
		Collections.sort(scoresVal, new Comparator<Integer>() {
			public int compare(Integer s1, Integer s2) {
				return s1 < s2 ? 1 : -1;
			}
		});
		end = System.currentTimeMillis();
		System.out.println("time = " + (end-start));
		Iterator<Integer> i = scoresVal.iterator();
		temp = i.next();
		System.out.println(temp);
		filename = valuesMap.get(temp);
		return filename;
	}
	/**
	 * Метод, вызывающий движение героев на карте с задержкой
	 * <p>
	 * Происходит вызов метода move() класса Map.
	 * @throws FileNotFoundException 
	 */
	static void move() throws InterruptedException, FileNotFoundException
	{
		  while(true)
	         {
	         	map.move();										//движение
	        	Thread.sleep(120);								//задержка
	         }
	}
	
	/**
	 * Главный метод
	 * 
	 * @param args параметр командной строки
	 * @throws InterruptedException блокирует поток исполнения при возникновении ошибки
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		 Game game = new Game();									//создание окна
         game.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

