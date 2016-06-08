package game_pacman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * <p>
 * Класс героя Pacman. Этот класс содержит всю логику движения героя.
 * <p>
 * Движение организовывается с помощью двух методов: move() и demoMove().
 * В первом методе движение pacmana юудет осуществляться в направлении,
 * выбранным в результате нажатия на клавиатуре. Во втором методе движение
 * осуществялется автоматически. Выбор направления осуществляется случайным образом
 * путём вызова метода changeDirection().
 */

public class Pacman {
	public static final int DIAMETER = 25;
	int x = 25 ;
	int y = 25;
	int dx = 0;
	int dy = 0;
	int desiredDX = 0;
	int desiredDY = 0;
	int Direction = 1;
	int lives = 3;
	int increment = 25;
	boolean demoMode = false;
	boolean replayMode = false;
	Image pac = new ImageIcon("images/Pacman.png").getImage();
	String fileName;
	ArrayList<String> dest;
    int counter;
	
	private Map map;
	
	boolean cantMove = false;
	/**
	 * <p>
	 * Конструктор класса.
	 * @param  map  Обеъект класса Map, передаваемый при создании объекта Pacman
	 */
	public Pacman(Map map) {								//конструктор
		this.map= map;
		if(Map.level == 0)
		{
			NotationWrite.create(Game.fileName);
		}
		counter = 0;
	}
	
	/**
	 * <p>
	 * Метод, реалиизующий движение героя. Вычисляет положение Pacmana 
	 * в матрице и проверяет, возможно ли движение в заданном направлении.
	 * Если да, то осуществляет смещение по осям координат путём добавления
	 * или отнимания смещения вдоль осей в зависимости от направления.
	 * <p>
	 * Осуществляет проверку на столкновение с привидением. Если есть, то
	 * уменьшает количество оставшихся жизней.
	 * <p>
	 * Проверяет, съедены ли все точки. Если да, то вызывает окно с сообщением о победе. 
	 * @throws FileNotFoundException  
	 */
	void move() throws FileNotFoundException{											
		int _x = (int)(x/map.BLOCK_SIZE);					//получение текущего положения		
	    int _y = (int)(y/map.BLOCK_SIZE);					//в матрице(карте)
	    
	    if(cantMove && canMove(_x + (desiredDX/increment),	//если появилась возможность для движения
	    		_y + desiredDY/increment)) {
	    	dx = desiredDX;									//устанавливаем направление
	        dy = desiredDY;
	        cantMove = false;
	        if(dx == -25)									//перерисовываем пакмана 
	        {
	        	drawPacmanLeft();							//в зависимости от направления
	        }
	        if(dx == 25)
	        {
	        	drawPacmanRight();
	        }
	        if(dy == -25)
	        {
	        	drawPacmanU();
	        }
	        if(dy == 25)
	        {
	        	drawPacmanD();
	        }
	    }
	    else if (canMove(_x + (dx/increment), _y + dy/increment)) {	//если нет преград
    		x += dx;												//сдвигаемся на dx
	        y += dy;												//или на dy
	        
	        if(Map.grid[y/increment][x/increment] == 0) {			//съедаем точки
	        	Map.grid[y/increment][x/increment] = 2;
	        	map.score++;										//увеличиаем количество очков
	        }
	    }
	    if(Map.level == 0) {
	    	NotationWrite.update(Game.fileName, x+" "+y);
	    }
		if (collision()) {											//если столкнулись с привидением
			lives--;												//уменьшаем кол-во жизней
			map.continueLevel();									//продолжаем игру
		}
		if(map.numOfPellets == 0) {									//если все точки съедены
			map.pacmanWin();										//окно с победой
			map.finish = true;
		}
	}
	/**
	 * <p>
	 * Метод, реалиизующий движение героя по записанным в файл координатам.
	 * Считывает из файла координаты x и y и перемещает на них Pacamana
	 * <p>
	 * Осуществляет проверку на столкновение с привидением. Если есть, то
	 * уменьшает количество оставшихся жизней.
	 * <p>
	 * Проверяет, съедены ли все точки. Если да, то вызывает окно с сообщением о победе. 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unused")
	public void notationMove(){
		int x1 = 0, y1 = 0;
	    if(counter == 0)
	    {
	    	try {
				dest = NotationWrite.readFile(Game.fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			x1 = Integer.parseInt(dest.get(0));
			y1 = Integer.parseInt(dest.get(1));
			counter+=2;
	    }
	    if(counter <= dest.size())
	    {
	    	 	if(Map.grid[y/increment][x/increment] == 0) {		//съедаем точки
		        	Map.grid[y/increment][x/increment] = 2;
		        	map.score++;
		        }
	    	 	if(collision())
				{
					lives--;
					map.continueLevel();
				}
			    if(map.numOfPellets == 0) {							//победа
					map.pacmanWin();
				}
			    
		    nextDirection();
	    }
	}
	/**
	 * <p>
	 * Метод для перехода к следующим координатам в файле.
	 * <p>
	 * Сначала считывает координату x, а затем y.
	 * В зависимости от направления движения перерисовывает Пакмана.
	 */
	void nextDirection()
	{
		int x1 = 0, y1 = 0;
		if(counter < dest.size())
		{
			x1 = Integer.parseInt(dest.get(counter));
			y1 = Integer.parseInt(dest.get(counter+1));
		}
		if((x1 - x) == 25)
			drawPacmanRight();
		if((x1 - x) == -25)
			drawPacmanLeft();
		if((y1 - y) == 25)
			drawPacmanD();
		if((y1 - y) == -25)
			drawPacmanU();
		x = x1;
		y = y1;
		counter+=2;
	}
	
	/**
	 * <p>
	 * Метод, проверяющий пересечение Пакмана с привидениями.
	 * Если расстояние между центрами героев меньше радиуса Пакмана
	 * метод возвращает значение true, иначе false.
	 */
	boolean collision() {
		for(int i = 0; i < map.ghostNum; i++)
			if((Math.abs(map.ghost[i].x - x) <= 25 && map.ghost[i].y == y) || 
					(Math.abs(map.ghost[i].y - y) <= 25 && map.ghost[i].x == x))
				return true; 
		return false;
	}
	
	/**
	 * <p>
	 * Метод, рисующий Пакмана, используя Graphics2D.
	 * 
	 * @param g объект класса Graphics2D для рисования в панели
	 */
	public void paint(Graphics2D g) {
		g.drawImage(pac,x,y, map);
	}
	
	/**
	 * <p>
	 * Метод для рисования Pacmana при его движении вправо.
	 * Ставит в качестве значения объекта pac новую картинку с повёрнутым Pacmanом.
	 */
	private void drawPacmanRight()
	{
		pac = new ImageIcon("images/Pacman.png").getImage();
	}

	/**
	 * <p>
	 *  Метод для рисования Pacmana при его движении влево.
	 * Ставит в качестве значения объекта pac новую картинку с повёрнутым Pacmanом.
	 */
	private void drawPacmanLeft()
	{
		pac = new ImageIcon("images/L.png").getImage();
	}
	
	/**
	 * <p>=
	 * Метод для рисования Pacmana при его движении вверх.
	 * Ставит в качестве значения объекта pac новую картинку с повёрнутым Pacmanом.
	 */
	private void drawPacmanU()
	{
		pac = new ImageIcon("images/U.png").getImage();
	}
	
	/**
	 * <p>
	 * Метод для рисования Pacmana при его движении вниз.
	 * Ставит в качестве значения объекта pac новую картинку с повёрнутым Pacmanом.
	 */
	private void drawPacmanD()
	{
		pac = new ImageIcon("images/D.png").getImage();
	}
	
	/**
	 * Метод, реализующий обработку клавиш. Вычисляет код нажатой клавиши
	 * и  положение Pacmana в матрице и проверяет, возможно ли движение в заданном направлении.
	 * Если возможно, то запоминает смещение для данного направления движения. Если нет, то тоже 
	 * запоминает смещение, но устанавливает флаг невозможности движения в заданном направлении.
	 * 
	 * @param e объект класса KeyEvent. Используется для получения кода нажатой клавиши.
	 * @throws FileNotFoundException 
	 */
	public void keyPressed(KeyEvent e) throws FileNotFoundException {
		int _x = (int)(x/map.BLOCK_SIZE);
	    int _y = (int)(y/map.BLOCK_SIZE);
	    if(!demoMode && !replayMode) {
		    if (e.getKeyCode() == KeyEvent.VK_LEFT 
		    		&& canMove(_x-1, _y)) {					//если можем двигаться налево
		    	drawPacmanLeft();
				dx = -increment;
				dy = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT 
					&& !canMove(_x-1, _y)) { 				//если хотим, но не можем двигаться налево
				cantMove = true; 
				desiredDX = -increment;
				desiredDY = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT 	
					&& canMove(_x + 1, _y)) {				//если можем двигаться направо
				drawPacmanRight();
				dx = increment;
				dy = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT 	//если хотим, но не можем двигатья направо
					&& !canMove(_x+1, _y)) {
				cantMove = true;
				desiredDX = increment;
				desiredDY = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP 			//если можем двигаться вверх
					&& canMove(_x, _y-1)) {
				drawPacmanU();
				dx = 0;
				dy = -increment;
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP		//если хотим, но не можем двигатья вверх
					&& !canMove(_x, _y-1)) {
				cantMove = true;
				desiredDX = 0;
				desiredDY = -increment;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN			//если можем двигаться вниз
					&& canMove(_x, _y + 1)) {
				drawPacmanD();
				dx = 0;
				dy = increment;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN 	//если хотим, но не можем двигатья вниз
					&& !canMove(_x, _y+1)) {
				cantMove = true;
				desiredDX = 0;
				desiredDY = increment;
			}
	    }
	}
	
	/**
	 * Метод, проверяющий возможность движения.
	 * В качестве параметров принимает координаты ячейки в матрице.
	 * <p>
	 * Если значение ячейки равно 0 или 2, то метод возвращает true, т.е.
	 * привидение может передвигаться на эти координаты. В противном случае 
	 * возвращается значение false, т.е. запрет на движение.
	 * 
	 * @param x номер строки матрицы
	 * @param y номер столбца матрицы
	 */
	public boolean canMove(int x, int y) {
		if((x >= 0 && x < 25 )&& (y >= 0 && y < 26) ) {
			if((Map.grid[y][x] == 0)||(Map.grid[y][x] ==2))
				return true;
			else 
				return false;
		}
		else return false;
	}
	/**
	 * Метод изменения направления
	 * Перменная Direction инициализируется случайным значением
	 * в промежутке [0 ; 4) путём использования Math.random().
	 */
	public void changeDirection() {
		dx = 0;
		dy = 0;
		Direction = (int)(Math.random()*4);							//случайное значение [0,3]
		//System.out.println( Direction );
	}

	/**
	 * Метод автоматического движения Pacmana. Используется в автоматическом режиме
	 * <p>
	 * Осуществляет смещение по осям координат привидения путём добавления
	 * или отнимания смещения вдоль осей в зависимости от направления.
	 * Использует случайное значение и проверяет, возможно ли двигаться 
	 * в этом напрвлении или нет. Если нельзя, то генерируется новое случайное направление.
	 * * <p>
	 * Осуществляет проверку на столкновение с привидением. Если есть, то
	 * уменьшает количество оставшихся жизней.
	 * <p>
	 * Проверяет, съедены ли все точки. Если да, то вызывает окно с сообщением о победе.
	 */
	@SuppressWarnings("static-access")
	public void demoMove(){
		int _x = (int)(x/map.BLOCK_SIZE);			//значение в массиве
	    int _y = (int)(y/map.BLOCK_SIZE);
	    switch(Direction) {
	        case 0: 
	        	if(canMove(_x-1, _y)) {				//если можно двигаться влево
	        			dx = -25; 
	        			dy = 0;
	        			drawPacmanLeft();			//перерисовка героя
	        		}
	        	else {   
	        			cantMove = true;			//флаг невозможности движения
	        			desiredDX = -25;
	        			desiredDY = 0;
	        			changeDirection();			//меняем направление
	        		}
	        	break;
	        case 1: 
	        	if(canMove(_x,_y-1)) {              //если можно двигаться вверх
	        		dy = -25;
	        		dx = 0;
	        		drawPacmanU();					//перерисовка героя
	        	}
	        	else {								//иначе
	        		cantMove = true;				//флаг невозможности движения
	        		desiredDX = 0;
        			desiredDY = -25;
        			changeDirection();				//меняем направление
        		}
	        	break;
	        case 2: 
	        	if(canMove(_x+1, _y)) {				//если можно двигаться вправо
	        		dx = 25;
	        		dy = 0;
	        		drawPacmanRight();
	        	}
	        	else  {								//иначе
	        		cantMove = true;				//флаг невозможности движения
	        		desiredDX = 25;
        			desiredDY = 0;
        			changeDirection();				//меняем направление
        		}
	        	break;
	        case 3: 					
	        	if(canMove(_x,_y+1)) {				//если можно двигаться вниз
	        		dy = 25; 
	        		dx = 0;
	        		drawPacmanD();
	        	}
	        	else  {								//иначе
	        		cantMove = true;				//флаг невозможности движения
	        		desiredDX = 0;
        			desiredDY = 25;
        			changeDirection();				//меняем направление
        		}
	        	break;
	        	default: break;
	        }
	    if(cantMove && canMove(_x + (desiredDX/25),
	    		_y + desiredDY/25)) {				//если появилась возможность для движения
	    	dx = desiredDX;
	        dy = desiredDY;
	        cantMove = false;
	        if(dx == -25)									//перерисовываем пакмана
	        	drawPacmanLeft();							//в зависимости от направления
	        if(dx == 25)
	        	drawPacmanRight();
	        if(dy == -25)
	        	drawPacmanU();
	        if(dy == 25)
	        	drawPacmanD();
	    }
	    x += dx;
	    y += dy;
	    if(Map.grid[y/increment][x/increment] == 0) {		//съедаем точки
        	Map.grid[y/increment][x/increment] = 2;
        	map.score++;
        }
	    if (collision()) {									//обработка столкновения с привидением
			lives--;
			map.continueLevel();
		}
	    if(map.numOfPellets == 0) {							//победа
			map.pacmanWin();
		}
	    if(map.level == 0){
	    	try {
				NotationWrite.update(Game.fileName, x+" "+y);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	/**
	  * Метод установки режима игры. Если режим игры автоматический, то в роли игрока выступает компьютер,
	  * иначе - пользователь.
	  * 
	  * @param mode принимает значение true, если используется автоматический режим игры, 
	  * false, если пользоваетльский.
	 */
	void setDemo(boolean mode) {							//установка автоматического режима
		demoMode = mode;
	}
	
	/**
	  * Метод установки режима игры. Если режим игры - повтор, то в роли игрока выступает компьютер,
	  * который воспроизводит действия, записанные в файл,
	  * иначе - пользователь.
	  * 
	  * @param mode принимает значение true, если используется режим повтора игры, 
	  * false, если пользоваетльский.
	 */
	void setReplay(boolean mode){
		replayMode = mode;
	}
}
