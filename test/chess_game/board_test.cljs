(ns chess-game.board-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [chess-game.moves :as m]
            [chess-game.board :as b]))

(deftest test-make-chessman
  (is (= (b/make-chessman :black :rook 0 0)
         {:color :black
          :type :rook
          :has-moved false
          :captured false
          :start-x 0
          :start-y 0})))

(deftest test-update-board
  (testing "When can move"
    (with-redefs [m/allowed? (constantly true)]
      (is (= (b/update-board {'(1 1) (b/make-chessman :black :pawn 0 0)}
                             '(1 1) '(1 2))
             {'(1 2) (b/make-chessman :black :pawn 0 0)
              '(1 1) nil}))))
  (testing "When can't"
    (with-redefs [m/allowed? (constantly false)]
      (let [board {'(1 1) (b/make-chessman :black :pawn 0 0)}]
        (is (= (b/update-board board '(1 1) '(1 2))
               board))))))
