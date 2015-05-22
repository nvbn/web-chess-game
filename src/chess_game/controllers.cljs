(ns chess-game.controllers
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :refer [chan <! >!]]
            [cljs.core.match :refer-macros [match]]
            [chess-game.board :as b]
            [chess-game.moves :as m]))

(defn get-click-decision
  [color chessboard old-pos new-pos]
  (cond
    (and (= old-pos []) (= color (get-in chessboard [new-pos :color]))) :select
    (= old-pos new-pos) :on-same
    (= color (get-in chessboard [new-pos color])) :on-same-color
    (not (m/allowed? chessboard old-pos new-pos)) :not-allowd-tile
    (not (nil? (get chessboard new-pos))) :eat
    :else :move))

(defn chessboard-controller
  [state ch]
  (let [chessboard-ch (chan)]
    (go-loop [old-pos []]
      (let [[color new-pos] (<! chessboard-ch)
            board (:chessboard @state)
            decison (get-click-decision color board old-pos new-pos)]
        (recur
          (condp = decison
            :select (do (swap! state assoc :selected new-pos) new-pos)
            :on-same (do (swap! state assoc :selected []) [])
            :on-same-color (do (swap! state assoc :selected new-pos) new-pos)
            :not-allowd-tile old-pos
            ; TODO: separate move and eat
            :eat (do (swap! state assoc
                            :chessboard (b/update-board board old-pos new-pos)
                            :selected new-pos)
                     new-pos)
            :move (do (swap! state assoc
                             :chessboard (b/update-board board old-pos new-pos)
                             :selected new-pos)
                      new-pos)))))
    chessboard-ch))

(defn init-router!
  [ch state]
  (let [chessboard-ch (chessboard-controller state ch)]
    (go-loop []
      (match (<! ch)
        [:tile-clicked & pos] (>! chessboard-ch [:white pos])
        [& msg] (println "Unknown message" msg))
      (recur))))
