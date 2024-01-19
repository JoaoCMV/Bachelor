%estado

%(Linha Jogador, Coluna Jogador, Linha Caixa, Coluna Caixa)

%estado inicial
estado_inicial( ( (7,2) , (6,2) ) ).

%estado final
estado_final( ( (_,_) , (6,3) ) ).

%op(Estado_act,operador,Estado_seg,Custo)

op( ((Lj, Cj) , (Lc, Cc) ) , esquerda, ( (Ljs, Cjs), (Lcs, Ccs) ), 1):- 
                            free(Lj, Cj),
                            Ljs is Lj, Cjs is Cj - 1, lim(Ljs, Cjs),
                            move(Ljs, Cjs, Lc, Cc, Lcs, Ccs, 0, -1),
                            lim(Lcs, Ccs),
                            free(Lcs, Ccs).

op( ((Lj, Cj) , (Lc, Cc) ) , direita, ( (Ljs, Cjs), (Lcs, Ccs) ), 1):- 
                            free(Lj, Cj),
                            Ljs is Lj, Cjs is Cj + 1, lim(Ljs, Cjs),
                            move(Ljs, Cjs, Lc, Cc, Lcs, Ccs, 0, 1),
                            lim(Lcs, Ccs),
                            free(Lcs, Ccs).

op( ((Lj, Cj) , (Lc, Cc) ) , cima, ( (Ljs, Cjs), (Lcs, Ccs) ), 1):- 
                            free(Lj, Cj),
                            Ljs is Lj - 1, Cjs is Cj, lim(Ljs, Cjs),
                            move(Ljs, Cjs, Lc, Cc, Lcs, Ccs, -1, 0),
                            lim(Lcs, Ccs),
                            free(Lcs, Ccs).

op( ((Lj, Cj) , (Lc, Cc) ) , baixo, ( (Ljs, Cjs), (Lcs, Ccs) ), 1):- 
                            free(Lj, Cj),
                            Ljs is Lj + 1, Cjs is Cj, lim(Ljs, Cjs),
                            move(Ljs, Cjs, Lc, Cc, Lcs, Ccs, 1, 0),
                            lim(Lcs, Ccs),
                            free(Lcs, Ccs).

move(L, C, L, C, Lcs, Ccs, X, Y):- Lcs is L + X, Ccs is C + Y, !.
move(L, C, Lc, Cc, Lcs, Ccs, X, Y):- Lcs is Lc, 
                                    Ccs is Cc.

% Limite do tabuleiro
lim(L, C) :- L>0, C>0, L<8, C<8.

% Casa com X
free(L, C) :- member( (L, C) , [ (1,3), (2,1), (2,3), (2,7), (4,4), (5,4), (6,4) ] ),!, fail.
free(L,C).

%heuristica para estimar distancia h(Estado,Valor) 

h(A, B):- h1(A, B).

%h(A, B):- h2(A, B).


% manhattan entre a caixa e a saida

h1( ((_,_), (X,Y)) ,N):- estado_final( ( ( _,_) , (W,Z) ) ),
                modulo_dif(X,W,M),
                modulo_dif(Y,Z,O),
                N is M+O.

modulo_dif(A,B,C):- A>B, C is A-B.
modulo_dif(A,B,C):- C is B-A.


% euclidiana entre a caixa e a saida

h2(((_,_), (X,Y)) ,N):- estado_final( ( ( _,_) , (W,Z) )),
                modulo_dif(X,W,M),
                modulo_dif(Y,Z,O),
                N is round(sqrt(M ** 2 + O ** 2)).

