(ns chess-game.controllers
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :refer [chan <! >!]]
            [cljs.core.match :refer-macros [match]]
            [chess-game.config :as config]
            [chess-game.board :as b]
            [chess-game.moves :as m]))

(defn get-click-decision
  [color chessboard old-pos new-pos]
  (cond
    (and (= old-pos []) (= color (get-in chessboard [new-pos :color]))) :select
    (= old-pos new-pos) :on-same
    (= color (get-in chessboard [new-pos :color])) :on-same-color
    (not (m/allowed? chessboard old-pos new-pos)) :not-allowd-tile
    (not (nil? (get chessboard new-pos))) :eat
    :else :move))

(defn player-controller
  [state ch color]
  (let [chessboard-ch (chan)]
    (go-loop [old-pos []]
      (let [new-pos (<! chessboard-ch)
            board (:chessboard @state)
            decison (get-click-decision color board old-pos new-pos)
            select! (fn [pos]
                      (swap! state assoc :selected pos)
                      pos)]
        (recur (if (:can-move @state)
                 (condp = decison
                   :select (select! new-pos)
                   :on-same (select! [])
                   :on-same-color (select! new-pos)
                   :not-allowd-tile old-pos
                   ; TODO: separate move and eat
                   :eat (do (swap! state assoc
                                   :chessboard (b/update-board board old-pos new-pos)
                                   :selected new-pos
                                   :can-move false)
                            (>! ch [:ai-turn])
                            new-pos)
                   :move (do (swap! state assoc
                                    :chessboard (b/update-board board old-pos new-pos)
                                    :selected new-pos
                                    :can-move false)
                             (>! ch [:ai-turn])
                             new-pos))
                 old-pos))))
    chessboard-ch))

(defn is-any-ai?
  [chessboard color]
  (->> chessboard
       vals
       (map :color)
       (filter #{color})
       seq))

(defn move-random-ai
  [chessboard color]
  (let [ais (filter #(-> % second :color (= color)) chessboard)
        [pos chessman] (rand-nth ais)
        ais-places (map first ais)
        moves (for [i (range 0 config/board-tiles-x)
                    j (range 0 config/board-tiles-y)
                    :when (not (get ais-places [i j]))]
                [i j])
        place (rand-nth moves)]
    (assoc chessboard
      pos nil
      place chessman)))

(defn ai-controller
  [state ch color]
  (let [ai-ch (chan)]
    (go-loop []
      (<! ai-ch)
      (if (is-any-ai? (:chessboard @state) color)
        (swap! state assoc
               :can-move true
               :chessboard (move-random-ai (:chessboard @state) color))
        (swap! state assoc :win true))
      (recur))
    ai-ch))

(defn init-router!
  [ch state user-color ai-color]
  (let [chessboard-ch (player-controller state ch user-color)
        ai-ch (ai-controller state ch ai-color)]
    (go-loop []
      (match (<! ch)
        [:tile-clicked & pos] (>! chessboard-ch pos)
        [:ai-turn] (>! ai-ch [])
        [& msg] (println "Unknown message" msg))
      (recur))))
