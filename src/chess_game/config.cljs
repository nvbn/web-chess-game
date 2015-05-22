(ns chess-game.config)

(def tile-size 120)

(def board-tiles-x 8)

(def board-tiles-y 8)

(def images {:white {:pawn "/images/white-pawn.png"
                     :king "/images/white-king.png"
                     :bishop "/images/white-bishop.png"
                     :knight "/images/white-knight.png"
                     :rook "/images/white-rook.png"
                     :queen "/images/white-queen.png"}
             :black {:pawn "/images/black-pawn.png"
                     :king "/images/black-king.png"
                     :bishop "/images/black-bishop.png"
                     :knight "/images/black-knight.png"
                     :rook "/images/black-rook.png"
                     :queen "/images/black-queen.png"}})

(def colors {:white {false "rgb(200,200,200)"
                     true "rgb(205,205,255)"}
             :black {false "rgb(50, 50, 50)"
                     true "rgb(50, 50, 100"}})

(def standard-board {[7 6] {:color :white :type :pawn}
                     [7 1] {:color :black :type :pawn}
                     [0 0] {:color :black :type :rook}
                     [7 7] {:color :white :type :rook}
                     [1 0] {:color :black :type :knight}
                     [6 7] {:color :white :type :knight}
                     [0 6] {:color :white :type :pawn}
                     [1 1] {:color :black :type :pawn}
                     [3 0] {:color :black :type :queen}
                     [6 6] {:color :white :type :pawn}
                     [4 7] {:color :white :type :king}
                     [4 1] {:color :black :type :pawn}
                     [4 6] {:color :white :type :pawn}
                     [5 7] {:color :white :type :bishop}
                     [1 7] {:color :white :type :knight}
                     [5 1] {:color :black :type :pawn}
                     [6 1] {:color :black :type :pawn}
                     [5 6] {:color :white :type :pawn}
                     [0 7] {:color :white :type :rook}
                     [2 7] {:color :white :type :bishop}
                     [3 6] {:color :white :type :pawn}
                     [7 0] {:color :black :type :rook}
                     [2 0] {:color :black :type :bishop}
                     [3 1] {:color :black :type :pawn}
                     [2 1] {:color :black :type :pawn}
                     [1 6] {:color :white :type :pawn}
                     [3 7] {:color :white :type :queen}
                     [2 6] {:color :white :type :pawn}
                     [5 0] {:color :black :type :bishop}
                     [6 0] {:color :black :type :knight}
                     [0 1] {:color :black :type :pawn}
                     [4 0] {:color :black :type :king}})
