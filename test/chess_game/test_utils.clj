(ns chess-game.test-utils)

(defmacro with-mock-quil
  "Mocks quil *graphics*."
  [& body]
  `(binding [quil.core/*graphics* (chess-game.test-utils/get-graphics-mock)]
     ~@body))
