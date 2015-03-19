(ns ^:figwheel-always chess-game.core
  (:require [chess-game.config :as config]
            [chess-game.colors :as colors]
            [chess-game.drawing :as drawing]
            [chess-game.environ :as environ]
            [figwheel.client :as fw]
            [monet.canvas :as canvas]
            [jayq.core :as jq]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(fw/start {
           ;; configure a websocket url if you are using your own server
           ;; :websocket-url "ws://localhost:3449/figwheel-ws"

           ;; optional callback
           :on-jsload (fn [] (print "reloaded"))

           ;; The heads up display is enabled by default
           ;; to disable it:
           ;; :heads-up-display false

           ;; when the compiler emits warnings figwheel
           ;; blocks the loading of files.
           ;; To disable this behavior:
           ;; :load-warninged-code true

           ;; if figwheel is watching more than one build
           ;; it can be helpful to specify a build id for
           ;; the client to focus on
           ;; :build-id "example"
           })

(defn draw-square [color]
  (fn [ctx val]
    (-> ctx
        (canvas/fill-style color)
        (canvas/fill-rect val))))

(def draw-white-square (draw-square "#fff"))

(def draw-black-square (draw-square "#000"))

(defonce state (atom {}))

(defn add-canvas-dom [st]
  (assoc st :canvas-dom (.getElementById js/document "canvas")))

(defn resize-canvas [st]
  (set! (.-width (:canvas-dom st)) (jq/width (jq/$ "body")))
  (set! (.-height (:canvas-dom st)) (- (jq/height (jq/$ "body")) 3)))

(defn add-drawing-context [st]
  (assoc st :drawing-context (.getContext (:canvas-dom st) "2d")))

(defn draw [st]

  nil)

;; (drawing/set-fill! (colors/tile-fill-color i j false))

(defn setup []
  (reset! state (-> state
                    add-canvas-dom
                    resize-canvas
                    add-drawing-context
                    drawing/draw-checkered-board
                    ))

  (println "The state is")
  (println @state)

  (canvas/add-entity monet-canvas :background
                     (canvas/entity {:x 0 :y 0 :w config/board-width :h config/board-height}
                                    nil
                                    draw-black-square))

  (add-white-square monet-canvas 0 0)
  (add-white-square monet-canvas 1 1)
  (add-white-square monet-canvas 2 2)
  (add-white-square monet-canvas 3 3)
  (add-white-square monet-canvas 4 4)
  (add-white-square monet-canvas 5 5)

  nil)

(jq/document-ready
  (setup))
