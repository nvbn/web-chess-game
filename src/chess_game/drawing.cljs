
(ns ^:figwheel-always chess-game.drawing
    (:require
     [jayq.core :as jq]
     [monet.canvas :as canvas]
     ))

(defn set-fill! [ctx color]
  (canvas/fill-style ctx color))

(defn make-color [red green blue]
  (str "#" (.toString red 16) (.toString green 16) (.toString blue 16)))
