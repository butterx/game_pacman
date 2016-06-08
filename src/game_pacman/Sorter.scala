package game_pacman

import scala.annotation.tailrec

object Sorter {
  def sortScala(toSort: java.util.ArrayList[Integer]) : java.util.ArrayList[Integer] = {
    val list = toSort

    @tailrec
    def outerLoop(i: Int) {

      if (i < list.size) {
        var max = 0
        var pos = -1

        @tailrec
        def innerLoop(j: Int) {
          if (j < list.size) {
            if (list.get(i) < list.get(j) && list.get(j) > max) {
              max = list.get(j)
              pos = j
            }

            innerLoop(j + 1)
          }
        }
        
        innerLoop(i + 1)

        if (pos != -1) {
          val a = list.get(i)
          list.set(i, list.get(pos))
          list.set(pos, a)
        }

        outerLoop(i + 1)
      }
    }

    outerLoop(0)
    list
  }
}