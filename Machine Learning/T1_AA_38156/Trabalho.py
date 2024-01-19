#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Dec 17 22:46:33 2021

@author: joaoverdilheiro
"""

import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split

# Lista com o numero de cada argumento
l_arg = []

# Lista de nos da arvore
arvore = []

# No da árvore


class No:

    def __init__(self, valor, ramo, indice, antecessor, profundidade, exemplos):
        self.valor = valor              # Etiqueta
        self.indice = indice            # Valor do indice calculado
        self.antecessor = antecessor    # Nó antecessor
        self.ramo = ramo                # Ramo conecta ao predecessor
        self.predecessor = []
        self.profundidade = profundidade
        self.exemplos = exemplos
        self.sinal = None               # No caso de ser o ultimo terá o sinal associado

    def getValor(self):
        return self.valor

    def setPredecessor(self):
        self.antecessor.predecessor.append(self)

    def setSinal(self, s):
        self.sinal = s

# Altera a lista de argumentos da tabela em questão


def setArgs(d):

    for col in d:
        l_argumentos = d[col].unique()
        l_arg.append(l_argumentos)


# Calcula as diferentes ocurrencias de cada valor/classe
# recebe uma tabela para que possa calcular
def get_Plist(d, poda):
    # iterador
    i = 0
    # lista de dados para calcular a impureza
    l_dataI = []
    l_dataI_2 = []

    # Percorre todos os argumentos para preparar os dados para calcular a impureza [x+, y-]
    for col in d:
        if(col != 'classe'):
            for l in l_arg[i]:
                # Com o shape verifica o numero de linhas com a condição dada
                pM = d[(d[col] == l) & (d['classe'] == '+')
                       ].value_counts().shape[0]
                pm = d[(d[col] == l) & (d['classe'] == '-')
                       ].value_counts().shape[0]

                if((pM > poda) & (poda > -1)):
                    pM = poda

                if((pm > poda) & (poda > -1)):
                    pm = poda
                l_dataI.append([pM, pm])
            i = i + 1
            l_dataI_2.append(l_dataI)
            l_dataI = []

    return l_dataI_2

# Calcula a impureza segundo Guini
# Utiliza a lista de argumentos possiveis para conseguir avaliar
# Recebe a lista criada ao analizar valor/classe


def getGuini(l_dI):

    # Calcula Impureza - Guini

    r_Guini = []

    for l in l_dI:

        p_total = 0
        ig_f = []

        # Procura todos os argumentos válidos para calcular o total de cada um
        for k in l:
            p_total = p_total + k[0] + k[1]

        # Sabendo o total basta calcular o indice com os casos positivos e negativos
        for j in l:

            p_Di = j[0] + j[1]
            ig = (j[0] + j[1]) / p_total
            ig = ig * 2
            ig = round(ig * ((j[0] / p_Di) * (j[1] / p_Di)), 3)
            ig_f.append(ig)

        r_Guini.append(ig_f)

    return r_Guini

# Calcula a impureza segundo entropia
# Utiliza a lista de argumentos possiveis para conseguir avaliar
# Recebe a lista criada ao analizar valor/classe


def getEntropia(l_dI):

    # Calcula Impureza - Entropia
    i = 0       # iterador
    y = 0       # iteradores argumentos
    w = 0

    r_Entropia = []

    for l in l_arg:

        p_total = 0
        ig_f = []
        # Procura todos os argumentos válidos para calcular o total de cada um
        while(y < len(l) + w):
            p_total = l_dI[y][0] + l_dI[y][1] + p_total
            y = y + 1
        w = y
        # Sabendo o total basta calcular o indice com os casos positivos e negativos
        for l2 in l:

            p_Di = l_dI[i][0] + l_dI[i][1]
            ig = p_Di / p_total

            # Verifica primeiro se algum argumento é zero para que não dê erro
            # np.log2(0) * 0 = NaN
            # parentesis 1
            if(l_dI[i][0] == 0):
                parentesis_1 = 0
            else:
                parentesis_1 = -1 * (l_dI[i][0] / p_Di) * \
                    np.log2(l_dI[i][0] / p_Di)

            # parentesis 2
            if(l_dI[i][1] == 0):
                parentesis_2 = 0
            else:
                parentesis_2 = (l_dI[i][1] / p_Di) * np.log2(l_dI[i][1] / p_Di)

            ig = round(ig * (parentesis_1 - parentesis_2), 3)
            ig_f.append(ig)
            i = i + 1

        r_Entropia.append(ig_f)

    return r_Entropia

# d -> data
# p -> nó antecessor
# i -> guini ou entropia


def fit_2(d, p, i, poda):
    
    # coloca os atributos da tabela na lista para puder analisar esta tabela em especifico
    # limpa a lista de argumentos primeiro
    l_arg.clear()
    setArgs(d)

    # Calcula a impureza total
    vf = 0
    val = []

    # Remove o argumento relativo á classe pois não é necessário para comparação
    l_arg.pop(-1)

    # Calcula quantos exemplos existe de cada caso
    p_list = get_Plist(d, poda)
    # Calcula a impureza com indice de guini
    if(i == 'guini'):

        # Com os valores devolvidos calcula o indice de Guini
        lista_r = getGuini(p_list)

        # Percorre todas os atributos para calcular a sua impureza
        for r in lista_r:
            vf = 0
            for v in r:
                vf = v + vf
            val.append(vf)

        # procura a coluna a que corresponde o atributo que deve ser utilizado
        ic = val.index(min(val))

    # calcula a impureza com entropia
    else:
        # Com os valores devolvidos calcula a entropia
        lista_r = getEntropia(p_list)

        # Percorre todas os atributos para calcular a sua impureza
        for r in lista_r:
            vf = 0
            for v in r:
                vf = v + vf
            val.append(vf)

        # procura a coluna a que corresponde o atributo que deve ser utilizado
        ic = val.index(min(val))

    # lista de tabelas que vai ser analizada
    l_tab = []

    # Coluna que será removida
    coluna = d.columns[ic]

    # Divide pelas tabelas necessárias para continuar a avaliação
    # Começa por criar uma tabela nova para cada valor, guardando no array de tabelas
    for a in l_arg[ic]:
        # Divide a tabela pelos diferentes valores possiveis
        l_tab_temp = (d[(d[d.columns[ic]] == a)])
        # Adiciona a tabela partida e o respetivo valor utilizado
        l_tab.append([l_tab_temp.drop(columns=[coluna]), a])

    y = 0
    while(y < len(l_tab)):
        l_tab[y].append(lista_r[ic][y])
        l_tab[y].append(p_list[ic][y])
        y = y + 1

    # iterador para saber o caminho escolhido (ramo)
    for l in l_tab:
        if(l[0].shape[1] > 1):
            n = No(coluna, l[1], l[2], p, data.shape[1] -
                   l[0].shape[1] - 1, l[3])
            n.setPredecessor()
            if(l[3][0] == 0):
                n.setSinal('-')
            elif(l[3][1] == 0):
                n.setSinal('+')
            else:
                fit_2(l[0], n, i, poda)
        else:
            n = No(coluna, l[1], l[2], p, data.shape[1] -
                   l[0].shape[1] - 1, l[3])
            n.setSinal('+')
            n.setPredecessor()
    return 0

# constroi a arvore


def build(r, limprofundidade):
    print(r.valor)
    # caso exista limite de profundidade e este seja atingido
    if((limprofundidade > -1) & (r.profundidade == limprofundidade)):
        print("Profundidade limite atingida... " + str(r.profundidade))

    else:
        print(" | \n" + r.valor + "\n |")
        for p in r.predecessor:
            print(p.valor)
            print(p.ramo)
            print(p.indice)
            print(p.profundidade)
            print(p.exemplos)
            print(p.sinal)
            build(p, limprofundidade)


# Por padrão não irá aplicar poda e ira aplicar o indice de Guini
class DecisionTree:

    def __init__(self, data, alvo):
        self.data = data
        self.alvo = alvo
        self.impureza = "guini"
        self.poda = -1
        self.limprofundidade = -1

        # Adiciona uma coluna vazia (classe) para que possa ser tratada posteriormente
        self.data['classe'] = ""

        # Itera as linhas para que possa ser devidamente alterado o valor da coluna classe
        for index, row in self.data.iterrows():
            if(row[alvo] == 'no'):
                row['classe'] = '-'
            else:
                row['classe'] = '+'

        self.n = No(" ", " ", -1, " ", 0, None)
        #data_2 = data.drop(alvo, axis = 1)
        # print(data_2.head)

        # criar conjs de treino e teste
        X = data.drop(alvo, axis=1)
        y = data.contact_lenses

        X_train, X_test, y_train, y_test = train_test_split(
            X, y, test_size=0.25, random_state=3)
        print("\nX_train:\n")
        print(X_train.shape)
        print(X_train.head())
        print("\nX_test:\n")
        print(X_test.shape)
        print(X_test.head())
        print("prever")

        self.X_train = X_train
        self.X_test = X_test

    def fit(self):

        fit_2(self.X_train, self.n, self.impureza, self.poda)
        build(self.n, self.limprofundidade)

    def setImpureza(self, i):
        if ((i == "guini") | (i == "entropia")):
            print("Metodo de calculo de impureza alterado com sucesso.\n")
            self.impureza = i
        else:
            print("Método não suportado\n")

    # Aplica o algoritmo de prepoda limitando os exemplos
    def setLimExemplos(self, poda):
        self.poda = poda

    def setLimprofundidade(self, p):
        self.limprofundidade = p

    def predict(self):
        
        pos = 0
        neg = 0
        r = self.n
        for index, row in self.X_test.iterrows():
            while(True):              
                y = 0

                # Enquanto a lista de predecessores tiver proximo
                while(y < len(r.predecessor)):

                    # Verifica se o valor de row é igual ao do no
                    if(row[r.predecessor[y].valor] == r.predecessor[y].ramo):
                        if(r.predecessor[y].sinal == '+'):
                            
                            r = self.n
                            pos = pos + 1
                            break
                        elif(r.predecessor[y].sinal == '-'):
                            r = self.n
                            neg = neg + 1
                            break
                        else:
                            r = r.predecessor[y]
                            y = -1
                           
                    y = y + 1
                break

            # if(self.n.predecessor[0].valor == row[r])
        return pos/(pos+neg)


# ler ficheiro csv
data = pd.read_csv('contact-lenses_or.csv')
print("---> DOCUMENTO CSV, info..")
print("Linhas, colunas")
print(data.shape)
print("Atributos")
print(data.columns)
print()

# Classe base para avaliar os dados
alvo = 'astigmatism'

# por default será aplicado o indice de guini no calculo de impureza
dt = DecisionTree(data, alvo)
# dt.setImpureza("entropia")
# dt.setLimExemplos(2)
# dt.setLimprofundidade(2)
dt.fit()
print (dt.predict() )
