(ns chess-game.graphics)


;; Note to self:
;;
;; I'm thinking of making this the only
;; platform specific part of the drawing
;; of this game. Most of the other parts
;; will be platform independent, although
;; still written in ClojureScript.

(defn set-fill! [st color]
  (q/fill color))

(defn make-color [red green blue]
  (q/color red green blue))

(defn fill-rect [st x y w h]
  (q/rect x y w h))
