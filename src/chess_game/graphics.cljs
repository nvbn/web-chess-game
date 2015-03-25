(ns chess-game.graphics
  (:require [quil.core :as q]))

;; Note to self:
;;
;; I'm thinking of making these functions
;; the only platform specific part of the
;; drawing of this game.
;;

(defn set-fill! [color]
  (q/fill color))

(defn make-color [red green blue]
  (q/color red green blue))

(defn fill-rect [x y w h]
  (q/rect x y w h))
