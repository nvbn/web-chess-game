(ns chess-game.controllers.player
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :refer [chan <! >!]]
            [cljs.core.match :refer-macros [match]]))

(defn move-allowed?
  [chessboard old-pos new-pos]
  "Returns true when move allowed"
  ; TODO: perform reall check
  (let [old-chessman (get chessboard old-pos)
        new-chessman (get chessboard new-pos)]
    (boolean (and old-chessman old-pos new-pos
                  (or (nil? new-chessman)
                      (not= (:color old-chessman)
                            (:color new-chessman)))))))

(defn get-click-decision
  [color chessboard old-pos new-pos]
  (cond
    (and (= old-pos []) (= color (get-in chessboard [new-pos :color]))) :select
    (= old-pos new-pos) :on-same
    (= color (get-in chessboard [new-pos :color])) :on-same-color
    (not (move-allowed? chessboard old-pos new-pos)) :not-allowd-tile
    (not (nil? (get chessboard new-pos))) :eat
    :else :move))

(defn update-board
  [chessboard old-pos new-pos]
  (assoc chessboard
    old-pos nil
    new-pos (get chessboard old-pos)))

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
                                   :chessboard (update-board board old-pos new-pos)
                                   :selected new-pos
                                   :can-move false)
                            (>! ch [:ai-turn])
                            new-pos)
                   :move (do (swap! state assoc
                                    :chessboard (update-board board old-pos new-pos)
                                    :selected new-pos
                                    :can-move false)
                             (>! ch [:ai-turn])
                             new-pos))
                 old-pos))))
    chessboard-ch))