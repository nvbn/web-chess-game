(ns chess-game.moves
  (:require [quil.core :as q]
            [chess-game.core :as core]))


(defn moves-for [curr-pos chessman chessboard]
  "Returns a pair (x y) for each possible move location
  for the chessman."

  (let [moves-to-try (try-moves-for curr-pos chessman chessboard)]
    (filter #(legal-move? curr-pos % chessman chessboard) moves-to-try)))


(defn try-moves-for [curr-pos chessman chessboard]
  "A list of positions to try, not necessarily legal."
  (let [type (:type chessman)
        color (:color chessman)]
    (case type
      :rook (try-moves-for-rook curr-pos color chessboard)
      :knight (try-moves-for-rook curr-pos color chessboard)
      :bishop (try-moves-for-rook curr-pos color chessboard)
      :queen (try-moves-for-rook curr-pos color chessboard)
      :king (try-moves-for-rook curr-pos color chessboard)
      :pawn (try-moves-for-rook curr-pos color chessboard))))


(defn try-moves-for-rook [curr-pos color chessboard]
  "Attempt moves in all the cardinal directions."

  nil)

(defn try-moves-for-knight [curr-pos color chessboard]
  "Attempt jumps in adjacent corners"
  nil)

(defn try-moves-for-bishop [curr-pos color chessboard]
  ""
  nil)

(defn try-moves-for-queen [curr-pos color chessboard]
  nil)

(defn try-moves-for-king [curr-pos color chessboard]
  nil)

(defn try-moves-for-pawn [curr-pos color chessboard]
  nil)

(defn legal-move? [start-pos dest-pos chessman chessboard]
  "Decide of the move of chessman to dest-pos"

  nil)
