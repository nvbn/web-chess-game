(ns chess-game.test-utils)

(defn get-graphics-mock
  []
  (reify Object
    (color [_ r g b] [r g b])))
