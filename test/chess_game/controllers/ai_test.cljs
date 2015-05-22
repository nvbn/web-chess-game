(ns chess-game.controllers.ai-test
  (:require [cljs.test :refer-macros [deftest are]]
            [chess-game.controllers.ai :as a]))

(deftest test-is-any-ai?
  (are [chessboard color result] (= (boolean (a/is-any-ai? chessboard color))
                                    result)
    {[0 0] {:color :white}} :white true
    {[0 0] {:color :white}} :black false
    {[0 0] {:color :black}} :black true
    {[0 0] {:color :black}} :white false
    {} :white false
    {} :black false))

; TODO: add tests for async conteroller
