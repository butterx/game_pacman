package game_pacman

import scala.collection.JavaConversions._
import scala.annotation.tailrec

object Statistics {
  def getStatistics(toScan: java.util.ArrayList[String]): java.util.ArrayList[Integer] = {
    val list = toScan.toList
    var x = 0
    var y = 0
    var x1 = 0
    var y1 = 0
    var countRight = 0
    var countLeft = 0
    var countUp=0
    var countDown = 0
    var countStop = 0
    var Right : java.util.ArrayList[Integer] = new java.util.ArrayList[Integer]()
    var Left : java.util.ArrayList[Integer] = new java.util.ArrayList[Integer]()
    var Up : java.util.ArrayList[Integer] = new java.util.ArrayList[Integer]()
    var Down : java.util.ArrayList[Integer] = new java.util.ArrayList[Integer]()
    var myList : java.util.ArrayList[Integer] = new java.util.ArrayList[Integer]()
   
    @tailrec
    def outerLoop(i:Int, j:Int)
    {
          if(j < list.size){
          x = list.get(i).toInt;
          x1 = list.get(j).toInt;
          print(x)
          if(x1-x == 25)
          { 
            countRight+=1
            Right.add(countRight)
          }
          else if(x1 - x == -25)
          {
            countLeft+=1
            Left.add(countLeft)
          }
          else if(x1 - x == 0)
          {
            y = list.get(i+1).toInt;
            y1 = list.get(j+1).toInt;
            
            if(y1-y == 25)
            {
              countDown+=1
              Down.add(countDown);
            }
            else if(y1 - y == -25)
            {
              countUp+=1
              Up.add(countUp);
            }
            else if(y1 - y == 0)
              countStop+=1
          }
          outerLoop(i+2, j+2)
        }
     }
    
     outerLoop(0, 2)
        myList.add(Right.size)
        myList.add(Left.size)
        myList.add(Up.size)
        myList.add(Down.size)
        
        myList
      }
}