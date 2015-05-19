(ns chess-game.moves-test
  (:require [cljs.test :refer-macros [deftest are]]
            [chess-game.board :as b]
            [chess-game.moves :as m]))

(deftest test-allowed?
  (let [board (b/make-standard-board)]
    (are [old-pos new-pos allowed?]
         (= (m/allowed? board old-pos new-pos) allowed?)
         ; TODO: change to the real moves
         '(0 0) '(0 1) false
         '(0 3) '(0 4) false
         '(0 0) '(0 6) true
         '(0 0) '(0 3) true)))
