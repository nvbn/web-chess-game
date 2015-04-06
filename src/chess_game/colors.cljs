(ns chess-game.colors
  "Note: Let's keep this file mostly stateless.
   We don't need to modify the drawing context,
  just access some pre-defined shades of black and white."
  (:require [chess-game.drawing :as d]))

(defn gray-scale
  [scale]
  "Sets the fill color to a gray scale, values ranging between 0 and 255"
  (d/make-color scale scale scale))

(defn light-blue
  [scale]
  "A light blue color, designed for clicking on a white square."
  (d/make-color (- scale 50) (- scale 50) scale))

(defn dark-blue
  [scale]
  "A dark blue color, designed for clicking on a black square."
  (d/make-color scale scale (+ scale 50)))

(defn white-default
  []
  "Default white color"
  (gray-scale 255))

(defn black-default
  []
  "Default black color"
  (gray-scale 50))

(defn white-selected
  []
  (light-blue 255))

(defn black-selected
  []
  (dark-blue 50))

(defn tile-fill
  [color selected]
  "Fill the tile based on color (:white or :black) and whether the tile is currently selected.
This function will probably take a hash table in the future, with options on what state the
square is in. For example, the square could be:

  * Tinted green to show that the currently selected piece can move into this square.
  * Tinted red to show that moving your piece there would leave you vulnerable to being
    captured.
  * Tinted purple for a move that would put you into check.
  * Tinted yellow when the computer is thinking about placing a piece.

There are probably lots more, but those are just some of them that came off the top of my head.
"
  (if selected
    (case color
      :white (white-selected)
      :black (black-selected))
    (case color
      :white (white-default)
      :black (black-default))))

(defn square-should-be-white?
  [x y]
  "Return true if the square at (x, y) should be white"
  (even? (+ x y)))

(defn square-color
  [x y]
  (if (square-should-be-white? x y)
    :white
    :black))

(defn tile-fill-color
  [x y selected]
  (tile-fill (square-color x y) selected))
