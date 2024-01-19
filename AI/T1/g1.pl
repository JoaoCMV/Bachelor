%estado

%(Linha, Coluna)

%estado inicial
estado_inicial((7,2)).

%estado final
estado_final((3,4)).

%op(Estado_act,operador,Estado_seg,Custo)
op((L,C),esquerda ,(Ls,Cs),1):- 
                            free(L, C),
                            Ls is L + 0, Cs is C - 1, lim(Ls, Cs).
op((L,C),direita ,(Ls,Cs),1):- 
                            free(L, C),
                            Ls is L + 0, Cs is C + 1, lim(Ls, Cs).
op((L,C),cima ,(Ls,Cs),1):- 
                            free(L, C),
                            Ls is L - 1, Cs is C + 0, lim(Ls, Cs).
op((L,C),baixo ,(Ls,Cs),1):- 
                            free(L, C),
                            Ls is L + 1, Cs is C + 0, lim(Ls, Cs).

% Limite do tabuleiro
lim(L, C) :- L>0, C>0, L<8, C<8.

% Casa com X
free(L, C) :- member( (L, C) , [ (1,3), (2,1), (2,3), (2,7), (4,4), (5,4), (6,4) ] ),!, fail.
free(L,C).

%heuristica para estimar distancia h(Estado,Valor) 

h(A, B):- h1(A, B).

%h(A, B):- h2(A, B).


% manhattan

h1((X,Y),N):- estado_final((W,Z)),
                modulo_dif(X,W,M),
                modulo_dif(Y,Z,O),
                N is M+O.

modulo_dif(A,B,C):- A>B, C is A-B.
modulo_dif(A,B,C):- C is B-A.


% euclidiana

h2((X,Y),N):- estado_final((W,Z)),
                modulo_dif(X,W,M),
                modulo_dif(Y,Z,O),
                N is round(sqrt(M ** 2 + O ** 2)).



