(ns chess-game.components.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.core.async :refer [>! <! timeout]]
            [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :as dom :include-macros true]
            [chess-game.config :as config]
            [chess-game.components.chessboard :refer [chessboard]]))

(defcomponent message
  [msg _]
  (render-state [_ _]
    (dom/text {:x config/tile-size
               :y config/tile-size
               :fill "red"
               :style {:font-size "60pt"}}
              msg)))

(defcomponent sprite
  [{:keys [x y img-width img-height scale sprite-x sprite-y sprite-w sprite-h href]} _]
  (render-state [_ _]
    (let [id (str (gensym))]
      (dom/g (dom/defs {:dangerouslySetInnerHTML {:__html (str "
            <pattern id='" id "'
                     patternUnits='userSpaceOnUse'
                     x='" x "'
                     y='" y "'
                     width='" sprite-w "'
                     height='" sprite-h "'>
              <image x='" (- 0 sprite-x) "'
                     y='" (- 0 sprite-y) "'
                     xlink:href='" href "'
                     width='" img-width "'
                     height='" img-height "'
                     transform='scale(" scale ")' />
            </pattern>")}})
             (dom/rect {:x x :y y :width sprite-w :height sprite-h
                        :fill (str "url(#" id ")")})))))

(defn mario-sprite
  [& opts]
  (om/build sprite (assoc (apply hash-map opts)
                     :img-width 406
                     :img-height 1507
                     :scale 2
                     :sprite-w 40
                     :sprite-h 80
                     :href "/images/mario/mario.png")))
; run - 328/320 354/ 378
; jump - 335/240
; stand - 214/240
; stand-right 174/240
; run-right 60/320 34/ 8/
; jump-right 54/240

(def sprites
  {:right {:run [328 320]
           :run-1 [354 320]
           :run-2 [378 320]
           :jump [335 240]
           :stand [214 240]}
   :left {:run [60 320]
          :run-1 [34 320]
          :run-2 [8 320]
          :jump [54 240]
          :stand [174 240]}})

(defcomponent mario
  [{:keys [x y]} owner]
  (init-state [_] {:state :stand
                   :direction :left})
  (will-mount [_] (go-loop []
                    (let [state (om/get-props owner :state)
                          direction (om/get-props owner :direction)
                          drawing-state (om/get-state owner :state)
                          drawing-direction (om/get-state owner :direction)
                          next-state (if (and (= direction drawing-direction) (= state :run))
                                       (condp = drawing-state
                                         :run :run-1
                                         :run-1 :run-2
                                         :run)
                                       state)]
                      (om/set-state! owner :state next-state)
                      (om/set-state! owner :direction direction))
                    (<! (timeout 100))
                    (recur)))
  (render-state [_ {:keys [state direction]}]
    (let [[sx sy] (get-in sprites [direction state])]
      (dom/g (mario-sprite :x x
                           :y y
                           :sprite-x sx
                           :sprite-y sy)))))

(defn world-ctrl
  [state]
  (swap! state assoc
         :mario-x 100
         :mario-y 300
         :mario-state :stand
         :mario-direction :right)
  (go-loop []
    ; Stand a second
    (swap! state assoc :mario-state :stand)
    (<! (timeout 500))
    ; Jump
    (swap! state assoc :mario-state :jump)
    (dotimes [_ 20]
      (<! (timeout 5))
      (swap! state update-in [:mario-y] dec))
    (dotimes [_ 20]
      (<! (timeout 5))
      (swap! state update-in [:mario-y] inc))
    ; Stand a half
    (swap! state assoc :mario-state :stand)
    (<! (timeout 500))
    ; Go right
    (swap! state assoc :mario-state :run)
    (dotimes [_ 300]
      (<! (timeout 5))
      (swap! state update-in [:mario-x] inc))
    ; Stand a second
    (swap! state assoc :mario-state :stand)
    (<! (timeout 500))
    (swap! state assoc :mario-direction :left)
    (<! (timeout 500))
    ; Jump
    (swap! state assoc :mario-state :jump)
    (dotimes [_ 20]
      (<! (timeout 5))
      (swap! state update-in [:mario-y] dec))
    (dotimes [_ 20]
      (<! (timeout 5))
      (swap! state update-in [:mario-y] inc))
    ;Stand a half
    (swap! state assoc :mario-state :stand)
    (<! (timeout 500))
    ; Go back
    (swap! state assoc :mario-state :run)
    (dotimes [_ 300]
      (<! (timeout 5))
      (swap! state update-in [:mario-x] dec))
    (swap! state assoc :mario-direction :right)
    (<! (timeout 500))
    (recur))
  )

(defcomponent surface
  [props _]
  (render-state [_ _]
    (dom/svg {:width (* config/tile-size config/board-tiles-x)
              :height (* config/tile-size config/board-tiles-y)}
             (om/build chessboard props)
             (om/build mario {:state (:mario-state props)
                              :direction (:mario-direction props)
                              :x (:mario-x props)
                              :y (:mario-y props)})
             (when (:win props)
               (om/build message "You're win!")))))

(defn init-components!
  [state target]
  (world-ctrl state)
  (om/root surface state {:target target}))
