(******************************************************************************)
(* PROBLEM 5: WRITING TEST CASES                                              *)
(******************************************************************************)

(* The module `SetTest` defined below is a reuseable component that we'll use
   to test other modules that conform to the `SET` interface. When `SetTest`
   is instantiated with a particular set implementation, it will run all of
   its test cases against the set type defined in that implementation.  This
   means that the _same_ tests can be used for both the OrderedListSet and
   BSTSet implementations -- this makes sense because all implementations of
   `SET` should behave the same!

   Read through the module, then write your test cases in the space provided
   below. Make sure NOT to test for structural equality with sets.  Instead,
   use the equals function specified in the interface.  Your TAs will be
   grading the completeness of your tests. *)

module SetTest (SetImpl: SetInterface.SET) = struct
  ;; open SetImpl

  (* We first redefine the `run_test` and `run_failing_test` functions so that
     they prepend the name of the set we're testing to the test description. *)

  let run_test desc = Assert.run_test (debug_name ^ ": " ^ desc)
  let run_failing_test desc = Assert.run_failing_test (debug_name ^ ": " ^ desc)

  ;; print_endline ("\n--- Running tests for " ^ debug_name ^ " ---")

  (* Here are a couple of test cases to help get you started... *)

  let test () : bool =
    is_empty empty
  ;; run_test "is_empty: call on empty returns true" test

  (* Note that some tests in this test module (such as the one below) may not
     pass until all the functions they depend on are implemented. For
     instance, the test below will fail for sets whose `set_of_list` function
     is not yet implemented (even if `is_empty` is correct).  This is fine:
     the goal here is just to record all the tests that we expect will pass
     when we get around to implementing everything later. *)

  let test () : bool =
    let s = set_of_list [1; 2; 3] in
    not (is_empty s)
  ;; run_test "is_empty: non-empty set returns false" test


(* Now, it's your turn! Make sure to comprehensively test all the other
   functions defined in the `SET` interface. It will probably be helpful to
   have the file `setInterface.ml` open as you work.  Your tests should stress
   the abstract properties of what it means to be a set, as well as the
   relationships among the operations provided by the SET interface.

   One thing to be careful of: your tests should not use `=` to compare sets:
   use the `equals` function instead.

   We strongly advise you to write tests for the functions in the order they
   appear in the interface. Write tests for all of the functions here before
   you start implementing. After the tests are written, you should be able to
   implement the functions one at a time in the same order and see your tests
   incrementally pass.

   Your TAs will be manually grading the completeness of your test cases. *)

  (* ---------- Write your own test cases below. ---------- *)

  let test () : bool = 
   let set = set_of_list [] in
   is_empty set
  ;; run_test "list_of_set: empty set" test

  let test () : bool =
   let set = set_of_list [1; 2; 3; 4] in
   list_of_set set = [1; 2; 3; 4]
  ;; run_test "list_of_set: pre-ordered set (no dupes)" test

  let test () : bool = 
   let set = set_of_list [3; 2; 4; 1] in
   list_of_set set = [1; 2; 3; 4]
   ;; run_test "list_of_set: unordered set (no dupes)"

   let test () : bool =
   let set = set_of_list [1; 2; 3; 3] in
   list_of_set set = [1; 2; 3]
  ;; run_test "list_of_set: pre-ordered set (with dupe)" test

  let test () : bool = 
   let set = set_of_list [3; 2; 4; 3] in
   list_of_set set = [1; 2; 3]
   ;; run_test "list_of_set: unordered set (with dupe)"

   let test () : bool =
   let set = set_of_list [1; 2; 3] in
   add 4 set = set_of_list [1; 2; 3; 4]
  ;; run_test "add: set with no dupes" test

  let test () : bool = 
   let set = set_of_list [1; 2; 3] in
   add 3 set = set_of_list [1; 2; 3]
   ;; run_test "add: set with dupes" test

   let test () : bool = 
   let set = set_of_list [] in
   add 1 set = set_of_list [1]
   ;; run_test "add: empty set" test

   let test () : bool =
   let set = set_of_list [1; 2; 3; 4] in
   remove 4 set = set_of_list [1; 2; 3]
  ;; run_test "remove: set with no dupes" test

  let test () : bool = 
   let set = set_of_list [1; 2; 3] in
   remove 4 set = set_of_list [1; 2; 3]
   ;; run_test "remove: set with dupes" test

   let test () : bool =
   let set = set_of_list [1; 2; 3; 4] in
   member 4 set
  ;; run_test "member: set with item" test

   let test () : bool =
   let set = set_of_list [1; 2; 3; 4] in
   not(member 0 set)
  ;; run_test "member: set with item" test

  let test () : bool = 
   let set = set_of_list [] in
   not(member 4 set)
   ;; run_test "member: empty set without item" test

   let test () : bool = 
   let set = set_of_list [1; 2; 3] in
   size set = 3
   ;; run_test "size: three items" test

   let test () : bool = 
   let set = set_of_list [] in
   size set = 0
   ;; run_test "size: zero items" test

   let test () : bool = 
   let set = set_of_list [1] in
   size set = 1
   ;; run_test "size: one item" test

   let test () : bool = 
   let set = set_of_list [1; 1] in
   size set = 1
   ;; run_test "size: one item (dupe)" test

   let test () : bool = 
   let a = set_of_list [1; 2; 3] in
   let b = set_of_list [1; 2; 3] in 
   equals a b
   ;; run_test "equals: same set, same order" test

   let test () : bool = 
   let a = set_of_list [1; 2; 3] in
   let b = set_of_list [2; 3; 1] in 
   equals a b
   ;; run_test "equals: same set, different order" test

   let test () : bool = 
   let a = set_of_list [1; 2; 3] in
   let b = set_of_list [2; 3; 4] in 
   not(equals a b)
   ;; run_test "equals: different set, same size" test

   let test () : bool = 
   let a = set_of_list [] in
   let b = set_of_list [] in 
   equals a b
   ;; run_test "equals: both empty" test

   let test () : bool = 
   let a = set_of_list [] in
   let b = set_of_list [1; 2; 3] in 
   not(equals a b)
   ;; run_test "equals: one empty set" test

   let test () : bool = 
   equals (add 2 (add 1 empty)) (set_of_list[1; 2])
   ;; run_test "set_of_list: set of two items" test

   let test () : bool = 
   not(equals (add 2 (add 1 empty)) (set_of_list[1; 3]))
   ;; run_test "set_of_list: set of two items (not equal)" test

   let test () : bool = 
   equals (add empty empty) (set_of_list[])
   ;; run_test "set_of_list: empty lists" test


  (* ---------- Write your own test cases above. ---------- *)

end

(* The rest of the file instantiates the above tests so they are
   executed for both OrderedListSet and BSTSet.  Don't modify anything
   below this comment. *)

module TestOrderedListSet = SetTest(ListSet.OrderedListSet)
;; print_newline ()

module TestBSTSet = SetTest(TreeSet.BSTSet)
;; print_newline ()
