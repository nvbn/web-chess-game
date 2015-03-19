(ns chess-game.board-test
  (:require-macros [cljs.test :refer [deftest is run-tests]])
  (:require [chess-game.board :as b]))

(deftest test-make-chessman
  (is (= (b/make-chessman :black :rook 0 0)
         {:color :black
          :type :rook
          :has-moved false
          :captured false
          :start-x 0
          :start-y 0})))
