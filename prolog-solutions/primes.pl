prime(N) :-
    integer(N),
    N > 1,
    \+ has_divisor(N, 2).

composite(N) :-
    integer(N),
    N > 1,
    has_divisor(N, 2).

has_divisor(N, D) :-
    D * D =< N,
    (N mod D =:= 0;
    D1 is D + 1,
    has_divisor(N, D1)).

prime_divisors(1, []).
prime_divisors(N, Divisors) :-
    integer(N),
    N > 1,
    find_prime_divisors(N, 2, Divisors).

find_prime_divisors(1, _, []).
find_prime_divisors(N, D, [D | Divisors]) :-
    N > 1,
    N mod D =:= 0,
    N1 is N // D,
    find_prime_divisors(N1, D, Divisors), !.
find_prime_divisors(N, D, Divisors) :-
    N > D,
    next_divisor(D, D1),
    find_prime_divisors(N, D1, Divisors).

next_divisor(D, D1) :-
    D1 is D + 1.

nth_prime(1, 2) :- !.
nth_prime(N, P) :-
    N > 1,
    nth_primer(2, 1, N, P).

nth_primer(Current, Count, N, P) :-
    (   prime(Current)
    ->  (   Count =:= N ->
        P = Current
        ;
        NextCount is Count + 1,
        Next is Current + 1,
        nth_primer(Next, NextCount, N, P))
    ;
    Next is Current + 1,
    nth_primer(Next, Count, N, P)).
