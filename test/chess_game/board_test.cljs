(ns chess-game.board-test
  (:require [cljs.test :refer-macros [deftest is]]
            [chess-game.board :as b]))

(deftest test-make-chessman
  (is (= (b/make-chessman :black :rook 0 0)
         {:color :black
          :type :rook
          :has-moved false
          :captured false
          :start-x 0
          :start-y 0})))
