(ns chess-game.components.tile-test
  (:require [cljs.test :refer-macros [deftest are]]
            [chess-game.config :as config]
            [chess-game.components.tile :as t]))

(deftest test-tile-color
  (are [x y selected color] (= (t/tile-color x y selected) color)
    0 0 false (get-in config/colors [:white false])
    1 1 true (get-in config/colors [:white true])
    1 2 false (get-in config/colors [:black false])
    2 3 true (get-in config/colors [:black true])))

; TODO: add tests for svg
