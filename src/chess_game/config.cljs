
(ns ^:figwheel-always
  chess-game.config
  (:require [jayq.core :as jq]))

(def board-tiles-x 8)
(def board-tiles-y 8)

(def tile-size 75)
(def board-width  (* board-tiles-x tile-size))
(def board-height (* board-tiles-y tile-size))

(def board-size
  [(* board-tiles-x tile-size)
   (* board-tiles-y tile-size)])
