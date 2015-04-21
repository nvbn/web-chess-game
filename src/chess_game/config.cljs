(ns chess-game.config)

(def tile-size 120)

(def board-tiles-x 8)
(def board-tiles-y 8)

(def board-size
  [(* board-tiles-x tile-size)
   (* board-tiles-y tile-size)])
