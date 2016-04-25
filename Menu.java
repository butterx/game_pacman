import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 

import org.eclipse.swt.SWT; 
import org.eclipse.swt.events.SelectionAdapter; 
import org.eclipse.swt.events.SelectionEvent; 
import org.eclipse.swt.graphics.Color; 
import org.eclipse.swt.graphics.Image; 
import org.eclipse.swt.widgets.Button; 
import org.eclipse.swt.widgets.Combo; 
import org.eclipse.swt.widgets.Control; 
import org.eclipse.swt.widgets.Display; 
import org.eclipse.swt.widgets.Event; 
import org.eclipse.swt.widgets.Label; 
import org.eclipse.swt.widgets.Listener; 
import org.eclipse.swt.widgets.Shell; 
import org.eclipse.swt.widgets.Table; 
import org.eclipse.swt.widgets.TableColumn; 
import org.eclipse.swt.widgets.TableItem; 
import org.eclipse.swt.widgets.Text; 


public class Menu {
	private static Table table; 
	private static int level; 
	public static PlayWindow play;
	
	Menu() {
		final  Display display  =  new Display( );        
		final Shell shell  =  new Shell( display,SWT.DIALOG_TRIM );       
		//play = new PlayWindow( shell, display ); 
		shell.setSize( 300, 400 );                       // СѓСЃС‚Р°РЅРѕРІРєР° СЂР°Р·РјРµСЂРѕРІ РѕРєРЅР°
		openMenu( shell, display );                       // РІС‹Р·РѕРІ РјРµС‚РѕРґР° openMenu
		shell.open( ); 
		while ( !shell.isDisposed( ) ) {
		  if ( !display.readAndDispatch( ) ) {
		    display.sleep( ); 
		  }
	    } 
		display.dispose( ); 
	}
	
	public static void openMenu( final Shell shell, final Display display ) {  // РјРµС‚РѕРґ openMenu
		for ( Control kid : shell.getChildren( ) ) {                         // С†РёРєР» РѕС‡РёС‰Р°РµС‚ РІСЃРµ СЂР°РЅРµРµ СЃРѕР·РґР°РЅРЅС‹Рµ РѕР±СЊРµРєС‚С‹ РІ РѕРєРЅРµ shell
		  kid.dispose( ); 
		}
		Color black  =  display.getSystemColor( SWT.COLOR_BLACK ); 
	    
		shell.setText( "Pacman" );         
		shell.setBackgroundImage(new Image(display,"pacman.jpg"));
		shell.setImage(new Image(display,"pacman_ico.png"));
		
		Button start_button  =  new Button( shell, SWT.PUSH );                  //РєРЅРѕРїРєР° СЃС‚Р°СЂС‚
		start_button.setText( "&START GAME" );                                  //С‚РµРєСЃС‚ РєРЅРѕРїРєРё
		start_button.setBounds( 100, 120, 100, 40 );                            //СЂР°Р·РјРµСЂ Рё РїРѕР»РѕР¶РµРЅРёРµ РєРЅРѕРїРєРё
		start_button.setForeground(black);
		Listener startListener  =  new Listener( ) {                            // РґРµР№СЃС‚РІРёРµ РЅР° РЅР°Р¶Р°С‚РёРµ РєРЅРѕРїРєРё
		  public void handleEvent( Event event ) {
	        chooseGameMod( shell, display );        
	      }
		};
		start_button.addListener( SWT.Selection, startListener );   // РїСЂРёРІСЏР·РєР° РєРЅРѕРїРєРё Рє РґРµР№СЃС‚РІРёСЋ
		
		Button rec_button  =  new Button( shell, SWT.None );    //РєРЅРѕРїРєР° СЂРµРєРѕСЂРґС‹
		rec_button.setText( "&RECORDS" ); 
		rec_button.setBounds( 100, 180, 100, 40 );
		rec_button.setForeground(black);
		Listener recListener  =  new Listener( ) {
		  public void handleEvent( Event event ) {
//		    openRecords( shell, display ); 
		  }
		};     
		rec_button.addListener( SWT.Selection, recListener );
		
		Button infoButton  =  new Button( shell, SWT.None );   //РєРЅРѕРїРєР° РёРЅС„РѕСЂРјР°С†РёСЏ
	    infoButton.setText( "&INFO" ); 
		infoButton.setBounds( 100, 240, 100, 40 );
		infoButton.setForeground(black);
		Listener infoListener  =  new Listener( ) {
		  public void handleEvent( Event event ) {
		    openInfo( shell, display ); 
		  }
		};     
		infoButton.addListener( SWT.Selection, infoListener ); 
		
		Button exitButton  =  new Button( shell, SWT.PUSH );   //РєРЅРѕРїРєР° РІС‹С…РѕРґ
		exitButton.setBounds( 100, 300, 100, 40 ); 
		exitButton.setText( "&EXIT" );
		exitButton.setForeground(black);
		Listener exitListener  =  new Listener( ) {
		  public void handleEvent( Event event ) {
		    System.exit( 0 ); 
		  }
	    };   		          
	    exitButton.addListener( SWT.Selection, exitListener );
	}
	
	public static void openInfo( final Shell shell, final Display display ) {
		for ( Control kid : shell.getChildren( ) ) {
	      kid.dispose( ); 
	    }
		 
	    Color red  =  display.getSystemColor( SWT.COLOR_RED ); 
	    Color black  =  display.getSystemColor( SWT.COLOR_BLACK ); 
	    Color white  =  display.getSystemColor( SWT.COLOR_WHITE ); 
	    shell.setText( "Information" ); 
	 
	    Button exitButton  =  new Button( shell, SWT.PUSH ); 
	    exitButton.setText( "EXIT" ); 
	    exitButton.setBackground( red ); 
	    exitButton.setForeground( black ); 
	    exitButton.setBounds( 155, 320, 100, 40 ); 
	 
	    Button backButton  =  new Button( shell, SWT.PUSH ); 
	    backButton.setText( "BACK" ); 
	    backButton.setBackground( white ); 
	    backButton.setForeground( black ); 
	    backButton.setBounds( 35, 320, 100, 40 );     
	 
	    String info = "Р�РіСЂРѕРє СѓРїСЂР°РІР»СЏРµС‚ РєСЂСѓРіР»С‹Рј, Р¶РµР»С‚С‹Рј С‚РµР»РѕРј, РЅР°РїРѕРјРёРЅР°СЋС‰РёРј С€Р°СЂ, " +
	    		      "РєРѕС‚РѕСЂРѕРµ РїРµСЂРµРґРІРёРіР°РµС‚СЃСЏ РїРѕ Р»Р°Р±РёСЂРёРЅС‚Сѓ, СЃРѕР±РёСЂР°СЏ РµРґСѓ, РёР·Р±РµРіР°СЏ РїСЂРёР·СЂР°РєРѕРІ." +
	    		      "РљР°Р¶РґС‹Р№ СЂР°Р·, РєРѕРіРґР° РїР°РєРјР°РЅ" +
	    		      " СЃСЉРµРґР°РµС‚ РєСѓСЃРѕРє РїРёС‰Рё, РѕРЅ Р·Р°СЂР°Р±Р°С‚С‹РІР°РµС‚ РѕС‡РєРёСЋ." +
	    		      "Р�РіСЂРѕРє СѓРїСЂР°РІР»СЏРµС‚ РЅР°РїСЂР°РІР»РµРЅРёРµРј РґРІРёР¶РµРЅРёСЏ РїР°РєРјСЌРЅР° . Р�РіСЂРѕРє" +
	 	           	  " РЅРµ РјРѕР¶РµС‚ РѕСЃС‚Р°РЅРѕРІРёС‚СЊ РґРІРёР¶РµРЅРёРµ РїР°РєРјСЌРЅР°."; 
	    Label infoLabel  =  new Label( shell, SWT.WRAP | SWT.CENTER );            
	    infoLabel.setText( info ); 
	    infoLabel.setBounds( 45, 20, 200, 240 ); 
	    infoLabel.setBackground(black);
	    infoLabel.setForeground(white);
	 
	    Listener  backListener  =  new Listener( ) {
	      public void handleEvent( Event event ) {
	  	    openMenu( shell, display ); 
	      }
	    };     
	    Listener exitListener  =  new Listener( ) {
	      public void handleEvent( Event event ) {
	        System.exit( 0 ); 
	      }
	    };   		          
	    exitButton.addListener( SWT.Selection, exitListener ); 
	    backButton.addListener( SWT.Selection, backListener );        
	  }
	
	public static void openStartSettings( final Shell shell, final Display display ) {
		for ( Control kid : shell.getChildren( ) ) {
		  kid.dispose( ); 
		}
	    level = 2; 	
		Color green  =  display.getSystemColor( SWT.COLOR_GREEN ); 
		Color red  =  display.getSystemColor( SWT.COLOR_RED ); 
		Color white  =  display.getSystemColor( SWT.COLOR_WHITE ); 
		Color black  =  display.getSystemColor( SWT.COLOR_BLACK ); 
		shell.setText( "New game" ); 
		    
		Label selectLevelLabel  =  new Label( shell, SWT.None );            
		selectLevelLabel.setText( "Select lvl  " ); 
		selectLevelLabel.setBounds( 55, 105, 70, 30 );
		selectLevelLabel.setBackground(black);
		selectLevelLabel.setForeground(white);
		   
		final Combo selectLevel  =  new Combo( shell, SWT.DROP_DOWN | SWT.READ_ONLY ); 
		selectLevel.setBounds( 130, 100, 100, 30 ); 
		selectLevel.add( "EASY" ); 
		selectLevel.add( "NORMAL" ); 
		selectLevel.add( "HARD" ); 
		selectLevel.setText( "EASY" ); 
		selectLevel.addSelectionListener( new SelectionAdapter( ) {
		  public void widgetSelected( SelectionEvent e ) {    
		    if ( selectLevel.getText( ).equals( "NORMAL" ) ) {
		   	  level = 1; 
		    } else if( selectLevel.getText( ).equals( "HARD" ) ) {
		        level = 0; 
		      } else level = 2; 
		  }
		}); 
		       
		Label playerNameLabel  =  new Label( shell, SWT.None );            
		playerNameLabel.setText( "Your name :  " ); 
		playerNameLabel.setBounds( 55, 155, 70, 30 ); 
		playerNameLabel.setBackground(black);
		playerNameLabel.setForeground(white);
		final Text playerName  =  new Text ( shell, SWT.BORDER ); 
		playerName.setBounds( 130, 150, 100, 30 ); 
		    
	    Button startButton  =  new Button( shell, SWT.PUSH ); 
		startButton.setText( "&START" ); 
		startButton.setBackground( red ); 
		startButton.setForeground( black ); 
		startButton.setBounds( 155, 300, 100, 40 ); 
		 
		Button backButton  =  new Button( shell, SWT.PUSH ); 
		backButton.setText( "BACK" ); 
		backButton.setBackground( white ); 
		backButton.setForeground( black ); 
		backButton.setBounds( 35, 300, 100, 40 );    
		Listener backListener  =  new Listener( ) {
	      public void handleEvent( Event event ) {  	
	    	  chooseGameMod( shell, display ); 
		  }
		};      
		          
		Listener startListener  =  new Listener( ) {
		  public void handleEvent( Event event ) {        		
	        String name; 	            	
		    if ( playerName.getCaretPosition( ) == 0 )
		      name = "( no name )"; 
		    else name = playerName.getText( ); 
//		      play.open( shell, display, name, level ); 
		    shell.setVisible( false ); 
		  }
		};   		          
		startButton.addListener( SWT.Selection, startListener ); 
		backButton.addListener( SWT.Selection, backListener ); 
	  }
	
	public static void chooseGameMod(final Shell shell, final Display display) {
		for ( Control kid : shell.getChildren( ) ) {
			  kid.dispose( ); 
			}
		Color white  =  display.getSystemColor( SWT.COLOR_WHITE ); 
		Color black  =  display.getSystemColor( SWT.COLOR_BLACK ); 
		shell.setText( "Game mod" );
		
		Button onePlayer  =  new Button( shell, SWT.PUSH ); 
		onePlayer.setText( "&One palyer" ); 
		onePlayer.setBackground( white ); 
		onePlayer.setForeground( black ); 
		onePlayer.setBounds( 100, 120, 100, 40 );
		Listener oneListener = new Listener() {
			public void handleEvent(Event event) {
				openStartSettings(shell, display);
			}
		};
		 
		Button multiPlayer  =  new Button( shell, SWT.PUSH ); 
		multiPlayer.setText( "Multiplayer" ); 
		multiPlayer.setBackground( white ); 
		multiPlayer.setForeground( black ); 
		multiPlayer.setBounds( 100, 180, 100, 40 );    
		/*Listener multiListener  =  new Listener( ) {
	      public void handleEvent( Event event ) {  	
		   	openMenu( shell, display ); 
		  }
		};*/
		
		Button backButton  =  new Button( shell, SWT.PUSH ); 
		backButton.setText( "Back" ); 
		backButton.setBackground( white ); 
		backButton.setForeground( black ); 
		backButton.setBounds( 100, 240, 100, 40 );
		Listener backListener  =  new Listener( ) {
			public void handleEvent( Event event ) {  	
				openMenu( shell, display ); 
				}
			};
			
		onePlayer.addListener( SWT.Selection, oneListener ); 
		//multiPlayer.addListener( SWT.Selection, backListener ); 
		backButton.addListener(SWT.Selection, backListener);
	}
	
	public static void fillTable( Table table, String filename, int k ) {   //СЃС‡РёС‚С‹РІР°РЅРёРµ РёР· С„Р°Р№Р»Р° Рё Р·Р°РїРёСЃСЊ РІ С‚Р°Р±Р»РёС†Сѓ
		String name[] = new String[10], rec[] = new String[10]; 
	    try{
		  BufferedReader myfile  =  new BufferedReader ( new FileReader( filename ) ); 	
		  try {
			for( int i = 0; i<10; i++ ) {
			  name[i] = myfile.readLine( ); 
			  rec[i] = myfile.readLine( ); 
			}
	      } finally {  
			  myfile.close( ); 
			} 
		} catch( IOException e ) {
		    throw new RuntimeException( e ); 
		  }
	    for ( int i  =  0; i < 10; i++ ) {
	      TableItem item  =  new TableItem( table, SWT.NONE ); 
	      item.setText( 0, name[i] ); 
	      item.setText( 1, rec[i] ); 
	    }
	    for ( int i = 0; i<k; i++ ) {
	      table.getColumn ( i ).pack ( ); 
	    }   
	    table.setBounds( 45, 40, 200, 280 ); 		  
	  }	             	
}