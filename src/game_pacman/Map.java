package game_pacman;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Класс Map наследуется от класса JPanel.
 * <p>
 * Объединяет логику поведения героев, рисует карту, содержит информацию
 * о текущей игре.
 */
public class Map extends JPanel{
	int ghostNum;
	private static final long serialVersionUID = 1L;
	public final int BLOCK_SIZE = 25;
	static int level = 1;
	String levelStr;
	public static final int WIDTH = 655;
	public static final int HEIGHT = 700;
	int x = 0;
	int y = 0;
	int score = 0;
	int numOfPellets = 1;
	boolean finish = false;
	
	Image pac = new ImageIcon("images/Pacman.png").getImage();
	Image box = new ImageIcon("images/Box.png").getImage();

	Ghost[] ghost;
	Pacman pacman;
	public boolean canMove;
	int count = 0;
	public boolean pause = true;
	public boolean inGame = false;
	public boolean demo = false;
	public boolean replay = false;
	
	
	/**
	 * Конструктор класса.
	 * <p>
	 * Cодержит методы инициализации переменных и обработку нажатия клавиш.
	 * Событие нажатия клавиши передатся в объект класса Pacman для последующей обработки.
	 */
	public Map() {

		initVariables();									//инициализация переменных
		
		addKeyListener(new KeyListener() {					//отлов нажатия клавиш
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(!pause && inGame)
					try {
						pacman.keyPressed(e);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}					//передача события объекту pacman
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)	//если нажат ESCAPE
				{
					count++;									
					if(count % 2 == 0)
						pause = false;
					else
						pause = true;						//останавливаем/запускаем игру
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER)	//если Enter
				{
						inGame = true;						//запускаем игру
						pause = false;
				}
			}
		});
		setFocusable(true);
	}
	
	/**
	 * Метод инициализации переменных. 
	 * <p>
	 * В зависимости от уровня устанавливается количество привидений и название уровня.
	 * Создаются объекты классов Pacman и Ghost.
	 */
	private void initVariables() {							//установка количества привидений в зависисмости
		switch(level){										//от уровня
		case 0:
		case 4:
			ghostNum = 1;
			levelStr = new String("REPLAY");
			break;
		case 1:												
			ghostNum = 2;
			levelStr = new String("EASY");
			break;
		case 2:
			ghostNum = 5;
			levelStr = new String("NORMAL");
			break;
		case 3:
			ghostNum  = 10;
			levelStr = new String("HARD");
			break;
		}
		ghost = new Ghost[ghostNum];						//созданеи массива объектов привидений
		for(int i = 0; i <ghostNum; i++)
			ghost[i] = new Ghost(this);
		pacman = new Pacman(this);							//создание объекта pacman
	}
	
	
	/**
	 * <p>
	 * Метод, рисующий всё содержимое игры, используя Graphics2D.
	 * 
	 * @param g объект класса Graphics для рисования в панели
	 */
	@Override						
	public void paint(Graphics g){			
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawScore(g);										//рисование панели с очками и жизнями
		drawMap(g);											//рисование самой карты
		drawPellets(g);										//рисование точек
		for (int i = 0; i<ghostNum; i++)
			ghost[i].paint(g2d, i);							//рисование привидений
		pacman.paint(g2d);									//и Пакмана
		if(!inGame) {										//если игра не запущена
			showIntroScreen(g2d);							//начальный экран
			refresh();										//обновление карты
		}
		
	}

	/**
	 * <p>
	 * Метод, рисующий начальный экран, используя Graphics2D.
	 * 
	 * @param g2d объект класса Graphics2D для рисования в панели
	 */
	 private void showIntroScreen(Graphics2D g2d) {

	        g2d.setColor(new Color(255, 188, 0));
	        g2d.fillRect(175, 275, 300, 50);					//рисуем прямоугольные области
	        g2d.setColor(Color.white);
	        g2d.drawRect(175, 275, 300, 50);

	        String s = "Press ENTER to start.";
	        Font small = new Font("Emulogic", Font.BOLD, 10);

	        g2d.setColor(Color.white);
	        g2d.setFont(small);
	        g2d.drawString(s, 215, 305);						//добавляем строку
	    }
	 
	 /**
		 * <p>
		 * Метод, рисующий карту, используя Graphics.
		 * 
		 * @param g объект класса Graphics для рисования в панели
		 */
	public void drawMap(Graphics g) {
		int x = 0, y = 0;
		for(int i = 0;  i < 25; i++, y+=BLOCK_SIZE) {
			for(int j = 0; j < 26; j++, x+=BLOCK_SIZE) {
					if(grid[i][j] == 1) {
						g.drawImage(box, x, y, this);			//рисуем блоки
					}
			}
			x=0;
		}
	}
	/**
	 * <p>
	 * Метод, рисующий точки на карте, используя Graphics.
	 * 
	 * @param g объект класса Graphics для рисования в панели
	 */
	public void drawPellets(Graphics g) {
		int x = 0, y = 0;
		numOfPellets = 0;
		g.setColor(Color.YELLOW);
		for(int i = 0;  i < 25; i++, y+=BLOCK_SIZE) {
			for(int j = 0; j < 26; j++, x+=BLOCK_SIZE) {
					if(grid[i][j] == 0) {
						g.fillOval(x+10, y+10 , 6, 6);			//рисуем круги
						numOfPellets++;
					}
			}
			x=0;
		}
	}
	
	/**
	 * <p>
	 * Метод, рисующий очки, жизни и название уровня, используя Graphics2D
	 * 
	 * @param g объект класса Graphics для рисования в панели
	 */
	private void drawScore(Graphics g) {

        int i;
        int x = 0;
        String s, lives;

        g.setFont(new Font("Emulogic", Font.BOLD, 14));
        g.setColor(new Color(96, 128, 255));
        s = "SCORE: " + score*10;
        g.drawString(s, WIDTH-250, HEIGHT-45);				//рисуется строка с очками

        lives = "LIVES: ";
        g.drawString(lives, WIDTH-625, HEIGHT-45);			//рисуется строка с жизнями
        g.setColor(Color.YELLOW);
        for (i = 0; i < pacman.lives; i++, x+=30) {
        	g.drawImage(pac ,WIDTH-530 + x, HEIGHT-65, this);	//сами жизни в виде Пакманов
        }
        
        g.drawString(levelStr, 275, HEIGHT-45);				//рисуется строка с уровнем
    }	
	
	/**
	 * Метод обновления (восстановления) карты
	 * <p>
	 * Матрица перезаписывается начальными значениями, герои устанавливаются 
	 * в свои исходные координаты.
	 */	
	public void refresh() {	
        for (int i = 0; i < 25; i++)
        	for(int j = 0; j < 26; j++){
            grid[i][j] = grid2[i][j];		//восстанавливаем карту
        }
        pacman.lives = 3;					//задаём начальные значения
		score = 0;							//жизней, очков, координат
		
		pacman.x = 25;
		pacman.y = 25;
		pacman.dx = 0;
		pacman.dy = 0;
		
		for(int i = 0; i <ghostNum; i++) {
			ghost[i].x = 325;
			ghost[i].y = 350;
			ghost[i].dx = 0;
			ghost[i].dy = 0;
		}
	}
	
	/**
	 * Метод продолжения уровня после столкновения Пакмана с привидением.
	 * <p>
	 * Герои устанавливаются в свои исходные координаты.
	 * <p>
	 * Если количество жизней Пакмана равно 0, то вызывается вывод окна о проигрыше.
	 */
	public void continueLevel()
	{	
		if(pacman.lives == 0) {
			if(replay)
			{
				score+=1;
			}
			repaint();
			finish = true;
			gameOver();							//окно "Конец игры"восстанавливаем начальное положение
			
		}
												//восстанавливаем начальное положение
		pacman.x = 25;							//пакмана и привидений
		pacman.y = 25;
		pacman.dx = 0;
		pacman.dy = 0;
		for(int i = 0; i <ghostNum; i++) {
			ghost[i].x = 325;
			ghost[i].y = 350;
			ghost[i].dx = 0;
			ghost[i].dy = 0;
		}
	}

	/**
	 * Метод, выводящий диалоговое окно с сообщением о победе в игре
	 */
	public void pacmanWin() {
		if(level == 0)
			try {
				NotationWrite.update(Game.mainFileName, score+" ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		JOptionPane.showMessageDialog(this, "You Win\nYour score: " +score*10, "Win", JOptionPane.YES_NO_OPTION);
		inGame = false;
		pause = true;
		replay = false;
		finish = true;
		setLevel(1);
		refresh();
	}
	/**
	 * Метод, выводящий диалоговое окно с сообщением о проигрыше в игре
	 */
	public void gameOver() {
		if(level == 0)
			try {
				NotationWrite.update(Game.mainFileName, score+" ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		JOptionPane.showMessageDialog(this, "Game Over\nYour score: " +score*10, "Game Over", JOptionPane.YES_NO_OPTION);
		inGame = false;
		pause = true;
		replay = false;
		finish = true;
		setLevel(1);
		refresh();
	}
	
	/**
	 * Метод, объединящий движение героев игры.
	 * <p>
	 * Для движения привидений используется метод Move(), а для
	 * движения Пакмана move() или demoMove, в зависимости от режима игры,
	 * т.е. значения переменной demo.
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public void move() throws FileNotFoundException, InterruptedException{	
		if(!demo && !replay) {								//обычный режим игры
			if(!pause) {	
				pacman.setDemo(false);
				pacman.setReplay(false);
				for (int i = 0; i<ghostNum; i++)
					ghost[i].Move();
				pacman.move();	
				repaint();
			}
		}
		else if(demo){									//автоматический режим
			pacman.setDemo(true);
			for (int i = 0; i<ghostNum; i++)
				ghost[i].Move();
			pacman.demoMove();
			repaint();
		}
		else if(replay){								//повтор игры
			pacman.setReplay(true);
			for (int i = 0; i<ghostNum; i++)
				ghost[i].notationMove();
				//ghost[i].Move();
				pacman.notationMove();
				if(pacman.collision())
				{
					pacman.lives--;
					continueLevel();
				}
				repaint();
		}
	}
	
	/**
	 * Метод установки уровня игры.
	 * 
	 * @param newLevel номер уровня игры (1-3). 1 - easy, 2 - normal, 3 - hard.
	 */
	public void setLevel(int newLevel) {
		level = newLevel;
	}
	public static int[][] grid = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,1},
			{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
			{2,2,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,2,2},
			{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
			{1,0,0,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,0,0,1},
			{1,1,1,0,1,0,0,0,0,0,0,1,2,2,1,0,0,0,0,0,0,1,0,1,1,1},
			{2,2,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,2,2},
			{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
			{2,2,1,0,1,1,1,1,0,1,1,2,2,2,2,1,1,0,1,1,1,1,0,1,2,2},
			{2,2,1,0,1,1,1,1,0,1,2,2,2,2,2,2,1,0,1,1,1,1,0,1,2,2},
			{2,2,1,0,1,1,0,0,0,0,2,2,2,2,2,2,0,0,0,0,1,1,0,1,2,2},
			{2,2,1,0,1,1,0,1,0,0,2,2,2,2,2,2,0,0,1,0,1,1,0,1,2,2},
			{1,1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,0,1,1,1},
			{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
			{1,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
			{2,2,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,2,2},
			{2,2,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,2,2},
			{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
			{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
			{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
			{2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2}};
	
	private static int[][] grid2 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,0,1},
		{1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,1},
		{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
		{2,2,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,2,2},
		{1,1,1,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,1,1,1},
		{1,0,0,0,1,0,1,0,1,1,0,1,2,2,1,0,1,1,0,1,0,1,0,0,0,1},
		{1,1,1,0,1,0,0,0,0,0,0,1,2,2,1,0,0,0,0,0,0,1,0,1,1,1},
		{2,2,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,2,2},
		{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
		{2,2,1,0,1,1,1,1,0,1,1,2,2,2,2,1,1,0,1,1,1,1,0,1,2,2},
		{2,2,1,0,1,1,1,1,0,1,2,2,2,2,2,2,1,0,1,1,1,1,0,1,2,2},
		{2,2,1,0,1,1,0,0,0,0,2,2,2,2,2,2,0,0,0,0,1,1,0,1,2,2},
		{2,2,1,0,1,1,0,1,0,0,2,2,2,2,2,2,0,0,1,0,1,1,0,1,2,2},
		{1,1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,0,1,1,1},
		{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
		{1,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
		{2,2,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,2,2},
		{2,2,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,2,2},
		{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
		{2,2,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,2,2},
		{2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2},
		{2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2}};
}
	
