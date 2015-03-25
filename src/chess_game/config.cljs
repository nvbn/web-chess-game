(ns chess-game.config)

(def tile-size 75)
(def board-width 8)
(def board-height 8)

(def board-tiles-x 8)
(def board-tiles-y 8)

(def board-width  (* board-tiles-x tile-size))
(def board-height (* board-tiles-y tile-size))

(def board-size
  [(* board-tiles-x tile-size)
   (* board-tiles-y tile-size)])
