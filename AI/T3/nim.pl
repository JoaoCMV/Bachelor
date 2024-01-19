% tres molhos de pauzinhos
estado_inicial(e(1,3,2)).

% ficar sem pauzinhos
terminal(e(0,0,0)).
  
% op1(E, Jogada, Es)
% E  -> Estado Atual
% Es -> Próximo estado
op1(e(N1,N2,N3),retiraMolho1(N),e(N11,N2,N3)) :-  num(1,N), N11 is N1 - N, N11 >= 0.

op1(e(N1,N2,N3),retiraMolho2(N),e(N1,N22,N3)) :-  num(1,N), N22 is N2 - N, N22 >= 0.

op1(e(N1,N2,N3),retiraMolho3(N),e(N1,N2,N33)) :-  num(1,N), N33 is N3 - N, N33 >= 0.


num(N,N).
num(L,N1) :- max(M), L<M, L1 is L+1, num(L1,N1).

max(5).

valor(E,V,P):-terminal(E),
              X is P mod 2,
              (X== 1,V=1;X==0,V= -1).