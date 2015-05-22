(ns chess-game.images-test
  (:require [cljs.test :refer-macros [deftest is]]
            [quil.core :as q]
            [chess-game.images :as img]))

(deftest test-get-images!
  (is (= (img/get-images!)
         {:white {:pawn "/images/white-pawn.png"
                  :king "/images/white-king.png"
                  :bishop "/images/white-bishop.png"
                  :knight "/images/white-knight.png"
                  :rook "/images/white-rook.png"
                  :queen "/images/white-queen.png"}
          :black {:pawn "/images/black-pawn.png"
                  :king "/images/black-king.png"
                  :bishop "/images/black-bishop.png"
                  :knight "/images/black-knight.png"
                  :rook "/images/black-rook.png"
                  :queen "/images/black-queen.png"}})))
