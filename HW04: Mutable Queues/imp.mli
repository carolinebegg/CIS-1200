(* This file is not needed for this assignment.  We would have omitted
   it entirely, but if we did, Codio would helpfully fill it back in
   for us.  :-|  You can ignore it. *)

type state = {mutable count: int}

val transform: ('a -> 'b) -> 'a list -> 'b list

val fold: ('a -> 'b -> 'b) -> 'b -> 'a list -> 'b

val assoc : 'k -> ('k *'v) list -> 'v option

val join_option : 'a option option -> 'a option

val cat_option : 'a option list -> 'a list

val partial_transform : ('a -> 'b option) -> 'a list -> 'b list

val iter : ('a -> unit) -> 'a list -> unit

val state_incr : state -> int

val swap : state -> state -> unit

val states_aliased : state -> state -> bool

val states_aliased : state -> state -> bool

val contains_alias : 'a -> 'a list -> bool

val equality_test_results : unit -> bool list

val contains_alias_option : 'a -> 'a option list -> bool
