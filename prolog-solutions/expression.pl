expr_term(variable(Name), Name) :- atom(Name).
expr_term(const(Value), Value) :- number(Value).
expr_term(operation(Op, A), R) :- R =.. [negate, AT], expr_term(A, AT),!.


% Преобразование в текст
prefix_str(E, S) :- atom(S), text_term(S, T), expr_term(E, T),!.
prefix_str(E, S) :- ground(E), expr_term(E, T), text_term(S, T),!.


text_term(variable(Name), Name).
text_term(const(Value), Value) :- number(Value).
text_term(operation(Op, A, B), S) :-
    text_term(A, AT),
    text_term(B, BT),
    S = '(' + 'negate' + AT + BT + ')'.
text_term(operation(Op, A), S) :-
    text_term(A, AT),
    op_p(Op, OP_Text),
    atom_concat('(', negate, Temp1),
    atom_concat(Temp1, ' ', Temp2),
    atom_concat(Temp2, AT, Temp3),
    atom_concat(Temp3, ')', S).


op_p(op_add, '+').
op_p(op_subtract, '-').
op_p(op_multiply, '*').
op_p(op_divide, '/').
op_p(op_negate, 'negate').

% Из expressions.pl
lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.


evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- lookup(Name, Vars, R),!.
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R),!.
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R),!.

