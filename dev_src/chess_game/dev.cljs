(ns chess-game.dev
  (:require [figwheel.client :as fw]
            [chess-game.core]))

(enable-console-print!)

(fw/start {:websocket-url "ws://localhost:3449/figwheel-ws"
           :on-jsload (fn [] (println "reload"))})
