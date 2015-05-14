(ns chess-game.drawing-test
  (:require [cljs.test :refer-macros [deftest is are]]
            [quil.core]
            [clj-di.test :refer-macros [with-registered]]
            [chess-game.test-utils :refer-macros [with-mock-quil]]
            [chess-game.images :as img]
            [chess-game.drawing :as d]))

(deftest test-gray-scale
  (with-mock-quil
    (is (= (d/gray-scale 100) [100 100 100]))))

(deftest test-light-blue
  (with-mock-quil
    (is (= (d/light-blue 100) [50 50 100]))))

(deftest test-dark-blue
  (with-mock-quil
    (is (= (d/dark-blue 100) [100 100 150]))))

(deftest test-draw-chessman
  (let [drawed (atom [])]
    (with-redefs [img/draw-chessman! #(swap! drawed conj [%1 %2])]
      (with-registered [:env (atom {:chessboard [[2 :white-pawn]
                                                 [1 :black-king]]})]
        (d/draw-chessmens!)
        (is (= @drawed [[:white-pawn 2]
                        [:black-king 1]]))))))

(deftest test-tile-fill
  (with-mock-quil
    (are [color selected result] (= (d/tile-fill color selected) result)
      :white true [205 205 255]
      :black true [50 50 100]
      :white false [200 200 200]
      :black false [50 50 50])))

(deftest test-square-should-be-white?
  (are [x y result] (= (d/square-should-be-white? x y) result)
    0 0 true
    1 1 true
    2 1 false
    3 0 false))

(deftest test-square-color
  (are [x y result] (= (d/square-color x y) result)
    0 0 :white
    1 2 :black))

(deftest test-tile-fill-color
  (with-mock-quil
    (are [x y selected result] (= (d/tile-fill-color x y selected) result)
      0 0 false [200 200 200]
      1 1 true [205 205 255]
      1 2 false [50 50 50]
      2 1 true [50 50 100])))

(deftest test-get-golor-from
  (with-mock-quil
    (are [x y x-selected y-selected result]
      (with-registered [:env (atom {:selected [x-selected y-selected]})]
        (= (d/get-color-from x y) result))
      0 0 0 0 [200 200 200]
      1 0 1 0 [50 50 50])))
