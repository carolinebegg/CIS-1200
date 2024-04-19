(* Do NOT modify this file.

   The `SET` interface is defined in more detail in setInterface.ml. You should
   read that file for descriptions of each of these values and functions. *)

module type SET = sig
  type 'a set
  val empty : 'a set
  val is_empty : 'a set -> bool
  val list_of_set : 'a set -> 'a list
  val add : 'a -> 'a set -> 'a set
  val remove : 'a -> 'a set -> 'a set
  val member : 'a -> 'a set -> bool
  val size : 'a set -> int
  val equals : 'a set -> 'a set -> bool
  val set_of_list : 'a list -> 'a set
  val debug_name: string
end

