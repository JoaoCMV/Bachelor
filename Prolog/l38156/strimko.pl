
:- use_module(library(clpfd)).

% go/1, recebe o ficheiro de input e separa as linhas de input
% de seguida inicia o predicado jogo/4 para montar o tabuleiro final
go(File):-
    read_lines(File, Lines),
    format(Lines, Rows, MapStreams, N, Inserts ),
    write('N -> '), write(N),write('\n'),
    write('MapStreams -> '), write(MapStreams),write('\n'),
    write('Inserts -> '), write(Inserts),write('\n'),
    jogo(Rows, MapStreams,N, Inserts).


% ---------------- INPUT --------------
read_lines(File, LinesF) :-
    seeing(OldStream), see(File),
    read_charlist(Chars), splitLines(Chars, Lines), setN(Chars, LinesF, Lines),
    see(OldStream).

read_charlist(L) :-
    get_char(X),
    (
        X == ' ', read_charlist(L), ! ;
        X == '\n', read_charlist(L), ! ;
        X == end_of_file, L = [], ! ;
        atom_number(X, X1),                 % visto o input restante ser so numeros podemos logo converter
        L = [X1|R], read_charlist(R), !
    ).


parse_charlist(T-T,[]) :- !.
parse_charlist(X1-X4,[L|Ls]) :-
   parse_line(X1-X2,L),
   parse_eol(X2-X3), !,
   parse_charlist(X3-X4,Ls).

parse_eol([]-[]) :- !.           % no end-of-line at end-of-file
parse_eol(['\r','\n'|R]-R) :- !. % DOS/Windows
parse_eol(['\n','\r'|R]-R) :- !. % Mac (?)
parse_eol(['\r'|R]-R) :- !.      % Mac (?)
parse_eol(['\n'|R]-R).           % UNIX

parse_line([]-[],[]) :- !.       % no end-of-line at end-of-file
parse_line([X|X1]-[X|X1],[]) :- eol_char(X), !.
parse_line([X|X1]-X2,[X|Xs]) :- \+ eol_char(X), parse_line(X1-X2,Xs).

eol_char('\r').
eol_char('\n').

% Guarda a primeira linha de input nas linhas Finais de input
setN([H|_], LinesF, Lines):-
    LinesF = [H|Lines].

% Divide as linhas do ficheiro 
splitLines([H|Chars], Lines):-
    splitLines(Chars, Lines, H, 0).   % N indica o numero de linhas que são para a matriz stream

splitLines(Chars, Lines, N, N):-
    Lines = [L1|Ln],
    % Depois de percorrer todas as linhas relativas á matriz as restantes 
    % teem 3 numeros por linhas, assim retira os 3 primeiros elementos da lista
    split(Chars, 3, L1, L2),
    splitEndLines(L2, Ln).           



splitLines(Chars, Lines, Nf, Ni):-
    N1 is Ni+1,
    Lines = [L1|Ln],
    % Percorre as N linhas para encontrar A matriz de Streams
    % Retirando os N primeiros elementos dessas linhas
    split(Chars, 4, L1, L2),            % L1-> lista com N elementos, L2 -> resto da lista inicial
    splitLines(L2, Ln, Nf, N1).

% Igual ao método anterior mas desta vez com N=3 por ser as linhas finais     
splitEndLines([],Lines):-!, Lines = [].
splitEndLines(Chars, Lines):-
    Lines = [L1|Ln],
    split(Chars, 3, L1, L2),
    splitEndLines(L2, Ln).

% Método auxiliar para dividir a lista pelos N primeiros elementos
split(L,0,[],L).
split([X|Xs],N,[X|Ys],Zs) :- N > 0, N1 is N - 1, split(Xs,N1,Ys,Zs).
    
% Formata as linhas para o input do jogo
format([H|Lines], Rows, MapStreams, H, Inserts):-
    split(Lines, H, MapStreams, Inserts),
    buildGame(H, Rows, 0).

% Constroi o 'tabuleiro' de jogo
% Criando N Rows, e N Columns por cada Row
% Todos os elemento sem estarem definidos
buildGame(N, Rows, Ni):-
    Ni < N, !,
    N1 is Ni+1,
    Rows = [Columns|R2],
    buildRows(N, Columns, 0),
    buildGame(N, R2,N1).

buildGame(N,Rows,N):-!,Rows = [].
    
buildRows(N,Rows, Ni):-
    Ni < N, !,
    N1 is Ni+1,
    Rows = [_|R],
    buildRows(N,R,N1).

buildRows(N,Rows,N):-!,Rows = [].

% -------------------- CONSTRUÇÃO DE FIM DE JOGO ----------------------

% Rows -> sao definidos pelo input onde já existe numeros
% MapStream -> definido pelo input a que stream corresponde cada posição
% n -> numero de colunasxlinhas
% Inserts -> numeros inseridos no txt
jogo(Rows, MapStreams,N, Inserts) :-
        % Insere um valor na linhas de 1 a N, conforme o numero de input
        append(Rows, Vs), Vs ins 1..N,
        % Certifica que todos os valores das linhas são diferentes
        maplist(all_distinct, Rows),
        % Transpoe as linhas para as tornar em colunas
        transpose(Rows, Columns),     
        % Certifica que todas as colunas também são diferentes
        maplist(all_distinct, Columns),  
        % Tenta as possiveis soluções para o problema  
        maplist(label, Rows),
        %Verifica se os inserts estam corretos
        checkInserts(Rows, Inserts,N,0),
        % Junta as linhas numa só lista
        % As tentativas de soluçao e o mapa de streams
        flatten(Rows, AllRows),
        flatten(MapStreams, AllStreams),
        % Dada a lista de ambas verifica se as streams estão certas
        % Correndo o mapa de streams e verificando, quando a stream desejada aparece
        % Se os numeros não se repetem
        check(AllRows, AllStreams, [], N, 0),
        p(AllRows, N, 0).

% Inicialmente usa algo semelhante ao fatorial
% Sabendo o NF(n_final), percorre todas as streams de 1 a NF enquanto N < NF
check(AllRows, AllStreams, Teste, NF, N):-
    N < NF, !,
    N1 is N+1,
    % Verifica a lista de numeros criada correspondente á stream N1
    check(AllRows, AllStreams, Teste, N1),
    check(AllRows, AllStreams, Teste, NF, N1).

check(_, _, _, NF, NF).


% Se o valor no mapa de streams corresponder á stream desejada o valor 
% colocado na matriz solução é gurdado na lista Teste
% De seguida passa para o proximo elemento(Cauda) da lista e continua a procurar
check([R|AllRows], [M|AllStreams], Teste, N):-
    M = N,! , check(AllRows, AllStreams, [R|Teste], N).

% Se o valor no mapa de streams não corresponder á stream desejada 
% ignora os valores e passa ao próximo elemento
check([_|AllRows], [_|AllStreams], Teste, N):-
    check(AllRows, AllStreams, Teste, N).

% Quando chegar ao final de ambas as listas(ambas serem vazias)
% verifica se os numeros da lista Teste (A lista com os numeros que correspondem
% á stream a ser verificada), são todos diferentes
check([],[],Teste, _):-
    all_distinct(Teste).

% verifica se na linha e coluna está o insert com o numero certo
checkInserts([], _, _, _).
checkInserts([H|Rows], Insert, Nf, Ni):-
    Ni < Nf,!,
    N1 is Ni+1,
    insert(H,Insert, N1),
    checkInserts(Rows, Insert, Nf, N1).

%Insere no 'tabuleiro' criado os numeros conhecidos
insert(R, [[L,C,X]|Insert], N):-
    % Se a linha for a correta...
    L = N, !,checknum(R,C,1,X),
    insert(R, Insert, N).

insert(_, [], _).
insert(R, [[_,_,_]|Insert], N):- insert(R, Insert, N).

% Se a coluna for a certa e o X também então esta correto
checknum([X|_],C,C,X).

checknum([_|R], C, Ci, X):-
    Ci < C, !, C1 is Ci+1, checknum(R,C,C1,X).

% Print do tabuleiro final
p(T, N, N):-!,
    write('\n'),
    p(T,N,0).

p([],_,_):-!,
    write('\n').

p([H|T], N, Ni):-
    N1 is Ni+1,
    write(H), write(' '),
    p(T, N, N1).