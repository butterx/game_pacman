package game_pacman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;


/**
 * Класс Ghost. Этот класс содержит всю логику движения привидения. 
 * Основным методом, реализующим движение является move(). В нём направление движения
 * генерируется автоматически путём вызова метода changeDirection().
 */
public class Ghost {
	int Direction = 1;
	int x = 325;
	int y = 350;
	int dx = 0;
	int dy = 0;
	int directDX = 0;
	int directDY = 0;
	int increment = 25;
	private Map map;
	boolean cantMove = false;
	int counter = 0;
	@SuppressWarnings("unused")
	private static final int DIAMETER = 25;
	
	ArrayList<String> dest;
	Pacman pacman;
	
	/**
	 * Конструктор класса
	 * 
	 * @param  map  Обеъект класса Map, передаваемый при создании объекта Ghost
	 */
	@SuppressWarnings("static-access")
	public Ghost(Map map) {
		this.map= map;
		if(map.level == 0)
		{
			NotationWrite.create(Game.ghostFileName);
		}
		pacman = new Pacman(map);
	}
	/**
	 * Метод изменения направления
	 * Перменная Direction инициализируется случайным значением
	 * в промежутке [0 ; 4) путём использования Math.random().
	 */
	public void changeDirection() {
		dx = 0;
		dy = 0;
		Direction = (int)(Math.random()*4);
	}
	/**
	 * Метод автоматического движения привидения.
	 * Осуществляет смещение по осям координат привидения путём добавления
	 * или отнимания смещения вдоль осей в зависимости от направления.
	 * Использует случайное значение и проверяет, возможно ли двигаться 
	 * в этом напрвлении или нет. Если нельзя, то генерируется новое случайное направление
	 * @throws FileNotFoundException  
	 */
	@SuppressWarnings("static-access")
	public void Move() throws FileNotFoundException{
		int _x = (int)(x/map.BLOCK_SIZE);					//значение в массиве
	    int _y = (int)(y/map.BLOCK_SIZE);
	    switch(Direction) {
	        case 0: 
	        	if(canMove(_x-1, _y)) {						//если можно двигаться влево
	        			dx = -25; 
	        			dy = 0;
	        		}
	        	else {   
	        			cantMove = true;
	        			directDX = -25;
	        			directDY = 0;
	        			changeDirection();					//меняем направление
	        		}
	        	break;
	        case 1: 
	        	if(canMove(_x,_y-1)) {						//если можно двигаться вверх
	        		dy = -25;
	        		dx = 0;
	        	}
	        	else {										///иначе
	        		cantMove = true;					    //флаг невозможности движения
	        		directDX = 0;
        			directDY = -25;
        			changeDirection();						//меняем направление
        		}
	        	break;
	        case 2: 
	        	if(canMove(_x+1, _y)) {						//если можно двигаться вправо
	        		dx = 25;
	        		dy = 0;
	        	}
	        	else {										//иначе
	        		cantMove = true;						//флаг невозможности движения
	        		directDX = 25;
        			directDY = 0;
        			changeDirection();						//меняем направление
        		}
	        	break;
	        case 3: 
	        	if(canMove(_x,_y+1)) {						//если можно двигаться вниз
	        		dy = 25; 
	        		dx = 0;
	        	}
	        	else {										//иначе
	        		cantMove = true;						//флаг невозможности движения
	        		directDX = 0;
        			directDY = 25;
        			changeDirection();						//меняем направление
        		}
	        	break;
	        	default: break;
	        }
	    if(cantMove && canMove(_x + (directDX/25), 			//если появилась возможность для движения
	    		_y + directDY/25)) {
	    	dx = directDX;
	        dy = directDY;
	        cantMove = false;
	    }
	    else if (canMove(_x + (dx/increment), _y + dy/increment)) {	
		    x = x + dx;
		    y = y + dy;
	    }
	    if(map.level == 0){
	    	NotationWrite.update(Game.ghostFileName, x+" "+y);
	    }
	}
	
	/**
	 * <p>
	 * Метод, реалиизующий движение призрака по записанным в файл координатам.
	 * Считывает из файла координаты x и y и перемещает на них привидение 
	 */
	public void notationMove(){
	    if(counter == 0) {
	    	try {
				dest = NotationWrite.readFile(Game.ghostFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			x = Integer.parseInt(dest.get(0));
			y = Integer.parseInt(dest.get(1));
			counter+=2;
	    }
	    if(counter <= dest.size()) {
		    nextDirection();
	    }
	}
	
	/**
	 * <p>
	 * Метод для перехода к следующим координатам в файле.
	 * <p>
	 * Сначала считывает координату x, а затем y.
	 * В зависимости от направления движения перерисовывает привидение.
	 */
	void nextDirection()
	{
		if(counter < dest.size())
		{
			x = Integer.parseInt(dest.get(counter));
			y = Integer.parseInt(dest.get(counter+1));
		}
		counter+=2;
	}
	
	/**
	 * Метод, рисующий привидений. 
	 * Принимает объект класса Graphics2D и номер привидения.
	 * 
	 * @param g объект класса Graphics2D для рисования в панели
	 * @param number номер привидения, используемый для выбора изображения  
	 */
	public void paint(Graphics2D g, int number) {
		
		if(number == 0 || number > 4)
			number = 1;
		Image ghost = new ImageIcon("images/ghost"+number+".png").getImage();
		g.drawImage(ghost, x, y, map);
	}
	/**
	 * Метод, проверяющий возможность движения.
	 * В качестве параметров принимает координаты ячейки в матрице.
	 * <p>
	 * Если значение ячейки равно 0, 2 или 3, то метод возвращает true, т.е.
	 * привидение может передвигаться на эти координаты. В противном случае 
	 * возвращается значение false, т.е. запрет на движение.
	 * 
	 * @param x номер строки матрицы
	 * @param y номер столбца матрицы
	 * @return true, если возможно двигать на данную ячейку, false в ином случае
	 */
	public boolean canMove(int x, int y) {
		if((x >= 0 && x < 25 )&& (y >= 0 && y < 26) ) {
			if((Map.grid[y][x] == 0)||(Map.grid[y][x] ==2) ||(Map.grid[y][x] ==3))
				return true;
			else 
				return false;
		}
		else return false;
	}
}
