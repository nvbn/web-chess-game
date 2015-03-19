(ns chess-game.board
  (:require [chess-game.colors :as colors]
            [chess-game.config :as config]
            [chess-game.drawing :as drawing]))

(defn make-chessman [color type start-x start-y]
  "Creates a hash table for storing a chessman"
  (hash-map :color color                                    ; Should only ever be :black or :white
            :type type                                      ; Should only ever be one of [:pawn, :king, :bishop,
            :has-moved false                                ;                             :queen, :rook, :knight]
            :has-moved false
            :captured false
            :start-x start-x
            :start-y start-y))

(defn standard-board []
  "Standard chess board implemented as a hash table"
  (hash-map

    ;; Black pieces
    '(0 0) (make-chessman :black :rook 0 0)
    '(1 0) (make-chessman :black :knight 1 0)
    '(2 0) (make-chessman :black :bishop 2 0)
    '(3 0) (make-chessman :black :queen 3 0)
    '(4 0) (make-chessman :black :king 4 0)
    '(5 0) (make-chessman :black :bishop 5 0)
    '(6 0) (make-chessman :black :knight 6 0)
    '(7 0) (make-chessman :black :rook 7 0)

    ;; Black pawns
    '(0 1) (make-chessman :black :pawn 0 1)
    '(1 1) (make-chessman :black :pawn 1 1)
    '(2 1) (make-chessman :black :pawn 2 1)
    '(3 1) (make-chessman :black :pawn 3 1)
    '(4 1) (make-chessman :black :pawn 4 1)
    '(5 1) (make-chessman :black :pawn 5 1)
    '(6 1) (make-chessman :black :pawn 6 1)
    '(7 1) (make-chessman :black :pawn 7 1)

    ;; White pawns
    '(0 6) (make-chessman :white :pawn 0 6)
    '(1 6) (make-chessman :white :pawn 1 6)
    '(2 6) (make-chessman :white :pawn 2 6)
    '(3 6) (make-chessman :white :pawn 3 6)
    '(4 6) (make-chessman :white :pawn 4 6)
    '(5 6) (make-chessman :white :pawn 5 6)
    '(6 6) (make-chessman :white :pawn 6 6)
    '(7 6) (make-chessman :white :pawn 7 6)

    ;; White pieces
    '(0 7) (make-chessman :white :rook 0 7)
    '(1 7) (make-chessman :white :knight 1 7)
    '(2 7) (make-chessman :white :bishop 2 7)
    '(3 7) (make-chessman :white :queen 3 7)
    '(4 7) (make-chessman :white :king 4 7)
    '(5 7) (make-chessman :white :bishop 5 7)
    '(6 7) (make-chessman :white :knight 6 7)
    '(7 7) (make-chessman :white :rook 7 7)))


(defn draw-checkered-board [env]
  "Draw the checkered board that all chess games are played on"
  (let [size config/tile-size]
    ;; Iterate both i & j over the sequence 0,1,2..9
    (doseq [i (range 0 config/board-width)
            j (range 0 config/board-height)]

      (let [ctx (:drawing-context env)
            selected (= (list i j) (:selected-tile env))
            fill-color (colors/square-color i j)]

        (drawing/set-fill! ctx (colors/tile-fill-color fill-color selected))
        (drawing/draw-rect ctx (* i size) (* j size) size size)))))
