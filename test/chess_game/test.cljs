(ns chess-game.test
  (:require [cljs.test :refer-macros [run-all-tests]]
            [chess-game.components.chessboard-test]
            [chess-game.components.core-test]
            [chess-game.components.tile-test]
            [chess-game.controllers.ai-test]
            [chess-game.controllers.core-test]
            [chess-game.controllers.player-test]))

(enable-console-print!)

(defn run [] (run-all-tests #"chess-game.*-test"))
