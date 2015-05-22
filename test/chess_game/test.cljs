(ns chess-game.test
  (:require [cljs.test :refer-macros [run-all-tests]]
            [chess-game.board-test]
            [chess-game.drawing-test]
            [chess-game.images-test]
            [chess-game.moves-test]))

(enable-console-print!)

(defn run [] (run-all-tests #"chess-game.*-test"))
