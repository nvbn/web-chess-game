(ns chess-game.components.chessboard-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [chess-game.config :as config]
            [chess-game.components.chessboard :as c]))

(deftest test-get-chessboard-items
  (testing "With blank chessboard"
    (let [board (c/get-chessboard-items {} [] nil)]
      (is (= (count board) (* config/board-tiles-x config/board-tiles-y)))
      (is (empty? (filter :chessman board)))
      (is (empty? (filter :selected board)))))
  (testing "Filled not and not selected"
    (let [chessman {:color :white
                    :type :rook}
          board (c/get-chessboard-items {[0 0] chessman} [] nil)]
      (is (= 1 (count (filter :chessman board))))
      (is (first board) {:chessman chessman
                         :i 0
                         :j 0
                         :selected false
                         :msg-ch nil})))
  (testing "Filled and selected"
    (let [board (c/get-chessboard-items {[0 0] {:color :white
                                                :type :rook}}
                                        [0 0] nil)]
      (is (true? (-> board first :selected))))))

; TODO: add tests for svg
