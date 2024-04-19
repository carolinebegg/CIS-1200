(* CIS 120 OCaml testing interface      *)
(* You do not need to modify this file. *)

(* Run a test function, printing the result of the test.  If the
   function returns true, the test passes. If the function returns
   false or throws an error, the test fails. *)
val run_test: string -> (unit -> bool) -> unit

(* Instruct the testing infrastructure to stop the program execution
   on the first failing test. *)
val stop_on_failure: unit -> unit

(* Run a test function, printing the result of the test.  If the
   function returns true or false, the test fails. If the function
   throws an error, the test passes. *)
val run_failing_test: string -> (unit -> bool) -> unit
