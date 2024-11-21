node(Key, Value, Left, Right).

map_build([], nil) :- !.
map_build([(Key, Value)], node(Key, Value, nil, nil)) :- !.
map_build(List, Tree) :-
    append(LeftList, [(Key, Value) | RightList], List),
    map_build(LeftList, LeftTree),
    map_build(RightList, RightTree),
    Tree = node(Key, Value, LeftTree, RightTree),
    !.

map_get(node(Key, Value, _, _), Key, Value) :- !.
map_get(node(NodeKey, _, Left, _), Key, Value) :-
    Key < NodeKey,
    map_get(Left, Key, Value).
map_get(node(NodeKey, _, _, Right), Key, Value) :-
    Key > NodeKey,
    map_get(Right, Key, Value).

map_lastKey(node(Key, _, _, nil), Key) :- !.
map_lastKey(node(_, _, _, Right), Key) :-
    map_lastKey(Right, Key).

map_lastValue(node(_, Value, _, nil), Value) :- !.
map_lastValue(node(_, _, _, Right), Value) :-
    map_lastValue(Right, Value).

map_lastEntry(node(Key, Value, _, nil), (Key, Value)) :- !.
map_lastEntry(node(_, _, _, Right), Entry) :-
    map_lastEntry(Right, Entry).