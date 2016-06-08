package game_pacman

import scala.annotation.tailrec
import scala.collection.JavaConversions._

object PseudoNotationScala {
  def writePseudoNotation(toScan: java.util.ArrayList[String], fileName: String, Player: String) {
    val coordList = toScan.toList
    var x = 0
    var x1 = 0
    var y = 0
    var y1 = 0

    def matcher(i: Int, j: Int) {
      x = coordList.get(i).toInt;
      x1 = coordList.get(j).toInt;
      x1 - x match {
        case -25 => {
          NotationWrite.update(fileName, Player + " сделал шаг влево");
          print(Player + " сделал шаг влево");
        }
        case 25 => {
          NotationWrite.update(fileName, Player + " сделал шаг вправо");
          print(Player + " сделал шаг вправо");
        }
        case 0 => {
          y = coordList.get(i + 1).toInt;
          y1 = coordList.get(j + 1).toInt;
          y1 - y match {
            case -25 => {
              NotationWrite.update(fileName, Player + " сделал шаг вверх");
              print(Player + " сделал шаг вверх");            
            }
            case 25 => {
              NotationWrite.update(fileName, Player + " сделал шаг вниз");
              print(Player + " сделал шаг вниз");
            }
            case 0 => {
              NotationWrite.update(fileName, Player + " стоит у стенки");
              print(Player + " стоит у стенки");
            }
            case _ => {
              NotationWrite.update(fileName, "Призрак съел Pacmana");
              print("Призрак съел Pacmana");
            }
          }
        }
        case _ => {
          NotationWrite.update(fileName, "Призрак съел Pacmana");
          print("Призрак съел Pacmana");
        }
      }
    }

    @tailrec
    def outerLoop(i: Int, j: Int) {
      if (j < coordList.size) {
        matcher(i, j)
        outerLoop(i + 2, j + 2)
      }
    }
    outerLoop(0, 2)
  }
}