import requests
import json

def menu():
    print("1 ->  Adicionar Evento")
    print("2 ->  Lista de Eventos numa Data")
    print("3 ->  Adicionar Participante")
    print("4 ->  Lista de Participantes")
    print("5 ->  Classificação Final")
    print("6 ->  Classificação Parcial")
    print("7 ->  Passagens na Etapa")
    print("------------------------------------")
    print("0 ->  Sair")
    print("30 -> Lista de todos os Eventos")
    print("31 -> Lista de todos os Participantes")

api_root_url='http://localhost:8080/sd/'


menu()
op = int(input("Selecione uma opção..."))

while op != 0:
    
    # ADICIONA EVENTO
    if op == 1:

        n = input("Nome do Evento ->")
        d = input("Data (aaaa/mm/dd) ->") 
        response = requests.post(api_root_url+'addE', 
                            params = {'nome': n, 'data': d} )
        # É devolvido apenas uma String e não um json
        print(response.text)

    # LISTA DE EVENTOS NA DATA PRETENDIDA
    elif op == 2:

        d = input("Data (aaaa/mm/dd) ->") 

        response = requests.post(api_root_url+'allE', 
                        params = {'data': d} )
        x = response.json()

        # Devolve o nome de cada participante
        for e in x:
            print("Evento " + e.get('nome'))
            print("Data: " + e.get('date') + "\n")


    # ADICIONA PARTICIPANTE
    elif op == 3:

        n = input("Nome do Participante ->")
        g = input("Genero (f/m) ->")
        i = input("Escalao ->")
        ne = input("Nome do evento ->")

        response = requests.post(api_root_url+'addP', 
                        params = {'nome': n, 'genero': g, 'escalao':i,
                                    'nome_Evento':ne} )
        
        print(response.text)

    # LISTA DE PARTICIPANTES NO EVENTO PRETENDIDO
    elif op == 4:

        ne = input("Nome do evento ->")

        response = requests.post(api_root_url+'allIns', 
                        params = {'nome': ne} )
        x = response.json()

        # Devolve o nome de cada participante
        for e in x:
            print("Participante: " + e.get('name') + ", dorsal: " + str( e.get('dorsal') ) + "\n")


    # CLASSIFICAÇÃO FINAL
    elif op ==5:

        ne = input("Nome do evento ->")

        response = requests.post(api_root_url+'Rank', 
                        params = {'nome': ne, 'etapa':'finish'} )
        x = response.json()

        # Devolve o nome de cada participante
        for e in x:
            print(e)

    # CLASSIFICAÇÃO PARCIAL
    elif op ==6:

        ne = input("Nome do evento ->")
        e = input("Etapa ->")

        response = requests.post(api_root_url+'Rank', 
                        params = {'nome': ne, 'etapa':e} )
        x = response.json()

        # Devolve o nome de cada participante
        for e in x:
            print(e)

    # Participantes que passaram em Pi
    elif op ==7:

        ne = input("Nome do evento ->")
        e = input("Etapa ->")

        response = requests.post(api_root_url+'Passou', 
                        params = {'nome': ne, 'etapa':e} )
        x = response.json()

        print(x)

    # LISTA DE TODOS OS EVENTOS    
    elif op == 30:

        # Pesquisa todos os eventos
        response = requests.get(api_root_url+'allE')
        x = response.json()

        # Devolve o nome e data de cada um 
        for e in x:
            print("Evento " + e.get('nome'))
            print("Data: " + e.get('date') + "\n")

    # LISTA DE TODOS OS PARTICIPANTES   
    elif op == 31:

        # Pesquisa todos os eventos
        response = requests.get(api_root_url+'allP')
        x = response.json()

        # Devolve o nome e data de cada um 
        for e in x:
            print("Nome " + e.get('name'))
            print("Chip: " + str( e.get('chipId') ) + "\n")


    # SE NENHUMA OP VALIDA FOR COLOCADA
    else:
        print("Operação inválida!")

    print()
    menu()
    op = int(input("Selecione uma opção..."))
