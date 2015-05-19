(ns chess-game.core-test
  (:require [cljs.test :refer-macros [deftest is]]
            [clj-di.test :refer-macros [with-registered]]
            [clj-di.core :refer [get-dep]]
            [chess-game.images :as i]
            [chess-game.board :as b]
            [chess-game.core :as c]))

(deftest test-setup!
  (with-registered [:env (atom {})]
    (with-redefs [i/get-images! (constantly :images)]
      (c/setup!)
      ; Just ensure that `:env` filed:
      (is (not= @(get-dep :env) {})))))

(deftest test-mark-selected-tile
  (let [board (b/make-standard-board)]
    (with-registered [:env (atom {:selected-tile nil
                                  :chessboard board})]
      (c/mark-selected-tile! {:x 250 :y 130})
      (is (= @(get-dep :env) {:selected-tile '(2 1)
                              :chessboard board}))
      (c/mark-selected-tile! {:x 250 :y 130})
      (is (= @(get-dep :env) {:selected-tile nil
                              :chessboard board})))))
