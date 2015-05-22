(ns chess-game.dev
  (:require [figwheel.client :as fw]
            [chess-game.core :refer [main]]))

(enable-console-print!)

(main)

(fw/start {:websocket-url "ws://localhost:3449/figwheel-ws"
           :on-jsload (fn[] (main))})
