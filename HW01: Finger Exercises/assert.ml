(* CIS 120 OCaml testing interface      *)
(* You do not need to modify this file. *)
(* ------------------------------------ *)

open Printf
open List

exception Timeout

(* Make sure that a test case does not run too long. Raises Failure 
   on a time out after t seconds. *)
let run_with_timeout (t:int) (f:'a -> 'b) (x:'a) : 'b =
  try
    Sys.set_signal Sys.sigalrm 
      (Sys.Signal_handle (fun _ -> raise Timeout));
    ignore (Unix.alarm t);
    let y = f x in
    ignore (Unix.alarm 0);
    Sys.set_signal Sys.sigalrm Sys.Signal_default;
    y
  with Timeout -> 
    (Sys.set_signal Sys.sigalrm Sys.Signal_default; 
     raise (Failure ("Timeout: test case ran for " 
                     ^ string_of_int t ^ " seconds.")))

(* The result of a test *)
type result =
  | Succ             (* Test succeeds *)
  | Fail             (* Test fails normally, usually returning false *)
  | Err of string    (* Test throws an error, indicated *)

(* When set to true, stop immediately on a failing test *)
let stop_on_failure_flag = ref false

let stop_on_failure ()     = stop_on_failure_flag := true
let continue_on_failure () = stop_on_failure_flag := false

(* Display a message about a failing test *)
let error_mesg (s: string) : unit =
  print_endline s;
  if !stop_on_failure_flag then exit 1 else ()


(* Run a testing function (actual_fcn) and compare its value with
   an expected value, using OCaml's polymorphic structural equality. *)
let assert_eqf (msg: string) (actual_fcn: unit -> 'a) (expected: 'a) : unit =
  let _ = print_string ("Running: "^msg^" ... ") in
  let _ = flush_all () in
  let outcome = try if expected = run_with_timeout 10 actual_fcn () 
      then Succ else Fail
    with Failure s -> Err s
       | e         -> Err (Printexc.to_string e) in

  begin match outcome with
    | Succ -> print_endline ("Test passed!")
    | Fail ->
      print_newline ();
      error_mesg ("Test failed: "^msg^"\n")
    | Err s ->
      print_newline ();
      let stack = Printexc.get_backtrace () in
      error_mesg ("Test error: `"^msg^"` reported `" ^ s ^ "`\n" ^ stack)
  end

(* Assert that the result of a test (actual) is the same as the expected
   value *)
let assert_eq (msg:string) (actual:'a) (expected:'a) : unit =
  assert_eqf msg (fun () -> actual) expected

(* Run a test function, printing the result of the test.
   If the function returns true, the test passes. If the function
   	returns false or throws an error, the test fails. *)
let run_test (msg:string) (f:unit -> bool) : unit = assert_eqf msg f true

(* Run a test function, printing the result of the test.
   	If the function returns true or false, the test fails. If the function
   	throws an error, the test passes. *)
let run_failing_test (msg:string) (f: unit -> bool) : unit =
  let _ = print_string ("Running: "^msg^" ... ") in
  let _ = flush_all () in
  let result = (try (ignore (f ()) ; Fail) with
      | _ -> Succ) in
  match result with
  | Succ -> print_endline ("Test passed!")
  | Fail -> error_mesg ("Test error: should have failed.")
  | Err s -> error_mesg ("run_failing_test BUG: shouldn't get here.")

